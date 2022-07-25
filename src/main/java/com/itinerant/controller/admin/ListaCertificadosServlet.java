package com.itinerant.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.entity.Certificado;
import com.itinerant.service.CertificadoServicios;

@WebServlet("/admin/lista_certificados/")
public class ListaCertificadosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaCertificadosServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		CertificadoServicios certificadoServicios = new CertificadoServicios(request, response);
		certificadoServicios.listarCertificadosNoValidados();
	}


}
