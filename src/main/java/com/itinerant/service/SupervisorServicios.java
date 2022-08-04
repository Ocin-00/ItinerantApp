package com.itinerant.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.SupervisorDAO;
import com.itinerant.entity.Supervisor;

public class SupervisorServicios {
	private EntityManager entityManager;
	private SupervisorDAO supervisorDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public SupervisorServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.entityManager = entityManager;
		supervisorDAO = new SupervisorDAO(entityManager);
		this.request = request;
		this.response = response;
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
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String telefono = request.getParameter("telefono");
		String orgCoord= request.getParameter("organismoCoordinador");
		String nss = request.getParameter("nss");	
		String fechaNacTexto = request.getParameter("fechaNac");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String nivelAcceso = request.getParameter("nivelAcceso");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fechaNac = null;
		try {
			fechaNac = dateformat.parse(fechaNacTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Supervisor supervisor = new Supervisor(login, password, email, nombre, apellidos, fechaNac, telefono, nss, orgCoord, nivelAcceso);
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
		String supervisorId = request.getParameter("id");
		Supervisor supervisor = supervisorDAO.get(supervisorId);
		System.out.println(supervisor.toString());
		
		String editpage = "supervisor_form.jsp";
		request.setAttribute("supervisor", supervisor);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editpage);
		requestDispatcher.forward(request, response);
	}

	public void actualizarSupervisor() throws ServletException, IOException {
		Supervisor supervisor = inicializarDatos();
		supervisorDAO.update(supervisor);
		listarSupervisores("El supervisor ha sido actualizado con éxito");
	}

	public void borrarSupervisor() throws ServletException, IOException {
		String supervisorId = request.getParameter("id");
		supervisorDAO.delete(supervisorId);
		listarSupervisores("El usuario ha sido borrado con éxito.");
	}
}
