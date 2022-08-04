package com.itinerant.controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.entity.Profesional;
import com.itinerant.service.ProfesionalServicios;
import com.itinerant.service.SupervisorServicios;

/**
 * Servlet implementation class CrearSupervisorServlet
 */
@WebServlet("/admin/crear_supervisor")
public class CrearSupervisorServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		SupervisorServicios supervisorServicios = new SupervisorServicios(entityManager, request, response);
		supervisorServicios.crearSupervisor();
	}

}
