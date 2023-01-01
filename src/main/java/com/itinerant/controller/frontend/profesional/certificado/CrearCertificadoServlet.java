package com.itinerant.controller.frontend.profesional.certificado;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CertificadoServicios;

@WebServlet("/profesional/crear_certificado")
@MultipartConfig(
		fileSizeThreshold = 1024 * 10,  // 10KB
		maxFileSize = 1024 * 1024 * 50,	// 50 MB
		maxRequestSize = 1024 * 1024 * 200   // 200 MB
)
public class CrearCertificadoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public CrearCertificadoServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CertificadoServicios certificadoServicios = new CertificadoServicios(entityManager, request, response);
		certificadoServicios.crearCertificado();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
