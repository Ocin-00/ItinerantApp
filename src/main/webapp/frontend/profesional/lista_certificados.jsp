<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Profesionales</title>
	<link rel="stylesheet" href="../css/layout.css">
	<link rel="stylesheet" href="../css/side-bar-style.css">
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
			<h2>Tus certificados:</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<c:if test="${empty certificados}" >
				<h5>Todavía no tienes certificados.</h5>
			</c:if>
			<c:if test="${not empty certificados}" >
			<div class="d-none d-md-block">
			<table border="1" class="table border table-hover text-center align-middle">
				<tr class="text-center">
					<th>Índice</th>
					<th>Título</th>
					<th>Entidad emisora</th>
					<th>Año</th>
					<th>Válido</th>
					<th class="text-center">Acciones</th>
				</tr>
				<c:forEach var="certificado" items="${certificados}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${certificado.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
						<td>${certificado.anyo}</td>
							<c:if test="${certificado.validez == true}">
								<td>Sí</td>
							</c:if>
							<c:if test="${certificado.validez == false}">
								<td>No</td>
							</c:if>
						<td class="text-center">
								<a href="${certificado.ruta}" class="btn btn-secondary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver certificado">
									<i class="fa-solid fa-eye"></i>
								</a>
								<a href="editar_certificado?id=${certificado.idCertificado}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Editar certificado">
									<i class="fa-solid fa-pencil"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${certificado.idCertificado}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar certificado">
									<i class="fa-solid fa-xmark"></i>
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
						<td class="text-center">
							<a href="nuevo_certificado" class="btn btn-warning table-icon validateLink" 
								data-bs-toggle="tooltip" data-bs-placement="top" title="Añadir certificado">
									<i class="fa-solid fa-plus"></i>
								</a>
						</td>
					</tr>
			</table>
			</div>
			
			<div class="d-md-none d-block">
			<table border="1" class="table border table-hover text-center align-middle">
				<tr>
					<th>Título</th>
					<th>Entidad emisora</th>
					<th>Válido</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="certificado" items="${certificados}" varStatus="status">
					<tr>
						<td>${certificado.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
							<c:if test="${certificado.validez == true}">
								<td>Sí</td>
							</c:if>
							<c:if test="${certificado.validez == false}">
								<td>No</td>
							</c:if>
						<td >
							<div class="d-flex flex-row align-items-center justify-content-center align-middle">
								<a href="${certificado.ruta}" class="btn btn-secondary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver certificado">
									<i class="fa-solid fa-eye"></i>
								</a>
								<a href="editar_certificado?id=${certificado.idCertificado}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Editar certificado">
									<i class="fa-solid fa-pencil"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${certificado.idCertificado}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar certificado">
									<i class="fa-solid fa-xmark"></i>
								</a>
							</div>
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td class="text-center">
							<a href="nuevo_certificado" class="btn btn-warning table-icon validateLink" 
								data-bs-toggle="tooltip" data-bs-placement="top" title="Añadir certificado">
									<i class="fa-solid fa-plus"></i>
								</a>
						</td>
					</tr>
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
					id = $(this).attr("id");
					if(confirm("¿Desea eliminar este certificado?")) {
						window.location = "borrar_certificado?id=" + id;
					}
				});
			});
		});
	</script>
</html>