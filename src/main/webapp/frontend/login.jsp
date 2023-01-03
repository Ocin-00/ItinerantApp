<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="css/layout.css">
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/general.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<div id="main-centered" align="center">
		<h1>Bienvenido:</h1>
		<c:if test="${message != null}">
			<div><h4>${message}</h4></div>
		</c:if>
		<form id="loginForm" action="login" method="post">
			<table>
				<tr>
					<th align="right">Nombre de usuario:</th>
					<th><input type="text" name="login" id="login" size="10"><br/></th>
				</tr>
				<tr>
					<th align="right">Contraseña:</th>
					<th><input type="password" name="password" id="password" size="10"><br/></th>
				</tr>
			</table>
			<div class="main-button">
				<button type="submit">Iniciar sesión</button>	
			</div>
		</form>
		</div>
	<jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#loginForm").validate({
				rules: {
					login: "required",
					password: "required",
				},
				
				messages: {
					login: "Por favor introduzca un nombre de usuario válido.",
					password: "Por favor introduzca la contraseña correcta.",
				}
			});
	});
</script>
</html>