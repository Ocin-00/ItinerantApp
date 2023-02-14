package com.itinerant.controller.frontend;

import com.itinerant.controller.BaseServlet;
import com.itinerant.service.UsuarioInternoServicios;
import com.itinerant.service.VisitaServicios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/profesional/actualizar_usuario", "/inicio/actualizar_usuario", "/admin/actualizar_usuario", "/supervisor/actualizar_usuario"})
@MultipartConfig(
		fileSizeThreshold = 1024,  // 1KB
		maxFileSize = 1024 * 1024 * 50,	// 50 MB
		maxRequestSize = 1024 * 1024 * 200   // 200 MB
)
public class ActualizarUsuarioServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public ActualizarUsuarioServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, request, response);
		usuarioInternoServicios.actualizarCuenta();
	}

}
