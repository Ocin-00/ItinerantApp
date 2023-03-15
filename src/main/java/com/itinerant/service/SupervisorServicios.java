package com.itinerant.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.AdministradorDAO;
import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CategoriaDAO;
import com.itinerant.dao.LocalidadDAO;
import com.itinerant.dao.SupervisorDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.entity.Administrador;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Categoria;
import com.itinerant.entity.Cita;
import com.itinerant.entity.CitaId;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Supervisor;
import com.itinerant.entity.Visita;
import com.itinerant.enums.EstadoCivil;
import com.itinerant.enums.NivelAcceso;
import com.itinerant.enums.Sexo;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class SupervisorServicios {
	private EntityManager entityManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private SupervisorDAO supervisorDAO;
	private AdministradorDAO administradorDAO;
	private AlertaDAO alertaDAO;
	private LocalidadDAO localidadDAO;
	private UsuarioInternoDAO usuarioInternoDAO;
	private CategoriaDAO categoriaDAO;
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	public SupervisorServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
		supervisorDAO = new SupervisorDAO(entityManager);
		administradorDAO = new AdministradorDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		localidadDAO = new LocalidadDAO(entityManager);
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		categoriaDAO = new CategoriaDAO(entityManager);
	}
	
	public void listarSupervisores() throws ServletException, IOException {
		listarSupervisores(null);
	}
	
	public void listarSupervisores(String message) throws ServletException, IOException {
		List<Supervisor> supervisores = supervisorDAO.listAll();
		request.setAttribute("supervisores", supervisores);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listpage = "/admin/lista_supervisores.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
		
	}
	
	public Supervisor inicializarDatos() {
		String nombre = StringEscapeUtils.escapeHtml4(request.getParameter("nombre"));
		String apellidos = StringEscapeUtils.escapeHtml4(request.getParameter("apellidos"));
		String telefono = StringEscapeUtils.escapeHtml4(request.getParameter("telefono"));
		String orgCoord= StringEscapeUtils.escapeHtml4(request.getParameter("organismoCoordinador"));
		String nss = StringEscapeUtils.escapeHtml4(request.getParameter("nss"));	
		String fechaNacTexto = request.getParameter("fechaNac");
		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
		
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, request, response);
		String encPassword;
		try {
			encPassword = usuarioInternoServicios.hashPassword(password);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			encPassword = password;
		}
		String nivelAcceso = StringEscapeUtils.escapeHtml4(request.getParameter("nivelAcceso"));
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");	
		Date fechaNac = null;
		try {
			fechaNac = dateformat.parse(fechaNacTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Supervisor supervisor = new Supervisor(login, encPassword, email, nombre, apellidos, fechaNac, telefono, nss, orgCoord, nivelAcceso);
		return supervisor;
	}
	
	public void crearSupervisor() throws ServletException, IOException {
		
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, request, response);
		Supervisor supervisor = inicializarDatos();
		if(usuarioInternoServicios.emailRepetido(supervisor.getEmail())) {
			String message = "El supervisor no pudo ser creado. Ya existe otro usuario con el email " + supervisor.getEmail() + ".";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		} else if (usuarioInternoServicios.loginRepetido(supervisor.getLogin())) {
			String message = "El supervisor no pudo ser creado. Ya existe otro usuario con el nombre de usuario " + supervisor.getLogin() + ".";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		} else {
			supervisorDAO.create(supervisor);
			listarSupervisores("El supervisor ha sido creado con éxito");
		}
		
	}

	public void editarSupervisor() throws ServletException, IOException {
		String supervisorId = StringEscapeUtils.escapeHtml4(request.getParameter("id"));
		Supervisor supervisor = supervisorDAO.get(supervisorId);
		
		String editpage = "supervisor_form.jsp";
		request.setAttribute("supervisor", supervisor);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editpage);
		requestDispatcher.forward(request, response);
	}

	public void actualizarSupervisor() throws ServletException, IOException {
		String adminId = (String) request.getSession().getAttribute("userLogin");
		Administrador admin = administradorDAO.get(adminId);
		Supervisor supervisor = inicializarDatos();
		supervisorDAO.update(supervisor);
		
		String cuerpoAlerta = "El administrador " + StringEscapeUtils.unescapeHtml4(admin.toString()) + ", cuyo organismo coordinador es  " 
				+ StringEscapeUtils.unescapeHtml4(admin.getOrganismoCoordinador()) + " y de login: " + StringEscapeUtils.unescapeHtml4(admin.getLogin())
				+ " ha modificado la información de su cuenta.";
		Alerta alerta = new Alerta(supervisor,  StringEscapeUtils.escapeHtml4("Cuenta modificada por administrador"), StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
				
		AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.mandarNotificacion(alerta);
				
		alertaDAO.create(alerta);
				
		
		listarSupervisores("El supervisor ha sido actualizado con éxito");
	}

	public void borrarSupervisor() throws ServletException, IOException {
		String supervisorId = request.getParameter("id");
		supervisorDAO.delete(supervisorId);
		listarSupervisores("El usuario ha sido borrado con éxito.");
	}

	public void estadisticasGenerales() throws ServletException, IOException {
		String supervisorId = (String) request.getSession().getAttribute("userLogin");
		Supervisor supervisor = supervisorDAO.get(supervisorId);
		/*
		if(supervisor.getNivelAcceso().equals(NivelAcceso.GENERAL.toString())) {
			request.setAttribute("tieneAcceso", true);
			//getStatistics(NivelAcceso.GENERAL.toString());
		} else {
			request.setAttribute("tieneAcceso", false);
		}
		*/
		String homepage = "/supervisor/estadisticas_generales.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(homepage);
		requestDispatcher.forward(request, response);
	}

	public void estadisticasMancomunales() throws ServletException, IOException {
		String supervisorId = (String) request.getSession().getAttribute("userLogin");
		Supervisor supervisor = supervisorDAO.get(supervisorId);
		
		if(supervisor.getNivelAcceso().equals(NivelAcceso.MUNICIPAL.toString()) || supervisor.getNivelAcceso().equals(NivelAcceso.GENERAL.toString())) {
			request.setAttribute("tieneAcceso", false);
		} else {
			request.setAttribute("tieneAcceso", true);	
			request.setAttribute("mancomunidad", supervisor.getOrganismoCoordinador());
		}		
		
		String homepage = "/supervisor/estadisticas_mancomunales.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(homepage);
		requestDispatcher.forward(request, response);
		
	}

	public void estadisticasMunicipales() throws ServletException, IOException {
		String supervisorId = (String) request.getSession().getAttribute("userLogin");
		Supervisor supervisor = supervisorDAO.get(supervisorId);
		
		if(supervisor.getNivelAcceso().equals(NivelAcceso.GENERAL.toString()) || supervisor.getNivelAcceso().equals(NivelAcceso.MANCOMUNAL.toString())) {
			request.setAttribute("tieneAcceso", false);
		} else {
			request.setAttribute("tieneAcceso", true);
			request.setAttribute("localidad", supervisor.getOrganismoCoordinador());
		}
		
		String homepage = "/supervisor/estadisticas_municipales.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(homepage);
		requestDispatcher.forward(request, response);
		
	}
	
	public List<String> getNames(Class<? extends Enum<?>> e) {
	    return Arrays.asList(Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new));
	}
	
	public void getStatistics() throws IOException {
		String ambito = request.getParameter("ambito");
		String lugar = request.getParameter("id");
		int tipo = Integer.parseInt(request.getParameter("tipo"));
		//if(ambito.equals(NivelAcceso.GENERAL.toString()) || (lugar != null && !lugar.isBlank() && lugar.isEmpty())) {
			Map<String, Long> genero = usuarioInternoDAO.getGenderStatistics(ambito, lugar, tipo);
			String[] listaSexoArray = {"Hombre", "Mujer", "Otro", "Prefiere no decir"};
			List<String> estadosGenero = Arrays.asList(listaSexoArray);
			
			JSONArray jsArray = new JSONArray();
			JSONArray generoJSON = new JSONArray();
			for (String estado : estadosGenero) {
				Long count = genero.get(estado.toString());
				if(count != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", count);
					jobj.put("label", estado.toString());
					jobj.toString();
					generoJSON.put(jobj);
				}		
			 }
			
			Map<String, Long> estadoCivil = usuarioInternoDAO.getMaritalStatusStatistics(ambito, lugar, tipo);
			String[] listaEstadosCivilesArray = new String[]{"Casado/a", "Divorciado/a", "Soltero/a","Viudo/a", "Prefiere no decir"};
			List<String> estadosestadoCivil = Arrays.asList(listaEstadosCivilesArray);
			JSONArray estadoCivilJSON = new JSONArray();
			for (String estado : estadosestadoCivil) {
				System.out.println(estado);
				Long count = estadoCivil.get(estado.toString());
				if(count != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", count);
					jobj.put("label", estado.toString());
					jobj.toString();
					estadoCivilJSON.put(jobj);
				}		
			 }
			
			Map<String, Long> roles = usuarioInternoDAO.getRoleStatistics(ambito, lugar, tipo);
			String[] listaRolesArray = new String[]{"CIUDADANO", "PROFESIONAL"};
			List<String> estadosRoles = Arrays.asList(listaRolesArray);
			JSONArray rolesJSON = new JSONArray();
			for (String estado : estadosRoles) {
				System.out.println(estado);
				Long count = roles.get(estado.toString());
				if(count != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", count);
					jobj.put("label", estado.toString());
					jobj.toString();
					rolesJSON.put(jobj);
				}		
			 }
			List<Long> edades = usuarioInternoDAO.getAgeStatistics(ambito, lugar, tipo);
			System.out.println(edades);
			System.out.println(edades.size());
			String[] listaEdades = new String[]{"0-5", "5-10", "10-15", "15-20", "20-25", "25-30", "30-35", "35-40", "40-45", "45-50", "50-55", "55-60", "60-65", "65-70", "70-75", "75-80", "80-85", "85-90", "90+"};
			int[] frecuencias = new int[listaEdades.length];
			
			for (int i = 0; i < listaEdades.length -1; i++) {
			    String[] rangoEdad = listaEdades[i].split("-");
			    int edadMinima = Integer.parseInt(rangoEdad[0]);
			    int edadMaxima = Integer.parseInt(rangoEdad[1]);
			    for (long edad : edades) {
			        if (edad >= edadMinima && edad <= edadMaxima) {
			            frecuencias[i]++;
			        }
			    }
			}
			for (long edad : edades) {
		        if (edad >= 90) {
		            frecuencias[listaEdades.length]++;
		        }
		    }
			
			JSONArray edadesJSON = new JSONArray();
			for (int i = 0; i < listaEdades.length; i++) {
				if(frecuencias != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", frecuencias[i]);
					jobj.put("label", listaEdades[i]);
					jobj.toString();
					edadesJSON.put(jobj);
				}		
			 }
			
			List<Localidad> localidades = usuarioInternoDAO.getLocationStatistics(ambito, lugar, tipo);
			List<Localidad> todasLocalidades = localidadDAO.listAll();
			int[] frecuenciasLocalidades = new int[todasLocalidades.size()];
			
			for (int i = 0; i < todasLocalidades.size(); i++) {
			    for (Localidad localidad : localidades) {
			        if (localidad.equals(todasLocalidades.get(i))) {
			        	frecuenciasLocalidades[i]++;
			        }
			    }
			}
			
			JSONArray localidadesJSON = new JSONArray();
			for (int i = 0; i < todasLocalidades.size(); i++) {
				if(frecuencias != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", frecuenciasLocalidades[i]);
					jobj.put("label",URLEncoder.encode(StringEscapeUtils.unescapeHtml4(todasLocalidades.get(i).getNombre()), "UTF-8"));
					jobj.toString();
					localidadesJSON.put(jobj);
				}		
			 }
			
			Map<Categoria, Long> categoriasOferta = categoriaDAO.StatisticsSupply(ambito, lugar);
			List<Categoria> categorias = categoriaDAO.listAll();
			JSONArray categoriasOfertaJSON = new JSONArray();
			for (Categoria categoria : categorias) {
				System.out.println(categoria.getNombre());
				Long count = categoriasOferta.get(categoria);
				if(count != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", count);
					jobj.put("label", URLEncoder.encode(StringEscapeUtils.unescapeHtml4(categoria.getNombre()), "UTF-8"));
					jobj.toString();
					categoriasOfertaJSON.put(jobj);
				}		
			 }
			
			Map<Categoria, Long> categoriasDemanda = categoriaDAO.StatisticsDemand(ambito, lugar);
			JSONArray categoriasDemandaJSON = new JSONArray();
			for (Categoria categoria : categorias) {
				System.out.println(categoria.getNombre());
				Long count = categoriasDemanda.get(categoria);
				if(count != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", count);
					jobj.put("label", URLEncoder.encode(StringEscapeUtils.unescapeHtml4(categoria.getNombre()), "UTF-8"));
					jobj.toString();
					categoriasDemandaJSON.put(jobj);
				}		
			 }
			
			Map<Localidad, Long> localidadCitas = localidadDAO.StatisticsCitas(ambito, lugar);
			JSONArray localidadCitasJSON = new JSONArray();
			for (Localidad localidad : todasLocalidades) {
				System.out.println(localidad.getNombre());
				Long count = localidadCitas.get(localidad);
				if(count != null) {
					JSONObject jobj = new JSONObject();
					jobj.put("y", count);
					jobj.put("label", URLEncoder.encode(StringEscapeUtils.unescapeHtml4(localidad.getNombre()), "UTF-8"));
					jobj.toString();
					localidadCitasJSON.put(jobj);
				}		
			 }
			
			jsArray.put(generoJSON);
			jsArray.put(estadoCivilJSON);
			jsArray.put(rolesJSON);
			jsArray.put(edadesJSON);
			jsArray.put(localidadesJSON);
			jsArray.put(categoriasOfertaJSON);
			jsArray.put(categoriasDemandaJSON);
			jsArray.put(localidadCitasJSON);
			response.getWriter().write(jsArray.toString());
		//}
	}
	
	public void pedirPermisos() throws ServletException, IOException {
		String nivel = request.getParameter("id");
		String acceso = request.getParameter("acceso");
		String supervisorId = (String) request.getSession().getAttribute("userLogin");
		Supervisor supervisor = supervisorDAO.get(supervisorId);
		
		String cuerpoAlerta = "El supervisor " + StringEscapeUtils.unescapeHtml4(supervisor.toString()) + ", cuyo organismo coordinador es  " 
							+ StringEscapeUtils.unescapeHtml4(supervisor.getOrganismoCoordinador()) + " y de login: " + StringEscapeUtils.unescapeHtml4(supervisor.getLogin())
							+ " ha pedido que se le otorgue el acceso de nivel " + nivel + " para la entidad " + acceso;
		List<Administrador> admins = administradorDAO.listAll();
		for(int i = 0; i < admins.size(); i++) {
			Alerta alerta = new Alerta(admins.get(i),  StringEscapeUtils.escapeHtml4("Petición de acceso"), StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
			
			AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
			alertaServicios.mandarNotificacion(alerta);
			
			alertaDAO.create(alerta);
		}
		
		response.sendRedirect("../supervisor");
	}

	
}
