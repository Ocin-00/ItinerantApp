<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
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
<title>Itinerant - Profesional</title>
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">
		<jsp:directive.include file="side_menu.jsp"/>
		
		<c:set var="imagenRuta" value="${usuario.imagenRuta}"></c:set>
		<c:set var="nombre" value="${usuario.nombre}"></c:set>
		<div class="container-fluid d-lg-flex flex-row mt-md-5 justify-content-evenly pb-3"  style="min-height: 75vh">
			<div class="d-flex flex-column flex-wrap align-content-center">
				
				<table >
					<tr>
						<td rowspan="5">
							<img src="<%String imgPath = request.getContextPath();
								   Object imagenRuta = pageContext.getAttribute("imagenRuta");
								   if(imagenRuta != null) {
									   out.println(imgPath + imagenRuta.toString().substring(2)); 
								   }
								%>" id="thumbnail" alt="No hay imagen disponible" width="200" class="rounded-circle"/>
						</td>
					<tr>
					</table>
					<h2>${usuario.nombre} ${usuario.apellidos}</h2>
					<table>
					<tr>
						<td>Rol: ${usuario.rol}</td>
					</tr>
					<tr>
						<td>Email: ${usuario.email}</td>
					</tr>
					<tr>
						<td>Fecha de nacimiento: <fmt:formatDate value="${usuario.fechaNac}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<!--<td>${profesional.formacion}</td> -->
					</tr>	
				</table>
				
				<c:if test="${sessionScope.userLogin != usuario.login}">
					<div class="d-flex justify-content-center m-3">
						<button  onclick="location.href='contactar?id=${usuario.login}';">Contactar</button>
					</div>
				</c:if>
				
			</div>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
</script>
</html>