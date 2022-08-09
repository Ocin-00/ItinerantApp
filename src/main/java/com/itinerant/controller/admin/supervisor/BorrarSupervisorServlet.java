package com.itinerant.controller.admin.supervisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.SupervisorServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/admin/borrar_supervisor")
public class BorrarSupervisorServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public BorrarSupervisorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SupervisorServicios supervisorServicios = new SupervisorServicios(entityManager, request, response);
		
		supervisorServicios.borrarSupervisor();
	}

}
