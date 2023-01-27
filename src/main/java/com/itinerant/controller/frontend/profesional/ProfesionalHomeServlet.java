package com.itinerant.controller.frontend.profesional;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.AsyncContext;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.VisitaServicios;

@WebServlet(urlPatterns = {"/profesional/"}, asyncSupported=true)
public class ProfesionalHomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public ProfesionalHomeServlet() {
        super();
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String homepage = "../frontend/profesional/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);

	}

}
