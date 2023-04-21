package com.itinerant.controller.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.entity.Alerta;
import com.itinerant.enums.Rol;
import com.itinerant.service.UsuarioInternoServicios;

@WebServlet("/terminos")
public class TerminosServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
     
    public TerminosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String homepage = "frontend/terminos.jsp";		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
