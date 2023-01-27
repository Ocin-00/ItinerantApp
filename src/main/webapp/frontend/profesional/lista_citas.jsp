<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
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
</head>
<style>
	#side-menu a:nth-child(3){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content">
			<h4>Bienvenido, <c:out value="${sessionScope.userLogin}"></c:out></h4>
			<h3>Mis citas</h3>
		
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<div class="tab-parent">
				<ul class="tabs">
					<li id="citasPendientes" class="option option-active"><a href="#contenidoCitasPendientes">Citas Pendientes</a></li>
					<li id="historicoCitas" class="option"><a href="#contenidoHistorialCitas">Histórico de Citas</a></li>
				</ul>
				
				<div class="tab-container">
					<div id="contenidoCitasPendientes" class="tab_content">
						<table border="1">
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
									<td>${cita.visita.fecha}</td>
									<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
									<td align="center">
										<a href="ver_cita?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}">Detalles</a> |
										<a href="javascript:void(0);" class="deleteLink" id="${cita.id.idCiudadano}" visita="${cita.id.idVisita}">Anular</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div id="contenidoHistorialCitas"  class="tab_content">
						<table border="1">
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
									<td>${cita.visita.fecha}</td>
									<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
									<td align="center">
										<a href="ver_cita?id=${cita.visita.idVisita}&login=${cita.id.idCiudadano}">Detalles</a>
									</td>
								</tr>
							</c:forEach>
						</table>
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
		});
	</script>
</html>