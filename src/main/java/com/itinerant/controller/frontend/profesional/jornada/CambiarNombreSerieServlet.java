package com.itinerant.controller.frontend.profesional.jornada;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.JornadaServicios;

@WebServlet("/profesional/cambiar_nombre_serie")
public class CambiarNombreSerieServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public CambiarNombreSerieServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		JornadaServicios jornadaServicios = new JornadaServicios(entityManager, request, response);
		jornadaServicios.cambiarNombreSerie();
	}

}
