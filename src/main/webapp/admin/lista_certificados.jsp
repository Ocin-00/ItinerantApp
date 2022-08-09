<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Administraci�n</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<h4>Administraci�n</h4>
	<ul class="menu">
	  <li><a href="lista_profesionales">Profesionales</a>
	  <li><a href="lista_certificados" class="active">Certificados</a>
	  <li><a href="lista_supervisores">Supervisores</a>
	</ul>
	
	<h3>Certificados pendientes de verificaci�n</h3>
	<div>
		<table border="1">
			<tr>
				<th>�ndice</th>
				<th>Profesional</th>
				<th>T�tulo</th>
				<th>Entidad emisora</th>
				<th>A�o</th>
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
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					profesionalId = $(this).attr("pr");
					titulo = $(this).attr("tit");
					if(confirm("�Desea anular el certificado '" + titulo + "' perteneciente a '" + profesionalId + "'?")) {
						window.location = "anular_certificado?pr=" + profesionalId
						  				+ "&tit=" + titulo;
					}
				});
			});
			$(".validateLink").each(function() {
				$(this).on("click", function() {
					profesionalId = $(this).attr("pr");
					titulo = $(this).attr("tit");
					if(confirm("�Desea validar el certificado '" + titulo + "' perteneciente a '" + profesionalId + "'?")) {
						window.location = "validar_certificado?pr=" + profesionalId
						  				+ "&tit=" + titulo;
					}
				});
			});
		});
</script>
</html>