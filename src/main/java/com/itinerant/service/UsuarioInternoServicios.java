package com.itinerant.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.itinerant.dao.AdministradorDAO;
import com.itinerant.dao.AlertaDAO;
import com.itinerant.dao.CategoriaDAO;
import com.itinerant.dao.CertificadoDAO;
import com.itinerant.dao.ChatDAO;
import com.itinerant.dao.CitaDAO;
import com.itinerant.dao.CiudadanoDAO;
import com.itinerant.dao.LocalidadDAO;
import com.itinerant.dao.ProfesionalDAO;
import com.itinerant.dao.SupervisorDAO;
import com.itinerant.dao.UsuarioInternoDAO;
import com.itinerant.dao.VisitaDAO;
import com.itinerant.entity.Administrador;
import com.itinerant.entity.Alerta;
import com.itinerant.entity.Categoria;
import com.itinerant.entity.Certificado;
import com.itinerant.entity.Chat;
import com.itinerant.entity.ChatMensaje;
import com.itinerant.entity.Cita;
import com.itinerant.entity.Ciudadano;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Supervisor;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.entity.Visita;
import com.itinerant.enums.Rol;
import com.itinerant.enums.Sexo;

import org.apache.commons.text.StringEscapeUtils;

import java.math.BigInteger;  
import java.nio.charset.StandardCharsets;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;   

public class UsuarioInternoServicios {
	
	private UsuarioInternoDAO usuarioInternoDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private EntityManager entityManager;
	private AlertaDAO alertaDAO;
	private VisitaDAO visitaDAO;
	private CitaDAO citaDAO;
	private AdministradorDAO administradorDAO;
	private ChatDAO chatDAO;
	private MensajeServicios mensajeServicios;
	private CiudadanoDAO ciudadanoDAO;
	private ProfesionalDAO profesionalDAO;
	private SupervisorDAO supervisorDAO;
	private final String DEFAULT_IMAGE = "../img/default.jpg";

