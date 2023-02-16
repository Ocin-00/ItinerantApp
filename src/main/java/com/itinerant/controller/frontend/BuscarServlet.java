package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.ProfesionalServicios;
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
		String keywordOriginal = request.getParameter("keyword");
		String keyword =StringEscapeUtils.escapeHtml4(keywordOriginal);
		visitaServicios.buscar(keyword.toLowerCase());
		profesionalServicios.buscar(keyword.toLowerCase());
		
		String homepage = "/frontend/busqueda_externa.jsp";
		if(request.getSession().getAttribute("userLogin") != null) {
			homepage = "../frontend/busqueda_interna.jsp";
		}
		request.setAttribute("keyword", keyword);
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
