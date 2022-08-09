package com.itinerant.controller.admin.certificado;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CertificadoServicios;

@WebServlet("/admin/lista_certificados/")
public class ListaCertificadosServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaCertificadosServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		CertificadoServicios certificadoServicios = new CertificadoServicios(entityManager, request, response);
		certificadoServicios.listarCertificadosNoValidados();
	}


}
