package com.itinerant.controller.frontend.profesional.visita;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profesional/ver_visita")
public class VerVisitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public VerVisitaServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		visitaServicios.VerVisita();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}