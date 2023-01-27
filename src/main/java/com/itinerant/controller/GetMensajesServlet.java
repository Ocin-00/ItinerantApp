package com.itinerant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.service.AlertaServicios;
import com.itinerant.service.ChatServicios;
import com.itinerant.service.MensajeServicios;

@WebServlet("/get_mensajes")
public class GetMensajesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public GetMensajesServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		MensajeServicios mensajeServicios = new MensajeServicios(entityManager, request, response);
		mensajeServicios.getMensajes();
	}

}
