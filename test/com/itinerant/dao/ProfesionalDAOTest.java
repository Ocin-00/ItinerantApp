package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.protobuf.TextFormat.ParseException;
import com.itinerant.entity.Certificado;
import com.itinerant.entity.CertificadoId;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProfesionalDAOTest {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static ProfesionalDAO profesionalDAO;
	private static CertificadoDAO certificadoDAO;
	
	@BeforeAll
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		profesionalDAO = new ProfesionalDAO(entityManager);
		certificadoDAO = new CertificadoDAO(entityManager);
	}
	
	@Test
	public void testCreateProfesional() throws java.text.ParseException, ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate = "01-12-2000 00:00:00";		
		Date fechaNac = dateformat.parse(strdate);
		Date fechaRegistro = new Date(System.currentTimeMillis());
		Profesional usuario = new Profesional("sermata", "1234", "sermata@ade.upv.es", "Sergio", "Martínez Tarín", 
												fechaNac, "Utiel", "Veterinario", "987654321", false, fechaRegistro);
		usuario = profesionalDAO.create(usuario);
		
		assertTrue(true);
	}
	
	@Test
	public void testUpdateProfesional() throws java.text.ParseException {
		new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date fechaRegistro = new Date(System.currentTimeMillis());
		Profesional usuario = profesionalDAO.get("femargar");
		usuario.setFechaRegistro(fechaRegistro);
		usuario = profesionalDAO.update(usuario);
		
		Date expected = fechaRegistro;
		Date actual = usuario.getFechaRegistro();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCreateCertificado() throws java.text.ParseException {
		Profesional usuario = profesionalDAO.get("sermata");
		new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date fechaRegistro = new Date(System.currentTimeMillis());
		CertificadoId id = new CertificadoId("sermata", "Diploma");
		Certificado certificado = new Certificado(id, usuario, "Escuela", fechaRegistro, 2005, false);
		
		certificado = certificadoDAO.create(certificado);
		
		assertTrue(true);
	}
	
	@Test
	public void testDeleteCertificado() throws java.text.ParseException {
		Profesional usuario = profesionalDAO.get("sermata");
		new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date fechaRegistro = new Date(System.currentTimeMillis());
		CertificadoId id = new CertificadoId("sermata", "Diploma");
		//Certificado certificado = new Certificado(id, usuario, "Escuela", fechaRegistro, 2005, false);
		
		certificadoDAO.delete(id);
		
		assertTrue(true);
	}
	
	@Test
	public void testDeleteProfesional() {
		String usuarioId = "femargar";
		profesionalDAO.delete(usuarioId);
		
		UsuarioInterno usuario = profesionalDAO.get(usuarioId);
		
		assertNull(usuario);
	}
	
	@Test
	public void testListAllNotValid() {
		List<Profesional> usuarios = profesionalDAO.listAllNotValid();
		
		for(Profesional usuario : usuarios) {
			System.out.println(usuario.getNombre() + " " + usuario.getApellidos());
		}
		
		assertTrue(usuarios.size() > 0);
	}
	
	
	@AfterAll
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
