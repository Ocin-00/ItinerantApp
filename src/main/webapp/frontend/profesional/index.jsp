<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Profesionales</title>
	<link rel="stylesheet" href="../../css/style.css">
	<script type="text/javascript" src="../../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<h4>Bienvenido, <c:out value="${sessionScope.userLogin}"></c:out></h4>
	<h3>Visitas programadas</h3>
	
	<c:if test="${message != null}">
		<div><h4>${message}</h4></div>
	</c:if>
	
	<div>
		<table border="1">
			<tr>
				<th>�ndice</th>
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
					<td>${visita.horaInicio}</td>
					<td>${visita.horaFin}</td>
					<td align="center">
						<a href="detalles">Ver detalles</a> |
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
						<a href="nueva_visita">A�adir</a>
					</td>
				</tr>
		</table>
	</div>
	
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					id = $(this).attr("id");
					if(confirm("�Desea eliminar la visita de id " + id + "?")) {
						window.location = "borrar_visita?id=" + id;
					}
				});
			});
		});
	</script>
</html>