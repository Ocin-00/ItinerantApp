package com.itinerant.controller.frontend.profesional.cita;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CitaServicios;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profesional/ver_cita")
public class VerCitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public VerCitaServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);
		citaServicios.verCita();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
