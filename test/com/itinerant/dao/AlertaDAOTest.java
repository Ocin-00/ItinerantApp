package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.entity.Alerta;
import com.itinerant.entity.UsuarioInterno;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AlertaDAOTest {
	
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static AlertaDAO alertaDAO;
	private static UsuarioInternoDAO usuarioDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		alertaDAO = new AlertaDAO(entityManager);
		usuarioDAO = new UsuarioInternoDAO(entityManager);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		entityManager.close();
		entityManagerFactory.close();
	}

	@Test
	void testCreateAlerta() {
		UsuarioInterno usuario = usuarioDAO.get("nialcha");
		Alerta alerta = new Alerta(1, usuario, "Alerta 1", "Esta es la alerta 1");
		alerta = alertaDAO.create(alerta);
		
		assertTrue(true);
	}

}
