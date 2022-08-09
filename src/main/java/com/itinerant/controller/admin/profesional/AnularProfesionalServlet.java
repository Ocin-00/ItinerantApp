package com.itinerant.controller.admin.profesional;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.ProfesionalServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/admin/anular_profesional")
public class AnularProfesionalServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public AnularProfesionalServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProfesionalServicios profesionalServicios = new ProfesionalServicios(entityManager, request, response);
		
		profesionalServicios.borrarProfesional();
	}

}
