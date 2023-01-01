<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
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
	#side-menu a:nth-child(4){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content">
			<h4>Bienvenido, <c:out value="${sessionScope.userLogin}"></c:out></h4>
			<h2>Mis certificados</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<table border="1">
				<tr>
					<th>Índice</th>
					<th>Título</th>
					<th>Entidad emisora</th>
					<th>Año</th>
					<th>Validez</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="certificado" items="${certificados}"
					varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${certificado.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
						<td>${certificado.anyo}</td>
						<td>${certificado.validez}</td>
						<td align="center">
							<a href="${certificado.ruta}">Ver</a> | 
							<a href="editar_certificado?id=${certificado.idCertificado}">Editar</a> |
							<a href="javascript:void(0);" class="deleteLink" id="${certificado.idCertificado}">Borrar</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="center">
						<a href="nuevo_certificado">Añadir</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					id = $(this).attr("id");
					if(confirm("¿Desea eliminar este certificado?")) {
						window.location = "borrar_certificado?id=" + id;
					}
				});
			});
		});
	</script>
</html>