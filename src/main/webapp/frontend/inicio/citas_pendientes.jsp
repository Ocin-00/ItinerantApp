<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Citas Pendientes</title>
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
		<jsp:directive.include file="../side_menu.jsp"/>
		<div class="container-fluid mt-3 mb-3" style="min-height: 75vh">
		<h3>Citas pendientes</h3>
		<c:if test="${empty citas}" >
			<h5>No tienes citas pendientes.</h5>
		</c:if>
		<c:if test="${not empty citas}" >
		<div class="d-none d-md-block">
			<table border="1" class="table border table-hover text-center align-middle">
				<tr>
					<th scope="col">Índice</th>
					<th scope="col">Profesional</th>
					<th scope="col">Visita</th>
					<th scope="col">Fecha</th>
					<th scope="col">Hora</th>
					<th scope="col">Teléfono</th>
					<th scope="col">Acciones</th>
				</tr>
				<c:forEach var="cita" items="${citas}" varStatus="status">
					<tr>
						<td scope="row">${status.index + 1}</td>
						<td>${cita.visita.profesional}</td>
						<td>${cita.visita.nombre}</td>
						<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
						<td>
							<c:set var="hora" value="${cita.horaInicio}"></c:set>
							<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
							String hora = format.format(pageContext.getAttribute("hora"));
							out.println(hora);  
							%></td>
						<td>${cita.visita.profesional.telefono}</td>
						<td>
							<a href="detalles_cita_pendiente?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
									<i class="fa-solid fa-eye"></i>
								</a>
							<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${profesional.login}"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
									<i class="fa-solid fa-xmark"></i>
								</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			
			<div class="d-md-none d-block">
			<table border="1" class="table border table-hover text-center align-middle">
				<tr>
					<th>Índice</th>
					<th>Visita</th>
					<th>Fecha</th>
					<th>Hora</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="cita" items="${citas}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${cita.visita.nombre}</td>
						<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
						<td>
							<c:set var="hora" value="${cita.horaInicio}"></c:set>
							<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
							String hora = format.format(pageContext.getAttribute("hora"));
							out.println(hora);  
							%>
						</td>
						<td>
							<a href="detalles_cita_pendiente?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
									<i class="fa-solid fa-eye"></i>
								</a>
							<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${profesional.login}"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
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
					idVisita = $(this).attr("id");
					login = "${sessionScope.userLogin}";
					if(confirm("¿Desea eliminar esta cita?")) {
						window.location = "anular_cita?id=" + idVisita + "&login=" + login;
					}
				});
			});
		});
	</script>
</html>