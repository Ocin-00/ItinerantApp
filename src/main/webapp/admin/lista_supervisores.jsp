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
	  <li><a href="lista_profesionales">Profesionales</a></link>
	  <li><a href="lista_certificados">Certificados</a></link>
	  <li><a href="lista_supervisores" class="active">Supervisores</a></link>
	</ul>
	
	<h3>Supervisores autorizados</h3>
	
	<c:if test="${message != null}">
		<div><h4>${message}</h4></div>
	</c:if>
	
	
	<div>
		<table border="1">
			<tr>
				<th>Índice</th>
				<th>Nombre</th>
				<th>Apellidos</th>
				<th>Login</th>
				<th>Email</th>
				<th>Teléfono</th>
				<th>Organismo Coordinador</th>
				<th>Nivel de acceso</th>
				<th>Acciones</th>
			</tr>
			<c:forEach var="supervisor" items="${supervisores}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${supervisor.nombre}</td>
					<td>${supervisor.apellidos}</td>
					<td>${supervisor.login}</td>
					<td>${supervisor.email}</td>
					<td>${supervisor.telefono}</td>
					<td>${supervisor.organismoCoordinador}</td>
					<td>${supervisor.nivelAcceso}</td>
					<td align="center">
						<a href="editar_supervisor?id=${supervisor.login}">Editar</a> |
						<a href="javascript:void(0);" onclick="confirmDelete('${supervisor.login}');">Borrar</a>
					</td>
				</tr>
			</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="center">
						<a href="supervisor_form.jsp">Añadir</a>
					</td>
				</tr>
		</table>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
	
	<script type="text/javascript">
		function confirmDelete(login){
			if(confirm("¿Desea eliminar el usuario de login " + login + "?")) {
				window.location = "borrar_supervisor?id=" + login;
			}
		}
	</script>
</body>
</html>