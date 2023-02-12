package com.itinerant.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CiudadanoDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Ciudadano;

import org.apache.commons.text.StringEscapeUtils;

public class CiudadanoServicios {
	private CiudadanoDAO ciudadanoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AlertaDAO alertaDAO;
	private EntityManager entityManager;

	public CiudadanoServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		ciudadanoDAO = new CiudadanoDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
	}

	public void listarPropuestosSancion() throws ServletException, IOException {
		listarPropuestosSancion(null);
	}
	
	public void listarPropuestosSancion(String message) throws ServletException, IOException {
		List<Ciudadano> ciudadanosSancion = ciudadanoDAO.listAllSanctioned();
		request.setAttribute("ciudadanosSancion", ciudadanosSancion);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listpage = "/admin/lista_sanciones.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
		
	}

	public void aplicarSancion() throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("id"));
		int dias = Integer.parseInt(request.getParameter("dias"));
		
		Date ahora = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(ahora);
		cal.add(Calendar.DATE, dias);
		Date finSancion = cal.getTime();
		
		Ciudadano ciudadano = ciudadanoDAO.get(login);
		if(ciudadano.getFinSancion() == null || ciudadano.getFinSancion().before(finSancion)) {
			ciudadano.setFinSancion(finSancion);
		}
		ciudadano.setPropuestoSancion(false);;
		ciudadanoDAO.update(ciudadano);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String cuerpoAlerta = "Se le ha sancionado hasta el día " + dateFormat.format(finSancion) + " a las " + timeFormat.format(finSancion) + ". Hasta entonces no podrá pedir nuevas citas. "; 
		Alerta alerta = new Alerta(ciudadano, "Se le ha sancionado", StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
		alertaDAO.create(alerta);
		
		AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.mandarNotificacion(alerta);
		
		listarPropuestosSancion("Usuario sancionado con éxito.");
	}

	public void noSancionar() throws ServletException, IOException {
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("id"));
		
		Ciudadano ciudadano = ciudadanoDAO.get(login);
		ciudadano.setPropuestoSancion(false);;
		ciudadanoDAO.update(ciudadano);
		
		listarPropuestosSancion("Usuario no sancionado.");
		
	}
}
