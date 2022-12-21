<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Resultado de búsqueda</title>
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id = "main">
		<c:if test="${sessionScope.rol == 'ADMINISTRADOR'}">
			<jsp:directive.include file="/../admin/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'PROFESIONAL'}">
			<jsp:directive.include file="/../frontend/profesional/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'CIUDADANO'}">
			<jsp:directive.include file="../frontend/inicio/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'SUPERVISOR'}">
			<jsp:directive.include file="../supervisor/side_menu.jsp"/>
		</c:if>
		
		<jsp:directive.include file="/frontend/busqueda.jsp"/>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>