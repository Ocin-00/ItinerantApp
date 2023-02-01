<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
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
</head>
<style>
	#side-menu a:nth-child(5){ background-color: #e0e0e0 }
</style>
<body localidades="${localidades }">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<jsp:directive.include file="side_menu.jsp"/>
		<div id="main-content-split">
			<div class="main-content-split-items">
				<h2>Calendario</h2>
				<div>
					<div>
						Jornada del día: 
						<input type="date" name="fecha" id="fecha" size="17" value="${fecha}"/>
					</div>
					<div>
						<button id="addToSerie">Añadir a serie</button>
						<button id="replicarJornada">Replicar jornada</button>
					</div>
				</div>
				<dialog id="addDialog" class="dialog">
				  <form method="dialog">
				    <p>
				      <label>Seleccione la serie:
				        <select id="selectSerie">
				        <c:forEach var="serie" items="${series}" varStatus="status">
				        	<option value="${serie.idSerie}">${serie.nombre}</option>
				        </c:forEach>
				          <option value="newSerie">Crear nueva</option>
				        </select>
				      </label>
				    </p>
				    <div>
				    	<button id="addConfirmar">Confirmar</button>
				      	<button class="cancelBtn">Cancelar</button>
				    </div>
				  </form>
				</dialog>
				<dialog id="newDialog" class="dialog">
				  <form method="dialog" id="newDialogForm">
				    <p>
				      <label>Nombre a la serie:
				        <input id="nombreSerie" name="nombreSerie">
				      </label>
				    </p>
				    <div>
				    	<button id="newConfirmar">Confirmar</button>
				      	<button class="cancelBtn">Cancelar</button>
				    </div>
				  </form>
				</dialog>
				<c:if test="${message != null}">
					<div>
						<h4>${message}</h4>
					</div>
				</c:if>
				<table border="1">
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
								<a href="ver_visita?id=${visita.idVisita}">Ver detalles</a>
								<c:if test="${esPendiente == true}"> 
								| <a href="javascript:void(0);" class="deleteLink" id="${visita.idVisita}">Borrar</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="main-content-split-items" style="height: 60vh; width: 80vh;">
				<div id="map" class="map"></div>
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
		    window.location = "listar_jornadas?fecha=" + date;
		});

		$("#addToSerie").on("click", function() {
			var series = "${series}"
			if(series.length < 1) {
				//$("#addDialog").show();
				$("#newDialog").show();
			} else {
				$("#addDialog").show();
			}
			
		});

		$("#addConfirmar").on("click", function() {
			var date = $("#fecha").val();
			var visitas = "${visitas}";
			if(visitas.length < 1) {
				if(!confirm("¿Está seguro de que quiere añadir una jornada vacía a su serie?")) {
					$("#addDialog").hide();
					return;
				}
			}
			var idSerie = $("#selectSerie").val();
			if(idSerie != "newSerie") {
				window.location = "add_jornada?id=" + idSerie + "&fecha=" + date;
			}
			
		});
		
		$("#selectSerie").on("change", function() {
			var value = $(this).val();
			if(value == "newSerie"){
				$("#newDialog").show();
			}
		});

		$("#newConfirmar").on("click", function() {
			var date = $("#fecha").val();
			var nombre = $("#nombreSerie").val();
			if(nombre != "") {
				window.location = "nueva_serie?nombre=" + nombre + "&fecha=" + date;
			}
		});
		
		$(".cancelBtn").on("click", function(){
			$(this).parent().parent().parent().hide();
		});

		$("#newDialogForm").validate({
			rules: {
				nombreSerie: "required"
			},
			messages: {	
				nombreSerie: "Por favor introduzca el nombre de la serie."
			}
		});
	});
</script>
</html>