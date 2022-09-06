package com.itinerant.controller.frontend.profesional;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

@WebServlet("/frontend/profesional/nueva_visita")
public class NuevaVisitaServlet extends BaseServlet {
	

	private static final long serialVersionUID = 1L;
 
    public NuevaVisitaServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, req, resp);
		visitaServicios.NuevaVisitaFormulario();
	}
}
