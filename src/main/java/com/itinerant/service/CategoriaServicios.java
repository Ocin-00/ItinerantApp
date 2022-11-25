package com.itinerant.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.CategoriaDAO;
import com.itinerant.entity.Categoria;

public class CategoriaServicios {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CategoriaDAO categoriaDAO;

	public CategoriaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		categoriaDAO = new CategoriaDAO(entityManager);
		this.request = request;
		this.response = response;		
	}

	public List<Categoria> listarCategorias() {
		return categoriaDAO.listAll();
	}

}
