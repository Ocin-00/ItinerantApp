package com.itinerant.controller.admin.profesional;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.ProfesionalServicios;

@WebServlet("/admin/lista_profesionales/")
public class ListaProfesionalesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaProfesionalesServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("UTF-8");
		
		ProfesionalServicios profesionalServicios = new ProfesionalServicios(entityManager, request, response);
		profesionalServicios.listarProfesionalesNoValidados();
	}


}
