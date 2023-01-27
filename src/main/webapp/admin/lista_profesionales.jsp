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
	#side-menu a:nth-child(2){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">	
		<jsp:directive.include file="side_menu.jsp"/>
		
		<div id="main-content">
			<h2>Profesionales pendientes de verificación</h2>
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