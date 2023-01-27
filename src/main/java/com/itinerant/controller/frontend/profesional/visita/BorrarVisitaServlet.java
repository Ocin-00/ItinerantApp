package com.itinerant.controller.frontend.profesional.visita;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CitaServicios;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profesional/borrar_visita")
public class BorrarVisitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public BorrarVisitaServlet() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		visitaServicios.borrarVisita();
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		visitaServicios.borrarVisita();
	}

}
