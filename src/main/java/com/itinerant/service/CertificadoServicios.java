package com.itinerant.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.itinerant.dao.AdministradorDAO;
import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CertificadoDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.entity.Administrador;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Certificado;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.Rol;

public class CertificadoServicios {
	private CertificadoDAO certificadoDAO;
	private AlertaDAO alertaDAO;
	private AdministradorDAO administradorDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ProfesionalDAO profesionalDAO;
	private UsuarioInternoDAO usuarioInternoDAO;
	private EntityManager entityManager;
	
	private final String FILE_FOLDER = "C:/Users/Nico/git/repository/ItinerantApp/src/main/webapp/files/";

	public CertificadoServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		certificadoDAO = new CertificadoDAO(entityManager);
		profesionalDAO = new ProfesionalDAO(entityManager);
		administradorDAO = new AdministradorDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
	}
	
	public void listarCertificadosNoValidados() throws ServletException, IOException {
		listarCertificadosNoValidados(null);
	}
	
	public void listarCertificadosNoValidados(String message) throws ServletException, IOException {
		List<Certificado> certificadosSinValidar = certificadoDAO.listAllNotValid();
		request.setAttribute("certificadosSinValidar", certificadosSinValidar);
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listpage = "/admin/lista_certificados.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void anularCertificado() throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		
		Integer id =Integer.parseInt(request.getParameter("id"));
		Certificado certificado = certificadoDAO.get(id);
		if(borrarArchivo(certificado.getRuta())) {
			certificadoDAO.delete(id);
			if(usuario.getRol().equals(Rol.ADMINISTRADOR.toString())) {
				listarCertificadosNoValidados("El certificado ha sido borrado con éxito.");
			} else {
				listarCertificados("El certificado ha sido borrado con éxito.");
			}
		} else {
			if(usuario.getRol().equals(Rol.ADMINISTRADOR.toString())) {
				listarCertificadosNoValidados("El certificado no ha podido ser borrado con éxito.");
			} else {
				listarCertificados("l certificado no ha podido ser borrado con éxito.");
			}
		}
		
	}

	private boolean borrarArchivo(String ruta) {
		String path = request.getServletContext().getRealPath("/") + ruta.substring(2);
		File file = new File(path);
		return file.delete();
	}
	
	public void validarCertificado() throws ServletException, IOException {
		Integer id =Integer.parseInt(request.getParameter("id"));
		Certificado certificado = certificadoDAO.get(id);
		certificado.setValidez(true);
		certificadoDAO.update(certificado);
		listarCertificadosNoValidados("La cuenta del profesional ha sido validada con éxito.");
	}

	public void listarCertificados() throws ServletException, IOException {
		listarCertificados(null);
	}
	
	public void listarCertificados(String message) throws ServletException, IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		Profesional profesional = profesionalDAO.get(login);
		List<Certificado> certificados = new ArrayList<>(profesional.getCertificados());
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("certificados", certificados);
		String listpage = "../frontend/profesional/lista_certificados.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}

	public void crearCertificado() throws IOException, ServletException {
		try {
			Certificado certificado = inicializarDatos();
			certificadoDAO.create(certificado);
			
			String cuerpoAlerta = "El profesional " + certificado.getProfesional().toString() + " ha subido un nuevo certificado que está pendiente de validación.";
			List<Administrador> admins = administradorDAO.listAll();
			for(int i = 0; i < admins.size(); i++) {
				Alerta alerta = new Alerta(admins.get(i), "Nuevo certificado", cuerpoAlerta, false);
				
				AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
				alertaServicios.mandarNotificacion(alerta);
				
				alertaDAO.create(alerta);
			}
			
			listarCertificados("El certificado se ha registrado con éxito.");
		} catch (Exception e) {
			throw e;
			//listarCertificados("Lo sentimos, no se ha podido realizar con éxito.");
		}
	}

	private String nombrarFichero() throws IOException {
		String path = request.getServletContext().getRealPath("/") + "/files/number.txt";
		List<Integer> ints = Files.lines(Paths.get(path)).map(Integer::parseInt).collect(Collectors.toList());
		Integer number = ints.get(0) + 1;
		Files.write(Paths.get(path), number.toString().getBytes());
		// El path de verdad es C:\Users\Nico\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ItinerantApp\files
		//System.out.println(path);
		return "certificado" + number + ".pdf";
	}

	private Certificado inicializarDatos() throws IOException, ServletException {
		String login = (String) request.getSession().getAttribute("userLogin");
		Profesional profesional = profesionalDAO.get(login);
		
		String titulo = request.getParameter("titulo");
		
		int anyo = Integer.parseInt(request.getParameter("anyo"));
		String entidadEmisora = request.getParameter("entidadEmisora");
		
		if(certificadoRepetido(profesional, titulo, entidadEmisora, anyo)) {
			listarCertificados("Lo sentimos, no puedes poner dos certificados con los mismos datos");
		}
		
		Part part = request.getPart("certificadoFile");
		String certificadoRuta = null;
		if(part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] fileBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			String nombreFichero = nombrarFichero();
			String src = FILE_FOLDER + nombreFichero;
			OutputStream outputStream = new FileOutputStream(src);
			int length; 
			
			while ((length = inputStream.read(fileBytes)) != -1) {
				outputStream.write(fileBytes, 0, length);
			}

			inputStream.close();
			outputStream.close();
			certificadoRuta = "../files/" + nombreFichero;
		}
		
		Date fechaRegistro = new Date();
		
		Certificado certificado = new Certificado(profesional, titulo, entidadEmisora, anyo, false, fechaRegistro, certificadoRuta);
		return certificado;
	}
	
	private boolean certificadoRepetido(Profesional profesional, String titulo, String entidadEmisora, int anyo) {
		List<Certificado> certificados= new ArrayList<Certificado>(profesional.getCertificados());
		for(Certificado certificado : certificados) {
			if(certificado.getTitulo().equals(titulo) && certificado.getEntidadEmisora().equals(entidadEmisora) && certificado.getAnyo() == anyo) {
				return true;
			}
		}
		return false;
	}

	public void actualizarCertificado() throws Exception {
		try {
			int idCertificado = Integer.parseInt(request.getParameter("id"));
			Certificado certificado = certificadoDAO.get(idCertificado);
			
			String titulo = request.getParameter("titulo");
			certificado.setTitulo(titulo);
			
			int anyo = Integer.parseInt(request.getParameter("anyo"));
			certificado.setAnyo(anyo);
			
			String entidadEmisora = request.getParameter("entidadEmisora");
			certificado.setEntidadEmisora(entidadEmisora);
		
			certificadoDAO.update(certificado);
			listarCertificados("El certificado se ha actualizado con éxito.");
		} catch (Exception e) {
			throw e;
			//listarCertificados("Lo sentimos, no se ha podido realizar con éxito.");
		}
	}

	public void editarCertificado() throws ServletException, IOException {
		int idCertificado = Integer.parseInt(request.getParameter("id"));
		Certificado certificado = certificadoDAO.get(idCertificado);
		
		request.setAttribute("certificado", certificado);
		String listpage = "../frontend/profesional/certificado_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);
		requestDispatcher.forward(request, response);
	}
}
