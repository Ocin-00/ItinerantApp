package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.enums.Rol;
import com.itinerant.service.CategoriaServicios;
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

@WebServlet({ "/buscar", "/profesional/buscar", "/inicio/buscar", "/admin/buscar", "/supervisor/buscar"})
public class BuscarServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public BuscarServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		VisitaServicios visitaServicios = new VisitaServicios(entityManager, request, response);
		ProfesionalServicios profesionalServicios = new ProfesionalServicios(entityManager, request, response);
		//CategoriaServicios categoriaServicios = new CategoriaServicios(entityManager, request, response);
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, request, response);
		String keywordOriginal = request.getParameter("keyword");
		String keyword =StringEscapeUtils.escapeHtml4(keywordOriginal);
		String rol = (String) request.getSession().getAttribute("rol");
		//String keywordMal = categoriaServicios.nombreMal(keyword);
		visitaServicios.buscar(keyword.toLowerCase());
		boolean esAdmin = rol != null && rol.equals(Rol.ADMINISTRADOR.toString());
		
		if(esAdmin) {
			usuarioInternoServicios.buscar(keyword.toLowerCase());
		} else {
			profesionalServicios.buscar(keyword.toLowerCase());
		}
		
		String homepage = "/frontend/busqueda_externa.jsp";
		if(request.getSession().getAttribute("userLogin") != null) {
			homepage = "../frontend/busqueda_interna.jsp";
		}
		request.setAttribute("keyword", keyword);
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			
			//System.out.println(rol);
			if(esAdmin) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
