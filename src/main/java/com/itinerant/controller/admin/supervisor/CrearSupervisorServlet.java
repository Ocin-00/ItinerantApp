package com.itinerant.controller.admin.supervisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
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
		request.setCharacterEncoding("UTF-8");
		
		SupervisorServicios supervisorServicios = new SupervisorServicios(entityManager, request, response);
		supervisorServicios.crearSupervisor();
	}

}
