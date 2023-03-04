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
	#side-menu ul li:nth-child(2){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container-fluid mt-3" style="min-height: 75vh">
			<h2>Certificados pendientes de verificación:</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<c:if test="${empty certificadosSinValidar}" >
				<h5>No hay nuevos certificados.</h5>
			</c:if>
			<c:if test="${not empty certificadosSinValidar}" >
			<div class="d-none d-md-block">
			<table border="1" class="table border table-hover text-center align-middle">
				<tr>
					<th>Índice</th>
					<th>Profesional</th>
					<th>Título</th>
					<th>Entidad emisora</th>
					<th>Año</th>
					<th>Fecha de Registro</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="certificado" items="${certificadosSinValidar}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${certificado.profesional}</td>
						<td>${certificado.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
						<td>${certificado.anyo}</td>
						<td><fmt:formatDate value="${certificado.fechaRegistro}" pattern="dd-MM-yyyy HH:mm" /></td>
						<td>
								<a href="${certificado.ruta}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver certificado">
									<i class="fa-solid fa-eye"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-success table-icon validateLink" id="${certificado.idCertificado}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Validar certificado">
									<i class="fa-solid fa-check"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${certificado.idCertificado}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Anular certificado">
									<i class="fa-solid fa-xmark"></i>
								</a>
					
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			
			<div class="d-md-none d-block">
			<table border="1" class="table border table-hover text-center align-middle" >
				<tr>
					<th>Título</th>
					<th>Entidad emisora</th>
					<th>Año</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="certificado" items="${certificadosSinValidar}" varStatus="status">
					<tr>
						<td>${certificado.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
						<td>${certificado.anyo}</td>
						<td class="d-flex flex-row align-items-center justify-content-center">
								<a href="${certificado.ruta}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver certificado">
									<i class="fa-solid fa-eye"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-success table-icon validateLink" id="${certificado.idCertificado}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Validar certificado">
									<i class="fa-solid fa-check"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${certificado.idCertificado}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Anular certificado">
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
					id = $(this).attr("id");
					if(confirm("¿Desea anular este certificado?")) {
						window.location = "anular_certificado?id=" + id;
					}
				});
			});
			$(".validateLink").each(function() {
				$(this).on("click", function() {
					id = $(this).attr("id");
					if(confirm("¿Desea validar este certificado?")) {
						window.location = "validar_certificado?id=" + id;
					}
				});
			});
		});
</script>
</html>