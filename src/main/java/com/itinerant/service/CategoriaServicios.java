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
		Part part = request.getPart("imagenCategoria");
		String imagenRuta = setImagen(part, null);
		
		Categoria categoria = new Categoria(nombre, imagenRuta);
		
		String nombreMal = nombreMal(nombre);
		categoria.setNombreMal(nombreMal);
		
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
		Part part = request.getPart("imagenCategoria");
		String imagenRuta = setImagen(part, categoria.getImagenRuta());
		String nombreMal = nombreMal(nombre);
		
		categoria.setImagenRuta(imagenRuta);
		categoria.setNombre(nombre);
		categoria.setNombreMal(nombreMal);
		
		categoriaDAO.update(categoria);
		
		listarCategoriasAdmin("La categoria ha sido modificada con éxito");
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
	
	private String nombreMal(String nombre) {
		return Normalizer.normalize(StringEscapeUtils.unescapeHtml4(nombre).replaceAll(" ", "").toLowerCase(), Normalizer.Form.NFC).replaceAll("[^\\p{ASCII}]", "");
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
		dispatcher.forward(request, response);
	}
}
