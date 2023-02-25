package com.itinerant.controller.admin.categoria;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CategoriaServicios;

@WebServlet("/admin/crear_categoria")
@MultipartConfig(
		fileSizeThreshold = 1024,  // 1KB
		maxFileSize = 1024 * 1024 * 50,	// 50 MB
		maxRequestSize = 1024 * 1024 * 200   // 200 MB
)
public class CrearCategoriaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public CrearCategoriaServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("UTF-8");
		
		CategoriaServicios categoriaServicios = new CategoriaServicios(entityManager, request, response);		
		categoriaServicios.crearCategoria();				
	}


}
