<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
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
<title>Itinerant - Visitas</title>
</head>
<style>
	#side-menu a:nth-child(3){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content-items">
			<div>
				<h2>Detalles de la Cita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="${visita.imagenRuta}" id="thumbnail" alt="No hay imagen disponible" width="200"/>
						</td>
						<td>Cliente: ${cita.ciudadano}</td>
					</tr>
					<tr>
						<td>Dirección: ${cita.direccion}</td>
					</tr>
					<tr>
						<td>Fecha: ${visita.fecha}</td>
					</tr>
					<tr>
						<td>Hora: <c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%></td>
					</tr>
				</table>
			</div>
			<div>
				<div>
					<label>Anotaciones:</label>
				</div>
				<textarea rows="5" cols="50" name="anotaciones" id="anotaciones" readonly="readonly">${cita.anotaciones}</textarea>
			</div>
			<div>
				<c:if test="${message != null}">
					<h3>${message}</h3>
				</c:if>
			</div>
			<c:if test="${pasadoTiempoLimite == true}">
				<form id="anularForm" action="anular_cita?id=${visita.idVisita}&login=${cita.ciudadano.login}" method="post">
					<div>
						<div>
							<label>Describa la urgencia:</label>
						</div>
						<textarea rows="5" cols="50" name="urgencia" id="urgencia"></textarea>
					</div>
					<div>
						<button type="submit">Anular Cita</button>
					</div>
				</form>
			</c:if>
			<div>
				<button  onclick="location.href='contactar?id=${cita.ciudadano.login}';">Contactar</button>
				<c:if test="${esFutura == false}">
					<button href="javascript:void(0);" class="ausenciaLink" id="${cita.id.idCiudadano}" visita="${visita.idVisita}">Informar de ausencia</button>
				</c:if>
				<c:if test="${esFutura == true}">
					<button href="javascript:void(0);" class="deleteLink" id="${cita.id.idCiudadano}" visita="${visita.idVisita}">Anular Cita</button>
				</c:if>
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
			$(".ausenciaLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					idVisita = $(this).attr("visita");
					if(confirm("¿Desea reportar la ausencia del usuario " + login + " en su cita?")) {
						window.location = "informar_ausencia?id=" + idVisita + "&login=" + login;
					}
				});
			});
			$("#anularForm").validate({
				rules: {
					urgencia: "required"
				},
				
				messages: {
					urgencia: "Por favor indique por qué tiene que cancelar la cita."
				}
			});
		});
	</script>
</html>