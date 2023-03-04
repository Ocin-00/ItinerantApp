<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Login</title>
	<link rel="stylesheet" href="css/layout.css">
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/general.js"></script>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="header.jsp"/>
	<div class="container d-flex flex-column align-items-center justify-content-center">
	  <h1 class="mb-4 mt-4">Bienvenido:</h1>
	  <c:if test="${message != null}">
	    <div><h4>${message}</h4></div>
	  </c:if>
		<form id="loginForm" action="login" method="post">
			<table>
				<tr>
					<td align="right"><label for="login" class="form-label"><h5> Nombre de usuario:</h5></label></td>
					<td><input type="text" name="login" id="login"  class="form-control border-dark-subtle" maxlength="20"><br/></td>
				</tr>
				<tr>
					<td align="right"><label for="password" class="form-label"><h5>Contraseña:</h5></label></td>
					<td><input type="password" name="password" id="password"  class="form-control border-dark-subtle" maxlength="30"><br/></td>
				</tr>
			</table>
			<div class=" d-flex justify-content-center align-items-center">
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