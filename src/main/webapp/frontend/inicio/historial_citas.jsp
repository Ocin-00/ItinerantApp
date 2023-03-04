<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<title>Itinerant - Historial de Citas</title>
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
		<jsp:directive.include file="../side_menu.jsp"/>
		<div class="container-fluid mt-3 mb-3" style="min-height: 75vh">
			<h3>Historial de citas</h3>
			<c:if test="${empty citas}">
				<h5>Todavía no hay citas en tu historial.</h5>
			</c:if>
			<c:if test="${not empty citas}">
			<div class="d-none d-md-block">
				<table border="1"  class="table border table-hover text-center align-middle">
					<tr>
						<th >Índice</th>
						<th >Profesional</th>
						<th >Visita</th>
						<th >Fecha</th>
						<th >Hora</th>
						<th >Teléfono</th>
						<th ></th>
					</tr>
					<c:forEach var="cita" items="${citas}" varStatus="status">
						<tr>
							<td >${status.index + 1}</td>
							<td >${cita.visita.profesional}</td>
							<td>${cita.visita.nombre}</td>
							<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
							<td>
								<c:set var="hora" value="${cita.horaInicio}"></c:set>
								<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
								String hora = format.format(pageContext.getAttribute("hora"));
								out.println(hora);  
								%></td>
							</td>
							<td >${cita.visita.profesional.telefono}</td>
							<td>
								<a href="detalles_cita_historial?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
									<i class="fa-solid fa-eye"></i>
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="d-md-none d-block">
				<table border="1"  class="table border table-hover text-center align-middle">
					<tr>
						<th >Índice</th>
						<th >Visita</th>
						<th >Fecha</th>
						<th >Hora</th>
						<th align="center" ></th>
					</tr>
					<c:forEach var="cita" items="${citas}" varStatus="status">
						<tr>
							<td >${status.index + 1}</td>
							<td>${cita.visita.nombre}</td>
							<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
							<td>
								<c:set var="hora" value="${cita.horaInicio}"></c:set>
								<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
								String hora = format.format(pageContext.getAttribute("hora"));
								out.println(hora);  
								%>
							</td>
							<td align="center">
								<a href="detalles_cita_historial?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
									<i class="fa-solid fa-eye"></i>
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
</html>