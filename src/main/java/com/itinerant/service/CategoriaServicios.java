package com.itinerant.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.dao.CategoriaDAO;
import com.itinerant.entity.Categoria;

import org.apache.commons.text.StringEscapeUtils;

import antlr.StringUtils;

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
	
	public void listarCategoriasAdmin() throws ServletException, IOException {
		listarCategoriasAdmin(null);
	}
	
	public void listarCategoriasAdmin(String message) throws ServletException, IOException {
		List<Categoria> categorias = categoriaDAO.listAll();
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("categorias", categorias);
		
		String listpage = "/admin/lista_categorias.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void borrarCategoria() throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		categoriaDAO.delete(id);
		
		listarCategoriasAdmin("La categoria ha sido borrada con éxito");
	}

	public void crearCategoria() throws ServletException, IOException {
		String nombre = StringEscapeUtils.escapeHtml4( request.getParameter("nombre"));
		
		Categoria categoria = new Categoria(nombre);
		
		try {
			categoriaDAO.create(categoria);
			listarCategoriasAdmin("La categoria ha sido creada con éxito");
		} catch (Exception e) {
			throw e;
		}
		
	}

	public void actualizarCategoria() throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Categoria categoria = categoriaDAO.get(id);
		
		String nombre = StringEscapeUtils.escapeHtml4( request.getParameter("nombre"));
		categoria.setNombre(nombre);
		
		categoriaDAO.update(categoria);
		
		listarCategoriasAdmin("La categoria ha sido modificada con éxito");
	}

}
