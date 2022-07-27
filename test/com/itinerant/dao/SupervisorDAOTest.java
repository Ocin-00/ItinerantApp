package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.entity.Supervisor;
import com.itinerant.entity.UsuarioInterno;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupervisorDAOTest {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static SupervisorDAO supervisorDAO;
	
	@BeforeAll
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		supervisorDAO = new SupervisorDAO(entityManager);
	}
	
	@Test
	public void testUpdateSupervisor() throws java.text.ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate = "30-06-1987 00:00:00";		
		Date fechaNac = dateformat.parse(strdate);
		Supervisor usuario = supervisorDAO.get("nifergar");
		usuario.setFechaNac(fechaNac);
		usuario = supervisorDAO.update(usuario);
		
		Date expected = fechaNac;
		Date actual = usuario.getFechaNac();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDeleteSupervisor() {
		String usuarioId = "nifergar";
		supervisorDAO.delete(usuarioId);
		
		UsuarioInterno usuario = supervisorDAO.get(usuarioId);
		
		assertNull(usuario);
	}
	
	@AfterAll
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
