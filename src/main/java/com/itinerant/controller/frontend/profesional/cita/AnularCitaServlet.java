package com.itinerant.controller.frontend.profesional.cita;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CitaServicios;
import com.itinerant.service.VisitaServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/profesional/anular_cita")
public class AnularCitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public AnularCitaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);	
		citaServicios.anularCitaProfesional();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);	
		citaServicios.anularCitaProfesional();
	}

}
