package com.itinerant.controller.frontend.profesional;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/frontend/profesional/borrar_visita")
public class BorrarVisitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public BorrarVisitaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);	
		visitaServicios.borrarVisita();
	}

}
