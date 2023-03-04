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
<title>Itinerant - Visitas</title>
</head>
<style>
	#side-menu a:nth-child(3){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">
		<jsp:directive.include file="../side_menu.jsp"/>
		<div class="container-fluid d-lg-flex flex-row mt-md-5 justify-content-evenly pb-3" style="min-height: 75vh">
			<div>
				<h2>Detalles de la cita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="${visita.imagenRuta}" id="thumbnail" alt="No hay imagen disponible" class="img-fluid" style="max-height: 250px;max-width: 250px"/>
						</td>
					</tr>
					<tr>
						<td>${visita.nombre}</td>
					</tr>
					<tr>
						<td>Profesional: ${visita.profesional}</td>
					</tr>
					<tr>
						<td>Fecha: <fmt:formatDate value="${visita.fecha}" pattern="dd-MM-yyyy" />
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
					</table>
					<table class="visita-descripcion">
					<tr>
						<td>Dirección: ${cita.direccion}</td>
					</tr>
					<tr>
						<td>Precio: ${visita.precio}</td>
					</tr>
					<tr>
						<td>Categorias: 
						<c:forEach var="categoria" items="${visita.categorias}" varStatus="status">
							<c:if test="${status.index + 1 == fn:length(visita.categorias)}">
								${categoria.nombre}.
							</c:if>
							<c:if test="${status.index + 1 != fn:length(visita.categorias)}">
								${categoria.nombre},
							</c:if>  
						</c:forEach>
						</td>
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
					<div  class="d-flex justify-content-center m-3">
						<button  onclick="location.href='contactar?id=${visita.profesional.login}';">Contactar</button>
					</div>
				</div>
			</div>
			<div class="main-content-split-items">
				
					<c:if test="${cita.review == null}">
					<div class="container justify-content-start">
						<form id="reviewForm" action="nueva_review?id=${visita.idVisita}&login=${cita.ciudadano.login}" method="post">
							<div>
								<h3>Deja tu reseña:</h3>
							</div>
							<div class="stars justify-content-center">
								<input type="radio" id="cinco" name="puntuacion" value="5">
								<label for="cinco"></label>
								<input type="radio" id="cuatro" name="puntuacion" value="4">
								<label for="cuatro"></label>
								<input type="radio" id="tres" name="puntuacion" value="3">
								<label for="tres"></label>
								<input type="radio" id="dos" name="puntuacion" value="2">
								<label for="dos"></label>
								<input type="radio" id="uno" name="puntuacion" value="1">
								<label for="uno"></label>
							</div>
							<textarea rows="5" cols="50" name="review" id="review" maxlength="150" class="form-control border-dark-subtle w-100" maxlength="150">${cita.review}</textarea>
							<div  class="d-flex justify-content-center m-3">				
								<button type="submit">Publicar</button>
							</div>
						</form>
					</div>
					</c:if>	
						
					<c:if test="${cita.review != null}">
					<div class="container justify-content-start">
						<div>
							<h3>Tu reseña:</h3>
						</div>
						<div class="blocked-stars justify-content-center">
							<input type="radio" id="cinco" name="puntuacion" value="5" disabled="disabled">
							<label for="cinco"></label>
							<input type="radio" id="cuatro" name="puntuacion" value="4" disabled="disabled">
							<label for="cuatro"></label>
							<input type="radio" id="tres" name="puntuacion" value="3" disabled="disabled">
							<label for="tres"></label>
							<input type="radio" id="dos" name="puntuacion" value="2" disabled="disabled">
							<label for="dos"></label>
							<input type="radio" id="uno" name="puntuacion" value="1" disabled="disabled">
							<label for="uno"></label>
							<i></i>
						</div>
						<textarea rows="5" cols="50" name="review" id="review" readonly="readonly" class="form-control border-dark-subtle w-100" maxlength="150">${cita.review}</textarea>
					</div>
					</c:if>	
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
			//review: "required",
		},
		messages: {
			puntuacion: "Por favor, introduzca su puntuación.",
			//review: "Por favor, describa su experiencia."
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