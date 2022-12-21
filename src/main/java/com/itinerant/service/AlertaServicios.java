package com.itinerant.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itinerant.adapter.AlertaTypeAdapter;
import com.itinerant.dao.AlertaDAO;
import com.itinerant.entity.Alerta;

import org.hibernate.proxy.HibernateProxy;
import org.json.JSONArray;
import org.json.JSONObject;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.ext.web.handler.sockjs.impl.SockJSImpl;

public class AlertaServicios {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AlertaDAO alertaDAO;

	public AlertaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		alertaDAO = new AlertaDAO(entityManager);
		this.request = request;
		this.response = response;		
	}

	public void getAlertas() throws IOException {
		String login = (String) request.getSession().getAttribute("userLogin");	
		HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
		Stack<Alerta> alertas = pilasUsuarios.get(login);
		AlertasToJSON(alertas, pilasUsuarios);
	}
	
	public void AlertasToJSON(Stack<Alerta> alertas, HashMap<String, Stack<Alerta>> pilasUsuarios) throws IOException {
		JSONArray jsArray = new JSONArray();
		if(alertas != null && alertas.size() >0) {
			
			request.getSession().setAttribute("misAlertas", alertas);
			
		    for (Alerta alerta: alertas) {
		    	JSONObject jobj = new JSONObject();
			    jobj.put("idAlerta", alerta.getIdAlerta());
			    jobj.put("titulo", alerta.getTitulo());
			    jobj.put("cuerpo", alerta.getCuerpo());
			    jobj.put("visto", alerta.getVisto());
			    jobj.put("nuevo", alerta.getNuevo());
			    jobj.put("usuarioInterno", alerta.getUsuarioInterno().getLogin());
			    jobj.toString();
			    	
			    jsArray.put(jobj);
			    
			    if(alerta.getNuevo()) {
			    	alerta.setNuevo(false);
			    	alertaDAO.update(alerta);
			    	actualizarAlertaEnPila(alerta, alertas, pilasUsuarios);
			    }
		    }

		}
        response.getWriter().write(jsArray.toString());
	}
	
	public void mandarNotificacion(Alerta alerta) {
		HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
		Stack<Alerta> notificaciones = pilasUsuarios.get(alerta.getUsuarioInterno().getLogin());
		if(notificaciones != null) {
			notificaciones.add(alerta);
			pilasUsuarios.put(alerta.getUsuarioInterno().getLogin(), notificaciones);
			request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
		}
	}

	public void marcarVisto() throws IOException {
		int idAlerta = Integer.parseInt(request.getParameter("idAlerta"));
		Alerta alerta = alertaDAO.get(idAlerta);
		if(!alerta.getVisto()) {
			HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
			Stack<Alerta> notificaciones = pilasUsuarios.get(alerta.getUsuarioInterno().getLogin());
			
			alerta.setVisto(true);
			alertaDAO.update(alerta);
			actualizarAlertaEnPila(alerta, notificaciones, pilasUsuarios);
			AlertasToJSON(notificaciones, pilasUsuarios);
		}
	}
	
	public void actualizarAlertaEnPila(Alerta alerta, Stack<Alerta> alertas, HashMap<String, Stack<Alerta>> pilasUsuarios) {
		boolean encontrado = false;
    	for(int i = 0; !encontrado && i < alertas.size(); i++) {
			if(alerta.getIdAlerta() == alertas.get(i).getIdAlerta()) {
				encontrado = true;
				alertas.set(i, alerta);
			}
		}
    	pilasUsuarios.put(alerta.getUsuarioInterno().getLogin(), alertas);
		request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
	}
	
	public void borrarAlerta() throws IOException {
		int idAlerta = Integer.parseInt(request.getParameter("idAlerta"));
		Alerta alerta = alertaDAO.get(idAlerta);
		
		alertaDAO.delete(alerta.getIdAlerta());
		
		String login = (String) request.getSession().getAttribute("userLogin");	
		HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
		Stack<Alerta> alertas = pilasUsuarios.get(login);
		
		boolean encontrado = false;
    	for(int i = 0; !encontrado && i < alertas.size(); i++) {
			if(alerta.getIdAlerta() == alertas.get(i).getIdAlerta()) {
				encontrado = true;
				alertas.remove(i);
			}
		}
    	
		pilasUsuarios.put(login, alertas);
		request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
		
		AlertasToJSON(alertas, pilasUsuarios);	
	}
	
	public void limpiarTodo() throws IOException {
		String login = (String) request.getSession().getAttribute("userLogin");	
		HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
		Stack<Alerta> alertas = pilasUsuarios.get(login);
		for(Alerta alerta: alertas) {
			alertaDAO.delete(alerta.getIdAlerta());
		}
		alertas.removeAll(alertas);
		pilasUsuarios.put(login, alertas);
		request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
		JSONArray jsArray = new JSONArray();
		response.getWriter().write(jsArray.toString());
	}

}
