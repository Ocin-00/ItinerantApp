<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Itinerant - Generalitat Valenciana</title>
	<c:if test="${hayRol == false }">
		<link href="/ItinerantApp/css/layout.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="js/general.js"></script>
		<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
		<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	</c:if>
	<c:if test="${hayRol == true }">
		<link rel="stylesheet" href="../css/layout.css">
		<link rel="stylesheet" href="../css/side-bar-style.css">
		<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/notify.js"></script>
	    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="../js/my-notifications.js"></script>
	    <script type="text/javascript" src="../js/general.js"></script>
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
		<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
		<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
		<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
		<link rel="stylesheet" href="../css/bootstrap.min.css">
		<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
	</c:if>
</head>
<body>
	<c:if test="${hayRol == false }">
		<jsp:directive.include file="header.jsp"/>
	</c:if>
	<c:if test="${hayRol == true }">
		<jsp:directive.include file="header_user.jsp"/>
	</c:if>
	
	<div id = "main">
		<c:if test="${sessionScope.rol == 'ADMINISTRADOR'}">
			<jsp:directive.include file="/../admin/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'PROFESIONAL'}">
			<jsp:directive.include file="../frontend/profesional/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'CIUDADANO'}">
			<jsp:directive.include file="../frontend/inicio/side_menu.jsp"/>
		</c:if>
		<c:if test="${sessionScope.rol == 'SUPERVISOR'}">
			<jsp:directive.include file="../supervisor/side_menu.jsp"/>
		</c:if>
		
		<c:if test="${hayRol == false }">
			<div id="main-plain">
		</c:if>
		<c:if test="${hayRol == true }">
			<div id="main-content">
		</c:if>
		<div id = "main-home">
			<h1 style="margin-bottom: 20px;">
				Servicios más ofertados: 
			</h1>
			<div class="main-scroll-div">
				<div>
					<button id="left-button" class="scroll-icon" type="button"><i class="fa-solid fa-angles-left"></i></i></button>
				</div>
				<div class="cover">
					<div class="scroll-images">
						<c:forEach var="categoria" items="${categorias}" varStatus="status">
							<a href="buscar?keyword=${categoria.nombre}">
								
								<div class="child">
								<c:set var="imagenRuta" value="${categoria.imagenRuta}"></c:set>
									<img class="child-img" alt="" src="<%String imgpath = request.getContextPath();
												   Object imagenRuta = pageContext.getAttribute("imagenRuta");
												   if(imagenRuta != null) {
													   out.println(imgpath + imagenRuta.toString().substring(2)); 
												   }
												%>" height="450" width="450">
									
								</div>
								<h1>${categoria.nombre}</h1>
							</a>
						</c:forEach>
					</div>
				</div>
				<div>
					<button id="right-button" class="scroll-icon" type="button"><i class="fa-solid fa-angles-right"></i></i></button>
				</div>
			</div>
		</div>
		</div>
	</div>
	<jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
$(document).ready(function() {

	$('#right-button').click(function() {
		event.preventDefault();
		$('.scroll-images').animate({
		    scrollLeft: "+=470px"
		}, "fast");
	});

	$('#left-button').click(function() {
		event.preventDefault();
		$('.scroll-images').animate({
			scrollLeft: "-=470px"
		}, "fast");
	});
});
</script>
</html>