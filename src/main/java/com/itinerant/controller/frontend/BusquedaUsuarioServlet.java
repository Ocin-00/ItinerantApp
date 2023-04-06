package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.ProfesionalServicios;
import com.itinerant.service.UsuarioInternoServicios;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/admin/ver_usuario"})
public class BusquedaUsuarioServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public BusquedaUsuarioServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, request, response);
		usuarioInternoServicios.VerUsuarioBusqueda();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
