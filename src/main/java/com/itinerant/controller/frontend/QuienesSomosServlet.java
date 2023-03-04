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

@WebServlet({"/profesional/quienes_somos", "/inicio/quienes_somos", "/admin/quienes_somos", "/supervisor/quienes_somos", "/quienes_somos"})
public class QuienesSomosServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
     
    public QuienesSomosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String rol = (String) request.getSession().getAttribute("rol");
		System.out.println(rol);
		String homepage = "";
		if(rol != null) {
			homepage = "../frontend/quienes_somos.jsp";	
			request.setAttribute("hayRol", true);
		} else {
			homepage = "frontend/quienes_somos.jsp";	
			request.setAttribute("hayRol", false);
		}	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			System.out.println(rol);
			if(rol.equals(Rol.ADMINISTRADOR.toString())) {
				homepage = "admin/";
			} else if(rol.equals(Rol.SUPERVISOR.toString())) {
				homepage = "supervisor/";
			} else if(rol.equals(Rol.CIUDADANO.toString())) {
				homepage = "inicio/";
			} else if(rol.equals(Rol.PROFESIONAL.toString())) {
				homepage = "profesional/";
			}
			response.sendRedirect(homepage);
			//RequestDispatcher dispatcher2 = request.getRequestDispatcher(homepage);
			//dispatcher2.(request, response);
		}
	}

}
