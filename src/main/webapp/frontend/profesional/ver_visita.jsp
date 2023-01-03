<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<title>Itinerant - Visitas</title>
</head>
<style>
	#side-menu a:nth-child(2){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content-items">
			<div>
				<h2>Detalles de la visita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="${visita.imagenRuta}" id="thumbnail" alt="No hay imagen disponible" width="200"/>
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
						<td>Hora: <c:set var="hora" value="${visita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%> - 
										<c:set var="hora" value="${visita.horaFin}"></c:set>
										<%hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
						</td>
					</tr>
					<tr>
						<td>Tiempo de cita: ${visita.duracionCitas} min</td>
					</tr>
					<tr>
						<td>Precio: ${visita.precio}</td>
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
			<c:if test="${numeroCitas > 0}">
				<div>
					<c:if test="${message != null}">
						<h3>${message}</h3>
					</c:if>
					<table border="1">
						<tr>
							<th>Índice</th>
							<th>Cliente</th>
							<th>Visita</th>
							<th>Fecha</th>
							<th>Hora</th>
							<th>Acciones</th>
						</tr>
						<c:forEach var="cita" items="${citas}" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${cita.ciudadano}</td>
								<td>${cita.visita.nombre}</td>
								<td>${cita.visita.fecha}</td>
								<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
								<td align="center">
									<a href="ver_cita?id=${visita.idVisita}&login=${cita.id.idCiudadano}">Detalles</a>
									<c:if test="${esFutura == true}">
										|
										<a href="javascript:void(0);" class="deleteLink" id="${cita.id.idCiudadano}" visita="${visita.idVisita}">Anular</a>
									</c:if> </td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			<c:if test="${pasadoTiempoLimite == true}">
				<div>
					<div>
						<label>Describa la urgencia:</label>
					</div>
					<textarea rows="5" cols="50" name="urgencia" id="urgencia"></textarea>
				</div>
			</c:if>
			<c:if test="${esFutura == true}">
				<div>
					<button  onclick="location.href='editar_visita?id=${visita.idVisita}';">Editar</button>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					idVisita = $(this).attr("visita");
					if(confirm("¿Desea eliminar la cita del usuario " + login + "?")) {
						window.location = "anular_cita?id=" + idVisita + "&login=" + login;
					}
				});
			});
		});
	</script>
</html>