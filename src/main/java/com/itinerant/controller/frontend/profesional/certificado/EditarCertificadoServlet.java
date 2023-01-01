package com.itinerant.controller.frontend.profesional.certificado;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CertificadoServicios;

@WebServlet("/profesional/editar_certificado")
public class EditarCertificadoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public EditarCertificadoServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CertificadoServicios certificadoServicios = new CertificadoServicios(entityManager, request, response);
		certificadoServicios.editarCertificado();
	}

}
