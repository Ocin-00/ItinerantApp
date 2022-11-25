package com.itinerant.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.itinerant.entity.Cita;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Supervisor;
import com.itinerant.entity.Visita;
import com.itinerant.enums.Rol;

public class VisitaServicios {
	private EntityManager entityManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private VisitaDAO visitaDAO;
	private final String IMAGE_FOLDER = "C:/Users/Nico/git/repository/ItinerantApp/src/main/webapp/img/"; //Si se cambia esta ruta cambiar la de visita.setImagenRuta();
	//private final String IMAGE_FOLDER = "../../../../webapp/img/";
	
	
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
		List<Visita> visitas = visitaDAO.listAllByLogin(login);
		
		request.setAttribute("visitas", visitas);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String homepage = "../frontend/profesional/lista_visitas.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void borrarVisita() throws ServletException, IOException {
		int visitaId = Integer.parseInt(request.getParameter("id"));
		visitaDAO.delete(visitaId);
		listarVisitas("La visita ha sido borrada con éxito.");
	}

	public void NuevaVisitaFormulario() throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		ProfesionalDAO profesionalDAO = new ProfesionalDAO(entityManager);
		Profesional profesional = profesionalDAO.get(login);
		if(profesional.getValidez()) {
			CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
			LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
			List<Categoria> categorias = categoriaDAO.listAll();
			List<Localidad> localidades = localidadDAO.listAll();
			request.setAttribute("listaCategorias", categorias);
			request.setAttribute("listaLocalidades", localidades);
			
			String homepage = "../frontend/profesional/visita_form.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
			dispatcher.forward(request, response);
		} else {
			listarVisitas("Lo sentiemos, tiene que esperar a que su cuenta sea validada.");
		}
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
		return false; //COMPLETAR
	}

	private Visita inicializarDatos() throws IOException, ServletException {
		String nombre = request.getParameter("nombre");		
		int codPostal = Integer.parseInt(request.getParameter("codPostal"));
		String fechaTexto = request.getParameter("fecha");
		int tiempo = Integer.parseInt(request.getParameter("tiempo"));
		String horaInicioTexto = request.getParameter("horaInicio");
		String horaFinTexto = request.getParameter("horaFin");
		String descripcion = request.getParameter("descripcion");
		double desplazamiento = Double.parseDouble(request.getParameter("desplazamiento"));
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
			String nombreImagen = nombrarImagen();
			String src = IMAGE_FOLDER + nombreImagen;
			OutputStream outputStream = new FileOutputStream(src);
			int length; 
			
			while ((length = inputStream.read(imageBytes)) != -1) {
				outputStream.write(imageBytes, 0, length);
			}

			inputStream.close();
			outputStream.close();
			visita.setImagenRuta("../img/" + nombreImagen);
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

	private String nombrarImagen() {
		return "img" + new File(IMAGE_FOLDER).list().length + ".jpg";
	}

	public void VerVisita() throws ServletException, IOException {
		int visitaId = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(visitaId);
		request.setAttribute("visita", visita);
		
		List<Cita> citas = new ArrayList(visita.getCitas());
		request.setAttribute("citas", citas);
		
		String homepage = "../frontend/profesional/ver_visita.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void vistaEdicionVisita() throws ServletException, IOException {
		CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
		List<Categoria> categorias = categoriaDAO.listAll();
		request.setAttribute("listaCategorias", categorias);
		
		int visitaId = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(visitaId);
		request.setAttribute("visita", visita);
		
		String homepage = "../frontend/profesional/visita_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	public void actualizarVisita() throws IOException, ServletException {
		Visita visita= inicializarDatos();
		int idVisita = Integer.parseInt(request.getParameter("id"));
		visita.setIdVisita(idVisita);
		if(!hayIncompatibilidades()) {
			visitaDAO.update(visita);
			listarVisitas("La visita ha sido modificada con éxito");
		}
		listarVisitas("La visita no se ha podido modificar");	
	}

	public void buscar(String keyword) {
		List<Visita> listaVisitas = null;
		if(keyword.equals("")) {
			listaVisitas = visitaDAO.listAll();
		} else {
			listaVisitas = visitaDAO.search(keyword);
		}
		request.setAttribute("listaVisitas", listaVisitas);
	}

	public void VerVisitaBusqueda() throws ServletException, IOException {
		int visitaId = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(visitaId);
		request.setAttribute("visita", visita);
		
		List<Date> listaHorasPosibles = calcularHorarios(visita);
		List<Cita> citasPedidas = new ArrayList(visita.getCitas());		
		List<Date> listaHoras = calcularHorasDisponibles(listaHorasPosibles, citasPedidas);
		request.setAttribute("listaHoras", listaHoras);
		
		String homepage = "frontend/inicio/busqueda_visita.jsp";
		String rol = (String) request.getSession().getAttribute("rol");
		if(rol == null) {
			request.setAttribute("esCiudadano", false);
		} else if(rol.equals(Rol.CIUDADANO.toString())) {
			request.setAttribute("esCiudadano", true);
			homepage = "../frontend/inicio/busqueda_visita.jsp";
		} else {
			request.setAttribute("esCiudadano", false);
			homepage = "../frontend/inicio/busqueda_visita.jsp";
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);	
	}

	public List<Date> calcularHorarios(Visita v){
		Date hora = new Date((long) (v.getHoraInicio().getTime()));
		List<Date> horario = new ArrayList<>();
		while(hora.before(v.getHoraFin())) {
			horario.add(hora);
			hora = new Date((long) (hora.getTime() + ((v.getDuracionCitas() + v.getDuracionDesplazamiento()) * 60 * 1000)));
		}
		return horario;
	}
	
	public List<Date> calcularHorasDisponibles(List<Date> listaHorasPosibles, List<Cita> citasPedidas) {
		List<Date> listaHoras = new ArrayList<>();
		
		for (int i = 0; i < listaHorasPosibles.size(); i++) {
			boolean encontrado = false;
			int j = 0; 
			while (encontrado == false && j < citasPedidas.size()) {
				encontrado = listaHorasPosibles.get(i).equals(citasPedidas.get(j).getHoraInicio());
				j++;
			}
			if(!encontrado) {
				listaHoras.add(listaHorasPosibles.get(i));
			}
		}
		
		return listaHoras;
	}
}
