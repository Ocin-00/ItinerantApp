package com.itinerant.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.SerieDAO;
import com.itinerant.dao.SerieJornadasDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Jornada;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Serie;
import com.itinerant.entity.SerieJornadas;
import com.itinerant.entity.SerieJornadasId;
import com.itinerant.entity.Visita;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JornadaServicios {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private VisitaDAO visitaDAO;
	private SerieJornadasDAO serieJornadasDAO;
	private SerieDAO serieDAO;
	private ProfesionalDAO profesionalDAO;
	private EntityManager entityManager;

	public JornadaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		visitaDAO = new VisitaDAO(entityManager);
		serieDAO = new SerieDAO(entityManager);
		serieJornadasDAO = new SerieJornadasDAO(entityManager);
		profesionalDAO = new ProfesionalDAO(entityManager);
		this.request = request;
		this.response = response;	
		this.entityManager = entityManager;
	}

	public void listarJornadas() throws ServletException, IOException {
		listarJornadas(null);
	}		
	
	public void listarJornadas(String mensaje) throws ServletException, IOException {	
		
		if(mensaje != null) {
			request.setAttribute("message", mensaje);
		}
		
		String fechaTexto = request.getParameter("fecha");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		Date fechaAhora = new Date();
		if(fechaTexto != null) {
			try {
				fecha = dateformat.parse(fechaTexto);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			fecha = fechaAhora;
		}
		
		
		String login = (String) request.getSession().getAttribute("userLogin");
		List<Visita> listaVisitas = visitaDAO.listAllByLogin(login);
		List<Visita> listaVisitasJornada = new ArrayList<>();
		for(Visita visita: listaVisitas) {
			if(dateformat.format(fecha).equals(dateformat.format(visita.getFecha()))) {
				listaVisitasJornada.add(visita);
			}
		}
		request.setAttribute("visitas", listaVisitasJornada);
		request.setAttribute("fecha", dateformat.format(fecha));
		
		List<Serie> series = serieDAO.listAllByLogin(login);
		request.setAttribute("series", series);
		
		boolean esPendiente = fecha.after(fechaAhora);
		request.setAttribute("esPendiente", esPendiente);
		
		String listpage = "../frontend/profesional/lista_jornadas.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void nuevaSerie() throws ServletException, IOException {
		String nombreSerie = StringEscapeUtils.escapeHtml4(request.getParameter("nombre"));
		
		String fechaTexto = request.getParameter("fecha");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		try {
			fecha = dateformat.parse(fechaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String login = (String) request.getSession().getAttribute("userLogin");
		Profesional profesional = profesionalDAO.get(login);
		
		Serie serie = new Serie(profesional, nombreSerie);
		serie = serieDAO.create(serie);
		
		SerieJornadasId serieJornadasId = new SerieJornadasId(serie.getIdSerie(), fecha);
		SerieJornadas serieJornadas = new SerieJornadas(serieJornadasId, serie);
		
		serie.getSerieJornadases().add(serieJornadas);
		
		
		serieJornadasDAO.create(serieJornadas);
		
		listarJornadas("La serie se ha creado con éxito.");
	}

	public void addJornada() throws ServletException, IOException {
		Integer idSerie = Integer.parseInt(request.getParameter("id"));
		
		String fechaTexto = request.getParameter("fecha");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		try {
			fecha = dateformat.parse(fechaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Serie serie = serieDAO.get(idSerie);
		
		SerieJornadasId serieJornadasId = new SerieJornadasId(idSerie, fecha);
		SerieJornadas serieJornadas = new SerieJornadas(serieJornadasId, serie);
		
		serie.getSerieJornadases().add(serieJornadas);
		
		try {
			serieJornadasDAO.create(serieJornadas);
			listarJornadas("La jornada se ha añadido con éxito a la serie.");
		} catch (javax.persistence.EntityExistsException e) {
			listarJornadas("La jornada ya está en la serie.");
		} catch (Exception e) {
			throw e;
		}
	}

	public void getLocalidades() throws IOException {
		String fechaTexto = request.getParameter("fecha");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		
		try {
			fecha = dateformat.parse(fechaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String login = (String) request.getSession().getAttribute("userLogin");
		List<Visita> listaVisitas = visitaDAO.listAllByLogin(login);
		JSONArray jsArray = new JSONArray();
		for(Visita visita: listaVisitas) {
			if(dateformat.format(fecha).equals(dateformat.format(visita.getFecha()))) {
				JSONObject jobj = new JSONObject();
				jobj.put("codPostal", visita.getLocalidad().getCodigoPostal());
				jobj.put("nombre", visita.getLocalidad().getNombre());
				jobj.put("comarca", visita.getLocalidad().getComarca());
				jobj.put("provincia", visita.getLocalidad().getProvincia());
				jobj.toString();
			    jsArray.put(jobj);
			}
		}
		response.getWriter().write(jsArray.toString());
		
	}

}
