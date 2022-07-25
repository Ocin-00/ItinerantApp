package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.entity.Localidad;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LocalidadDAOTest {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static LocalidadDAO localidadDAO;
	
	@BeforeAll
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		localidadDAO = new LocalidadDAO(entityManager);
	}
	
	@Test
	public void testCreateLocalidad() {
		Localidad pueblo = new Localidad(46340, "Requena", "Plana de Utiel-Requena", 20000);
		pueblo = localidadDAO.create(pueblo);
		
		assertTrue(true);
	}
	
	@Test	
	public void testCreateLocalidadFieldNotSet() {
		Localidad pueblo = new Localidad();
		pueblo = localidadDAO.create(pueblo);
		
		//assertThrows(Exception.class, null);
	}

	@Test
	public void testUpdateLocalidad() {
		Localidad pueblo = new Localidad(46314, "Fuenterrobles", "Plana de Utiel-Requena", 682);
		pueblo.setMancomunidad("Mancomunidad del interior - Tierra del vino");
		
		pueblo = localidadDAO.update(pueblo);
		
		String expected = "Mancomunidad del interior - Tierra del vino";
		String actual = pueblo.getMancomunidad();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetLocalidadFound() {
		int localidadId = 46300;
		Localidad pueblo = localidadDAO.get(localidadId);
		
		if(pueblo != null) {
			System.out.println(pueblo.getNombre());
		}
		
		assertNotNull(pueblo);
	}
	
	@Test
	public void testGetLocalidadNotFound() {
		int localidadId = 12345;
		Localidad pueblo = localidadDAO.get(localidadId);
		
		assertNull(pueblo);
	}
	
	@Test
	public void testDeleteLocalidad() {
		int localidadId = 46340;
		localidadDAO.delete(localidadId);
		
		Localidad pueblo = localidadDAO.get(localidadId);
		assertNull(pueblo);
	}
	
	@Test
	public void testDeleteNonExistentLocalidad() {
		int localidadId = 12345;
		localidadDAO.delete(localidadId);
		
		assertThrows(Exception.class, null);
	}
	
	@Test
	public void testListAll() {
		List<Localidad> localidades = localidadDAO.listAll();
		
		for(Localidad localidad : localidades) {
			System.out.println(localidad.getNombre());
		}
		
		assertTrue(localidades.size() > 0);
	}
	
	@Test
	public void testCount() {
		long total = localidadDAO.count();
		
		assertEquals(3, total);
	}
	
	@AfterAll
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
