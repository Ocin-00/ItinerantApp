package com.itinerant.service;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.Rol;

public class UsuarioInternoServicios {
	
	private UsuarioInternoDAO usuarioInternoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UsuarioInternoServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		this.request = request;
		this.response = response;
	}

	public boolean emailRepetido(String email) {
		return usuarioInternoDAO.findByEmail(email) != null;		
	}
	
	public boolean loginRepetido(String login) {
		return usuarioInternoDAO.get(login) != null;		
	}
	
	public void login() throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		boolean loginResult = usuarioInternoDAO.checkLogin(login, password);
		
		if(loginResult) {
			String homepage = null;
			request.getSession().setAttribute("userLogin", login);
			UsuarioInterno usuario = usuarioInternoDAO.get(login);
			
			if(usuario.getRol().equals(Rol.ADMINISTRADOR.toString())) {
				homepage = "/admin/";
			} else if(usuario.getRol().equals(Rol.SUPERVISOR.toString())) {
				homepage = "/supervisor/";
			} else if(usuario.getRol().equals(Rol.CIUDADANO.toString())) {
				homepage = "/inicio/";
			} else if(usuario.getRol().equals(Rol.PROFESIONAL.toString())) {
				homepage = "/profesional/";
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(homepage);
			requestDispatcher.forward(request, response);
		} else {
			String message = "Error de inicio de sesión";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	public String getRol(String login) {
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		return usuario.getRol();
	}

}
