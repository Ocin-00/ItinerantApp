package com.itinerant.controller.frontend.ciudadano;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CitaServicios;

@WebServlet("/inicio/")
public class CiudadanoHomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public CiudadanoHomeServlet() {
        super();
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CitaServicios citaServicios = new CitaServicios(entityManager, request, response);
		citaServicios.listarCitasPendientes();
	}

}
