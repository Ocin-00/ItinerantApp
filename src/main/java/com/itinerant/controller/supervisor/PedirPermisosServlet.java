package com.itinerant.controller.supervisor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CategoriaServicios;
import com.itinerant.service.SupervisorServicios;

@WebServlet("/supervisor/pedir_permisos")
public class PedirPermisosServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public PedirPermisosServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("UTF-8");
		
		SupervisorServicios supervisorServicios = new SupervisorServicios(entityManager, request, response);		
		supervisorServicios.pedirPermisos();				
	}


}
