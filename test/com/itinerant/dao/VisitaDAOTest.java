package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.protobuf.TextFormat.ParseException;
import com.itinerant.entity.Certificado;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.entity.Visita;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VisitaDAOTest {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static ProfesionalDAO profesionalDAO;
	private static LocalidadDAO localidadDAO;
	private static VisitaDAO visitaDAO;
	
	@BeforeAll
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		profesionalDAO = new ProfesionalDAO(entityManager);
		localidadDAO = new LocalidadDAO(entityManager);
		visitaDAO = new VisitaDAO(entityManager);
	}
	
	@Test
	public void testCreateVisita() throws java.text.ParseException, ParseException {
		Localidad pueblo = localidadDAO.get(46330);
		Profesional profesional = profesionalDAO.get("sermata");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy");
		String strdate = "14-08-2022";		
		Date fecha = dateformat.parse(strdate);
		
		SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate2 = "14-08-2022 08:00:00";		
		Date horaIni = dateformat2.parse(strdate2);
		
		String strdate3 = "14-08-2022 14:00:00";		
		Date horaFin = dateformat2.parse(strdate3);
		
		
		Visita visita = new Visita(pueblo, profesional, fecha, horaIni, horaFin, "Sesión Veterinaria", 15, 10, 10, "Veterinario");
		
		visita = visitaDAO.create(visita);
		
		assertTrue(true);
	}
	
	@Test
	public void testUpdateVisita() throws java.text.ParseException {	
		Visita visita = visitaDAO.get(2);
		
		visita.setDescripcion("Veterinaria especializada en mascotas domésticas");
		
		visita = visitaDAO.update(visita);
		
		String expected = "Veterinaria especializada en mascotas domésticas";
		String actual = visita.getDescripcion();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDeleteVisita() throws java.text.ParseException {

		
		Visita visita = visitaDAO.get(1);
		System.out.println(visita.getNombre());
		visitaDAO.delete(1);
		
		visita = visitaDAO.get(1);
		
		assertNull(visita);
	}
	
	@Test
	public void testListAllByLogin() {
		List<Visita> visitas = visitaDAO.listAllByLogin("sermata");
		
		for(Visita visita : visitas) {
			System.out.println(visita.getNombre() + " " + visita.getDescripcion());
		}
		
		assertTrue(visitas.size() > 0);
	}
	
	@Test
	public void testSearch() {
		String keyword = "fisiología";
		List<Visita> visitas = visitaDAO.search(keyword);
		
		assertEquals(1, visitas.size());
	}
	
	@AfterAll
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
