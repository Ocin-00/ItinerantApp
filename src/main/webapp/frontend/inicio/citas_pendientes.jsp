<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Itinerant - Citas Pendientes</title>
<link rel="stylesheet" href="../css/layout.css">
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content">
		<h3>Citas pendientes</h3>
		<table border="1">
			<tr>
				<th>Índice</th>
				<th>Profesional</th>
				<th>Visita</th>
				<th>Fecha</th>
				<th>Hora</th>
				<th>Teléfono</th>
				<th>Acciones</th>
			</tr>
			<c:forEach var="cita" items="${citas}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${cita.visita.profesional}</td>
					<td>${cita.visita.nombre}</td>
					<td>${cita.visita.fecha}</td>
					<td>${cita.horaInicio}</td>
					<td>${cita.visita.profesional.telefono}</td>
					<td align="center">
						<a href="detalles_cita">Detalles</a> |
						<a href="anular_cita">Anular</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</div>
	
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>