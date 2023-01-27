<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
</head>
<style>
	#side-menu a:nth-child(4){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		
		<div id="main-content">
			<h2>Supervisores autorizados</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
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
							<a href="javascript:void(0);" class="deleteLink" id="${supervisor.login}">Borrar</a>
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
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					if(confirm("¿Desea eliminar el usuario de login " + login + "?")) {
						window.location = "borrar_supervisor?id=" + login;
					}
				});
			});
		});
	</script>
</body>
</html>