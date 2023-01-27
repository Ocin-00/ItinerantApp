package com.itinerant.controller.frontend.profesional.visita;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profesional/actualizar_visita")
@MultipartConfig(
		fileSizeThreshold = 1024 * 10, // 10KB
		maxFileSize = 1024 * 300, 	   // 300 KB
		maxRequestSize = 1024 * 1024   // 1 MB
)
public class ActualizarVisitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public ActualizarVisitaServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		visitaServicios.actualizarVisita();
	}

}
