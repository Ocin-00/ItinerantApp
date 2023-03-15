package com.itinerant.controller.supervisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.AlertaServicios;
import com.itinerant.service.JornadaServicios;
import com.itinerant.service.SupervisorServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/supervisor/get_datos")
public class GetDatosServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public GetDatosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		SupervisorServicios supervisorServicios = new SupervisorServicios(entityManager, request, response);
		supervisorServicios.getStatistics();
	}

}
