<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
</head>
<style>
	#side-menu a:nth-child(3){ background-color: #e0e0e0 }
</style>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content">
			<h2>Certificados pendientes de verificación</h2>
			<table border="1">
				<tr>
					<th>Índice</th>
					<th>Profesional</th>
					<th>Título</th>
					<th>Entidad emisora</th>
					<th>Año</th>
					<th>Fecha de Registro</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="certificado" items="${certificadosSinValidar}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${certificado.profesional}</td>
						<td>${certificado.id.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
						<td>${certificado.anyo}</td>
						<td>${certificado.fechaRegistro}</td>
						<td align="center">
							<a href="javascript:void(0);" class="validateLink" tit="${certificado.id.titulo}" pr="${certificado.id.idProfesional}">Validar</a> |
							<a href="javascript:void(0);" class="deleteLink" tit="${certificado.id.titulo}" pr="${certificado.id.idProfesional}">Anular</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					profesionalId = $(this).attr("pr");
					titulo = $(this).attr("tit");
					if(confirm("¿Desea anular el certificado '" + titulo + "' perteneciente a '" + profesionalId + "'?")) {
						window.location = "anular_certificado?pr=" + profesionalId
						  				+ "&tit=" + titulo;
					}
				});
			});
			$(".validateLink").each(function() {
				$(this).on("click", function() {
					profesionalId = $(this).attr("pr");
					titulo = $(this).attr("tit");
					if(confirm("¿Desea validar el certificado '" + titulo + "' perteneciente a '" + profesionalId + "'?")) {
						window.location = "validar_certificado?pr=" + profesionalId
						  				+ "&tit=" + titulo;
					}
				});
			});
		});
</script>
</html>