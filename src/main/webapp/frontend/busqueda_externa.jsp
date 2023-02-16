<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Itinerant - Resultado de búsqueda</title>
	<link rel="stylesheet" href="css/layout.css">
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
</head>
<body>
	<jsp:directive.include file="/frontend/header.jsp"/>
	<div id="main">
		<jsp:directive.include file="/frontend/busqueda.jsp"/>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>