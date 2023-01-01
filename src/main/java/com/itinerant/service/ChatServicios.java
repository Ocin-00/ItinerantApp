package com.itinerant.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ChatDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Chat;
import com.itinerant.entity.ChatMensaje;
import com.itinerant.entity.UsuarioInterno;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatServicios {
	private ChatDAO chatDAO;
	private UsuarioInternoDAO usuarioInternoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private MensajeServicios mensajeServicios;

	public ChatServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		chatDAO = new ChatDAO(entityManager);
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		this.request = request;
		this.response = response;
		mensajeServicios  = new MensajeServicios(entityManager, request, response);
		
	}
	
	public void listarChats() throws ServletException, IOException {
		
		String login = (String) request.getSession().getAttribute("userLogin");
		//UsuarioInterno usuario = usuarioInternoDAO.get(login);
		HashMap<String, List<Chat>> chats = (HashMap<String, List<Chat>>) request.getSession().getServletContext().getAttribute("chats");
		List<Chat> misChats = chats.get(login);
		getUsuariosChats(login, misChats, chats);
	}

	public void getUsuariosChats(String login, List<Chat> misChats, HashMap<String, List<Chat>> chats) throws IOException{
		List<UsuarioInterno> usuariosChats = new ArrayList<>();
		JSONArray jsArray = new JSONArray();
		if(misChats != null && misChats.size() >0) {
			
			request.setAttribute("usuariosChats", usuariosChats);
			
			for(Chat chat: misChats) {
				UsuarioInterno usuario;
				if(chat.getUsuarioInternoByIdRecipient().getLogin().equals(login)) {
					usuario = chat.getUsuarioInternoByIdSender();
				} else {
					usuario = chat.getUsuarioInternoByIdRecipient();
				}
				JSONObject jobj = new JSONObject();
				jobj.put("idChat", chat.getIdChat());
			    jobj.put("login", usuario.getLogin());
			    jobj.put("nombre", usuario.getNombre());
			    jobj.put("apellidos", usuario.getApellidos());
			    jobj.put("foto_perfil", usuario.getFotoPerfil());
			    jobj.put("email", usuario.getEmail());
			    jobj.put("nuevo", chat.getNuevo());
			    jobj.put("mensajes_pendientes", mensajeServicios.mensajesPendientes(chat));
			    jobj.put("mensajes_chat",  mensajeServicios.getNumeroMensajesChat(chat));
			    jobj.put("sender_id", chat.getUsuarioInternoByIdSender().getLogin());
			    jobj.toString();
			    jsArray.put(jobj);
			    
			    if(chat.getUsuarioInternoByIdRecipient().getLogin().equals(login) && chat.getNuevo() && mensajeServicios.getNumeroMensajesChat(chat) > 0) {
			    	chat.setNuevo(false);
			    	chatDAO.update(chat);
			    	actualizarChatEnLista(login, chat, misChats, chats);
			    }
			}

		}
		
        response.getWriter().write(jsArray.toString());
		
	}
	
	public void actualizarChatEnLista(String senderLogin, Chat chat, List<Chat> misChats, HashMap<String, List<Chat>> chats) {
		boolean encontrado = false;
    	for(int i = 0; !encontrado && i < misChats.size(); i++) {
			if(chat.getIdChat() == misChats.get(i).getIdChat()) {
				encontrado = true;
				misChats.set(i, chat);
			}
		}
    	chats.put(senderLogin, misChats);
		request.getSession().getServletContext().setAttribute("chats", chats);
	}

	public void inicializarChat() throws ServletException, IOException {
		String recipient = request.getParameter("usuario");
		String id = request.getParameter("id");
		if(recipient != null) {
			inicializarChat(recipient, id);
		} else {
			String listpage = "../frontend/chat.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
			requestDispatcher.forward(request, response);
		}
	}
	
	public void inicializarChat(String recipient, String id) throws ServletException, IOException {
		
		UsuarioInterno usuario = usuarioInternoDAO.get(recipient);
		request.setAttribute("recipient", usuario);
		request.setAttribute("idChat", id);
		
		String listpage = "../frontend/chat.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void contactar() throws ServletException, IOException {
		String idRecipient = request.getParameter("id");
		String login = (String) request.getSession().getAttribute("userLogin");		
		
		Chat chat = chatDAO.findBySenderAndRecipient(login, idRecipient);
		
		if(chat == null) {
			UsuarioInterno recipient = usuarioInternoDAO.get(idRecipient);
			UsuarioInterno sender = usuarioInternoDAO.get(login);
			
			chat = new Chat(recipient, sender);
			chatDAO.create(chat);
			
			HashMap<String, List<Chat>> chats = (HashMap<String, List<Chat>>) request.getSession().getServletContext().getAttribute("chats");
			List<Chat> misChats = chats.get(login);
			List<Chat> susChats = chats.get(idRecipient);
			
			misChats.add(chat);
			susChats.add(chat);
			chats.put(login, misChats);
			chats.put(idRecipient, susChats);
			request.getSession().getServletContext().setAttribute("chats", chats);
		}
	
		mensajeServicios.inicializarMensajes(chat.getIdChat());
		
		inicializarChat(idRecipient, chat.getIdChat().toString());
	}
}
