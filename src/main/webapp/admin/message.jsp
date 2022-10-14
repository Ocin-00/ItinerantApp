<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Administración</title>
	<link rel="stylesheet" href="../css/layout.css">
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	
	<div id="main" align="center">
		<h3>${message}</h3>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
</html>