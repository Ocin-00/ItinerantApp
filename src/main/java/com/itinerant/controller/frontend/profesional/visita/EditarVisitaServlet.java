package com.itinerant.controller.frontend.profesional.visita;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profesional/editar_visita")
public class EditarVisitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public EditarVisitaServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		visitaServicios.vistaEdicionVisita();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
