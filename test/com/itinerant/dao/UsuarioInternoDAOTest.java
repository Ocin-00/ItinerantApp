package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.protobuf.TextFormat.ParseException;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.Rol;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UsuarioInternoDAOTest {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static UsuarioInternoDAO usuarioInternoDAO;
	
	@BeforeAll
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
	}
	
	@Test
	public void testCreateUsuarioInterno() throws java.text.ParseException, ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate = "01-12-2000 00:00:00";		
		Date fechaNac = dateformat.parse(strdate);
		UsuarioInterno usuario = new UsuarioInterno("nialcha", "1234", "nialcha@ade.upv.es", "Nicolás", "Alcantarilla Chaves", Rol.CIUDADANO.toString(),fechaNac);
		usuario = usuarioInternoDAO.create(usuario);
		
		assertTrue(true);
	}
	
	@Test	
	public void testCreateUsuarioInternoFieldNotSet() {
		UsuarioInterno usuario = new UsuarioInterno();
		usuario = usuarioInternoDAO.create(usuario);
		
		//assertThrows(Exception.class, null);
	}

	@Test
	public void testUpdateUsuario() throws java.text.ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate = "01-12-2000 00:00:00";		
		Date fechaNac = dateformat.parse(strdate);
		UsuarioInterno usuario = new UsuarioInterno("nialcha", "1234", "nialcha@ade.upv.es", "Nicolás", "Alcantarilla Chaves", Rol.CIUDADANO.toString(), fechaNac);
		usuario.setPassword("4321");
		
		usuario = usuarioInternoDAO.update(usuario);
		
		String expected = "4321";
		String actual = usuario.getPassword();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUsuarioFound() {
		String usuarioId = "nialcha";
		UsuarioInterno usuario = usuarioInternoDAO.get(usuarioId);
		
		if(usuario != null) {
			System.out.println(usuario.getNombre());
		}
		
		assertNotNull(usuario);
	}
	
	@Test
	public void testGetUsuarioNotFound() {
		String usuarioId = "chanial";
		UsuarioInterno pueblo = usuarioInternoDAO.get(usuarioId);
		
		assertNull(pueblo);
	}
	
	@Test
	public void testDeleteUsuario() {
		String usuarioId = "nialcha";
		usuarioInternoDAO.delete(usuarioId);
		
		UsuarioInterno usuario = usuarioInternoDAO.get(usuarioId);
		
		assertNull(usuario);
	}
	
	@Test
	public void testDeleteNonExistentUsuario() {
		String usuarioId = "chanial";
		usuarioInternoDAO.delete(usuarioId);
		
		assertThrows(Exception.class, null);
	}
	
	@Test
	public void testListAll() {
		List<UsuarioInterno> usuarios = usuarioInternoDAO.listAll();
		
		for(UsuarioInterno usuario : usuarios) {
			System.out.println(usuario.getNombre() + " " + usuario.getApellidos());
		}
		
		assertTrue(usuarios.size() > 0);
	}
	
	@Test
	public void testCount() {
		long total = usuarioInternoDAO.count();
		
		assertEquals(1, total);
	}
	
	@Test
	public void testAlertas() {
		UsuarioInterno usuario = usuarioInternoDAO.get("nialcha");
		System.out.println(usuario.getAlertas().iterator().next().getTitulo());
		
		assertEquals(1, usuario.getAlertas().size());
	}
	
	@Test
	public void checkLoginSuccess() {
		String login = "femargar";
		String password = "1234";
		
		boolean resultado = usuarioInternoDAO.checkLogin(login, password);
		assertTrue(resultado);
	}
	
	@Test
	public void checkLoginFail() {
		String login = "noexiste";
		String password = "1234";
		
		boolean resultado = usuarioInternoDAO.checkLogin(login, password);
		assertFalse(resultado);
	}
	
	@AfterAll
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
