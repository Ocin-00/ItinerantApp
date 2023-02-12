package com.itinerant.controller.admin.categoria;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CategoriaServicios;

@WebServlet("/admin/lista_categorias")
public class ListaCategoriasServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaCategoriasServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("HOLA");
		CategoriaServicios categoriaServicios = new CategoriaServicios(entityManager, request, response);		
		categoriaServicios.listarCategoriasAdmin();				
	}


}