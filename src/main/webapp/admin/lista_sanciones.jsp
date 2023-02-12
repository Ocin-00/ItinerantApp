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
	#side-menu a:nth-child(6){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">	
		<jsp:directive.include file="side_menu.jsp"/>
		
		<div id="main-content">
			<h2>Usuarios propuestos a sanción</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<table border="1">
				<tr>
					<th>Índice</th>
					<th>Nombre</th>
					<th>Login</th>
					<th>Email</th>
					<th>Teléfono</th>
					<th>Sanciones</th>
				</tr>
				<c:forEach var="ciudadano" items="${ciudadanosSancion}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${ciudadano.nombre} ${ciudadano.apellidos}</td>
						<td>${ciudadano.login}</td>
						<td>${ciudadano.email}</td>
						<td>${ciudadano.telefono}</td>
						<td align="center">
							<button class="noSanctionBtn" login="${ciudadano.login}">No sancionar</button>
							<button class="sanctionBtn" value="1" login="${ciudadano.login}">1 día</button>
							<button class="sanctionBtn" value="3" login="${ciudadano.login}">3 días</button>
							<button class="sanctionBtn" value="5" login="${ciudadano.login}">5 días</button>
							<button class="sanctionBtn" value="10" login="${ciudadano.login}">10 días</button>
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
			$(".sanctionBtn").on("click", function() {
				diasSancion = $(this).attr("value");
				login = $(this).attr("login");
				if(confirm("¿Desea aplicar una sanción al usuario de login " + login + "?")) {
					window.location = "aplicar_sancion?id=" + login + "&dias="+diasSancion;
				}
			});

			$(".noSanctionBtn").on("click", function() {
				login = $(this).attr("login");
				if(confirm("¿Desea no sancionar al usuario de login " + login + "?")) {
					window.location = "no_sancionar?id=" + login;
				}
			});
		});
</script>
</html>