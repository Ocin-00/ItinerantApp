<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
	<c:if test="${sessionScope.userLogin != null}">
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
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<link rel="stylesheet" href="css/layout.css">
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	</c:if>
<title>Itinerant - Profesional</title>
</head>
<body class="d-flex flex-column min-vh-100">
	<c:if test="${sessionScope.userLogin != null}">
		<jsp:directive.include file="/frontend/header_user.jsp"/>
	</c:if>
	<c:if test="${sessionScope.userLogin == null}">
		<jsp:directive.include file="/frontend/header.jsp"/>
	</c:if>
	<div class="wrapper">
		<c:if test="${sessionScope.userLogin != null}">
			<jsp:directive.include file="../side_menu.jsp"/>
		</c:if>
		
		<c:set var="imagenRuta" value="${profesional.imagenRuta}"></c:set>
		<c:set var="nombre" value="${profesioanl.nombre}"></c:set>
		<div class="container-fluid d-lg-flex flex-row mt-md-5 justify-content-evenly pb-3"  style="min-height: 75vh">
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
				<div class="mt-3">
					<h3>Certificados</h3>
					<c:if test="${certificados == null}">
						<h5>Este usuario todavía no tiene certificados validados.</h5>
					</c:if>
					<c:if test="${certificados != null}">
						<table border="1" class="table border table-hover text-center align-middle">
							<tr>
								<th scope="col">Índice</th>
								<th scope="col">Título</th>
								<th scope="col">Entidad emisora</th>
								<th scope="col">Año</th>
								<th></th>
							</tr>
							<c:forEach var="certificado" items="${certificados}"
								varStatus="status">
								<tr>
									<td scope="row" >${status.index + 1}</td>
									<td>${certificado.titulo}</td>
									<td>${certificado.entidadEmisora}</td>
									<td>${certificado.anyo}</td>
									<c:set var="certificadoRuta" value="${certificado.ruta}"></c:set>
									<td align="center">
										<a href="${certificado.ruta}" class="btn btn-primary table-icon"
										data-bs-toggle="tooltip" data-bs-placement="top" title="Ver certificado">
											<i class="fa-solid fa-eye"></i>
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					
				</div>
				
				<c:if test="${sessionScope.rol != null}">
					<c:if test="${sessionScope.userLogin != profesional.login}">
						<div class="d-flex justify-content-center m-3">
							<button  onclick="location.href='contactar?id=${profesional.login}';">Contactar</button>
						</div>
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
					<h3>Puntuación: </h3>
					<i data-star="${averageReview}" style="font-size: 90px;"></i>
					<p>Media de <c:out value="${numReviews}"></c:out> reseñas</p>
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