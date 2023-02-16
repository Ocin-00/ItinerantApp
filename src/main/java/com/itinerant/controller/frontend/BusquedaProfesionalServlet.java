package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.ProfesionalServicios;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/ver_profesional", "/profesional/ver_profesional", "/inicio/ver_profesional", "/admin/ver_profesional", "/supervisor/ver_profesional"})
public class BusquedaProfesionalServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public BusquedaProfesionalServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ProfesionalServicios profesionalServicios = new ProfesionalServicios(entityManager, request, response);
		profesionalServicios.VerProfesionalBusqueda();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
