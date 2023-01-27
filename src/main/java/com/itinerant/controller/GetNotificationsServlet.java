package com.itinerant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.service.AlertaServicios;

@WebServlet("/get_notifications")
public class GetNotificationsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public GetNotificationsServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.getAlertas();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//doGet(request, response);
		/*AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.getAlertas();*/
	}

}
