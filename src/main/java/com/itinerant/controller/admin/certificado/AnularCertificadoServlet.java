package com.itinerant.controller.admin.certificado;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CertificadoServicios;

@WebServlet("/admin/anular_certificado")
public class AnularCertificadoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public AnularCertificadoServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		CertificadoServicios certificadoServicios = new CertificadoServicios(entityManager, request, response);
		certificadoServicios.anularCertificado();
	}

}

