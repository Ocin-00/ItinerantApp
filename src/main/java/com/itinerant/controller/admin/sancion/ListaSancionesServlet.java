package com.itinerant.controller.admin.sancion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.CiudadanoServicios;

@WebServlet("/admin/lista_sanciones")
public class ListaSancionesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaSancionesServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("UTF-8");
		
		//System.out.println("HOLA");
		CiudadanoServicios ciudadanoServicios = new CiudadanoServicios(entityManager, request, response);		
		ciudadanoServicios.listarPropuestosSancion();				
	}


}
