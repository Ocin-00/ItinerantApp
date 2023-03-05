<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Profesionales</title>
	<link rel="stylesheet" href="../css/layout.css">
	<link rel="stylesheet" href="../css/side-bar-style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/notify.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../js/my-notifications.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
	<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	
	<link rel="stylesheet" href="../css/leaflet.css" />
	<script src="../js/leaflet.js"></script>
	
	<link rel="stylesheet" href="../css/leaflet-routing-machine/leaflet-routing-machine.css" />
	<script src="../js/leaflet-routing-machine.min.js"></script>
	
	<link rel="stylesheet" href="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css" />
	<script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>
	
	 <link rel="stylesheet" href="../css/Control.FullScreen.css" />
 	<script src="../js/Control.FullScreen.js"></script>
	
	<script type="text/javascript" src="../js/maps.js"></script>	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
	
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
</head>
<style>
	#side-menu ul li:nth-child(4){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100" localidades="${localidades }">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<input type="hidden" value="${numVisitas}" id="numVisitas">
		<jsp:directive.include file="../side_menu.jsp"/>
		<div class="container-fluid d-xl-flex flex-row mt-3 justify-content-evenly pb-3" style="min-height: 75vh">
			<div class="container-fluid">
				<h2>Calendario</h2>
				<div>
					<div class="input-group flex-nowrap justify-content-start">
						<label for="fecha" class="input-group-text">Jornada del día:</label>
						<input type="date" name="fecha" id="fecha" value="${fecha}" class="form-control border-dark-subtle" style="max-width: 200px"/>
					</div>
					<div class= " container-fluid mt-3 mb-3">
					<c:if test="${empty series}">
						<button id="addToSerie" data-bs-toggle="modal" data-bs-target="#nuevaSerieModal">Añadir a serie</button>
					</c:if>
					<c:if test="${not empty series}">
						<button id="addToSerie" data-bs-toggle="modal" data-bs-target="#selectSerieModal">Añadir a serie</button>
					</c:if>
						
						<button id="replicarJornada" data-bs-toggle="modal" data-bs-target="#replicarJornadaModal">Replicar</button>
						<button id="verInforme"  onclick="location.href='ver_informe?fecha=${fecha}';">Ver informe</button>
					</div>
				</div>
				<div class="modal fade" id="selectSerieModal" tabindex="-1" aria-labelledby="selectSerieModal" aria-hidden="true">
				  <div class="modal-dialog modal-dialog-centered">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h1 class="modal-title fs-5" id="exampleModalLabel">Añadir jornada a serie</h1>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				       <div class="form-container">
						  <form method="dialog" id="selectForm">
						    <div class="d-flex justify-content-center input-group m-2">
						      <label for="selectSerie" class="form-label">Seleccione la serie:
						        <select id="selectSerie" class="form-select border-dark-subtle">
						        <c:forEach var="serie" items="${series}" varStatus="status">
						        	<option value="${serie.idSerie}">${serie.nombre}</option>
						        </c:forEach>
						          <option value="newSerie">Crear nueva</option>
						        </select>
						      </label>
						    </div>
						    <div class="modal-footer">
						    	<button id="addConfirmar" type="button" class="btn btn-secondary">Confirmar</button>
						      	<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
						    </div>
						  </form>  
						</div>
				      </div>
				    </div>
				  </div>
				</div>
			<div class="modal fade" id="nuevaSerieModal" tabindex="-1" aria-labelledby="nuevaSerieModal" aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">Nueva serie</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<div class="form-container">
								<form method="dialog" id="newDialogForm">
								    <div class="d-flex justify-content-center input-group m-2">
								      <label for="nombreSerie" class="form-label">Nombre a la serie:
								        <input id="nombreSerie" name="nombreSerie" class="form-control border-dark-subtle" maxlength="70">
								      </label>
								    </div>
								    <div class="modal-footer">
								    	<button id="newConfirmar" type="button" class="btn btn-secondary">Confirmar</button>
								      	<button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Cancelar</button>
								    </div>
								  </form>
								</div>
							</div>
						</div>
					</div>
				</div>
			<div class="modal fade" id="changeNombreModal" tabindex="-1" aria-labelledby="changeNombreModal" aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">Cambiar nombre</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<div class="form-container">
							  <form method="dialog" id="changeNameDialogForm">
							    <div class="d-flex justify-content-center input-group m-2">
							      <label for="nuevoNombreSerie" class="form-label">Nombre a la serie:
							        <input id="nuevoNombreSerie" name="nuevoNombreSerie" class="form-control border-dark-subtle" maxlength="70">
							        <input type="hidden" id="idSerie" name="idSerie">
							      </label>
							    </div>
							    <div class="modal-footer">
							    	<button id="changeConfirmar" type="button" class="btn btn-secondary">Confirmar</button>
								     <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Cancelar</button>
							    </div>
							  </form>
							  	</div>
							</div>
						</div>
					</div>
				</div>
			<div class="modal fade" id="replicarJornadaModal" tabindex="-1" aria-labelledby="replicarJornadaModal" aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">Replicar jornada</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
						  <form method="dialog" id="replicarDialogForm">
						    <div class="d-flex justify-content-center input-group m-2">
						      <label for="fechaReplicar" class="form-label">Elige un día para replicar esta jornada:
						        <input type="date" id="fechaReplicar" name="fechaReplicar" class="form-control border-dark-subtle">
						      </label>
						    </div>
						    <div class="modal-footer">
						    	<button id="replicarConfirmar" type="button" class="btn btn-secondary">Confirmar</button>
						      	<button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Cancelar</button>
						    </div>
						  </form>
						</div>
						</div>
					</div>
				</div>
				<div class="modal fade" id="replicarSerieModal" tabindex="-1" aria-labelledby="replicarSerieModal" aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">Replicar serie</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
						  <form method="dialog" id="replicarSerieDialogForm">
						    <p>
						      <label for="fechaReplicarSerie" class="form-label">Elige un día a partir del que replicar esta serie:
						        <input type="date" id="fechaReplicarSerie" name="fechaReplicarSerie" class="form-control border-dark-subtle">
						        <input type="hidden" id="idSerieReplicar" name="idSerie">
						      </label>
						    </p>
						    <div class="modal-footer">
						    	<button id="replicarSerieConfirmar"  type="button" class="btn btn-secondary">Confirmar</button>
						      	<button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Cancelar</button>
						    </div>
						  </form>
						</div>
						</div>
					</div>
				</div>
				<c:if test="${message != null}">
					<div>
						<h4>${message}</h4>
					</div>
				</c:if>
				<c:if test="${empty visitas}">
					<div>
						<h5 class="mt-4">No hay visitas programadas para este día.</h5>
					</div>
				</c:if>
				<c:if test="${not empty visitas}">
				<div class="d-none d-md-block">
				<table border="1" class="table border table-hover text-center align-middle">
					<tr>
						<th>Índice</th>
						<th>Localidad</th>
						<th>Hora inicio</th>
						<th>Hora fin</th>
						<th>Acciones</th>
					</tr>
					<c:forEach var="visita" items="${visitas}" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${visita.localidad.nombre}</td>
							<td><c:set var="hora" value="${visita.horaInicio}"></c:set> <%
								 SimpleDateFormat format = new SimpleDateFormat("HH:mm");
								 String hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
							<td><c:set var="hora" value="${visita.horaFin}"></c:set> <%
								 hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
							<td align="center">
								<a href="ver_visita?id=${visita.idVisita}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
									<i class="fa-solid fa-eye"></i>
								</a>
								<c:if test="${esPendiente == true}"> 
									<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${visita.idVisita}"
									data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
										<i class="fa-solid fa-xmark"></i>
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				</div>
				
				<div class="d-md-none d-block">
				<table border="1" class="table border table-hover text-center align-middle">
					<tr>
						<th>Localidad</th>
						<th>Hora inicio</th>
						<th>Hora fin</th>
						<th>Acciones</th>
					</tr>
					<c:forEach var="visita" items="${visitas}" varStatus="status">
						<tr>
							<td>${visita.localidad.nombre}</td>
							<td><c:set var="hora" value="${visita.horaInicio}"></c:set> <%
								 SimpleDateFormat format = new SimpleDateFormat("HH:mm");
								 String hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
							<td><c:set var="hora" value="${visita.horaFin}"></c:set> <%
								 hora = format.format(pageContext.getAttribute("hora"));
								 out.println(hora);
								 %>
							</td>
							<td align="center">
								<a href="ver_visita?id=${visita.idVisita}" class="btn btn-primary table-icon"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Ver visita">
									<i class="fa-solid fa-eye"></i>
								</a>
								<c:if test="${esPendiente == true}"> 
									<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${visita.idVisita}"
									data-bs-toggle="tooltip" data-bs-placement="top" title="Anular visita">
										<i class="fa-solid fa-xmark"></i>
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				</div>
				</c:if>
			</div>
			<div class="container-fluid d-md-flex align-items-center justify-content-center" >
				<div id="map" class="container-fluid map" style="height: 65vh;"></div>
				
				<div class="side-bar d-lg-none container-fluid d-flex align-items-center justify-content-center">
			<div>
			<div class="side-menu-title m-2">
				<h3>Series de Jornadas</h3>
			</div>
				<div id="seriesMenu" class="seriesMenu">
					<c:forEach var="serie" items="${series}" varStatus="status">
						<div id="${serie.idSerie}" class="serie">
							<a class="serie-btn" href="#">
								<i class="fa-solid fa-angle-down dropdown"></i>
								${serie.nombre}
								
							</a>
							<i class="fa-solid fa-pen-to-square edit" serie="${serie.idSerie}" serieNombre="${serie.nombre}"
							 data-bs-toggle="modal" data-bs-target="#changeNombreModal"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Editar serie"></i>
							<i class="fa-solid fa-clone clone" serie="${serie.idSerie}" serieNombre="${serie.nombre}"
							data-bs-toggle="modal" data-bs-target="#replicarSerieModal"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Replicar serie"></i>
							<i class="fa-solid fa-trash delete-serie" serie="${serie.idSerie}" serieNombre="${serie.nombre}"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar serie"></i>
							
							<div class="submenu">
								<ul>
								<c:forEach var="serieJornada" items="${serieJornadas}" varStatus="status">
									<c:if test="${serie.idSerie == serieJornada.serie.idSerie}">
										<li><a class="sub-item" href="listar_jornadas?fecha=${serieJornada.id.fecha}">
										Jornada del 
										<fmt:formatDate value="${serieJornada.id.fecha}" pattern="dd-MM-yyyy" />
										</a>
										<i class="fa-solid fa-trash delete" serie="${serie.idSerie}" fecha=<fmt:formatDate value="${serieJornada.id.fecha}" pattern="yyyy-MM-dd" /> serieNombre="${serie.nombre}"
										data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar jornada de serie"></i>
									</c:if>
								</c:forEach>
								</li></ul>
							</div>
						</div>
					</c:forEach>
					<div id="${serie.idSerie}" class="serie">
						<a class="serie-btn" href="#" id="crearBtn" data-bs-toggle="modal" data-bs-target="#nuevaSerieModal">
								<i class="fa-regular fa-square-plus nueva-serie"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Crear serie"></i> 
								Crear serie
						</a>
					</div>
				</div>
			</div>
			</div>
			</div>
		</div>
		<div class="side-bar d-none d-lg-block">
			<div class="side-menu-title m-2">
				<h3>Series de Jornadas</h3>
			</div>
				<div id="seriesMenu" class="seriesMenu">
					<c:forEach var="serie" items="${series}" varStatus="status">
						<div id="${serie.idSerie}" class="serie">
							<a class="serie-btn" href="#">
								<i class="fa-solid fa-angle-down dropdown"></i>
								${serie.nombre}
								
							</a>
							<i class="fa-solid fa-pen-to-square edit" serie="${serie.idSerie}" serieNombre="${serie.nombre}"
							data-bs-toggle="modal" data-bs-target="#changeNombreModal"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Editar serie"></i>
							<i class="fa-solid fa-clone clone" serie="${serie.idSerie}" serieNombre="${serie.nombre}"
							data-bs-toggle="modal" data-bs-target="#replicarSerieModal"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Replicar serie"></i>
							<i class="fa-solid fa-trash delete-serie" serie="${serie.idSerie}" serieNombre="${serie.nombre}"
							data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar serie"></i>
							
							<div class="submenu">
								<ul>
								<c:forEach var="serieJornada" items="${serieJornadas}" varStatus="status">
									<c:if test="${serie.idSerie == serieJornada.serie.idSerie}">
										<li><a class="sub-item" href="listar_jornadas?fecha=${serieJornada.id.fecha}">
										Jornada del 
										<fmt:formatDate value="${serieJornada.id.fecha}" pattern="dd-MM-yyyy" />
										</a>
										<i class="fa-solid fa-trash delete" serie="${serie.idSerie}" fecha=<fmt:formatDate value="${serieJornada.id.fecha}" pattern="yyyy-MM-dd" /> serieNombre="${serie.nombre}"
										data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar jornada de serie"></i>
									</c:if>
								</c:forEach>
								</li></ul>
							</div>
						</div>
					</c:forEach>
					<div id="${serie.idSerie}" class="serie" data-bs-toggle="modal" data-bs-target="#nuevaSerieModal">
						<a class="serie-btn" href="#" id="crearBtn">
								<i class="fa-regular fa-square-plus nueva-serie"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Crear serie"></i> 
								Crear serie
						</a>
					</div>
				</div>
			</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$(".deleteLink").each(function() {
			$(this).on("click", function() {
				id = $(this).attr("id");
				if(confirm("¿Desea eliminar la visita de id " + id + "?")) {
					window.location = "borrar_visita?id=" + id;
				}
			});
		});
		
		$("#fecha").change(function() {
		    var date = $(this).val();
		    if(date != null && date != '') {
		    	window.location = "listar_jornadas?fecha=" + date;
			}
		    
		});
