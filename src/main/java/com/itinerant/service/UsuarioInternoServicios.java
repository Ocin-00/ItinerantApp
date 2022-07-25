package com.itinerant.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.SupervisorDAO;
import com.itinerant.dao.UsuarioInternoDAO;

public class UsuarioInternoServicios {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private UsuarioInternoDAO usuarioInternoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UsuarioInternoServicios(HttpServletRequest request, HttpServletResponse response) {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
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

}
