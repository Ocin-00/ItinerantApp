<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Resultado de búsqueda</title>
	<link rel="stylesheet" href="css/layout.css">
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
	
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	
</head>
<body>
	<jsp:directive.include file="/frontend/header.jsp"/>
	<div class="wrapper" >
		<jsp:directive.include file="/frontend/busqueda.jsp"/>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>