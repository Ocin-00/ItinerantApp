<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<h4>Administración</h4>
	<ul class="menu">
	  <li><a href="lista_profesionales" class="active">Profesionales</a></link>
	  <li><a href="lista_certificados">Certificados</a></link>
	  <li><a href="lista_supervisores">Supervisores</a></link>
	</ul>
	
	<h3>Profesionales pendientes de verificación</h3>
	<div>
		<table border="1">
			<tr>
				<th>Índice</th>
				<th>Nombre</th>
				<th>Apellidos</th>
				<th>Login</th>
				<th>Email</th>
				<th>Teléfono</th>
				<th>Fecha de Registro</th>
				<th>Acciones</th>
			</tr>
			<c:forEach var="profesional" items="${profesionalesSinValidar}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${profesional.nombre}</td>
					<td>${profesional.apellidos}</td>
					<td>${profesional.login}</td>
					<td>${profesional.email}</td>
					<td>${profesional.telefono}</td>
					<td>${profesional.fechaRegistro}</td>
					<td align="center">
						<a href="Validar">Validar</a> |
						<a href="Anular">Anular</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>