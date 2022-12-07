package com.itinerant.controller.frontend.ciudadano;

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
@WebServlet("/inicio/nueva_review")
public class NuevaReviewServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public NuevaReviewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);	
		citaServicios.dejarReview();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);	
		citaServicios.dejarReview();
	}

}
