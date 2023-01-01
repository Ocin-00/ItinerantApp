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
@WebServlet("/profesional/informar_ausencia")
public class InformarAusenciaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public InformarAusenciaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);	
		citaServicios.informarAusencia();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);	
		citaServicios.informarAusencia();
	}

}