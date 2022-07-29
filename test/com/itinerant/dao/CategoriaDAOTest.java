package com.itinerant.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.entity.Categoria;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class CategoriaDAOTest {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static CategoriaDAO categoriaDAO;
	
	@BeforeAll
	public static void setupClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		entityManager = entityManagerFactory.createEntityManager();
		categoriaDAO = new CategoriaDAO(entityManager);
	}
	
	@Test
	public void testCreateCategoria() {
		Categoria categoria = new Categoria("Psicología");
		categoria = categoriaDAO.create(categoria);
		
		assertTrue(true);
	}
	
	@Test	
	public void testCreateCategoriaFieldNotSet() {
		Categoria categoria = new Categoria();
		categoria = categoriaDAO.create(categoria);
		
		//assertThrows(Exception.class, null);
	}

	@Test
	public void testUpdateCategoria() {
		Categoria pueblo = categoriaDAO.getByName("Psicología");
		pueblo.setNombre("Fisiología");
		
		pueblo = categoriaDAO.update(pueblo);
		
		String expected = "Fisiología";
		String actual = pueblo.getNombre();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDeleteCategoria() {
		int categoriaId = 1;
		categoriaDAO.delete(categoriaId);
		
		Categoria pueblo = categoriaDAO.get(categoriaId);
		assertNull(pueblo);
	}
	
	@Test
	public void testListAll() {
		List<Categoria> categorias = categoriaDAO.listAll();
		
		for(Categoria categoria : categorias) {
			System.out.println(categoria.getNombre());
		}
		
		assertTrue(categorias.size() > 0);
	}
	
	@Test
	public void testCount() {
		long total = categoriaDAO.count();
		
		assertEquals(1, total);
	}
	
	@AfterAll
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
