package com.itinerant.controller.frontend.profesional.jornada;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.AlertaServicios;
import com.itinerant.service.JornadaServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/profesional/get_localidades")
public class GetLocalidadesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public GetLocalidadesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		JornadaServicios jornadaServicios = new JornadaServicios(entityManager, request, response);
		jornadaServicios.getLocalidades();
	}

}
