package com.itinerant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.service.AlertaServicios;
import com.itinerant.service.ChatServicios;

@WebServlet("/get_chats")
public class GetChatsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public GetChatsServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ChatServicios chatServicios = new ChatServicios(entityManager, request, response);
		chatServicios.listarChats();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
