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

import com.itinerant.dao.CertificadoDAO;
import com.itinerant.entity.Certificado;

public class CertificadoServicios {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private CertificadoDAO certificadoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CertificadoServicios(HttpServletRequest request, HttpServletResponse response) {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		certificadoDAO = new CertificadoDAO(entityManager);
		this.request = request;
		this.response = response;
	}
	
	public void listarCertificadosNoValidados() throws ServletException, IOException {
		List<Certificado> certificadosSinValidar = certificadoDAO.listAllNotValid();
		request.setAttribute("certificadosSinValidar", certificadosSinValidar);
		
		String listpage = "/admin/lista_certificados.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}
}
