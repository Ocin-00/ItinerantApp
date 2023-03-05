<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
	<c:if test="${sessionScope.userLogin != null}">
		<link rel="stylesheet" href="../css/layout.css">
		<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/notify.js"></script>
	    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="../js/my-notifications.js"></script>
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
		<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
		
		<link rel="stylesheet" href="../css/bootstrap.min.css">
		<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<link rel="stylesheet" href="css/layout.css">
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	</c:if>
<title>Itinerant - Visitas</title>
</head>
<body class="d-flex flex-column min-vh-100">
	<c:if test="${sessionScope.userLogin != null}">
		<jsp:directive.include file="/frontend/header_user.jsp"/>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<jsp:directive.include file="/frontend/header.jsp"/>
	</c:if>
	<div class="wrapper">
		<c:if test="${sessionScope.userLogin != null}">
			<jsp:directive.include file="../side_menu.jsp"/>
		</c:if>
		
		<c:set var="imagenRuta" value="${visita.imagenRuta}"></c:set>
		<c:set var="nombre" value="${visita.nombre}"></c:set>
		<div class="container-fluid d-lg-flex flex-row mt-md-5 justify-content-evenly pb-3"  style="min-height: 75vh">
			<div class="main-content-split-items">
				<h2>Detalles de la visita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="<%String imgPath = request.getContextPath();
								   Object imagenRuta = pageContext.getAttribute("imagenRuta");
								   if(imagenRuta != null) {
									   out.println(imgPath + imagenRuta.toString().substring(2)); 
								   }
								%>" id="thumbnail" alt="No hay imagen disponible" class="img-fluid" style="max-height: 250px;max-width: 250px"/>
						</td>
						<td>Nombre: ${visita.nombre}</td>
					</tr>
					<tr>
						<td>Localidad: ${visita.localidad.nombre}</td>
					</tr>
					<tr>
						<td>Fecha: <fmt:formatDate value="${visita.fecha}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<td>Hora: ${visita.horaInicio.hours}:${visita.horaInicio.minutes} - ${visita.horaFin.hours}:${visita.horaFin.minutes}</td>
					</tr>
					<tr>
						<td>Tiempo de cita: ${visita.duracionCitas} min</td>
					</tr>
					</table>
					<table class="visita-descripcion">
					<tr>
						<td>Precio: ${visita.precio} &euro;</td>
					</tr>
					<tr>
						<td>Categorias: 
						<c:forEach var="categoria" items="${categorias}" varStatus="status">
							<c:if test="${status.index + 1 == fn:length(visita.categorias)}">
								${categoria.nombre}.
							</c:if>
							<c:if test="${status.index + 1 != fn:length(visita.categorias)}">
								${categoria.nombre},
							</c:if>  
						</c:forEach>
						</td>
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
			<c:if test="${visitaFutura == true}">
			<c:if test="${sancionado == true}">
				<div class="main-content-split-items">
					<h4>Pedir cita:</h4>
						<div><h4>Lo sentimos, pero se le ha sancionado.</h4></div>
				</div>
			</c:if>
			<c:if test="${sancionado == false}">
				<div class="main-content-split-items">
					<h4>Pedir cita:</h4>
					<form action="pedir_cita" method="post" id="pedirCitaForm" class="">
					<input type="hidden" id="id" name="id" value="${visita.idVisita}">
					<c:if test="${message != null}">
						<div><h4>${message}</h4></div>
					</c:if>
						<div class="input-group">
							<label for="horaCita"  class="form-label">Horas disponibles:</label>
							<select name="horaCita" id="horaCita" class="form-select border-dark-subtle">
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
								<label for="direccion" class="form-label">Dirección:</label>
							</div>
								<input type="text" name="direccion" id="direccion" class="form-control border-dark-subtle">
							<div>
								<input type="checkbox" name="direccionUsuario" id="direccionUsuario" value="true" class="form-check-input border-dark-subtle">
								<label for="direccionUsuario" class="form-label">Usar dirección guardada en la cuenta</label>
							</div>
								
						</div>
						<div>					
							<div>
								<label for="anotaciones" class="form-label">Anotaciones:</label>
							</div>
							<textarea rows="5" cols="50" name="anotaciones" id="anotaciones" class="form-control border-dark-subtle" maxlength="150"></textarea>
						</div>
						<div class="d-flex justify-content-center">
							<button type="submit" class="">Pedir cita</button>
						</div>
					</form>
				</div>
			</c:if>
			</c:if>
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