package com.itinerant.service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import com.itinerant.dao.AdministradorDAO;
import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CitaDAO;
import com.itinerant.dao.CiudadanoDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Administrador;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Cita;
import com.itinerant.entity.CitaId;
import com.itinerant.entity.Ciudadano;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.entity.Visita;
import com.itinerant.enums.Rol;

import org.apache.commons.text.StringEscapeUtils;

public class CitaServicios {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CitaDAO citaDAO;
	private CiudadanoDAO ciudadanoDAO;
	private UsuarioInternoDAO usuarioInternoDAO;
	private ProfesionalDAO profesionalDAO;
	private VisitaDAO visitaDAO;
	private AlertaDAO alertaDAO;
	private AdministradorDAO administradorDAO;
	private EntityManager entityManager;

	public CitaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.entityManager = entityManager;
		citaDAO = new CitaDAO(entityManager);
		ciudadanoDAO = new CiudadanoDAO(entityManager);
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		profesionalDAO = new ProfesionalDAO(entityManager);
		visitaDAO = new VisitaDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		administradorDAO = new AdministradorDAO(entityManager);
		this.request = request;
		this.response = response;		
	}

	public void listarCitasPendientes() throws ServletException, IOException {
		listarCitasPendientes(null);
	}
	
	public void listarCitasPendientes(String message) throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		
		String homepage = null;
		List<Cita> citas = new ArrayList<Cita>();
		
		if(usuario.getRol().equals(Rol.CIUDADANO.toString())) {
			Ciudadano ciudadano = ciudadanoDAO.get(login);
			citas.addAll(ciudadano.getCitas());
			
			homepage = "../frontend/inicio/citas_pendientes.jsp";
			List<Cita> citasPendientes = buscarCitasPendientes(citas);
			listarCitas(message, homepage, citasPendientes);
			
		} else if(usuario.getRol().equals(Rol.PROFESIONAL.toString())){
			/*Profesional profesional = profesionalDAO.get(login);
			List<Visita> visitas = new ArrayList<Visita>(profesional.getVisitas());
			for(Visita visita : visitas) {
				citas.addAll(visita.getCitas());
			}*/
			citas = citaDAO.listAllByLogin(login);
			List<Cita> citasPendientes = buscarCitasPendientes(citas);
			request.setAttribute("citasPendientes", citasPendientes);
		} else {
			homepage = "../admin/message.jsp";
			request.setAttribute("message", "Ha habido un error.");
			RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
			dispatcher.forward(request, response);
		}
	}
	
	public void listarHistorialCitas() throws ServletException, IOException {
		listarHistorialCitas(null);
		
	}
	
	public void listarHistorialCitas(String message) throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		
		String homepage = null;
		List<Cita> citas = new ArrayList<Cita>();
		
		if(usuario.getRol().equals(Rol.CIUDADANO.toString())) {
			Ciudadano ciudadano = ciudadanoDAO.get(login);
			citas.addAll(ciudadano.getCitas());
			
			homepage = "../frontend/inicio/historial_citas.jsp";
			List<Cita> historialCitas = buscarHistorialCitas(citas);
			listarCitas(message, homepage, historialCitas);
			
		} else if(usuario.getRol().equals(Rol.PROFESIONAL.toString())){
			Profesional profesional = profesionalDAO.get(login);
			List<Visita> visitas = new ArrayList<Visita>(profesional.getVisitas());
			for(Visita visita : visitas) {
				citas.addAll(visita.getCitas());
			}
			List<Cita> historialCitas = buscarHistorialCitas(citas);
			request.setAttribute("historialCitas", historialCitas);
		} else {
			homepage = "../admin/message.jsp";
			request.setAttribute("message", "Ha habido un error.");
			RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
			dispatcher.forward(request, response);
		}
		
	}
	
	public List<Cita> buscarCitasPendientes(List<Cita> citas) {
		Date ahora = new Date();
		List<Cita> citasPendientes = new ArrayList<>();
		
		for(int i = 0; i < citas.size(); i++) {
			if(ahora.before(citas.get(i).getHoraInicio())) {
				citasPendientes.add(citas.get(i));
			}
		}
		
		return citasPendientes;
	}
	
	public List<Cita> buscarHistorialCitas(List<Cita> citas) {
		Date ahora = new Date();
		List<Cita> historialCitas = new ArrayList<>();
		
		for(int i = 0; i < citas.size(); i++) {		
			if(ahora.after(citas.get(i).getHoraInicio())) {
				historialCitas.add(citas.get(i));
			}
		}
		
		return historialCitas;
	}
	
	
	public void listarCitas(String message, String homepage, List<Cita> citas) throws ServletException, IOException {	
		request.setAttribute("citas", citas);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
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
			direccion = StringEscapeUtils.escapeHtml4(request.getParameter("direccion"));
		}
		String anotaciones = StringEscapeUtils.escapeHtml4(request.getParameter("anotaciones"));
		
		CitaId citaId = new CitaId(visitaId, login);
		Cita cita = new Cita(citaId, usuario, visita, hora, direccion);
		if(anotaciones != null) {
			cita.setAnotaciones(anotaciones);
		}
		try {
			citaDAO.create(cita);	
			visita.getCitas().add(cita);
			message = "La cita se ha guardado con éxito.";
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			String cuerpoAlerta = "El usuario " + cita.getCiudadano() + " ha pedido cita el " 
								+ dateFormat.format(cita.getHoraInicio()) + " a las " 
								+ timeFormat.format(cita.getHoraInicio()) + " en el municipio " 
								+ cita.getVisita().getLocalidad() + " con las siguientes anotaciones: " + anotaciones;
			Alerta alerta = new Alerta(cita.getVisita().getProfesional(), "Nueva cita", cuerpoAlerta, false);
			alertaDAO.create(alerta);
			
			AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
			alertaServicios.mandarNotificacion(alerta);
			
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

	
	public void anularCitaProfesional() throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		Date fechaCita = cita.getHoraInicio();
		
		Date ahora = new Date();
		Date tiempoLimite = new Date(fechaCita.getTime() - 3 * 60 * 60 * 1000); //Tiempo límite = 3 horas antes de la cita
		
		String urgenciaTexto = StringEscapeUtils.escapeHtml4(request.getParameter("urgencia")); //Construir alerta con esto.
		boolean urgencia = !((urgenciaTexto == null) || urgenciaTexto.isBlank());
		boolean pasadoTiempoLimite = ahora.after(tiempoLimite);
		
		if(urgencia || !pasadoTiempoLimite) {
			citaDAO.delete(citaId);
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			
			String cuerpoAlerta = null;
			
			if(urgencia) { 
				cuerpoAlerta = "Su cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
									+ timeFormat.format(cita.getHoraInicio()) + " ha sido cancelada por el siguiente motivo:" + urgenciaTexto;
			} else {
				cuerpoAlerta = "Su cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
									+ timeFormat.format(cita.getHoraInicio()) + " ha sido cancelada.";
			}
			Alerta alerta = new Alerta(cita.getCiudadano(), "Cita anulada", cuerpoAlerta, false);
			alertaDAO.create(alerta);
			//Mandar notificación
			AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
			alertaServicios.mandarNotificacion(alerta);
			
			VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
			visitaServicios.verVisita("La cita ha sido borrada con éxito.");
		} else {
			request.setAttribute("pasadoTiempoLimite", pasadoTiempoLimite);
			verCita("Es demasiado tarde para cancelar la cita a menos que se trate de una urgencia, si este es el caso por favor indíquelo.");	
		}
		
	}
	
	public void anularCitaCiudadano() throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		Date fechaCita = cita.getHoraInicio();
		
		Date ahora = new Date();
		Date tiempoLimite = new Date(fechaCita.getTime() - 24 * 60 * 60 * 1000); //Tiempo límite =24 horas antes de la cita
		
		boolean pasadoTiempoLimite = ahora.after(tiempoLimite); //Comprobar que esto funcione bien
		
		if(!pasadoTiempoLimite) {
			citaDAO.delete(citaId);
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			String cuerpoAlerta = "La cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
								+ timeFormat.format(cita.getHoraInicio()) + " en el municipio " 
								+ cita.getVisita().getLocalidad() + " ha sido cancelada.";;
			Alerta alerta = new Alerta(cita.getVisita().getProfesional(), "Cita anulada", cuerpoAlerta, false);
			alertaDAO.create(alerta);
			//Mandar notificación
			AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
			alertaServicios.mandarNotificacion(alerta);
			
			listarCitasPendientes("La cita ha sido borrada con éxito.");
		} else {
			detallesCitaPendiente("Lo sentimos, es demasiado tarde para cancelar la cita.");	
		}
	}

	public void verCita() throws ServletException, IOException {
		verCita(null);
	}
	
	public void verCita(String message) throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		System.out.println(login);
		int idVisita = Integer.parseInt(request.getParameter("id"));
		System.out.println(idVisita);
		Visita visita = visitaDAO.get(idVisita);
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		Date ahora = new Date();
		
		request.setAttribute("esFutura", !ahora.after(cita.getHoraInicio()));
		request.setAttribute("visita", visita);
		request.setAttribute("cita", cita);
		
		request.setAttribute("message", message);
		
		String homepage = "../frontend/profesional/ver_cita.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void detallesCitaPendiente() throws ServletException, IOException {
		detallesCitaPendiente(null);
	}
	
	public void detallesCitaPendiente(String message) throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(idVisita);
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		request.setAttribute("visita", visita);
		request.setAttribute("cita", cita);
		
		request.setAttribute("message", message);
		
		String homepage = "../frontend/inicio/detalles_cita_pendiente.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	public void modificarCita() throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		Date fechaCita = cita.getHoraInicio();
		
		Date ahora = new Date();
		Date tiempoLimite = new Date(fechaCita.getTime() - 24 * 60 * 60 * 1000); //Tiempo límite =24 horas antes de la cita
		
		boolean pasadoTiempoLimite = ahora.after(tiempoLimite); //Comprobar que esto funcione bien
		
		String anotaciones = StringEscapeUtils.escapeHtml4(request.getParameter("anotaciones"));
		cita.setAnotaciones(anotaciones);
		
		if(!pasadoTiempoLimite) {
			citaDAO.update(cita);
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			String cuerpoAlerta = "El usuario " + cita.getCiudadano() + " ha modificado las anotaciones para su cita el " 
								+ dateFormat.format(cita.getHoraInicio()) + " a las " 
								+ timeFormat.format(cita.getHoraInicio()) + " en el municipio " 
								+ cita.getVisita().getLocalidad() + " por las siguientes: " + anotaciones;
			Alerta alerta = new Alerta(cita.getVisita().getProfesional(), "Cita modificada", cuerpoAlerta, false);
			alertaDAO.create(alerta);
			//Mandar notificación
			AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
			alertaServicios.mandarNotificacion(alerta);
			
			detallesCitaPendiente("Las anotaciones han sido modificadas con éxito.");
		} else {
			detallesCitaPendiente("Lo sentimos, es demasiado tarde para modificar la cita.");	
		}
	}
	
	public void detallesCitaHistorial() throws ServletException, IOException {
		detallesCitaHistorial(null);
	}
	
	public void detallesCitaHistorial(String message) throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(idVisita);
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		request.setAttribute("visita", visita);
		request.setAttribute("cita", cita);
		
		request.setAttribute("message", message);
		
		String homepage = "../frontend/inicio/detalles_cita_historial.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	public void dejarReview() throws ServletException, IOException {	
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		String review = StringEscapeUtils.escapeHtml4(request.getParameter("review"));
		Integer puntuacion = Integer.parseInt(request.getParameter("puntuacion"));
		cita.setReview(review);
		cita.setPuntuacion(puntuacion);
		
		citaDAO.update(cita);
		detallesCitaHistorial("¡Has añadido una reseña!");
	}

	public void informarAusencia() throws ServletException, IOException, NumberFormatException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		int idVisita = Integer.parseInt(request.getParameter("id"));
		
		CitaId citaId = new CitaId(idVisita, login);
		Cita cita = citaDAO.get(citaId);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
		String cuerpoAlerta = "El profesional " + cita.getVisita().getProfesional() + " ha notificado que el usuario " 
							+ cita.getCiudadano() + ", de login: " + cita.getCiudadano().getLogin() 
							+ " no ha asistido a la cita prevista el día " + dateFormat.format(cita.getHoraInicio())
							+ " a las " + timeFormat.format(cita.getHoraInicio()) 
							+ " en el municipio " + cita.getVisita().getLocalidad();
		List<Administrador> admins = administradorDAO.listAll();
		for(int i = 0; i < admins.size(); i++) {
			Alerta alerta = new Alerta(admins.get(i), "Ausencia en cita", cuerpoAlerta, false);
			
			AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
			alertaServicios.mandarNotificacion(alerta);
			
			alertaDAO.create(alerta);
		}
		
		verCita("La ausencia ha sido notificada");
	}

	public void listarCitasProfesional() throws ServletException, IOException {
		listarCitasPendientes();
		listarHistorialCitas();
		
		String homepage = "../frontend/profesional/lista_citas.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
}
