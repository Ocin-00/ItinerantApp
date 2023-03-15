package com.itinerant.controller.frontend.ciudadano;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itinerant.enums.Rol;
import com.itinerant.service.UsuarioInternoServicios;

@WebFilter(urlPatterns = {"/ciudadano/*"}, asyncSupported=true)
public class CiudadanoLoginFilter extends HttpFilter implements Filter {

    public CiudadanoLoginFilter() {
        super();
    }


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String login = (String) session.getAttribute("userLogin");
		
		boolean loggedIn = session != null && login != null;
		
		if(loggedIn) {
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			UsuarioInternoServicios usuarioInternoServicios = new UsuarioInternoServicios(entityManager, httpRequest, httpResponse);
			boolean esCiudadano = usuarioInternoServicios.getRol(login).equals(Rol.CIUDADANO.toString());
			if(esCiudadano) {
				chain.doFilter(request, response);
			} else {
				String message = "No tiene autorización para acceder.";
				request.setAttribute("message", message);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("../admin/message.jsp");
				requestDispatcher.forward(request, response);
			}
		} else {
			httpResponse.sendRedirect("login");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}