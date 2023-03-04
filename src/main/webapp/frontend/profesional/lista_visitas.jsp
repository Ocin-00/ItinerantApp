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
	#side-menu ul li:nth-child(1){ background-color: #e0e0e0 }
</style>

<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container-fluid mt-3" style="min-height: 75vh">
				<h3>Visitas programadas: </h3>
			
				<c:if test="${message != null}">
					<div><h4>${message}</h4></div>
				</c:if>
				<div class="">
					<ul class="nav nav-tabs"  id="myTab" role="tablist">
						<li class="nav-item" role="presentation">
							<button  class="nav-link active"
								id="visitasPendientes" data-bs-toggle="tab" data-bs-target="#contenidoVisitasPendientes" type="button" role="tab" aria-controls="contenidoVisitasPendientes" aria-selected="true"
							>Visitas Pendientes</button>
						</li>
						<li id="historicoVisitas" class=" nav-item" role="presentation">
							<button  class="nav-link" id="historialVisitas" data-bs-toggle="tab" data-bs-target="#contenidoHistorialVisitas" type="button" role="tab" aria-controls="contenidoHistorialVisitas" aria-selected="false">
							Histórico de Visitas</button>
						</li>
					</ul>
					
					<div class="tab-content">
						<div id="contenidoVisitasPendientes" class="tab-pane fade show active" role="tabpanel" aria-labelledby="contenidoVisitasPendientes" tabindex="0">
							<c:if test="${empty visitasPendientes}">
								<h5 class="m-3">No tiene visitas pendientes.</h5>
							</c:if>								
							
							<table border="1" class="container-fluid table table-responsive border table-hover text-center align-middle">
								<tr>
									<th>Índice</th>
									<th>Localidad</th>
									<th>Fecha</th>
									<th>Hora inicio</th>
									<th>Hora fin</th>
									<th>Acciones</th>
								</tr>
								<c:forEach var="visita" items="${visitasPendientes}" varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td>${visita.localidad.nombre}</td>
										<td><fmt:formatDate value="${visita.fecha}" pattern="dd-MM-yyyy" /></td>
										<td><c:set var="hora" value="${visita.horaInicio}"></c:set>
											<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
											String hora = format.format(pageContext.getAttribute("hora"));
											out.println(hora);  
											%>
										</td>
										<td><c:set var="hora" value="${visita.horaFin}"></c:set>
											<%
											hora = format.format(pageContext.getAttribute("hora"));
											out.println(hora);  
											%>
										</td>
										<td align="center">
											<a href="ver_visita?id=${visita.idVisita}" class="btn btn-primary table-icon"
											data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
												<i class="fa-solid fa-eye"></i>
											</a>
											<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${visita.idVisita}"
											data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
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
										<td align="center">
											<a href="nueva_visita" class="btn btn-warning table-icon validateLink" 
											data-bs-toggle="tooltip" data-bs-placement="top" title="Crear visita">
												<i class="fa-solid fa-plus"></i>
											</a>
										</td>
									</tr>
							</table>
						</div>
						<div id="contenidoHistorialVisitas"  class="tab-pane fade" role="tabpanel" aria-labelledby="contenidoHistorialVisitas" tabindex="0">
							<c:if test="${empty historialVisitas}">
								<h5 class="m-3">Aún no hay ninguna visita en su historial.</h5>
							</c:if>
							<c:if test="${not empty historialVisitas}">
							<table border="1" class="container-fluid table table-responsive border table-hover text-center align-middle">
								<tr>
									<th>Índice</th>
									<th>Localidad</th>
									<th>Fecha</th>
									<th>Hora inicio</th>
									<th>Hora fin</th>
									<th>Acciones</th>
								</tr>
								<c:forEach var="visita" items="${historialVisitas}" varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td>${visita.localidad.nombre}</td>
										<td><fmt:formatDate value="${visita.fecha}" pattern="dd-MM-yyyy" /></td>
										<td><c:set var="hora" value="${visita.horaInicio}"></c:set>
											<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
											String hora = format.format(pageContext.getAttribute("hora"));
											out.println(hora);  
											%>
										</td>
										<td><c:set var="hora" value="${visita.horaFin}"></c:set>
											<%
											hora = format.format(pageContext.getAttribute("hora"));
											out.println(hora);  
											%>
										</td>
										<td align="center">
											<a href="ver_visita?id=${visita.idVisita}" class="btn btn-primary table-icon"
											data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
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
					id = $(this).attr("id");
					if(confirm("¿Desea eliminar la visita de id " + id + "?")) {
						window.location = "borrar_visita?id=" + id;
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
			});*/
		});
	</script>
</html>