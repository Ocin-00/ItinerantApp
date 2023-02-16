<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<title>Itinerant - Informe</title>
</head>
<style>
	#side-menu a:nth-child(5){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content-items">
			<div>
				<h2>Detalles de la Jornada del día <c:out value="${fecha}"></c:out></h2>
				
				Número de visitas: <c:out value="${numVisitas}"></c:out>
				
				<table border="1">
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
				
				<table border="1">
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
			<button id="buttonCancel">Volver</button>	
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