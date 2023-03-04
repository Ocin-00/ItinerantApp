<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	#side-menu ul li:nth-child(1){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container-fluid mt-3" style="min-height: 75vh">
			<h2>Profesionales pendientes de verificación:</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<c:if test="${empty profesionalesSinValidar}" >
				<h5>No hay nuevos profesionales.</h5>
			</c:if>
			<c:if test="${not empty profesionalesSinValidar}" >
			<div class="d-none d-md-block">
				<table border="1" class="table border table-hover text-center align-middle">
					<tr>
						<th >Índice</th>
						<th scope="col">Nombre</th>
						<th scope="col">Apellidos</th>
						<th scope="col">Login</th>
						<th scope="col">Email</th>
						<th scope="col">Teléfono</th>
						<th scope="col">Fecha de Registro</th>
						<th scope="col">Acciones</th>
					</tr>
					<c:forEach var="profesional" items="${profesionalesSinValidar}" varStatus="status">
						<tr>
							<td scope="row">${status.index + 1}</td>
							<td>${profesional.nombre}</td>
							<td>${profesional.apellidos}</td>
							<td>${profesional.login}</td>
							<td>${profesional.email}</td>
							<td>${profesional.telefono}</td>
							<td><fmt:formatDate value="${profesional.fechaRegistro}" pattern="dd-MM-yyyy HH:mm" /></td>
							<td>
								<a href="javascript:void(0);" class="btn btn-success table-icon validateLink" id="${profesional.login}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Validar profesional">
									<i class="fa-solid fa-check"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${profesional.login}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Anular cuenta">
									<i class="fa-solid fa-xmark"></i>
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<div class="d-flex d-md-none justify-content-center mb-3">
				<table border="1" class="border border-dark">
					<c:forEach var="profesional" items="${profesionalesSinValidar}" varStatus="status">
						<tr>
							<th>${profesional.nombre} ${profesional.apellidos}</th>
						</tr>
						<tr>
							<td><label class="fw-bold">Email:</label> ${profesional.email}</td>
						</tr>
						<tr>
							<td><label class="fw-bold">Teléfono:</label> ${profesional.telefono}</td>
						</tr>
						<tr>
						<tr>
							<td><label class="fw-bold">Fecha de registro:</label> <fmt:formatDate value="${profesional.fechaRegistro}" pattern="dd-MM-yyyy HH:mm" /></td>
						</tr>
						<tr class="border-bottom border-dark">
						<td colspan="4" class="d-flex flex-row align-items-center justify-content-center">
								<a href="javascript:void(0);" class="btn btn-success table-icon validateLink m-2" id="${profesional.login}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Validar profesional">
									<i class="fa-solid fa-check"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink m-2" id="${profesional.login}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Anular cuenta">
									<i class="fa-solid fa-xmark"></i>
								</a>
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