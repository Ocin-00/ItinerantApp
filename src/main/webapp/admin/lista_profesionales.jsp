<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<h4>Administración</h4>
	<ul class="menu">
	  <li><a href="lista_profesionales" class="active">Profesionales</a>
	  <li><a href="lista_certificados">Certificados</a>
	  <li><a href="lista_supervisores">Supervisores</a>
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
						<a href="javascript:void(0);" class="validateLink" id="${profesional.login}">Validar</a> |
						<a href="javascript:void(0);" class="deleteLink" id="${profesional.login}">Anular</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					if(confirm("¿Desea anular la cuenta del profesional de login " + login + "?")) {
						window.location = "anular_profesional?id=" + login;
					}
				});
			});
			$(".validateLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					if(confirm("¿Desea validar la cuenta del profesional de login " + login + "?")) {
						window.location = "validar_profesional?id=" + login;
					}
				});
			});
		});
</script>
</html>