<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Profesionales</title>
	<link rel="stylesheet" href="../css/layout.css">
	<link rel="stylesheet" href="../css/side-bar-style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
</head>
<style>
	#side-menu a:nth-child(2){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content">
			<h4>Bienvenido, <c:out value="${sessionScope.userLogin}"></c:out></h4>
			<h3>Visitas programadas</h3>
		
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<table border="1">
				<tr>
					<th>Índice</th>
					<th>Localidad</th>
					<th>Fecha</th>
					<th>Hora inicio</th>
					<th>Hora fin</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="visita" items="${visitas}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${visita.localidad.nombre}</td>
						<td>${visita.fecha}</td>
						<td>${visita.horaInicio.hours}:${visita.horaInicio.minutes}</td>
						<td>${visita.horaFin.hours}:${visita.horaFin.minutes}</td>
						<td align="center">
							<a href="ver_visita?id=${visita.idVisita}">Ver detalles</a> |
							<a href="javascript:void(0);" class="deleteLink" id="${visita.idVisita}">Borrar</a>
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td align="center">
							<a href="nueva_visita">Añadir</a>
						</td>
					</tr>
			</table>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					id = $(this).attr("id");
					if(confirm("¿Desea eliminar la visita de id " + id + "?")) {
						window.location = "borrar_visita?id=" + id;
					}
				});
			});
		});
	</script>
</html>