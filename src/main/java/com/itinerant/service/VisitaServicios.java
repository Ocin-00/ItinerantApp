package com.itinerant.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Visita;

public class VisitaServicios {
//TODO
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	private VisitaDAO visitaDAO;
	
	public VisitaServicios() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		visitaDAO = new VisitaDAO(entityManager );
	}
	
	public List<Visita> listarVisitas() {
		return visitaDAO.listAll();
	}

}
