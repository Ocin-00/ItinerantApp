package com.itinerant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.service.AlertaServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/marcar_visto")
public class MarcarVistoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public MarcarVistoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.marcarVisto();
	}

}
