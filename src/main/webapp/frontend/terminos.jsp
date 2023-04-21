<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Términos y condiciones de uso</title>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
	<link href="/ItinerantApp/css/layout.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/general.js"></script>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="header.jsp"/>
	
	<div class = "container-fluid" style="min-height: 75vh;">
		<div>
			<h1 align="center">Programa Itinerant</h1>
		</div>
		<div class="container-fluid d-lg-flex d-m-inline-block align-items-center">
			<div class="container main-centered-text">
				<div>
					<h4>¿Qué información recogemos?</h4>
					<label>La aplicación recogerá datos de los usuarios de forma anónima, tales como: sexo, edad, localización, estado civil, el número de usuarios ciudadanos y usuarios profesionales, servicios ofertados, zonas con más profesionales activos, zonas con más ciudadanos activos, citas, perfil y servicios más demandados.</label>
				</div>	
				<div>
					<h4>¿Para qué se usa?</h4>
					<label>La información se usa para monitorizar la aplicación y ver las necesidades específicas de cada zona con el fin de mejorar servicios específicos y usar más eficientemente los recursos disponibles.</label>
				</div>	
			</div>	
		</div>
	</div>
	<jsp:directive.include file="footer.jsp"/>
</body>
</html>