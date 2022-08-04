package com.itinerant.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.entity.Supervisor;
import com.itinerant.service.SupervisorServicios;

@WebServlet("/admin/lista_supervisores/")
public class ListaSupervisoresServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaSupervisoresServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		SupervisorServicios supervisorServicios = new SupervisorServicios(entityManager, request, response);		
		supervisorServicios.listarSupervisores();				
	}


}
