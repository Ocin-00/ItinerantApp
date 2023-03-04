<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<link rel="stylesheet" href="../css/layout.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
<title>Itinerant - Visitas</title>
</head>
<style>
	#side-menu ul li:nth-child(1){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">
		<jsp:directive.include file="../side_menu.jsp"/>
		<div class="container-fluid d-lg-flex flex-row mt-3 justify-content-evenly pb-3" style="min-height: 75vh">
			<div>
				<h2>Detalles de la visita</h2>
				<table>
					<tr>
						<td rowspan="5">
							<img src="${visita.imagenRuta}" id="thumbnail" alt="No hay imagen disponible" class="img-fluid" style="max-height: 250px;max-width: 250px"/>
						</td>
						<td>Nombre: ${visita.nombre}</td>
					</tr>
					<tr>
						<td>Localidad: ${visita.localidad.nombre}</td>
					</tr>
					<tr>
						<td>Fecha: <fmt:formatDate value="${visita.fecha}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<td>Hora: <c:set var="hora" value="${visita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%> - 
										<c:set var="hora" value="${visita.horaFin}"></c:set>
										<%hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
						</td>
					</tr>
					<tr>
						<td>Tiempo de cita: ${visita.duracionCitas} min</td>
					</tr>
					</table>
					<table class="visita-descripcion">
					<tr>
						<td>Precio: ${visita.precio} &euro;</td>
					</tr>
					<tr>
						<td>Categorias: 
						<c:forEach var="categoria" items="${visita.categorias}" varStatus="status">
							<c:if test="${status.index + 1 == fn:length(visita.categorias)}">
								${categoria.nombre}.
							</c:if>
							<c:if test="${status.index + 1 != fn:length(visita.categorias)}">
								${categoria.nombre},
							</c:if> 
						</c:forEach>
						</td>
					</tr>
					<tr>
						<td>Descripcion:</td>
					</tr>
					<tr>
						<td>${visita.descripcion}</td>
					</tr>
				</table>
				<c:if test="${esFutura == true}">
				<div class="d-flex justify-content-around m-3">
					<button  onclick="location.href='editar_visita?id=${visita.idVisita}';">Editar</button>
				</div>
			</c:if>
			</div>
			<c:if test="${numeroCitas > 0}">
				<div class="main-content-split-items">
				<h4>Citas de la visita:</h4>
				<div class="d-none d-md-block mt-4">
					
					<c:if test="${message != null}">
						<h3>${message}</h3>
					</c:if>
					<table border="1" class="table border table-hover text-center">
						<tr>
							<th>Índice</th>
							<th>Cliente</th>
							<th>Visita</th>
							<th>Fecha</th>
							<th>Hora</th>
							<th>Acciones</th>
						</tr>
						<c:forEach var="cita" items="${citas}" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${cita.ciudadano}</td>
								<td>${cita.visita.nombre}</td>
								<td><fmt:formatDate value="${cita.visita.fecha}" pattern="dd-MM-yyyy" /></td>
								<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
								<td align="center">
									<a href="ver_cita?id=${visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
									data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
										<i class="fa-solid fa-eye"></i>
									</a>
									<c:if test="${esFutura == true}">
										<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${cita.id.idCiudadano}" visita="${visita.idVisita}
										data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
											<i class="fa-solid fa-xmark"></i>
										</a>
									</c:if> </td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<div class="d-md-none d--block mt-2">
					<c:if test="${message != null}">
						<h3>${message}</h3>
					</c:if>
					<table border="1" class="table border table-hover text-center align-middle">
						<tr>
							<th>Cliente</th>
							<th>Hora</th>
							<th>Acciones</th>
						</tr>
						<c:forEach var="cita" items="${citas}" varStatus="status">
							<tr>
								<td>${cita.ciudadano}</td>
								<td><c:set var="hora" value="${cita.horaInicio}"></c:set>
										<%hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%>
									</td>
								<td class="d-flex flex-row align-items-center justify-content-center">
									<a href="ver_cita?id=${visita.idVisita}&login=${cita.id.idCiudadano}" class="btn btn-primary table-icon"
									data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
										<i class="fa-solid fa-eye"></i>
									</a>
									<c:if test="${esFutura == true}">
										<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${cita.id.idCiudadano}" visita="${visita.idVisita}
										data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
											<i class="fa-solid fa-xmark"></i>
										</a>
									</c:if> </td>
							</tr>
						</c:forEach>
					</table>
				</div>
				</div>
			</c:if>
			<c:if test="${pasadoTiempoLimite == true}">
				<div>
					<div>
						<label>Describa la urgencia:</label>
					</div>
					<textarea rows="5" cols="50" name="urgencia" id="urgencia"></textarea>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					login = $(this).attr("id");
					idVisita = $(this).attr("visita");
					if(confirm("¿Desea eliminar la cita del usuario " + login + "?")) {
						window.location = "anular_cita?id=" + idVisita + "&login=" + login;
					}
				});
			});
		});
	</script>
</html>