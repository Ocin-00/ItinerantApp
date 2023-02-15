package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.service.ProfesionalServicios;
import com.itinerant.service.UsuarioInternoServicios;
import com.itinerant.service.VisitaServicios;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/profesional/eliminar_cuenta", "/inicio/eliminar_cuenta", "/admin/eliminar_cuenta", "/supervisor/eliminar_cuenta"})
public class EliminarCuentaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public EliminarCuentaServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, request, response);
		usuarioInternoServicios.eliminarCuenta();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
