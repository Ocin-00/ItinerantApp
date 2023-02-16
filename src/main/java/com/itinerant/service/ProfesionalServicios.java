package com.itinerant.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CertificadoDAO;
import com.itinerant.dao.CitaDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Certificado;
import com.itinerant.entity.Cita;
import com.itinerant.entity.Ciudadano;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Visita;
import com.itinerant.enums.Rol;

import org.apache.commons.text.StringEscapeUtils;

public class ProfesionalServicios {
	private ProfesionalDAO profesionalDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AlertaDAO alertaDAO;
	private CitaDAO citaDAO;
	private VisitaDAO visitaDAO;
	private CertificadoDAO certificadoDAO;
	private EntityManager entityManager;

	public ProfesionalServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		profesionalDAO = new ProfesionalDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		citaDAO = new CitaDAO(entityManager);
		visitaDAO = new VisitaDAO(entityManager);
		certificadoDAO = new CertificadoDAO(entityManager);
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
	}
	
	public void listarProfesionalesNoValidados() throws ServletException, IOException {
		listarProfesionalesNoValidados(null);
	}
	
	public void listarProfesionalesNoValidados(String message) throws ServletException, IOException {
		List<Profesional> profesionalesSinValidar = profesionalDAO.listAllNotValid();
		request.setAttribute("profesionalesSinValidar", profesionalesSinValidar);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listpage = "/admin/lista_profesionales.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void borrarProfesional() throws ServletException, IOException {
		String profesionalId = StringEscapeUtils.escapeHtml4(request.getParameter("id"));
		profesionalDAO.delete(profesionalId);
		listarProfesionalesNoValidados("El usuario ha sido borrado con éxito.");
	}

	public void validarProfesional() throws ServletException, IOException {
		String profesionalId = StringEscapeUtils.escapeHtml4(request.getParameter("id"));
		Profesional profesional = profesionalDAO.get(profesionalId);
		profesional.setValidez(true);
		profesionalDAO.update(profesional);
		String cuerpoAlerta = "Enhorabuena, su cuenta ha sido sido validada.";								
		Alerta alerta = new Alerta(profesional, "Cuenta validada", cuerpoAlerta, false);
		
		AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
		alertaServicios.mandarNotificacion(alerta);
		
		alertaDAO.create(alerta);
		listarProfesionalesNoValidados("La cuenta del profesional ha sido validada con éxito.");
	}

	public void buscar(String keyword) {
		List<Profesional> listaProfesionales = null;
		if(keyword.equals("")) {
			listaProfesionales = profesionalDAO.listAllValid();
		} else {
			listaProfesionales = profesionalDAO.search(keyword);
		}
		request.setAttribute("listaProfesionales", listaProfesionales);
		
	}

	public void VerProfesionalBusqueda() throws ServletException, IOException {
		String profesionalId = (String) request.getParameter("id");
		String login = (String) request.getSession().getAttribute("userLogin");
		Profesional profesional = profesionalDAO.get(profesionalId);
		request.setAttribute("profesional", profesional);
		
		List<Visita> visitas = visitaDAO.listAllByLogin(profesionalId);
		Date ahora = new Date();
		System.out.println(visitas);
		int suma = 0;
		int numero = 0;
		List<String> reviews = new ArrayList<>();
		if(visitas != null) {
			for(int i = 0; i < visitas.size(); i++) {
				List<Cita> citas = new ArrayList<Cita>(visitas.get(i).getCitas());
				for(Cita cita : citas) {
					//if(ahora.before(cita.getHoraInicio())) {
						if(cita.getPuntuacion() != null) {
							suma += cita.getPuntuacion();
							numero++;
							reviews.add(cita.getReview());
						}
					//}
				}
			}
		}
		System.out.println(suma);
		System.out.println(numero);
		if(numero == 0) {
			request.setAttribute("noReviews", true);
		} else {
			double avg = ((double) suma) / numero;
			DecimalFormat df = new DecimalFormat("#.#");
			request.setAttribute("noReviews", df.format(avg));
			request.setAttribute("averageReview", avg);
			request.setAttribute("reviews", reviews);
		}
	
		List<Certificado> certificados = certificadoDAO.listAllValidByLogin(profesionalId);
		request.setAttribute("certificados", certificados);
		
		String homepage = "frontend/inicio/busqueda_profesional.jsp";
		String rol = (String) request.getSession().getAttribute("rol");
		if(rol != null) {
			homepage = "../frontend/inicio/busqueda_profesional.jsp";
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);	
	}
}
