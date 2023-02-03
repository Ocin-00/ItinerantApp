package com.itinerant.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CategoriaDAO;
import com.itinerant.dao.CitaDAO;
import com.itinerant.dao.LocalidadDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.SupervisorDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Categoria;
import com.itinerant.entity.Cita;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Supervisor;
import com.itinerant.entity.Visita;
import com.itinerant.enums.Rol;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class VisitaServicios {
	private EntityManager entityManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private VisitaDAO visitaDAO;
	private CitaDAO citaDAO;
	private AlertaDAO alertaDAO;
	private LocalidadDAO localidadDAO;
	
	public VisitaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		visitaDAO = new VisitaDAO(entityManager);
		citaDAO = new CitaDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		localidadDAO = new LocalidadDAO(entityManager);
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
		
		List<Visita> visitasPendientes = new ArrayList<Visita>();
		List<Visita> historialVisitas = new ArrayList<Visita>();
		Date ahora = new Date();
		
		for(Visita visita : visitas) {
			if(ahora.before(visita.getHoraInicio())) {
				visitasPendientes.add(visita);
			} else {
				historialVisitas.add(visita);
			}
		}
		
		request.setAttribute("visitasPendientes", visitasPendientes);
		request.setAttribute("historialVisitas", historialVisitas);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String homepage = "../frontend/profesional/lista_visitas.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void borrarVisita() throws ServletException, IOException {
		Integer visitaId = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(visitaId);
		List<Cita> citas = citaDAO.listAllById(visitaId);
		
		Date fechaCita = visita.getHoraInicio();
		
		Date ahora = new Date();
		Date tiempoLimite = new Date(fechaCita.getTime() - 3 * 60 * 60 * 1000); //Tiempo límite = 3 horas antes de la cita
		
		String urgenciaTexto = StringEscapeUtils.escapeHtml4(request.getParameter("urgencia"));
		boolean urgencia = !((urgenciaTexto == null) || urgenciaTexto.isBlank());
		boolean pasadoTiempoLimite = ahora.after(tiempoLimite);
		
		if(urgencia || !pasadoTiempoLimite) {
			if(citas != null) {
				for(int i = 0; i < citas.size(); i++) {
					Cita cita = citas.get(i);
					citaDAO.delete(cita.getId());
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
					
				}
			}
			if(visita.getImagenRuta() != null) {
				borrarImagen(visita.getImagenRuta());
			}
			visitaDAO.delete(visitaId);
			listarVisitas("La visita ha sido borrada con éxito.");
		} else {
			request.setAttribute("pasadoTiempoLimite", pasadoTiempoLimite);
			verVisita("Es demasiado tarde para cancelar la cita a menos que se trate de una urgencia, si este es el caso por favor indíquelo.");	
		}
	}

	public void nuevaVisitaFormulario() throws ServletException, IOException {
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
		String message = hayIncompatibilidades(visita);
		if(message.isBlank()) {
			visitaDAO.create(visita);
			listarVisitas("La visita ha sido creada con éxito");
		} else {
			listarVisitas(message);
		}
		
	}

