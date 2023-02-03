package com.itinerant.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.SerieDAO;
import com.itinerant.dao.SerieJornadasDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Categoria;
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
		request.setAttribute("numVisitas", listaVisitasJornada.size());
		request.setAttribute("fecha", dateformat.format(fecha));
		
		List<Serie> series = serieDAO.listAllByLogin(login);
		List<SerieJornadas> serieJornadas = serieJornadasDAO.listAllByLogin(login);
		request.setAttribute("series", series);
		request.setAttribute("serieJornadas", serieJornadas);
		
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
		
		serie.getSerieJornadas().add(serieJornadas);
		
		
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
		
		serie.getSerieJornadas().add(serieJornadas);
		
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
		LocalidadServicios localidadServicios = new LocalidadServicios(entityManager, request, response);
		for(Visita visita: listaVisitas) {
			if(dateformat.format(fecha).equals(dateformat.format(visita.getFecha()))) {
				//Esto es para usar el geocoder de nominatim
				JSONObject jobj = new JSONObject();
				jobj.put("codPostal", visita.getLocalidad().getCodigoPostal());
				jobj.put("nombre", visita.getLocalidad().getNombre());
				jobj.put("comarca", visita.getLocalidad().getComarca());
				jobj.put("provincia", visita.getLocalidad().getProvincia());
				jobj.toString();
			    jsArray.put(jobj);
				
				/*	//Esto es para usar el geocoder de OpenRouteService
				double[] coords = localidadServicios.getCoordenadas(visita.getLocalidad());
				JSONObject jobj = new JSONObject();
				jobj.put("lat", coords[1]);
				jobj.put("lng", coords[0]);
				jobj.toString();
			    jsArray.put(jobj);*/
			}
		}
		response.getWriter().write(jsArray.toString());
		
	}

	public void replicarJornada() throws ServletException, IOException {
		String fechaTexto = request.getParameter("fecha");
		String fechaReplicaTexto = request.getParameter("replica");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		Date fechaReplica = null;
		
		try {
			fecha = dateformat.parse(fechaTexto);
			fechaReplica = dateformat.parse(fechaReplicaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		replicarJornada(fecha, fechaReplica);
		listarJornadas("La jornada se ha replicado con éxito");
	}
	
	public void replicarJornada(Date fecha, Date fechaReplica) throws ServletException, IOException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		String login = (String) request.getSession().getAttribute("userLogin");
		List<Visita> listaVisitas = visitaDAO.listAllByLogin(login);
		List<Visita> listaVisitasJornada = new ArrayList<>();
		for(Visita visita: listaVisitas) {
			if(dateformat.format(fecha).equals(dateformat.format(visita.getFecha()))) {
				listaVisitasJornada.add(visita);
			}
		}
		
		for(Visita visita: listaVisitas) {
			if(dateformat.format(fechaReplica).equals(dateformat.format(visita.getFecha()))) {
				listarJornadas("Ya existen visitas el día en que está intenado replicar la jornada. Por favor, borrelas antes de continuar.");
				return;
			}
		}
		
		for(Visita visita: listaVisitasJornada) {
			
			SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");	
			String horaInicioCompleta = dateformat.format(fechaReplica) + " " + timeformat.format(visita.getHoraInicio());
			String horaFinCompleta = dateformat.format(fechaReplica) + " " + timeformat.format(visita.getHoraFin());
			
			SimpleDateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
			Date horaInicio = null;
			Date horaFin = null;
			try {
				horaInicio = dateTimeformat.parse(horaInicioCompleta);
				horaFin = dateTimeformat.parse(horaFinCompleta);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Set<Categoria> categorias = new HashSet<>(visita.getCategorias());
			
			Visita replica = new Visita(visita.getLocalidad(), visita.getProfesional(), fechaReplica, horaInicio, horaFin, visita.getDescripcion(), visita.getDuracionCitas(), visita.getDuracionDesplazamiento(), visita.getPrecio(), visita.getImagenRuta(), visita.getNombre(), categorias, null);
			visitaDAO.create(replica);
		}
	}

	public void borrarJornada() throws ServletException, IOException {
		Integer idSerie = Integer.parseInt(request.getParameter("id"));
		
		String fechaTexto = request.getParameter("fecha");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		try {
			fecha = dateformat.parse(fechaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Serie serie = serieDAO.get(idSerie);
		
		SerieJornadasId serieJornadasId = new SerieJornadasId(idSerie, fecha);
		

		serieJornadasDAO.delete(serieJornadasId);
		listarJornadas("La jornada se ha borrado con éxito de la serie.");

		
	}

	public void borrarSerie() throws ServletException, IOException {
		Integer idSerie = Integer.parseInt(request.getParameter("id"));
		
		serieDAO.delete(idSerie);
		listarJornadas("La serie se ha borrado con éxito.");

		
	}

	public void cambiarNombreSerie() throws ServletException, IOException {
		String nombreSerie = StringEscapeUtils.escapeHtml4(request.getParameter("nombre"));
		Integer idSerie = Integer.parseInt(request.getParameter("id"));
		
		Serie serie = serieDAO.get(idSerie);
		serie.setNombre(nombreSerie);
		serieDAO.update(serie);
		
		listarJornadas("El nombre de la serie se ha cambiado con éxito.");
		
	}

	public void replicarSerie() throws ServletException, IOException {
		Integer idSerie = Integer.parseInt(request.getParameter("id"));
		
		Serie serie = serieDAO.get(idSerie);
		List<SerieJornadas> serieJornadas = serieJornadasDAO.listAllById(idSerie);
		
		String fechaReplicaTexto = request.getParameter("fecha");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fechaReplica = null;
		
		try {
			fechaReplica = dateformat.parse(fechaReplicaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for(SerieJornadas jornada: serieJornadas) {
			replicarJornada(jornada.getId().getFecha(), fechaReplica);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaReplica);
			cal.add(Calendar.DATE, 1);
			fechaReplica = cal.getTime();
		}
		
		listarJornadas("La serie se ha replicado con éxito.");
	}

}
