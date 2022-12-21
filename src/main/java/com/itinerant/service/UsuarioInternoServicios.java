package com.itinerant.service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itinerant.dao.AdministradorDAO;
import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CategoriaDAO;
import com.itinerant.dao.CiudadanoDAO;
import com.itinerant.dao.LocalidadDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.entity.Administrador;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Categoria;
import com.itinerant.entity.Ciudadano;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.Rol;
import com.itinerant.enums.Sexo;

public class UsuarioInternoServicios {
	
	private UsuarioInternoDAO usuarioInternoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private EntityManager entityManager;
	private AlertaDAO alertaDAO;
	private AdministradorDAO administradorDAO;

	public UsuarioInternoServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		administradorDAO = new AdministradorDAO(entityManager);
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
	}

	public boolean emailRepetido(String email) {
		return usuarioInternoDAO.findByEmail(email) != null;		
	}
	
	public boolean loginRepetido(String login) {
		return usuarioInternoDAO.get(login) != null;		
	}
	
	public void removeSessionAttributes() {
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("userLogin");
		session.removeAttribute("userLogin");
		session.removeAttribute("rol");
		session.removeAttribute("misAlertas");
		/*
		HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
		pilasUsuarios.remove(login);
		request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
		*/
		
	}
	
	public void login(String login, String password) throws ServletException, IOException {
		if(login == null || password == null) {
			login = request.getParameter("login");
			password = request.getParameter("password");
		}
		boolean loginResult = usuarioInternoDAO.checkLogin(login, password);
		
		if(loginResult) {
			String homepage = null;
			if(request.getSession().getAttribute("userLogin") != null) {
				removeSessionAttributes();
			}
			request.getSession().setAttribute("userLogin", login);			
			UsuarioInterno usuario = usuarioInternoDAO.get(login);
			String rol = usuario.getRol();
			request.getSession().setAttribute("rol", rol);
			
			HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
			
			Stack<Alerta> notificaciones;
			notificaciones = pilasUsuarios.get(login);
			
			if(notificaciones == null) {
				List<Alerta> alertas = alertaDAO.listAllByLogin(login);
				notificaciones = new Stack<>();
				notificaciones.addAll(alertas);
			}
			
			request.getSession().setAttribute("misAlertas", notificaciones);
			
			pilasUsuarios.put(login, notificaciones);			
			request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
			
			if(rol.equals(Rol.ADMINISTRADOR.toString())) {
				homepage = "/admin/";
			} else if(rol.equals(Rol.SUPERVISOR.toString())) {
				homepage = "/supervisor/";
			} else if(rol.equals(Rol.CIUDADANO.toString())) {
				homepage = "/inicio/";
			} else if(rol.equals(Rol.PROFESIONAL.toString())) {
				homepage = "/profesional/";
			}
			response.sendRedirect(request.getContextPath() + homepage);
		} else {
			String message = "Error de inicio de sesión";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("frontend/login.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	
	public void login() throws ServletException, IOException {
		login(null, null);
	}

	public String getRol(String login) {
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		return usuario.getRol();
	}

	public void register() throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String telefono = request.getParameter("telefono");
		String direccion = request.getParameter("direccion");
		int codPostal = Integer.parseInt(request.getParameter("codPostal"));
		String fechaNacTexto = request.getParameter("fechaNac");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String rol = request.getParameter("tipoCuenta");
		String sexo = request.getParameter("sexo");
		String estadoCivil = request.getParameter("estadoCivil");
		String formacion = request.getParameter("formacion");
		
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		Localidad municipio = localidadDAO.get(codPostal);
		String localizacion = direccion + " " + municipio.getNombre();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fechaNac = null;
		try {
			fechaNac = dateformat.parse(fechaNacTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Date fechaRegistro = new Date();
		
		if(this.emailRepetido(email)) {
			String message = "No se pudo crear la cuenta. Ya existe otro usuario con el email " + email + ".";
			request.setAttribute("message", message);
			registerView();
		} else if (this.loginRepetido(login)) {
			String message = "No se pudo crear la cuenta. Ya existe otro usuario con el nombre de usuario " + login + ".";
			request.setAttribute("message", message);
			registerView();
		} else if(rol.equals(Rol.PROFESIONAL.toString())) {
			Profesional profesional = new Profesional(login, password, email, nombre, apellidos, fechaNac, localizacion, formacion, telefono, sexo, estadoCivil, null, false, fechaRegistro, null, null, null, null, null, null);
			ProfesionalDAO profesionalDAO = new ProfesionalDAO(entityManager);
			profesionalDAO.create(profesional);
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			String cuerpoAlerta = "Un nuevo usuario profesional se registró el día " + dateFormat.format(fechaRegistro) 
								+ " a las " + timeFormat.format(fechaRegistro);
			List<Administrador> admins = administradorDAO.listAll();
			for(int i = 0; i < admins.size(); i++) {
				Alerta alerta = new Alerta(admins.get(i), "Nuevo profesional", cuerpoAlerta, false);
				
				AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
				alertaServicios.mandarNotificacion(alerta);
				
				alertaDAO.create(alerta);
			}
		} else {
			Ciudadano ciudadano = new Ciudadano(login, password, email, nombre, apellidos, fechaNac, localizacion, 0, sexo, estadoCivil, formacion, telefono, null, null, null, null, null);
			CiudadanoDAO ciudadanoDAO = new CiudadanoDAO(entityManager);
			ciudadanoDAO.create(ciudadano);
		}
		login(login, password);
	}

	private Administrador elegirAdmin() {
		return null;
	}

	public void registerView() throws ServletException, IOException {
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		List<Localidad> localidades = localidadDAO.listAll();
		request.setAttribute("listaLocalidades", localidades);
		
		String[] listaSexoArray = {"Hombre", "Mujer", "Otro", "Prefiere no decir"};
		List<String> listaSexo = Arrays.asList(listaSexoArray);
		request.setAttribute("listaSexo", listaSexo);
		
		String[] listaEstadosCivilesArray = new String[]{"Casado/a", "Divorciado/a", "Soltero/a","Viudo/a", "Prefiere no decir"};
		List<String> listaEstadosCiviles = Arrays.asList(listaEstadosCivilesArray);
		request.setAttribute("listaEstadosCiviles", listaEstadosCiviles);
		
		String homepage = "frontend/register_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
