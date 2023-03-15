<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Portal de supervisión</title>
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js" charset="UTF-8"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
	
	<script type="text/javascript" src="../js/jquery.canvasjs.min.js"></script>
</head>
<style>
	#side-menu ul li:nth-child(1){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		<div class="container-fluid m-3" style="min-height: 75vh">
			<h2 class="mb-4">Estadísticas generales: </h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
				<div class="d-lg-flex justify-content-between mb-5">
					<h3 class="mb-2">Perfiles de usuarios: </h3>
					<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
						<input type="radio" class="btn-check tipo" name="tipo" id="btnradio1" value="0" >
					  	<label class="btn btn-outline-success" for="btnradio1">Ciudadanos</label>
					
					  	<input type="radio" class="btn-check tipo" name="tipo" id="btnradio2"  value="1">
					  	<label class="btn btn-outline-success" for="btnradio2">Profesionales</label>
					
					  	<input type="radio" class="btn-check tipo" name="tipo" id="btnradio3"  value="2" checked>
					  	<label class="btn btn-outline-success" for="btnradio3">Todos</label>
					</div>
				</div>
				<div class="d-lg-flex">
					<div id="chartGender" class="mb-5" style="min-height: 370px; width: 100%;"></div>
					<div id="chartEstadoCivil" class="mb-5" style="min-height: 370px; width: 100%;"></div>
					<div id="chartRoles" class="mb-5" style="min-height: 370px; width: 100%;"></div>
				</div>
				<div class="d-lg-flex">
					<div id="chartEdad" class="mb-5" style="min-height: 370px; width: 100%;"></div>
					<div id="chartLocalidad" class="mb-5" style="min-height: 370px; width: 100%;"></div>
				</div>
				<div class="d-flex justify-content-end mb-5">
					<button id="exportarPerfil" class="btn btn-secondary">Exportar</button>
				</div>
				<div class="mb-5">
					<h3 class="mb-2">Tráfico de información: </h3>
				</div>
				<div class="d-lg-flex">
					<div id="chartOferta" class="mb-5" style="min-height: 370px; width: 100%;"></div>
					<div id="chartDemanda" class="mb-5" style="min-height: 370px; width: 100%;"></div>
					<div id="chartCitas" class="mb-5" style="min-height: 370px; width: 100%;"></div>
				</div>
				<div class="d-flex justify-content-end">
					<button  id="exportarTrafico" class="btn btn-secondary">Exportar</button>
				</div>
			<input type="hidden" id="ambito" value="GENERAL">
			<input type="hidden" id="lugar" value="">
			<input type="hidden" id="tieneAcceso" value="true">
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript" src="../js/charts.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('.permitBtn').on('click', function() {
			if(confirm("¿Estás seguro de que quiere pedir permisos generales?")) {
				window.location = "pedir_permisos?id=GENERAL";
			}
		});
	});
</script>
</html>