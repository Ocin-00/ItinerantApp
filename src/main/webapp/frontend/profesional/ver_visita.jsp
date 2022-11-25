<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
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
						<td>Hora: ${visita.horaInicio.hours}:${visita.horaInicio.minutes} - ${visita.horaFin.hours}:${visita.horaFin.minutes}</td>
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
			<div>
				<table border="1">
					<tr>
						<th>�ndice</th>
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
							<td>${cita.horaInicio}</td>
							<td align="center">
								<a href="ver_detalles">Detalles</a> |
								<a href="anular_cita">Anular</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div>
				<button  onclick="location.href='editar_visita?id=${visita.idVisita}';">Editar</button>
			</div>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					id = $(this).attr("id");
					id = $(this).attr("visita");
					if(confirm("�Desea eliminar la cita del usuario " + id + "?")) {
						window.location = "borrar_cita?visita=" + visita + "&id=" + id;
					}
				});
			});
		});
	</script>
</html>