package com.itinerant.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.CertificadoDAO;
import com.itinerant.entity.Certificado;
import com.itinerant.entity.CertificadoId;

public class CertificadoServicios {
	private CertificadoDAO certificadoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CertificadoServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		certificadoDAO = new CertificadoDAO(entityManager);
		this.request = request;
		this.response = response;
	}
	
	public void listarCertificadosNoValidados() throws ServletException, IOException {
		listarCertificadosNoValidados(null);
	}
	
	public void listarCertificadosNoValidados(String message) throws ServletException, IOException {
		List<Certificado> certificadosSinValidar = certificadoDAO.listAllNotValid();
		request.setAttribute("certificadosSinValidar", certificadosSinValidar);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listpage = "/admin/lista_certificados.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void borrarCertificado() throws ServletException, IOException {
		String profesionalId = request.getParameter("pr");
		String titulo = request.getParameter("tit");
		CertificadoId certificadoId = new CertificadoId(profesionalId, titulo);
		certificadoDAO.delete(certificadoId);
		listarCertificadosNoValidados("El usuario ha sido borrado con éxito.");
	}

	public void validarCertificado() throws ServletException, IOException {
		String profesionalId = request.getParameter("pr");
		String titulo = request.getParameter("tit");
		CertificadoId certificadoId = new CertificadoId(profesionalId, titulo);
		Certificado certificado = certificadoDAO.get(certificadoId);
		certificado.setValidez(true);
		certificadoDAO.update(certificado);
		listarCertificadosNoValidados("La cuenta del profesional ha sido validada con éxito.");
	}
}
