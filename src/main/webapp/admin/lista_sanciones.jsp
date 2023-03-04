<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
</head>
<style>
	#side-menu ul li:nth-child(5){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container mt-3" style="min-height: 75vh">
			<h2>Usuarios propuestos a sanción:</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<c:if test="${empty ciudadanosSancion}" >
				<h5>No hay nuevos usuarios propuestos a sanción.</h5>
			</c:if>
			<c:if test="${not empty ciudadanosSancion}" >
			<div class="d-none d-md-block">
			<table border="1" class="table border table-hover text-center align-middle">
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
						<td>
							<button class="btn btn-success noSanctionBtn" login="${ciudadano.login}">No sancionar</button>
							<button class="btn btn-primary sanctionBtn" value="1" login="${ciudadano.login}">1 día</button>
							<button class="btn btn-warning sanctionBtn" value="3" login="${ciudadano.login}">3 días</button>
							<button class="btn btn-danger sanctionBtn" value="5" login="${ciudadano.login}">5 días</button>
							<button class="btn btn-dark sanctionBtn" value="10" login="${ciudadano.login}">10 días</button>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			<div class="d-flex d-md-none justify-content-center mb-3">
				<table border="1" class="border border-dark">
					<c:forEach var="ciudadano" items="${ciudadanosSancion}" varStatus="status">
						<tr>
							<th>${ciudadano.nombre} ${ciudadano.apellidos}</th>
						</tr>
						<tr>
							<td><label class="fw-bold">Email:</label> ${ciudadano.email}</td>
						</tr>
						<tr>
							<td><label class="fw-bold">Teléfono:</label> ${ciudadano.telefono}</td>
						</tr>
						<tr>
						<tr class="border-bottom border-dark">
							<td colspan="4" class="d-flex flex-row align-items-center justify-content-center">
								<button class="btn btn-success noSanctionBtn" login="${ciudadano.login}">No sancionar</button>
								<button class="btn btn-primary sanctionBtn" value="1" login="${ciudadano.login}">1 día</button>
								<button class="btn btn-warning sanctionBtn" value="3" login="${ciudadano.login}">3 días</button>
								<button class="btn btn-danger sanctionBtn" value="5" login="${ciudadano.login}">5 días</button>
								<button class="btn btn-dark sanctionBtn" value="10" login="${ciudadano.login}">10 días</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			</c:if>
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