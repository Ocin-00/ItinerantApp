package com.itinerant.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.ChatDAO;
import com.itinerant.service.AlertaServicios;
import com.itinerant.service.ChatServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet({"/profesional/contactar", "/inicio/contactar", "/admin/contactar", "/supervisor/contactar"})
public class ContactarServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public ContactarServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChatServicios chatServicios = new ChatServicios(entityManager, request, response);
		chatServicios.contactar();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
