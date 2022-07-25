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
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private SupervisorDAO supervisorDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public SupervisorServicios(HttpServletRequest request, HttpServletResponse response) {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
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
	
	public void crearSupervisor() throws ServletException, IOException {
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
		
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(request, response);
		if(usuarioInternoServicios.emailRepetido(email)) {
			String message = "El supervisor no pudo ser creado. Ya existe otro usuario con el email " + email + ".";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		} else if (usuarioInternoServicios.loginRepetido(login)) {
			String message = "El supervisor no pudo ser creado. Ya existe otro usuario con el nombre de usuario " + login + ".";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		} else {
			Supervisor supervisor = new Supervisor(login, password, email, nombre, apellidos, fechaNac, telefono, nss, orgCoord, nivelAcceso);
			supervisorDAO.create(supervisor);
			listarSupervisores("El supervisor ha sido creado con éxito");
		}
		
	}
}
