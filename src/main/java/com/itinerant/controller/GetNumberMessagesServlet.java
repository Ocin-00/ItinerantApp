package com.itinerant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.service.AlertaServicios;
import com.itinerant.service.MensajeServicios;

@WebServlet("/get_number_messages")
public class GetNumberMessagesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public GetNumberMessagesServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MensajeServicios mensajeServicios = new MensajeServicios(entityManager, request, response);
		mensajeServicios.getNumeroMensajes();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
