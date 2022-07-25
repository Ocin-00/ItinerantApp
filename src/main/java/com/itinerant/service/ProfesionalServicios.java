package com.itinerant.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.entity.Profesional;

public class ProfesionalServicios {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private ProfesionalDAO profesionalDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ProfesionalServicios(HttpServletRequest request, HttpServletResponse response) {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		profesionalDAO = new ProfesionalDAO(entityManager );
		this.request = request;
		this.response = response;
	}
	
	public void listarProfesionalesNoValidados() throws ServletException, IOException {
		List<Profesional> profesionalesSinValidar = profesionalDAO.listAllNotValid();
		request.setAttribute("profesionalesSinValidar", profesionalesSinValidar);
		
		String listpage = "/admin/lista_profesionales.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}
}
