<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<c:if test="${sessionScope.userLogin != null}">
		<link rel="stylesheet" href="../css/layout.css">
		<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/notify.js"></script>
	    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="../js/my-notifications.js"></script>
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
		<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<link rel="stylesheet" href="css/layout.css">
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	</c:if>
<title>Itinerant - Profesional</title>
</head>
<body>
	<c:if test="${sessionScope.userLogin != null}">
		<jsp:directive.include file="/frontend/header_user.jsp"/>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<jsp:directive.include file="/frontend/header.jsp"/>
	</c:if>
	<div id="main">
		<c:if test="${sessionScope.rol == 'ADMINISTRADOR'}">
			<jsp:directive.include file="/../admin/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'PROFESIONAL'}">
			<jsp:directive.include file="/../frontend/profesional/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'CIUDADANO'}">
			<jsp:directive.include file="/../frontend/inicio/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'SUPERVISOR'}">
			<jsp:directive.include file="/../supervisor/side_menu.jsp"/>
		</c:if>
		
		<c:set var="imagenRuta" value="${profesional.imagenRuta}"></c:set>
		<c:set var="nombre" value="${profesioanl.nombre}"></c:set>
		<div id="main-content-split">
			<div>
				<h2>${profesional.nombre} ${profesional.apellidos}</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="<%String imgPath = request.getContextPath();
								   Object imagenRuta = pageContext.getAttribute("imagenRuta");
								   if(imagenRuta != null) {
									   out.println(imgPath + imagenRuta.toString().substring(2)); 
								   }
								%>" id="thumbnail" alt="No hay imagen disponible" width="200"/>
						</td>
					<tr>
					</table>
					<table>
					<tr>
						<td>${profesional.descripcion}</td>
					</tr>
					<tr>
						<td>${profesional.formacion}</td>
					</tr>	
				</table>
				<div>
					<h3>Certificados</h3>
					<table border="1">
						<tr>
							<th>Índice</th>
							<th>Título</th>
							<th>Entidad emisora</th>
							<th>Año</th>
							<th></th>
						</tr>
						<c:forEach var="certificado" items="${certificados}"
							varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${certificado.titulo}</td>
								<td>${certificado.entidadEmisora}</td>
								<td>${certificado.anyo}</td>
								<c:set var="certificadoRuta" value="${certificado.ruta}"></c:set>
								<td align="center">
									<a href="<%String certPath = request.getContextPath();
									   Object certificadoRuta = pageContext.getAttribute("certificadoRuta");
										 out.println(certPath + certificadoRuta.toString().substring(2)); 
									%>">Ver</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<c:if test="${sessionScope.rol != null}">
					<c:if test="${sessionScope.userLogin != profesional.login}">
						<button  onclick="location.href='contactar?id=${profesional.login}';">Contactar</button>
					</c:if>
				</c:if>
				
			</div>
			
			<c:if test="${noReviews == true}">
				<div class="main-content-split-items">
					<h4>Reseñas:</h4>
						<div><h4>Todavía no existen reseñas.</h4></div>
				</div>
			</c:if>
			<c:if test="${noReviews == false}">
				<div class="main-content-split-items">
					<h3>Puntuación media:</h3>
					<i data-star="${averageReview}" style="font-size: 90px;"></i>
					<div>
						<h3>Reseñas:</h3>
						<c:forEach var="review" items="${reviews}" varStatus="status">
							<p>${review}</p>
						</c:forEach>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
</script>
</html>