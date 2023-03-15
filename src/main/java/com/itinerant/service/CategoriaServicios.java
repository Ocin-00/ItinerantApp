package com.itinerant.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.itinerant.dao.CategoriaDAO;
import com.itinerant.dao.LocalidadDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.entity.Categoria;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.enums.Rol;

import org.apache.commons.text.StringEscapeUtils;

import antlr.StringUtils;

public class CategoriaServicios {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CategoriaDAO categoriaDAO;
	private EntityManager entityManager;

	public CategoriaServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		categoriaDAO = new CategoriaDAO(entityManager);
		this.request = request;
		this.response = response;		
		this.entityManager = entityManager;
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
		Part part = request.getPart("imagenCategoria");
		String imagenRuta = setImagen(part, null);
		
		Categoria categoria = new Categoria(nombre, imagenRuta);
		
		String nombreMal = nombreMal(nombre);
		categoria.setNombreMal(nombreMal);
		
		try {
			categoriaDAO.create(categoria);
			listarCategoriasAdmin("La categoria ha sido creada con éxito");
		} catch (Exception e) {
			listarCategoriasAdmin("La categoría no pudo ser creada con éxito. Comprueba que el nombre no coincida con ninguno existente.");
			return;
			//throw e;
		}
		
	}

	public void actualizarCategoria() throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Categoria categoria = categoriaDAO.get(id);
		
		String nombre = StringEscapeUtils.escapeHtml4( request.getParameter("nombre"));
		Part part = request.getPart("imagenCategoria");
		boolean imagenCambia = Boolean.parseBoolean(request.getParameter("imagenCambia"));
		if(imagenCambia) {
			String imagenRuta = setImagen(part, categoria.getImagenRuta());
			categoria.setImagenRuta(imagenRuta);
		}
		String nombreMal = nombreMal(nombre);
		
		categoria.setNombre(nombre);
		categoria.setNombreMal(nombreMal);
		
		try {
			categoriaDAO.update(categoria);
		} catch (Exception e) {
			listarCategoriasAdmin("La categoría no pudo ser modificada con éxito. Comprueba que el nombre no coincida con ninguno existente.");
			return;
		}
		
		
		listarCategoriasAdmin("La categoria ha sido modificada con éxito.");
	}
	
	private String setImagen(Part part, String imagenActual) throws IOException, ServletException {
		//Part part = request.getPart("imagenPerfil");
		String imagenRuta = imagenActual;
		System.out.println("Imagen: " + imagenActual);
		if(imagenActual != null && !imagenActual.isBlank() && !imagenActual.isEmpty()) {
			borrarArchivo(imagenActual);
		}
		if(part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			String nombreImagen = nombrarImagen();
			String src = request.getServletContext().getRealPath("/") + "/img/" + nombreImagen;
			OutputStream outputStream = new FileOutputStream(src);
			int length; 
			
			while ((length = inputStream.read(imageBytes)) != -1) {
				outputStream.write(imageBytes, 0, length);
			}

			inputStream.close();
			outputStream.close();
			imagenRuta = "../img/" + nombreImagen;
		}
		return imagenRuta;
	}
	
	private String nombrarImagen() throws IOException {
		String path = request.getServletContext().getRealPath("/") + "/img/number.txt";
		List<Integer> ints = Files.lines(Paths.get(path)).map(Integer::parseInt).collect(Collectors.toList());
		Integer number = ints.get(0) + 1;
		Files.write(Paths.get(path), number.toString().getBytes());
		// El path de verdad es C:\Users\Nico\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ItinerantApp\img
		//System.out.println(path);
		return "img" + number + ".jpg";
	}
	
	private boolean borrarArchivo(String ruta) {
		String path = request.getServletContext().getRealPath("/") + ruta.substring(2);
		System.out.println(path);
		File file = new File(path);
		boolean a = file.delete();
		System.out.println(a);
		return a;
	}
	
	public String nombreMal(String nombre) {
		return org.apache.commons.lang3.StringUtils.stripAccents(Normalizer.normalize(StringEscapeUtils.unescapeHtml4(nombre).replaceAll(" ", "").toLowerCase(), Normalizer.Form.NFKC).replaceAll("\\p{M}", ""));
	}

	public void editarCategoria() throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Categoria categoria = categoriaDAO.get(id);
		request.setAttribute("categoria", categoria);
		
		String homepage = "/admin/categoria_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	public void nuevaCategoria() throws ServletException, IOException {	
		String homepage = "/admin/categoria_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}
	
	public void categoriasInicio() throws ServletException, IOException {
		List<Categoria> categorias = categoriaDAO.getByUse();
		
		request.setAttribute("categorias", categorias);
		
		String rol = (String) request.getSession().getAttribute("rol");
		System.out.println(rol);
		String homepage = "";
		if(rol != null) {
			homepage = "../frontend/index.jsp";	
			request.setAttribute("hayRol", true);
		} else {
			homepage = "frontend/index.jsp";	
			request.setAttribute("hayRol", false);
		}	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			System.out.println(rol);
			if(rol.equals(Rol.ADMINISTRADOR.toString())) {
				homepage = "admin/";
			} else if(rol.equals(Rol.SUPERVISOR.toString())) {
				homepage = "supervisor/";
			} else if(rol.equals(Rol.CIUDADANO.toString())) {
				homepage = "inicio/";
			} else if(rol.equals(Rol.PROFESIONAL.toString())) {
				homepage = "profesional/";
			}
			response.sendRedirect(homepage);
			//RequestDispatcher dispatcher2 = request.getRequestDispatcher(homepage);
			//dispatcher2.(request, response);
		}
	}
}