private String hayIncompatibilidades(Visita visita) throws ServletException, IOException {		
		Date ahora = new Date();
		
		if(ahora.after(visita.getHoraInicio())) {									//La visita debe ser a tiempo futuro.
			return "La visita debe ser un evento futuro.";
		} else if(visitaRepetida(visita)) {											//No puede haber dos visitas en la misma localidad el mismo día
			return "No puede haber dos visitas en la misma localidad el mismo día.";
		} else if (visitasSolapadas(visita)) {
			return "La visita se solapa con otras.";
		} else if(!llegaATiempo(visita)) {
			return "No llegarías a tiempo.";
		}
		
		return "";
	}

	private boolean llegaATiempo(Visita visitaNueva) {
		List<Visita> visitas = visitaDAO.listAllByLogin(visitaNueva.getProfesional().getLogin());
		//Visita visitaAnt = null; 		//visitasContiguas[0]
		//Visita visitaPost = null;		//visitasContiguas[1]
		Visita[] visitasContiguas = new Visita[2];
		boolean encontrado = false;
		for(int i = 0; !encontrado && i < visitas.size(); i++) {
			
			Visita visita1 = visitas.get(i);
			if(visitaNueva.getIdVisita() == null || visitaNueva.getIdVisita().intValue() != visita1.getIdVisita().intValue()) {
				if(visita1.getHoraInicio().before(visitaNueva.getHoraInicio())) {
					if(i == visitas.size() - 1){
						visitasContiguas[0] = visita1;
						visitasContiguas[1] = null;
						encontrado = true;
						System.out.println("IF 1");
					} else {
						Visita visita2 = visitas.get(i + 1);
						if(visitaNueva.getIdVisita() == null || visitaNueva.getIdVisita().intValue() != visita2.getIdVisita().intValue()) {
							if(visita2.getHoraInicio().after(visitaNueva.getHoraInicio())) {
								visitasContiguas[0] = visita1;
								visitasContiguas[1] = visita2;
								encontrado = true;
								System.out.println("IF 2");
							}
						}
					}
				} else {
					visitasContiguas[1] = visita1;
					visitasContiguas[0] = null;
					encontrado = true;
					System.out.println("IF 3");
				}
			} else {
				encontrado = true;
				if(i == 0 && visitas.size() > 1) {
					visitasContiguas[1] = visitas.get(i + 1);;
					visitasContiguas[0] = null;
					System.out.println("IF 4");
				} else if(i == visitas.size() - 1 && visitas.size() > 1) {
					visitasContiguas[0] = visitas.get(i - 1);;
					visitasContiguas[1] = null;
					System.out.println("IF 5");
				} else if(visitas.size() > 2){
					visitasContiguas[0] = visitas.get(i - 1);
					visitasContiguas[1] = visitas.get(i + 1);
					System.out.println("IF 6");
				} else {
					visitasContiguas[0] = null;
					visitasContiguas[1] = null;
					System.out.println("IF 7");
				}
			}
		}
		
		LocalidadServicios localidadServicios = new LocalidadServicios(entityManager, request, response);
		double[] coordsVisitaNueva = localidadServicios.getCoordenadas(visitaNueva.getLocalidad());
		for(int i = 0; i < visitasContiguas.length; i++) {
			if(visitasContiguas[i] != null) {
				double[] coordsVisitaContigua = localidadServicios.getCoordenadas(visitasContiguas[i].getLocalidad());
				double tiempoViaje = getTiempoRuta(coordsVisitaNueva, coordsVisitaContigua);
				if(i == 0) {
					double diferencia = (visitaNueva.getHoraInicio().getTime() - visitasContiguas[i].getHoraFin().getTime())/1000;
					System.out.println("Diferencia: " + diferencia);
					if(tiempoViaje > diferencia) {
						return false;
					}
				} else {
					double diferencia = (visitasContiguas[i].getHoraInicio().getTime() - visitaNueva.getHoraFin().getTime())/1000 ;
					System.out.println("Diferencia: " + diferencia);
					if(tiempoViaje > diferencia) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private double getTiempoRuta(double[] coordsNuevo, double[] coordsContiguo) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(
		"https://api.openrouteservice.org/v2/directions/driving-car"
		+ "?api_key=5b3ce3597851110001cf62488e46d28f49534f3094ceb181a7bfe9cc&start="
		+ coordsNuevo[0] + "," + coordsNuevo[1] + "&end=" + coordsContiguo[0] + "," + coordsContiguo[1])
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
		  .get();

		//System.out.println("status: " + response.getStatus());
		//System.out.println("headers: " + response.getHeaders());
		//System.out.println("body:" + response.readEntity(String.class));
		
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		JSONObject json = new JSONObject(responseString);
		JSONArray features = json.getJSONArray("features");
		JSONObject feature = features.getJSONObject(0);
		JSONArray segments = feature.getJSONObject("properties").getJSONArray("segments");
		JSONObject segment = segments.getJSONObject(0);
		double travelTime = segment.getDouble("duration");
		System.out.println(travelTime);
		
		return travelTime;
	}

	private boolean visitaRepetida(Visita visitaNueva) {
		List<Visita> visitas = visitaDAO.listAllByLogin(visitaNueva.getProfesional().getLogin());
		for(Visita visita: visitas) {
			if(visitaNueva.getIdVisita() != null && visita.getIdVisita().intValue() == visitaNueva.getIdVisita().intValue()) {
				return false;
			}
			if(visita.getLocalidad().equals(visitaNueva.getLocalidad()) && visita.getFecha().equals(visitaNueva.getFecha())) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean visitasSolapadas(Visita visitaNueva) {
		List<Visita> visitas = visitaDAO.listAllByLogin(visitaNueva.getProfesional().getLogin());
		
		for(Visita visita: visitas) {
			if(visitaNueva.getIdVisita() == null || visita.getIdVisita().intValue() != visitaNueva.getIdVisita().intValue()) {
				if(visita.getFecha().equals(visitaNueva.getFecha())) {
					if(visitaNueva.getHoraInicio().after(visita.getHoraInicio()) && visitaNueva.getHoraInicio().before(visita.getHoraFin())) {
						return true;
					}
					if(visitaNueva.getHoraFin().after(visita.getHoraInicio()) && visitaNueva.getHoraFin().before(visita.getHoraFin())) {
						return true;
					}
				}
			}
		}		
		return false;
	}
	
	
	private Visita inicializarDatos() throws IOException, ServletException {
		String nombre =  StringEscapeUtils.escapeHtml4(request.getParameter("nombre"));	
		int codPostal = Integer.parseInt(request.getParameter("codPostal"));
		String fechaTexto = request.getParameter("fecha");
		int tiempo = Integer.parseInt(request.getParameter("tiempo"));
		String horaInicioTexto = request.getParameter("horaInicio");
		String horaFinTexto = request.getParameter("horaFin");
		String descripcion = StringEscapeUtils.escapeHtml4(request.getParameter("descripcion"));
		double desplazamiento = Double.parseDouble(request.getParameter("desplazamiento"));
		double precio = Double.parseDouble(request.getParameter("precio"));
				
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		Localidad localidad = localidadDAO.get(codPostal);
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha = null;
		try {
			fecha = dateformat.parse(fechaTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String horaInicioCompleta = fechaTexto + " " + horaInicioTexto;
		String horaFinCompleta = fechaTexto + " " + horaFinTexto;
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		Date horaInicio = null;
		Date horaFin = null;
		try {
			horaInicio = timeformat.parse(horaInicioCompleta);
			horaFin = timeformat.parse(horaFinCompleta);
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
			String src = request.getServletContext().getRealPath("/") + "/img/" + nombreImagen;
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
		//visitaDAO.create(visita);
		return visita;
	}

	private boolean borrarImagen(String ruta) {
		String path = request.getServletContext().getRealPath("/") + ruta.substring(2);
		File file = new File(path);
		return file.delete();
	}
	
	private String nombrarImagen() throws IOException {
		String path = request.getServletContext().getRealPath("/") + "/img/number.txt";
		List<Integer> ints = Files.lines(Paths.get(path)).map(Integer::parseInt).collect(Collectors.toList());
		Integer number = ints.get(0) + 1;
		Files.write(Paths.get(path), number.toString().getBytes());
		// El path de verdad es C:\Users\Nico\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ItinerantApp\img
		//System.out.println(path);
		return "img" + number + ".jpg";
	}

	public void verVisita() throws ServletException, IOException {
		verVisita(null);
	}
	
	public void verVisita(String message) throws ServletException, IOException {
		int visitaId = Integer.parseInt(request.getParameter("id"));
		Visita visita = visitaDAO.get(visitaId);
		request.setAttribute("visita", visita);
		
		Date ahora = new Date();
		request.setAttribute("esFutura", !ahora.after(visita.getHoraInicio()));
		
		List<Cita> citas = new ArrayList(visita.getCitas());
		request.setAttribute("citas", citas);
		request.setAttribute("numeroCitas", citas.size());
		
		request.setAttribute("message", message);
		
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
		Visita visita = inicializarDatos();
		int idVisita = Integer.parseInt(request.getParameter("id"));
		Visita visitaOriginal = visitaDAO.get(idVisita);
		visita.setIdVisita(idVisita);
		String message = hayIncompatibilidades(visita);
		if(message.isBlank()) {
			List<Cita> citas = new ArrayList<>(visita.getCitas());
			
			Date fechaVisita = visita.getHoraInicio();
			
			Date ahora = new Date();
			Date tiempoLimite = new Date(fechaVisita.getTime() - 3 * 60 * 60 * 1000); //Tiempo límite = 3 horas antes de la cita
			
			String urgenciaTexto = StringEscapeUtils.escapeHtml4(request.getParameter("urgencia")); //FALTA AÑADIR URGENCIA EN HTTP
			boolean urgencia = !((urgenciaTexto == null) || urgenciaTexto.isBlank());
			boolean pasadoTiempoLimite = ahora.after(tiempoLimite);
			
			if(urgencia || !pasadoTiempoLimite) {
				for(int i = 0; i < citas.size(); i++) {
					Cita cita = citas.get(i);
					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
					
					String cuerpoAlerta = null;
					
					if(urgencia) { 
						cuerpoAlerta = "Su cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
											+ timeFormat.format(cita.getHoraInicio()) + " ha sido modificada por el siguiente motivo:" + urgenciaTexto;
					} else {
						cuerpoAlerta = "Su cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
											+ timeFormat.format(cita.getHoraInicio()) + " ha sido modificada.";
					}
					Alerta alerta = new Alerta(cita.getCiudadano(), "Cita modificada", cuerpoAlerta, false);
					alertaDAO.create(alerta);
					//Mandar notificación
					AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
					alertaServicios.mandarNotificacion(alerta);
					
				}
				if(visitaOriginal.getImagenRuta() != null) {
					borrarImagen(visitaOriginal.getImagenRuta());
				}
				visitaDAO.update(visita);
				listarVisitas("La visita ha sido modificada con éxito");
			} else {
				request.setAttribute("pasadoTiempoLimite", pasadoTiempoLimite);
				verVisita("Es demasiado tarde para modificar la cita a menos que se trate de una urgencia, si este es el caso por favor indíquelo.");	
			}
		} else {
			listarVisitas(message);	
		}
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
