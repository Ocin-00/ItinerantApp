package com.itinerant.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Visita;

public class ProfesionalServicios {
	private ProfesionalDAO profesionalDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ProfesionalServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		profesionalDAO = new ProfesionalDAO(entityManager);
		this.request = request;
		this.response = response;
	}
	
	public void listarProfesionalesNoValidados() throws ServletException, IOException {
		listarProfesionalesNoValidados(null);
	}
	
	public void listarProfesionalesNoValidados(String message) throws ServletException, IOException {
		List<Profesional> profesionalesSinValidar = profesionalDAO.listAllNotValid();
		request.setAttribute("profesionalesSinValidar", profesionalesSinValidar);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listpage = "/admin/lista_profesionales.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void borrarProfesional() throws ServletException, IOException {
		String profesionalId = request.getParameter("id");
		profesionalDAO.delete(profesionalId);
		listarProfesionalesNoValidados("El usuario ha sido borrado con éxito.");
	}

	public void validarProfesional() throws ServletException, IOException {
		String profesionalId = request.getParameter("id");
		Profesional profesional = profesionalDAO.get(profesionalId);
		profesional.setValidez(true);
		profesionalDAO.update(profesional);
		listarProfesionalesNoValidados("La cuenta del profesional ha sido validada con éxito.");
	}

	public void buscar(String keyword) {
		List<Profesional> listaProfesionales = null;
		if(keyword.equals("")) {
			listaProfesionales = profesionalDAO.listAllValid();
		} else {
			listaProfesionales = profesionalDAO.search(keyword);
		}
		request.setAttribute("listaProfesionales", listaProfesionales);
		
	}
}
