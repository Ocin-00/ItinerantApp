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
	#side-menu ul li:nth-child(3){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container-fluid mt-3" style="min-height: 75vh">
			<h2>Supervisores autorizados: </h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			
			<div class="d-none d-md-block">
			<table border="1" class="table border table-hover text-center align-middle">
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
						<td>
						
								<a href="editar_supervisor?id=${supervisor.login}" class="btn btn-primary table-icon validateLink"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Editar supervisor">
									<i class="fa-solid fa-pencil"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${supervisor.login}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar supervisor">
									<i class="fa-solid fa-trash-can"></i>
								</a>
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
						<td class="justify-content-center">
							<a href="supervisor_form.jsp" class="btn btn-warning table-icon validateLink" 
								data-bs-toggle="tooltip" data-bs-placement="top" title="Añadir supervisor">
									<i class="fa-solid fa-plus"></i>
								</a>
						</td>
					</tr>
			</table>
			</div>
			
			<div class="d-flex d-md-none justify-content-center mb-3">
			<table border="1" class="border border-dark">
				<c:forEach var="supervisor" items="${supervisores}" varStatus="status">
						<tr>
							<th>${supervisor.nombre} ${supervisor.apellidos}</th>
						</tr>
						<tr>
							<td><label class="fw-bold">Email:</label> ${supervisor.email}</td>
						</tr>
						<tr>
							<td><label class="fw-bold">Teléfono:</label> ${supervisor.telefono}</td>
						</tr>
						<tr>
						<tr>
							<td><label class="fw-bold">Organismo Coordinador:</label> ${supervisor.organismoCoordinador}</td>
						</tr>
						<tr>
							<td><label class="fw-bold">Nivel de acceso:</label> ${supervisor.nivelAcceso}</td>
						</tr>
					<tr class="border-bottom border-dark">
						<td class="d-flex flex-row align-items-center justify-content-center">
								<a href="editar_supervisor?id=${supervisor.login}" class="btn btn-primary table-icon validateLink"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Editar supervisor">
									<i class="fa-solid fa-pencil"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${supervisor.login}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar supervisor">
									<i class="fa-solid fa-trash-can"></i>
								</a>
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td class="d-flex flex-row align-items-center justify-content-center">
							<a href="supervisor_form.jsp" class="btn btn-warning table-icon validateLink" 
								data-bs-toggle="tooltip" data-bs-placement="top" title="Añadir supervisor">
									<i class="fa-solid fa-plus"></i>
								</a>
						</td>
					</tr>
			</table>
			</div>
			
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