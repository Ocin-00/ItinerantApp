package com.itinerant.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.service.SupervisorServicios;

/**
 * Servlet implementation class ActualizarSupervisorServlet
 */
@WebServlet("/admin/actualizar_supervisor")
public class ActualizarSupervisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		SupervisorServicios supervisorServicios = new SupervisorServicios(request, response);
		supervisorServicios.actualizarSupervisor();
	}

}