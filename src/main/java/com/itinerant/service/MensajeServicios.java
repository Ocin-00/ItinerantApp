package com.itinerant.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ChatDAO;
import com.itinerant.dao.ChatMensajeDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Chat;
import com.itinerant.entity.ChatMensaje;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.MessageStatus;

import org.json.JSONArray;
import org.json.JSONObject;

public class MensajeServicios {
	private ChatDAO chatDAO;
	private UsuarioInternoDAO usuarioInternoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ChatMensajeDAO chatMensajeDAO;

	public MensajeServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		chatDAO = new ChatDAO(entityManager);
		chatMensajeDAO = new ChatMensajeDAO(entityManager);
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		this.request = request;
		this.response = response;
	}
	
	public void inicializarMensajes(Integer idChat) {
		HashMap<Integer, List<ChatMensaje>> mensajesChat = (HashMap<Integer, List<ChatMensaje>>) request.getSession().getServletContext().getAttribute("mensajesChat");
		
		List<ChatMensaje> mensajes;
		mensajes = mensajesChat.get(idChat);
		
		if(mensajes == null) {
			mensajes = new ArrayList<>();
			List<ChatMensaje> mensajesBD = chatMensajeDAO.findAllByChatId(idChat);
			if(mensajesBD != null) {
				mensajes.addAll(mensajesBD);
			}
		}
		request.getSession().setAttribute("mensajes", mensajes);
		
		mensajesChat.put(idChat, mensajes);			
		request.getSession().getServletContext().setAttribute("mensajesChat", mensajesChat);
	}
	
	public void getMensajes() throws IOException {
		String idChatText = request.getParameter("id");
		if(idChatText != null && !idChatText.isBlank() && !idChatText.isEmpty()) {
			Integer idChat = Integer.parseInt(idChatText);
			HashMap<Integer, List<ChatMensaje>> mensajesChat = (HashMap<Integer, List<ChatMensaje>>) request.getSession().getServletContext().getAttribute("mensajesChat");
			
			List<ChatMensaje> mensajes = mensajesChat.get(idChat);
			
			mensajesToJSON(mensajes, mensajesChat);
		}	
	}

	private void mensajesToJSON(List<ChatMensaje> mensajes, HashMap<Integer, List<ChatMensaje>> mensajesChat) throws IOException {
		JSONArray jsArray = new JSONArray();
		String login = (String) request.getSession().getAttribute("userLogin");
		
		if(mensajes != null && mensajes.size() >0) {
			for(ChatMensaje mensaje: mensajes) {
				String senderId = mensaje.getUsuarioInternoByIdSender().getLogin();
				
				JSONObject jobj = new JSONObject();
				jobj.put("idChat", mensaje.getChat().getIdChat());
			    jobj.put("sender", senderId);
			    jobj.put("recipient", mensaje.getUsuarioInternoByIdRecipient().getLogin());
			    jobj.put("cuerpo", mensaje.getCuerpo());
			    jobj.put("status", mensaje.getStatus().toString());
			    jobj.put("hora", mensaje.getHora());
			    jobj.toString();
			    	
			    jsArray.put(jobj);
			    
			    if(!senderId.equals(login) && !mensaje.getStatus().equals(MessageStatus.READ)) {
			    	mensaje.setStatus(MessageStatus.READ);;
			    	chatMensajeDAO.update(mensaje);
			    	actualizarMensajeEnLista(mensaje.getChat().getIdChat(), mensaje, mensajes, mensajesChat);
			    }
			}

		}
		request.setAttribute("mensajes", mensajes);
        response.getWriter().write(jsArray.toString());
	}
	
	public void actualizarMensajeEnLista(Integer chatId, ChatMensaje mensaje, List<ChatMensaje> mensajes, HashMap<Integer, List<ChatMensaje>> mensajesChat) {
		boolean encontrado = false;
    	for(int i = 0; !encontrado && i < mensajes.size(); i++) {
			if(mensaje.getIdMensaje() == mensajes.get(i).getIdMensaje()) {
				encontrado = true;
				mensajes.set(i, mensaje);
			}
		}
    	mensajesChat.put(chatId, mensajes);
		request.getSession().getServletContext().setAttribute("mensajesChat", mensajesChat);
	}

	public void sendMensaje() {
		String cuerpoMensaje = request.getParameter("mensaje");
		if(!cuerpoMensaje.isBlank() && !cuerpoMensaje.isEmpty() && cuerpoMensaje != null) {
			System.out.println("SEND MESSAGE");
			Integer idChat = Integer.parseInt(request.getParameter("id"));
			Chat chat = chatDAO.get(idChat);
			
			String recipientLogin = request.getParameter("usuario");
			UsuarioInterno recipient = usuarioInternoDAO.get(recipientLogin);
			
			String senderLogin = (String) request.getSession().getAttribute("userLogin");
			UsuarioInterno sender = usuarioInternoDAO.get(senderLogin);
			
			HashMap<Integer, List<ChatMensaje>> mensajesChat = (HashMap<Integer, List<ChatMensaje>>) request.getSession().getServletContext().getAttribute("mensajesChat");
			
			List<ChatMensaje> mensajes;
			mensajes = mensajesChat.get(idChat);
			
			ChatMensaje mensaje = new ChatMensaje(chat, recipient, sender, cuerpoMensaje);
			chatMensajeDAO.create(mensaje);

			mensajes.add(mensaje);
			mensajesChat.put(idChat, mensajes);
			request.getSession().getServletContext().setAttribute("mensajesChat", mensajesChat);
		}
	}

	public int mensajesPendientes(Chat chat) {
		String login = (String) request.getSession().getAttribute("userLogin");

		HashMap<Integer, List<ChatMensaje>> mensajesChat = (HashMap<Integer, List<ChatMensaje>>) request.getSession().getServletContext().getAttribute("mensajesChat");
			
		List<ChatMensaje> mensajes = mensajesChat.get(chat.getIdChat());
		int mensajes_pendientes = 0;
		if(mensajes != null && mensajes.size() >0) {
		for(ChatMensaje mensaje: mensajes) {
				if(login.equals(mensaje.getUsuarioInternoByIdRecipient().getLogin()) 
					&& !mensaje.getStatus().equals(MessageStatus.READ)) {
					mensajes_pendientes++;
				}
			}
		}
		return mensajes_pendientes;	
	}

	public void getNumeroMensajes() throws IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		HashMap<String, List<Chat>> chats = (HashMap<String, List<Chat>>) request.getSession().getServletContext().getAttribute("chats");
		List<Chat> misChats = chats.get(login);
		
		int numeroMensajes = 0;
		for(Chat chat : misChats) {
			numeroMensajes += mensajesPendientes(chat);
		}
		JSONArray jsArray = new JSONArray();
		JSONObject jobj = new JSONObject();
		jobj.put("numeroMensajes", numeroMensajes);
		jobj.toString();
		jsArray.put(jobj);
		response.getWriter().write(jsArray.toString());
	}	
	
	public int getNumeroMensajesChat(Chat chat) throws IOException {
		HashMap<Integer, List<ChatMensaje>> mensajesChat = (HashMap<Integer, List<ChatMensaje>>) request.getSession().getServletContext().getAttribute("mensajesChat");	
		List<ChatMensaje> mensajes = mensajesChat.get(chat.getIdChat());
		return mensajes.size();	
	}	
	
}
