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
import com.itinerant.service.CategoriaServicios;

@WebServlet("")
public class HomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
     
    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//String homepage = "frontend/index.jsp";
		//RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		//dispatcher.forward(request, response);
		CategoriaServicios categoriaServicios = new CategoriaServicios(entityManager, request, response);
		categoriaServicios.categoriasInicio();
	}

}
