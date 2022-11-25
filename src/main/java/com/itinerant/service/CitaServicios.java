package com.itinerant.service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import com.itinerant.dao.CitaDAO;
import com.itinerant.dao.CiudadanoDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Cita;
import com.itinerant.entity.CitaId;
import com.itinerant.entity.Ciudadano;
import com.itinerant.entity.Visita;

public class CitaServicios {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CitaDAO citaDAO;
	private CiudadanoDAO ciudadanoDAO;
	private VisitaDAO visitaDAO;

	public CitaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		citaDAO = new CitaDAO(entityManager);
		ciudadanoDAO = new CiudadanoDAO(entityManager);
		visitaDAO = new VisitaDAO(entityManager);
		this.request = request;
		this.response = response;		
	}

	public void listarCitasPendientes() throws ServletException, IOException {
		listarCitasPendientes(null);
	}
	
	public void listarCitasPendientes(String message) throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		Ciudadano usuario = ciudadanoDAO.get(login);
		List<Cita> citas = new ArrayList(usuario.getCitas());
		
		request.setAttribute("citas", citas);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String homepage = "../frontend/inicio/citas_pendientes.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void pedirCita() throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		Ciudadano usuario = ciudadanoDAO.get(login);
		int visitaId = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(visitaId);
		String horaTexto = request.getParameter("horaCita");
		System.out.println(horaTexto);
		String message = null;
		DateFormat timeformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);	
		Date hora = null;
		try {
			hora = timeformat.parse(horaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Boolean direccionUsuario = Boolean.parseBoolean(request.getParameter("direccionUsuario"));
		String direccion = null;
		if(direccionUsuario) {
			direccion = usuario.getLocalizacion();
		} else {
			direccion = request.getParameter("direccion");
		}
		String anotaciones = request.getParameter("anotaciones");
		
		CitaId citaId = new CitaId(visitaId, login);
		Cita cita = new Cita(citaId, usuario, visita, hora, direccion,anotaciones);
		try {
			citaDAO.create(cita);							  
			message = "La cita se ha guardado con éxito.";
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			message = "Lo sentimos, no puedes pedir cita dos veces";
			e.printStackTrace();
		} catch (Exception e){
			message = "ERROR: Hubo problemas al pedir cita."; //Recoger cada posible excepción para personalizar el mensaje
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		String homepage = "visita?id=" + visitaId;
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
}
