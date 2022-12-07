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
<title>Itinerant - Visitas</title>
</head>
<style>
	#side-menu a:nth-child(3){ background-color: #e0e0e0 }
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
					</div>
				</div>
			</div>
			<div class="main-content-split-items">
				<div>
					<c:if test="${cita.review == null}">
						<form id="reviewForm" action="nueva_review?id=${visita.idVisita}&login=${cita.ciudadano.login}" method="post">
							<div>
								<label>Deja tu reseña:</label>
							</div>
							<div class="stars">
								<input type="radio" id="uno" name="puntuacion" value="1">
								<label for="uno"></label>
								<input type="radio" id="dos" name="puntuacion" value="2">
								<label for="dos"></label>
								<input type="radio" id="tres" name="puntuacion" value="3">
								<label for="tres"></label>
								<input type="radio" id="cuatro" name="puntuacion" value="4">
								<label for="cuatro"></label>
								<input type="radio" id="cinco" name="puntuacion" value="5">
								<label for="cinco"></label>
							</div>
							<textarea rows="5" cols="50" name="review" id="review">${cita.review}</textarea>
							<div>				
								<button type="submit">Publicar</button>
							</div>
						</form>
					</c:if>	
				</div>
				<div>
					<c:if test="${cita.review != null}">
						<div>
							<label>Tu reseña:</label>
						</div>
						<div class="blocked-stars">
							<input type="radio" id="uno" name="puntuacion" value="1" disabled="disabled">
							<label for="uno"></label>
							<input type="radio" id="dos" name="puntuacion" value="2" disabled="disabled">
							<label for="dos"></label>
							<input type="radio" id="tres" name="puntuacion" value="3" disabled="disabled">
							<label for="tres"></label>
							<input type="radio" id="cuatro" name="puntuacion" value="4" disabled="disabled">
							<label for="cuatro"></label>
							<input type="radio" id="cinco" name="puntuacion" value="5" disabled="disabled">
							<label for="cinco"></label>
							<i></i>
						</div>
						<textarea rows="5" cols="50" name="review" id="review" readonly="readonly">${cita.review}</textarea>
					</c:if>	
				</div>
			</div>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#reviewForm").validate({
		ignore: [],
		rules: {
			puntuacion: "required",
			review: "required",
		},
		messages: {
			puntuacion: "Por favor, introduzca su puntuación.",
			review: "Por favor, describa su experiencia."
		}
	});

	var puntuacion = "${cita.puntuacion}";
	if(puntuacion == 5) {
		document.getElementById("cinco").checked = true;
	} else if(puntuacion == 4) {
		document.getElementById("cuatro").checked = true;
	} else if(puntuacion == 3) {
		document.getElementById("tres").checked = true;
	} else if(puntuacion == 2) {
		document.getElementById("dos").checked = true;
	} else if(puntuacion == 1) {
		document.getElementById("uno").checked = true;
	}
});		
</script>
</html>