	public UsuarioInternoServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		usuarioInternoDAO = new UsuarioInternoDAO(entityManager);
		alertaDAO = new AlertaDAO(entityManager);
		administradorDAO = new AdministradorDAO(entityManager);
		chatDAO = new ChatDAO(entityManager);
		ciudadanoDAO = new CiudadanoDAO(entityManager);
		profesionalDAO = new ProfesionalDAO(entityManager);
		supervisorDAO = new SupervisorDAO(entityManager);
		visitaDAO = new VisitaDAO(entityManager);
		citaDAO = new CitaDAO(entityManager);
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
		mensajeServicios = new MensajeServicios(entityManager, request, response);
	}

	public boolean emailRepetido(String email) {
		return usuarioInternoDAO.findByEmail(email) != null;		
	}
	
	public boolean loginRepetido(String login) {
		return usuarioInternoDAO.get(login) != null;		
	}
	
	private byte[] getSHA(String input) throws NoSuchAlgorithmException  
    {  
        /* MessageDigest instance for hashing using SHA256 */  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        /* digest() method called to calculate message digest of an input and return array of byte */  
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    }  
      
    private String toHexString(byte[] hash)  
    {  
        /* Convert byte array of hash into digest */  
        BigInteger number = new BigInteger(1, hash);  
  
        /* Convert the digest into hex value */  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        /* Pad with leading zeros */  
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    }  
	
	
	/*SHA-256 Hashing Technique*/
	 public String hashPassword(String password) throws NoSuchAlgorithmException {  
		 	String encryptedpassword  = toHexString(getSHA(password));  
	        
	        return encryptedpassword;
	 }  
	
	public void removeSessionAttributes() {
		HttpSession session = request.getSession();
		//String login = (String) session.getAttribute("userLogin");
		session.removeAttribute("userLogin");
		session.removeAttribute("fotoPerfil");
		session.removeAttribute("rol");
		session.removeAttribute("misAlertas");
		session.removeAttribute("misChats");
		session.removeAttribute("mensajes");
		/*
		HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
		pilasUsuarios.remove(login);
		request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
		*/
		
	}
	
	public void login(String login, String password) throws ServletException, IOException, NoSuchAlgorithmException {
		if(login == null || password == null) {
			login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
			password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
		}
		String encpass = hashPassword(password);
		//String encpass = password;
		boolean loginResult = usuarioInternoDAO.checkLogin(login, encpass);
		
		if(loginResult) {
			String homepage = null;
			if(request.getSession().getAttribute("userLogin") != null) {
				removeSessionAttributes();
			}
			request.getSession().setAttribute("userLogin", login);			
			UsuarioInterno usuario = usuarioInternoDAO.get(login);
			String rol = usuario.getRol();
			request.getSession().setAttribute("rol", rol);
			request.getSession().setAttribute("fotoPerfil", usuario.getImagenRuta());
			
			HashMap<String, Stack<Alerta>> pilasUsuarios = (HashMap<String, Stack<Alerta>>) request.getSession().getServletContext().getAttribute("PilasUsuarios");
			
			Stack<Alerta> notificaciones;
			notificaciones = pilasUsuarios.get(login);
			
			if(notificaciones == null) {
				List<Alerta> alertas = alertaDAO.listAllByLogin(login);
				notificaciones = new Stack<>();
				notificaciones.addAll(alertas);
			}
			
			request.getSession().setAttribute("misAlertas", notificaciones);
			
			pilasUsuarios.put(login, notificaciones);			
			request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
			
			HashMap<String, List<Chat>> chats = (HashMap<String, List<Chat>>) request.getSession().getServletContext().getAttribute("chats");
			
			List<Chat> misChats;
			misChats = chats.get(login);
			
			if(misChats == null) {
				misChats = new ArrayList<>();
				List<Chat> chatsBD = chatDAO.findAllByLogin(login);
				if(chatsBD != null) {
					misChats.addAll(chatsBD);
				}
			}
			
			for(Chat chat : misChats) {
				mensajeServicios.inicializarMensajes(chat.getIdChat());
			}
			
			request.getSession().setAttribute("misChats", misChats);
			
			chats.put(login, misChats);			
			request.getSession().getServletContext().setAttribute("chats", chats);
			
			if(rol.equals(Rol.ADMINISTRADOR.toString())) {
				homepage = "/admin/";
			} else if(rol.equals(Rol.SUPERVISOR.toString())) {
				homepage = "/supervisor/";
			} else if(rol.equals(Rol.CIUDADANO.toString())) {
				homepage = "/inicio/";
			} else if(rol.equals(Rol.PROFESIONAL.toString())) {
				homepage = "/profesional/";
			}
			response.sendRedirect(request.getContextPath() + homepage);
		} else {
			String message = "Error de inicio de sesión";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("frontend/login.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	
	public void login() throws ServletException, IOException, NoSuchAlgorithmException {
		login(null, null);
	}

	public String getRol(String login) {
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		return usuario.getRol();
	}

	public void register() throws ServletException, IOException, NoSuchAlgorithmException {
		String nombre = StringEscapeUtils.escapeHtml4(request.getParameter("nombre"));
		String apellidos = StringEscapeUtils.escapeHtml4(request.getParameter("apellidos"));
		String telefono = StringEscapeUtils.escapeHtml4(request.getParameter("telefono"));
		String direccion = StringEscapeUtils.escapeHtml4(request.getParameter("direccion"));
		int codPostal = Integer.parseInt(request.getParameter("codPostal"));
		String fechaNacTexto = request.getParameter("fechaNac");
		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
		String encpass = hashPassword(password);
		String rol = StringEscapeUtils.escapeHtml4(request.getParameter("tipoCuenta"));
		String sexo = StringEscapeUtils.escapeHtml4(request.getParameter("sexo"));
		String estadoCivil = StringEscapeUtils.escapeHtml4(request.getParameter("estadoCivil"));
		String formacion = StringEscapeUtils.escapeHtml4(request.getParameter("formacion"));
		
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		Localidad municipio = localidadDAO.get(codPostal);
		String localizacion = direccion;
		if(!direccion.contains(municipio.getNombre())) {
			localizacion += " " + municipio.getNombre();
		}
		
		
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fechaNac = null;
		try {
			fechaNac = dateformat.parse(fechaNacTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Date fechaRegistro = new Date();
		
		if(this.emailRepetido(email)) {
			String message = "No se pudo crear la cuenta. Ya existe otro usuario con el email " + email + ".";
			request.setAttribute("message", message);
			registerView();
		} else if (this.loginRepetido(login)) {
			String message = "No se pudo crear la cuenta. Ya existe otro usuario con el nombre de usuario " + login + ".";
			request.setAttribute("message", message);
			registerView();
		} else if(rol.equals(Rol.PROFESIONAL.toString())) {
			Profesional profesional = new Profesional(login, encpass, email, nombre, apellidos, fechaNac, localizacion, formacion, telefono, false, fechaRegistro, municipio);
			profesional.setEstadoCivil(estadoCivil);
			profesional.setSexo(sexo);
			profesional.setImagenRuta(DEFAULT_IMAGE);
			try {
				profesionalDAO.create(profesional);
			} catch (Exception e) {
				String message = "No se pudo crear la cuenta.";
				request.setAttribute("message", message);
				registerView();
				return;
			}
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			String cuerpoAlerta = "Un nuevo usuario profesional se registró el día " + dateFormat.format(fechaRegistro) 
								+ " a las " + timeFormat.format(fechaRegistro);
			List<Administrador> admins = administradorDAO.listAll();
			for(int i = 0; i < admins.size(); i++) {
				Alerta alerta = new Alerta(admins.get(i), "Nuevo profesional", StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
				
				AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
				alertaServicios.mandarNotificacion(alerta);
				
				alertaDAO.create(alerta);
			}
		} else {
			Ciudadano ciudadano = new Ciudadano(login, encpass, email, nombre, apellidos, fechaNac, localizacion, false, municipio);
			ciudadano.setTelefono(telefono);
			ciudadano.setEstadoCivil(estadoCivil);
			ciudadano.setFormacion(formacion);
			ciudadano.setSexo(sexo);
			ciudadano.setImagenRuta(DEFAULT_IMAGE);
			
			try {
				ciudadanoDAO.create(ciudadano);
			} catch (Exception e) {
				String message = "No se pudo crear la cuenta.";
				request.setAttribute("message", message);
				registerView();
				return;
			}
			
		}
		login(login, password);
	}
	
	private void inicializarUsuarioForm() {
		System.out.println("inicializar");
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		List<Localidad> localidades = localidadDAO.listAll();
		request.setAttribute("listaLocalidades", localidades);
		
		String[] listaSexoArray = {"Hombre", "Mujer", "Otro", "Prefiere no decir"};
		List<String> listaSexo = Arrays.asList(listaSexoArray);
		request.setAttribute("listaSexo", listaSexo);
		
		String[] listaEstadosCivilesArray = new String[]{"Casado/a", "Divorciado/a", "Soltero/a","Viudo/a", "Prefiere no decir"};
		List<String> listaEstadosCiviles = Arrays.asList(listaEstadosCivilesArray);
		request.setAttribute("listaEstadosCiviles", listaEstadosCiviles);
	}

	public void registerView() throws ServletException, IOException {
		System.out.println("register");
		inicializarUsuarioForm();
		
		String homepage = "frontend/register_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	public void modificarCuentaView() throws ServletException, IOException {
		modificarCuentaView(null);
	}
	
	public void modificarCuentaView(String message) throws ServletException, IOException {
		inicializarUsuarioForm();
		String login = (String) request.getSession().getAttribute("userLogin");
		String rol = (String) request.getSession().getAttribute("rol");
		request.setAttribute("rol", rol);
		System.out.println(login);
		System.out.println(rol);
		if(rol.equals(Rol.CIUDADANO.toString())) {
			Ciudadano usuario = ciudadanoDAO.get(login);
			request.setAttribute("usuario", usuario);
			System.out.println(usuario);
		} else if(rol.equals(Rol.PROFESIONAL.toString())) {
			Profesional usuario = profesionalDAO.get(login);
			request.setAttribute("usuario", usuario);
			System.out.println(usuario);
		} else if(rol.equals(Rol.ADMINISTRADOR.toString())) {
			Administrador usuario = administradorDAO.get(login);
			request.setAttribute("usuario", usuario);
			System.out.println(usuario);
		} else if(rol.equals(Rol.SUPERVISOR.toString())) {
			Supervisor usuario = supervisorDAO.get(login);
			request.setAttribute("usuario", usuario);
			System.out.println(usuario);
		}
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String homepage = "../frontend/register_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
		
	}

	public void eliminarCuenta() throws IOException {
		String login = (String) request.getSession().getAttribute("userLogin");
		String rol = (String) request.getSession().getAttribute("rol");
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		String imagenActual = usuario.getImagenRuta();
		if(!imagenActual.equals("../default.jpg")) {
			borrarArchivo(imagenActual);
		}
		//BORRAR COSAS DE LA BASE DE DATOS
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
		if(rol.equals(Rol.PROFESIONAL.toString())) {
			List<Visita> visitas = visitaDAO.listAllByLogin(login);
			if(visitas != null) {
				for(Visita visita : visitas) {
					List<Cita> citas = new ArrayList<>(visita.getCitas());
					for(Cita cita : citas) {	
						Date ahora = new Date();
						if(ahora.before(cita.getHoraInicio())) {
							String cuerpoAlerta = "Su cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
									+ timeFormat.format(cita.getHoraInicio()) + " ha sido cancelada.";								
							Alerta alerta = new Alerta(visita.getProfesional(), "Cita cancelada", StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
							
							alerta = alertaDAO.create(alerta);
							
							AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
							alertaServicios.mandarNotificacion(alerta);
						}
						citaDAO.delete(cita);
					}
					visitaDAO.delete(visita);
				}
			}
		} else if(rol.equals(Rol.CIUDADANO.toString())) {
			List<Cita> citas = citaDAO.listAllByLogin(login);
			if(citas != null) {
				for(Cita cita : citas) {	
					Date ahora = new Date();
					if(ahora.before(cita.getHoraInicio())) {	
						String cuerpoAlerta = "La cita del día " + dateFormat.format(cita.getHoraInicio()) + " a las " 
								+ timeFormat.format(cita.getHoraInicio()) + " ha sido cancelada.";								
						Alerta alerta = new Alerta(cita.getVisita().getProfesional(), "Cita cancelada", StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
							
						alerta = alertaDAO.create(alerta);
							
						AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
						alertaServicios.mandarNotificacion(alerta);
					}
					citaDAO.delete(cita);
				}
			}
		}
		usuarioInternoDAO.delete(login);
		removeSessionAttributes();
		response.sendRedirect(request.getContextPath());
	}

	public void actualizarCuenta() throws ServletException, IOException {
		String nombre = StringEscapeUtils.escapeHtml4(request.getParameter("nombre"));
		String apellidos = StringEscapeUtils.escapeHtml4(request.getParameter("apellidos"));
		String telefono = StringEscapeUtils.escapeHtml4(request.getParameter("telefono"));
		String direccion = StringEscapeUtils.escapeHtml4(request.getParameter("direccion"));
		
		String fechaNacTexto = request.getParameter("fechaNac");
		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		String login = StringEscapeUtils.escapeHtml4(request.getParameter("login"));
		String rol = (String) request.getSession().getAttribute("rol");
		boolean imagenCambia = Boolean.parseBoolean(request.getParameter("imagenCambia"));
		Part part = request.getPart("imagenPerfil");
		
		int codPostal = Integer.parseInt(request.getParameter("codPostal"));
		LocalidadDAO localidadDAO = new LocalidadDAO(entityManager);
		Localidad municipio = localidadDAO.get(codPostal);
		//String localizacion = direccion;
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");	
		Date fechaNac = null;
		try {
			fechaNac = dateformat.parse(fechaNacTexto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Date fechaRegistro = new Date();
		
		if(rol.equals(Rol.PROFESIONAL.toString())) {
			String sexo = StringEscapeUtils.escapeHtml4(request.getParameter("sexo"));
			String estadoCivil = StringEscapeUtils.escapeHtml4(request.getParameter("estadoCivil"));
			String formacion = StringEscapeUtils.escapeHtml4(request.getParameter("formacion"));
			String descripcion = StringEscapeUtils.escapeHtml4(request.getParameter("descripcion"));
			
			Profesional profesional = profesionalDAO.get(login);
			profesional.setApellidos(apellidos);
			profesional.setEmail(email);
			profesional.setEstadoCivil(estadoCivil);
			profesional.setFechaNac(fechaNac);
			profesional.setFormacion(formacion);
			if(imagenCambia) {
				String imagenRuta = setImagen(part, profesional.getImagenRuta());
				profesional.setImagenRuta(imagenRuta);
			}
			String localizacion = direccion;
			if(!direccion.contains(municipio.getNombre())) {
				localizacion += " " + municipio.getNombre();
			}
			profesional.setLocalizacion(localizacion);
			profesional.setLocalidad(municipio);
			profesional.setNombre(nombre);
			profesional.setRol(rol);
			profesional.setSexo(sexo);
			profesional.setTelefono(telefono);
			profesional.setDescripcion(descripcion);
			
			try {
				profesionalDAO.update(profesional);
			} catch (Exception e) {
				modificarCuentaView("Su información no ha podido ser modificada.");
				return;
			}
			
			/*
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
			String cuerpoAlerta = "Un nuevo usuario profesional se registró el día " + dateFormat.format(fechaRegistro) 
								+ " a las " + timeFormat.format(fechaRegistro);
			List<Administrador> admins = administradorDAO.listAll();
			for(int i = 0; i < admins.size(); i++) {
				Alerta alerta = new Alerta(admins.get(i), "Nuevo profesional", StringEscapeUtils.escapeHtml4(cuerpoAlerta), false);
				
				AlertaServicios alertaServicios = new AlertaServicios(entityManager, request, response);
				alertaServicios.mandarNotificacion(alerta);
				
				alertaDAO.create(alerta);
			}
			*/
		} else if(rol.equals(Rol.CIUDADANO.toString())){
			String sexo = StringEscapeUtils.escapeHtml4(request.getParameter("sexo"));
			String estadoCivil = StringEscapeUtils.escapeHtml4(request.getParameter("estadoCivil"));
			String formacion = StringEscapeUtils.escapeHtml4(request.getParameter("formacion"));
			
			Ciudadano ciudadano = ciudadanoDAO.get(login);
			ciudadano.setApellidos(apellidos);
			ciudadano.setEmail(email);
			ciudadano.setEstadoCivil(estadoCivil);
			ciudadano.setFechaNac(fechaNac);
			ciudadano.setFormacion(formacion);
			if(imagenCambia) {
				String imagenRuta = setImagen(part, ciudadano.getImagenRuta());
				ciudadano.setImagenRuta(imagenRuta);
			}
			String localizacion = direccion;
			if(!direccion.contains(municipio.getNombre())) {
				localizacion += " " + municipio.getNombre();
			}
			ciudadano.setLocalizacion(localizacion);
			ciudadano.setLocalidad(municipio);
			ciudadano.setNombre(nombre);
			ciudadano.setRol(rol);
			ciudadano.setSexo(sexo);
			ciudadano.setTelefono(telefono);
			try {
				ciudadanoDAO.update(ciudadano);
			} catch (Exception e) {
				modificarCuentaView("Su información no ha podido ser modificada.");
				return;
			}
			
		} else if(rol.equals(Rol.ADMINISTRADOR.toString())) {
			Administrador admin = administradorDAO.get(login);
			admin.setApellidos(apellidos);
			admin.setEmail(email);
			admin.setFechaNac(fechaNac);
			if(imagenCambia) {
				String imagenRuta = setImagen(part, admin.getImagenRuta());
				admin.setImagenRuta(imagenRuta);
			}
			admin.setNombre(nombre);
			String nss = StringEscapeUtils.escapeHtml4(request.getParameter("nss"));
			String organismoCoordinador = StringEscapeUtils.escapeHtml4(request.getParameter("organismoCoordinador"));
			admin.setNss(nss);
			admin.setOrganismoCoordinador(organismoCoordinador);
			admin.setRol(rol);
			admin.setTelefono(telefono);
			
			try {
				administradorDAO.update(admin);
			} catch (Exception e) {
				modificarCuentaView("Su información no ha podido ser modificada.");
				return;
			}
		} else if(rol.equals(Rol.SUPERVISOR.toString())) {
			Supervisor supervisor = supervisorDAO.get(login);
			supervisor.setApellidos(apellidos);
			supervisor.setEmail(email);
			supervisor.setFechaNac(fechaNac);
			if(imagenCambia) {
				String imagenRuta = setImagen(part, supervisor.getImagenRuta());
				supervisor.setImagenRuta(imagenRuta);
			}
			supervisor.setNombre(nombre);
			String nss = StringEscapeUtils.escapeHtml4(request.getParameter("nss"));
			String organismoCoordinador = StringEscapeUtils.escapeHtml4(request.getParameter("organismoCoordinador"));
			supervisor.setNss(nss);
			supervisor.setOrganismoCoordinador(organismoCoordinador);
			supervisor.setRol(rol);
			supervisor.setTelefono(telefono);		
			
			try {
				supervisorDAO.update(supervisor);
			} catch (Exception e) {
				modificarCuentaView("Su información no ha podido ser modificada.");
				return;
			}
		}
		
		//login(login, password);
		
		modificarCuentaView("Su información ha sido modificada con éxito.");
	}
	
	
	public  void changePassword() throws ServletException, IOException, NoSuchAlgorithmException { 
		String login = (String) request.getSession().getAttribute("userLogin");
		//String rol = (String) request.getSession().getAttribute("rol");
		UsuarioInterno usuario = usuarioInternoDAO.get(login);
		String password = StringEscapeUtils.escapeHtml4(request.getParameter("newPassword"));
		System.out.println(password);
		String encpass = hashPassword(password);
		
		usuario.setPassword(encpass);
		usuarioInternoDAO.update(usuario);
		
		modificarCuentaView("La contraseña ha sido cambiada con éxito.");
	}
	
	private String setImagen(Part part, String imagenActual) throws IOException, ServletException {
		//Part part = request.getPart("imagenPerfil");
		String imagenRuta = DEFAULT_IMAGE;
		System.out.println("AQUI");
		if(!imagenActual.equals(DEFAULT_IMAGE)) {
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
}
