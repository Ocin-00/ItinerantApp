package com.itinerant.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.entity.Profesional;
import com.itinerant.service.ProfesionalServicios;

@WebServlet("/admin/lista_profesionales/")
public class ListaProfesionalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaProfesionalesServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		ProfesionalServicios profesionalServicios = new ProfesionalServicios(request, response);
		profesionalServicios.listarProfesionalesNoValidados();
	}


}