/*
		$("#addToSerie").on("click", function() {
			var series = "${series}"
			if(series.length < 1) {
				//$("#addDialog").show();
				$("#newDialog").show();
			} else {
				$("#addDialog").show();
			}
			
		});

		$("#replicarJornada").on("click", function() {
			$("#replicarDialog").show();
		});
*/
		$("#addConfirmar").on("click", function() {
			var date = $("#fecha").val();
			var numVisitas = $("#numVisitas").val();
			if(numVisitas < 1) {
				//$("#addDialog").hide();
				$("#selectSerieModal").modal('hide');
				if(!confirm("¿Está seguro de que quiere añadir una jornada vacía a su serie?")) {
					return;
				}
			}
			var idSerie = $("#selectSerie").val();
			if(idSerie != "newSerie") {
				if($("#selectForm").valid() == true){
					window.location = "add_jornada?id=" + idSerie + "&fecha=" + date;
				}
			}
			
		});
		
		$("#selectSerie").on("change", function() {
			var value = $(this).val();
			if(value == "newSerie"){
				$("#selectSerieModal").modal('hide');
				$("#nuevaSerieModal").modal('show');
			}
		});

		$("#newConfirmar").on("click", function() {
			var date = $("#fecha").val();
			var nombre = $("#nombreSerie").val();
			if($("#newDialogForm").valid() == true) {
				window.location = "nueva_serie?nombre=" + nombre + "&fecha=" + date;
			}
		});
		
		$("#replicarConfirmar").on("click", function() {
			var fechaReplicarTexto = $("#fechaReplicar").val();
			var fechaTexto = $("#fecha").val();
			var fechaReplicar = new Date(fechaReplicarTexto);
			var ahora = new Date();
			if(fechaReplicar.getTime() > ahora) {
				//alert("adios");
				window.location = "replicar_jornada?fecha=" + fechaTexto + "&replica=" + fechaReplicarTexto;
			} else {
				//alert("hola");
				$("#fechaReplicar").after("<label id='fechaReplicar-error' class='error' for='fechaReplicar'>La fecha en la que replicará la jornada deber ser posteior a la original.</label>");
			}
		});
		/*
		$(".cancelBtn").on("click", function(){
			$(this).parent().parent().parent().hide();
		});
		
		$("#crearBtn").on("click", function(){
			$("#newDialog").show();
		});
		*/
		$("#newDialogForm").validate({
			rules: {
				nombreSerie: "required"
			},
			messages: {	
				nombreSerie: "Por favor introduzca el nombre de la serie."
			}
		});
		
		$("#changeNameDialogForm").validate({
			rules: {
				nuevoNombreSerie: "required"
			},
			messages: {	
				nuevoNombreSerie: "Por favor introduzca el nuevo nombre de la serie."
			}
		});

		$("#replicarDialogForm").validate({
			rules: {
				fechaReplicar: "required"
			},
			messages: {	
				fechaReplicar: "Por favor elija un día en el que replicar la jornada."
			}
		});

		$("#replicarSerieDialogForm").validate({
			rules: {
				fechaSerieReplicar: "required"
			},
			messages: {	
				fechaSerieReplicar: "Por favor elija un día a partir del que replicar la serie."
			}
		});
		/*
		$(".side-bar .seriesMenu .serie").each(function() {
			var menuItem = $(this);
		    var subMenu = menuItem.next().next().next().next(".submenu");
		    var isExpanded = localStorage.getItem("menu-" + menuItem.text().trim());
		    console.log(localStorage.getItem("menu-" + menuItem.text()));
		    if (isExpanded == "true") {
		      subMenu.show();
		    }
		  });*/
		
		$(".serie-btn").click(function() {
			/*
			var menuItem = $(this);
		    var subMenu = menuItem.next().next().next().next(".submenu");
		    subMenu.slideToggle();
		    localStorage.setItem("menu-" + menuItem.text().trim(), subMenu.is(":visible"));
		    console.log(localStorage.getItem("menu-" + menuItem.text()));*/
		 
			$(this).next().next().next().next(".submenu").slideToggle();
			$(this).find(".dropdown").toggleClass("rotate");
		});

		$(".side-bar .seriesMenu .serie .submenu .delete").click(function() {
			var serie = $(this).attr("serie");
			var serieNombre = $(this).attr("serieNombre");
			var fecha = $(this).attr("fecha");
			if(confirm("¿Desea eliminar la jornada del " + fecha + " de la serie " + serieNombre + "?")) {
				window.location = "borrar_jornada?id=" + serie + "&fecha=" + fecha;
			}
		});

		$(".side-bar .seriesMenu .serie .delete-serie").click(function() {
			var serie = $(this).attr("serie");
			var serieNombre = $(this).attr("serieNombre");
			if(confirm("¿Desea eliminar la serie " + serieNombre + "?")) {
				window.location = "borrar_serie?id=" + serie;
			}
		});

		$(".side-bar .seriesMenu .serie .edit").click(function() {
			var serie = $(this).attr("serie");
			var serieNombre = $(this).attr("serieNombre");
			$("#nuevoNombreSerie").attr("value",serieNombre);
			$("#idSerie").attr("value",serie);
			//$("#changeNameDialog").show();
		});

		$("#changeConfirmar").on("click", function() {
			var nombre = $("#nuevoNombreSerie").val();
			var serie = $("#idSerie").val();
			if($('#changeNombreModal').valid == true) {
				window.location = "cambiar_nombre_serie?id=" + serie + "&nombre=" + nombre;
			}
		});

		$(".side-bar .seriesMenu .serie .clone").on("click", function() {
			var serie = $(this).attr("serie");
			$("#idSerieReplicar").attr("value",serie);
			//$("#replicarSerieDialog").show();
		});

		$("#replicarSerieConfirmar").on("click", function() {
			var serie = $("#idSerieReplicar").val()
			var fechaReplicarTexto = $("#fechaReplicarSerie").val();
			var fechaReplicar = new Date(fechaReplicarTexto);
			var ahora = new Date();
			if(fechaReplicar.getTime() > ahora) {
				//alert("adios");
				window.location = "replicar_serie?id=" + serie + "&fecha=" + fechaReplicarTexto;
			} else {
				//alert("hola");
				$("#fechaReplicarSerie").after("<label id='fechaReplicar-error' class='error' for='fechaReplicar'>La fecha en la que replicará la serie deber ser posteior a la original.</label>");
			}
		});
	});
</script>
</html>