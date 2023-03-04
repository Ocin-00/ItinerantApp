<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
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
<title>Itinerant - Informe</title>
</head>
<style>
	#side-menu ul li:nth-child(4){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">
		<jsp:directive.include file="../side_menu.jsp"/>
		<div class="container-fluid d-lg-flex flex-row mt-3 justify-content-evenly pb-3" style="min-height: 75vh">
			<div>
				<h2 class="mt-3 mb-3">Detalles de la Jornada del día <c:out value="${fecha}"></c:out></h2>
				
				
				
				<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
				</c:if>
				<c:if test="${numVisitas == 0}" >
					<h5>No hay visitas planeadas para este día.</h5>
				</c:if>
				<c:if test="${numVisitas > 0}" >
				<label class="mb-3">Número de visitas: <c:out value="${numVisitas}"></c:out></label>
				<div class="d-flex flex-column justify-content-center">
				<div class="d-none d-md-block">
				<table border="1" class="table border table-hover text-center">
					<tr>
						<th>Índice</th>
						<th>Localidad</th>
						<th>Hora inicio</th>
						<th>Hora fin</th>
						<th>Tiempo de cita</th>
						<th>Tiempo de desplazamiento</th>
					</tr>
					<c:forEach var="visita" items="${visitas}" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${visita.localidad.nombre}</td>
							<td><c:set var="hora" value="${visita.horaInicio}"></c:set> <%
								 SimpleDateFormat format = new SimpleDateFormat("HH:mm");
								 String hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
							<td><c:set var="hora" value="${visita.horaFin}"></c:set> <%
								 hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
							<td>${visita.duracionCitas} mins</td>
							<td>${visita.duracionDesplazamiento} mins</td>
						</tr>
					</c:forEach>
				</table>
				</div>
				<div class="d-md-none d-flex justify-content-center mb-3">
				<table border="1" class="border border-dark">
					<c:forEach var="visita" items="${visitas}" varStatus="status">
						<tr>
							<th>${visita.localidad.nombre}</th>
						</tr>
						<tr>
							<td><label class="fw-bold">Hora inicio:</label><c:set var="hora" value="${visita.horaInicio}"></c:set> <%
								 SimpleDateFormat format = new SimpleDateFormat("HH:mm");
								 String hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
						</tr>
						<tr>
							<td><label class="fw-bold">Hora fin:</label><c:set var="hora" value="${visita.horaFin}"></c:set> <%
								 hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
						</tr>
						<tr>
							<td><label class="fw-bold">Duración:</label>${visita.duracionCitas} mins</td>
						</tr>
						<tr class="border-bottom border-dark">
							<td><label class="fw-bold">Desplazamiento:</label>${visita.duracionDesplazamiento} mins</td>
						</tr>
						
					</c:forEach>
				</table>
				</div>
				<c:if test="${numVisitas > 1}" >
				<div class="d-flex justify-content-center mt-4">
					<table border="1" class="table border table-hover text-center" style="max-width: 500px;">
						<tr>
							<th>Índice</th>
							<th>Trayecto</th>
							<th>Duración</th>
						</tr>
						<c:forEach var="viaje" items="${viajes}" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${viaje.trayecto}</td>
								<td><c:set var="tiempo" value="${viaje.tiempo}"></c:set><%
									 DecimalFormat format = new DecimalFormat("#.##");
									 String tiempo = format.format(pageContext.getAttribute("tiempo"));
									 out.println(tiempo);
									 %>
									mins</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				</c:if>
				</div>
				</c:if>
				<div class="d-flex justify-content-center m-5">
					<button id="buttonCancel">Volver</button>	
				</div>
			</div>
			
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$("#buttonCancel").click(function() {
				history.go(-1);
			});
		});
	</script>
</html>