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
	#side-menu ul li:nth-child(2){ background-color: #e0e0e0 }
</style>

<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container-fluid mt-3" style="min-height: 75vh">
			<h3>Mis citas</h3>
		
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<div class="">
				<ul class="nav nav-tabs"  id="myTab" role="tablist">
						<li class="nav-item" role="presentation">
							<button  class="nav-link active"
								id="citasPendientes" data-bs-toggle="tab" data-bs-target="#contenidoCitasPendientes" type="button" role="tab" aria-controls="contenidoCitasPendientes" aria-selected="true"
							>Visitas Pendientes</button>
						</li>
						<li id="historicoCitas" class=" nav-item" role="presentation">
							<button  class="nav-link" id="historialVisitas" data-bs-toggle="tab" data-bs-target="#contenidoHistorialCitas" type="button" role="tab" aria-controls="contenidoHistorialCitas" aria-selected="false">
							Histórico de Visitas</button>
						</li>
					</ul>
				
				<div class="tab-content">
					<div id="contenidoCitasPendientes" class="tab-pane fade show active" role="tabpanel" aria-labelledby="contenidoCitasPendientes" tabindex="0">
						<c:if test="${empty citasPendientes}">
								<h5 class="m-3">No tiene citas pendientes.</h5>
							</c:if>								
							<c:if test="${not empty citasPendientes}">
								
							<table border="1" class="container-fluid table table-responsive border table-hover text-center align-middle">
							<tr>
								<th>Índice</th>
								<th>Cliente</th>
								<th>Visita</th>
								<th>Fecha</th>
								<th>Hora</th>
								<th>Acciones</th>
							</tr>
							<c:forEach var="cita" items="${citasPendientes}" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${cita.ciudadano}</td>
									<td>${cita.visita.nombre}</td>
									<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
									<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
									<td align="center">
										<a href="ver_cita?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
											data-bs-toggle="tooltip" data-bs-placement="top" title="Ver cita">
												<i class="fa-solid fa-eye"></i>
											</a>
											<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${cita.id.idCiudadano}" visita="${cita.id.idVisita}"
											data-bs-toggle="tooltip" data-bs-placement="top" title="Anular cita">
												<i class="fa-solid fa-xmark"></i>
											</a>
									</td>
								</tr>
							</c:forEach>
						</table>
						</c:if>
					</div>
					<div id="contenidoHistorialCitas" class="tab-pane fade" role="tabpanel" aria-labelledby="contenidoHistorialCitas" tabindex="0">
						<c:if test="${empty historialCitas}">
								<h5 class="m-3">Aún no hay ninguna cita en su historial.</h5>
							</c:if>
							<c:if test="${not empty historialCitas}">
							<table border="1" class="container-fluid table table-responsive border table-hover text-center align-middle">
							<tr>
								<th>Índice</th>
								<th>Cliente</th>
								<th>Visita</th>
								<th>Fecha</th>
								<th>Hora</th>
								<th>Acciones</th>
							</tr>
							<c:forEach var="cita" items="${historialCitas}" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${cita.ciudadano}</td>
									<td>${cita.visita.nombre}</td>
									<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
									<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
									<td align="center">
										<a href="ver_cita?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
											data-bs-toggle="tooltip" data-bs-placement="top" title="Ver cita">
												<i class="fa-solid fa-eye"></i>
											</a>
									</td>
								</tr>
							</c:forEach>
						</table>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					idVisita = $(this).attr("visita");
					if(confirm("¿Desea eliminar la cita del usuario " + login + "?")) {
						window.location = "anular_cita?id=" + idVisita + "&login=" + login;
					}
				});
			});
/*
			$(".tab_content").hide(); //Hide all content
			$("ul.tabs li:first").addClass("active").show(); //Activate first tab
			$(".tab_content:first").show(); //Show first tab content

			//On Click Event
			$("ul.tabs li").click(function() {

				$("ul.tabs li").removeClass("active"); //Remove any "active" class
				$(this).addClass("active"); //Add "active" class to selected tab
				$(".tab_content").hide(); //Hide all tab content

				var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
				$(activeTab).fadeIn(); //Fade in the active ID content
				return false;
			});
			*/
		});
	</script>
</html>