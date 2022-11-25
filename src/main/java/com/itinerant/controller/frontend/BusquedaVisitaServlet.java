package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/visita", "/profesional/visita", "/inicio/visita", "/admin/visita", "/supervisor/visita"})
public class BusquedaVisitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public BusquedaVisitaServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		visitaServicios.VerVisitaBusqueda();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
