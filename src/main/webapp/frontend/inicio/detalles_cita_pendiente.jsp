<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<title>Itinerant - Visitas</title>
</head>
<style>
	#side-menu a:nth-child(2){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content-split">
			<div>
				<h2>Detalles de la cita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="${visita.imagenRuta}" id="thumbnail" alt="No hay imagen disponible" width="200"/>
						</td>
					</tr>
					<tr>
						<td>${visita.nombre}</td>
					</tr>
					<tr>
						<td>Profesional: ${visita.profesional}</td>
					</tr>
					<tr>
						<td>Fecha: ${visita.fecha}
						</td>
					</tr>
					<tr>
						<td>Hora: 
							<c:set var="hora" value="${cita.horaInicio}"></c:set>
							<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
							String hora = format.format(pageContext.getAttribute("hora"));
							out.println(hora);  
							%>
						</td>
					</tr>
					<tr>
						<td>Dirección: ${cita.direccion}</td>
					</tr>
					<tr>
						<td>Precio: ${visita.precio}</td>
					</tr>
					<tr>
						<td>Categorías: </td>
					</tr>
					<tr>
						<c:forEach  var="categoria" items="${visita.categorias}" varStatus="status">
							<td>${categoria.nombre}</td>
						</c:forEach>
					</tr>
					<tr>
						<td>Descrpición: </td>
					</tr>
					<tr>
						<td>${visita.descripcion}</td>
					</tr>
				</table>
				<div>
					<div>
						<c:if test="${message != null}">
							<h3>${message}</h3>
						</c:if>
					</div>
					<div>
						<button  onclick="location.href='editar_visita?id=${visita.idVisita}';">Contactar</button>
						<button href="javascript:void(0);" class="deleteLink" id="${cita.id.idCiudadano}" visita="${visita.idVisita}">Anular Cita</button>
					</div>
				</div>
			</div>
			<div class="main-content-split-items">
				<form id="modificarCitaForm" action="modificar_cita?id=${visita.idVisita}&login=${cita.ciudadano.login}" method="post">
					<div>
						<label>Anotaciones:</label>
					</div>
					<textarea rows="5" cols="50" name="anotaciones" id="anotaciones">${cita.anotaciones}</textarea>
					<div>
						<button type="submit">Modificar anotaciones</button>
					</div>
				</form>
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
					if(confirm("¿Desea eliminar esta cita?")) {
						window.location = "anular_cita?id=" + idVisita + "&login=" + login;
					}
				});
			});
		});
	</script>
</html>