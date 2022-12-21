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
@WebServlet("/limpiar_todo")
public class LimpiarTodoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public LimpiarTodoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.limpiarTodo();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
