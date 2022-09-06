package com.itinerant.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.LocalidadDAO;
import com.itinerant.entity.Localidad;

public class LocalidadServicios {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private LocalidadDAO localidadDAO;

	public LocalidadServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		localidadDAO = new LocalidadDAO(entityManager);
		this.request = request;
		this.response = response;		
	}
	public Localidad getLocalidad(int codPostal) {
		return localidadDAO.get(localidadDAO);
	}
	public List<Localidad> listarLocalidades() {
		return localidadDAO.listAll();
	}

}
