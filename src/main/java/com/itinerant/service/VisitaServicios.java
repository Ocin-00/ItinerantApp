package com.itinerant.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.itinerant.dao.CategoriaDAO;
import com.itinerant.dao.LocalidadDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.SupervisorDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Categoria;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Supervisor;
import com.itinerant.entity.Visita;

public class VisitaServicios {
	private EntityManager entityManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private VisitaDAO visitaDAO;
	
	
	
	public VisitaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		visitaDAO = new VisitaDAO(entityManager);
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
	}
	
	public void listarVisitas() throws ServletException, IOException {
		listarVisitas(null);
	}
	
	public void listarVisitas(String message) throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		System.out.println(login);
		List<Visita> visitas = visitaDAO.listAllByLogin(login);
		
		request.setAttribute("visitas", visitas);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String homepage = "index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void borrarVisita() throws ServletException, IOException {
		int visitaId = Integer.parseInt(request.getParameter("id"));
		visitaDAO.delete(visitaId);
		listarVisitas("La visita ha sido borrada con éxito.");
	}

	public void NuevaVisitaFormulario() throws ServletException, IOException {
		CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		List<Categoria> categorias = categoriaDAO.listAll();
		List<Localidad> localidades = localidadDAO.listAll();
		request.setAttribute("listaCategorias", categorias);
		request.setAttribute("listaLocalidades", localidades);
		
		String homepage = "visita_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	public void crearVisita() throws ServletException, IOException {		
		Visita visita = inicializarDatos();
		if(!hayIncompatibilidades()) {
			visitaDAO.create(visita);
			listarVisitas("La visita ha sido creada con éxito");
		}
		listarVisitas("La visita no se ha podido crear");
	}

	private boolean hayIncompatibilidades() {
		return false;
	}

	private Visita inicializarDatos() throws IOException, ServletException {
		String nombre = request.getParameter("nombre");		
		int codPostal = Integer.parseInt(request.getParameter("codPostal"));
		String fechaTexto = request.getParameter("fecha");
		int tiempo = Integer.parseInt(request.getParameter("tiempo"));
		String horaInicioTexto = request.getParameter("horaInicio");
		String horaFinTexto = request.getParameter("horaFin");
		String descripcion = request.getParameter("descripcion");
		int desplazamiento = Integer.parseInt(request.getParameter("desplazamiento"));
		double precio = Double.parseDouble(request.getParameter("precio"));
		
		System.out.println(horaInicioTexto);
		
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		Localidad localidad = localidadDAO.get(codPostal);
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		try {
			fecha = dateformat.parse(fechaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");	
		Date horaInicio = null;
		Date horaFin = null;
		try {
			horaInicio = timeformat.parse(horaInicioTexto);
			horaFin = timeformat.parse(horaFinTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String login = (String) request.getSession().getAttribute("userLogin");
		ProfesionalDAO profesionalDAO = new ProfesionalDAO(entityManager);
		Profesional profesional = profesionalDAO.get(login);
		
		Visita visita = new Visita(localidad, profesional, fecha, horaInicio, horaFin, descripcion, tiempo, desplazamiento, precio, nombre);

		Part part = request.getPart("imagenVisita");
		if(part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			visita.setImagen(imageBytes);
		}
		
		String[] categoriasId = request.getParameterValues("categorias");
		if (categoriasId != null) {
			CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
			Set<Categoria> categorias = new HashSet<Categoria>();
		    for(String categoriaId: categoriasId){
		        categorias.add(categoriaDAO.get(Integer.parseInt(categoriaId)));
		    }
		    visita.setCategorias(categorias);
		}
		
		return visita;
	}

}
