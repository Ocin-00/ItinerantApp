<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<h3>Administración</h3>
	<ul class="menu">
	  <li><a href="../admin/lista_profesionales">Profesionales</a>
	  <li><a href="../admin/lista_certificados">Certificados</a>
	  <li><a href="../admin/lista_supervisores">Supervisores</a>
	</ul>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>