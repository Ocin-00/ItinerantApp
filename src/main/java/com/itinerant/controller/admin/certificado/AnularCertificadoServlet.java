package com.itinerant.controller.admin.certificado;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CertificadoServicios;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/admin/anular_certificado")
public class AnularCertificadoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public AnularCertificadoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CertificadoServicios certificadoServicios = new CertificadoServicios(entityManager, request, response);
		
		certificadoServicios.borrarCertificado();
	}

}
