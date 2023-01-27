package com.itinerant.controller.frontend.ciudadano;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/inicio/detalles_cita_historial")
public class DetallesCitaPasadaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public DetallesCitaPasadaServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);
		citaServicios.detallesCitaHistorial();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
