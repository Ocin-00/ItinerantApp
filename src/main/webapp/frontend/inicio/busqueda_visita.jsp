<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<c:if test="${sessionScope.userLogin != null}">
		<link rel="stylesheet" href="../css/layout.css">
		<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/notify.js"></script>
	    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="../js/my-notifications.js"></script>
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
		<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<link rel="stylesheet" href="css/layout.css">
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	</c:if>
<title>Itinerant - Visitas</title>
</head>
<body>
	<c:if test="${sessionScope.userLogin != null}">
		<jsp:directive.include file="/frontend/header_user.jsp"/>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<jsp:directive.include file="/frontend/header.jsp"/>
	</c:if>
	<div id="main">
		<c:if test="${sessionScope.rol == 'ADMINISTRADOR'}">
			<jsp:directive.include file="/../admin/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'PROFESIONAL'}">
			<jsp:directive.include file="/../frontend/profesional/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'CIUDADANO'}">
			<jsp:directive.include file="/../frontend/inicio/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'SUPERVISOR'}">
			<jsp:directive.include file="/../supervisor/side_menu.jsp"/>
		</c:if>
		
		<c:set var="imagenRuta" value="${visita.imagenRuta}"></c:set>
		<div id="main-content-split">
			<div>
				<h2>Detalles de la visita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="<%String imagePath = request.getContextPath();
		 						 out.println(imagePath + pageContext.getAttribute("imagenRuta").toString().substring(2)); 
								 %>" id="thumbnail" alt="No hay imagen disponible" width="200"/>
						</td>
						<td>Nombre: ${visita.nombre}</td>
					</tr>
					<tr>
						<td>Localidad: ${visita.localidad.nombre}</td>
					</tr>
					<tr>
						<td>Fecha: ${visita.fecha}</td>
					</tr>
					<tr>
						<td>Hora: ${visita.horaInicio.hours}:${visita.horaInicio.minutes} - ${visita.horaFin.hours}:${visita.horaFin.minutes}</td>
					</tr>
					<tr>
						<td>Tiempo de cita: ${visita.duracionCitas} min</td>
					</tr>
					<tr>
						<td>Precio: ${visita.precio} &euro;</td>
					</tr>
					<tr>
						<td>Categorias:</td>
					</tr>
					<tr>
						<td>Descripcion:</td>
					</tr>
					<tr>
						<td>${visita.descripcion}</td>
					</tr>
				</table>
			</div>
			<c:if test="${esCiudadano == true}">
				<div class="main-content-split-items">
					<h4>Pedir cita:</h4>
					<form action="pedir_cita" method="post" id="pedirCitaForm">
					<input type="hidden" id="id" name="id" value="${visita.idVisita}">
					<c:if test="${message != null}">
						<div><h4>${message}</h4></div>
					</c:if>
						<div>
							<label for="horaCita">Horas disponibles:</label>
							<select name="horaCita" id="horaCita">
								<c:forEach items="${listaHoras}" var="horaCita">
									<option value="${horaCita}">
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										  String hora = format.format(pageContext.getAttribute("horaCita"));
										  out.println(hora);  
										%>
									</option>
								</c:forEach>
							</select>
						</div>
						<div>
							<div>
								<label>Dirección: ${horaCita.hours}:${horaCita.minutes}</label>
							</div>
								<input type="text" name="direccion" id="direccion">
								<input type="checkbox" name="direccionUsuario" id="direccionUsuario" value="true">
								<label for="direccionUsuario">Usar dirección guardada en la cuenta</label>
						</div>
						<div>					
							<div>
								<label>Anotaciones:</label>
							</div>
							<textarea rows="5" cols="50" name="anotaciones" id="anotaciones"></textarea>
						</div>
						<button type="submit">Pedir cita</button>
					</form>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
	var checkbox = document.querySelector("#direccionUsuario");
	var input = document.querySelector("#direccion");
	
	var toogleInput = function(e){
	  input.disabled = e.target.checked;
	};
	
	toogleInput({target: checkbox});
	checkbox.addEventListener("change", toogleInput);
</script>
</html>