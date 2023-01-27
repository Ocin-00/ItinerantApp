<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
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
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
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
						<td>${certificado.titulo}</td>
						<td>${certificado.entidadEmisora}</td>
						<td>${certificado.anyo}</td>
						<td>${certificado.fechaRegistro}</td>
						<td align="center">
							<a href="${certificado.ruta}">Ver</a> |
							<a href="javascript:void(0);" class="validateLink" id="${certificado.idCertificado}">Validar</a> |
							<a href="javascript:void(0);" class="deleteLink" id="${certificado.idCertificado}">Anular</a>
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
					id = $(this).attr("id");
					if(confirm("¿Desea anular este certificado?")) {
						window.location = "anular_certificado?id=" + id;
					}
				});
			});
			$(".validateLink").each(function() {
				$(this).on("click", function() {
					id = $(this).attr("id");
					if(confirm("¿Desea validar este certificado?")) {
						window.location = "validar_certificado?id=" + id;
					}
				});
			});
		});
</script>
</html>