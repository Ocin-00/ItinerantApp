<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>
		<c:if test="${visita == null}">
			Itinerant - Crear visita
		</c:if>	
		<c:if test="${visita != null}">
			Itinerant - Editar visita
		</c:if>
	</title>
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
	
	<link rel="stylesheet" href="../css/bootstrap-multiselect.css">
	<script type="text/javascript" src="../js/bootstrap-multiselect.js"></script>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="container-fluid ov" style="min-height: 75vh">
		<c:if test="${visita == null}">
			<form action="crear_visita" method="post" id="visitaForm" enctype="multipart/form-data">
		</c:if>	
		<c:if test="${visita != null}">
			<form action="actualizar_visita" method="post" id="visitaForm" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${visita.idVisita}">
		</c:if>
		<div class="d-flex justify-content-center align-items-center m-4">
				<div>
					<c:if test="${visita != null}">
						<img id="thumbnail" alt="" height="250" src="${visita.imagenRuta}" class="img-fluid" style="max-height: 250px;"/>
					</c:if>
					<c:if test="${visita == null}">
						<img id="thumbnail" alt="¡Elige una imagen!" height="250" class="img-fluid" style="max-height: 250px;"/>
					</c:if>	
				</div>
				<div>
					<label for="imagenVisita" class="img-pencil">
						<i class="fa-solid fa-pen"></i>
					</label>
				</div>			
			</div>
		<div class="container-fluid d-lg-flex flex-row align-items-center justify-content-center">
			<div class="main-centered-visita-items">
				<div>
					<table>
						<tr>
							<td><label for="nombre" class="form-label">Nombre:</label></td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="nombre" id="nombre" class="form-control border-dark-subtle" value="${visita.nombre}" maxlength="30"/></td>					
						</tr>
						<tr>
							<td><label for="codPostal" class="form-label">Localidad:</label> ${visita.localidad}</td>
							<c:if test="${visita != null}">
								<input type="hidden" value="${visita.localidad.codigoPostal}" id="codPostal" name="codPostal"/>
							</c:if>
						</tr>
						<c:if test="${visita == null}">
						<tr>
							<td colspan="2">
								<select name="codPostal" id="codPostal" class="form-select border-dark-subtle">
									<c:forEach items="${listaLocalidades}" var="localidad">
										<option value="${localidad.codigoPostal}">
											${localidad.nombre}
										</option>
									</c:forEach>
								</select> 
							</td>
						</tr>
						</c:if>
						<tr>
							<td><label for="fecha" class="form-label">Fecha:</label></td>
							<td><label for="tiempo" class="form-label">Tiempo de cita (min):</label></td>
						</tr>
						<tr>
							<c:if test="${visita == null}">
								<td><input type="date" name="fecha" id="fecha" class="form-control border-dark-subtle" value="${visita.fecha}"/></td>
							</c:if>
							<c:if test="${visita != null}">
								<td>
									<input type="date" name="fecha" id="fecha" class="form-control border-dark-subtle" value="${visita.fecha}" disabled="disabled"/>
									<input type="hidden" name="fecha" id="fecha" class="form-control border-dark-subtle" value="${visita.fecha}"/>
								</td>
							</c:if>
							<td><input type="number" name="tiempo" id="tiempo" class="form-control border-dark-subtle" value="${visita.duracionCitas}"/></td>
						</tr>
						<tr>
							<td><label for="horaInicio" class="form-label">Hora de inicio:</label></td>
							<td><label for="horaFin" class="form-label">Hora de fin:</label></td>
						</tr>
						<tr>
							<td><input type="time" name="horaInicio" id="horaInicio" class="form-control border-dark-subtle" value="${visita.horaInicio.hours}:${visita.horaInicio.minutes}:00"/></td>
							<td><input type="time" name="horaFin" id="horaFin" class="form-control border-dark-subtle" value="${visita.horaFin.hours}:${visita.horaFin.minutes}:00"/></td>
						</tr>
					</table>
				</div>
				<div>
					<table>
						<tr>
							<td><label for="desplazamiento" class="form-label">Tiempo medio de desplazamiento:</label></td>
							<td class="form-group"><input type="number" name="desplazamiento" id="desplazamiento" class="form-control border-dark-subtle" value="${visita.duracionDesplazamiento}"/><label> &nbsp; mins</label></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="main-centered-visita-items">
				<div>
					<table>
						<tr>
							<td><label for="descripcion" class="form-label">Descripción:</label></td>
						</tr>
						<tr>
							<td><textarea rows="5" cols="50" name="descripcion" id="descripcion" class="form-control border-dark-subtle" maxlength="150">${visita.descripcion}</textarea></td>
						</tr>
					</table>
					<table>
						<tr>
							<td><label for="precio" class="form-label">Precio:</label></td>
							<td class="form-group"><input type="number" name="precio" id="precio" class="form-control border-dark-subtle" value="${visita.precio}"/><label>&nbsp; &euro;</label></td>
						</tr>
					</table>
				</div>
			
				<div>
					<table>
						<tr>
						<!-- REPASAR POR CUESTIONES DE EFICIENCIA Y SENCILLEZ -->
							<td><label for="categorias" class="form-label">Categorías:</label></td>
							<td>
								<select multiple="multiple" name="categorias" id="categorias" class="form-select border-dark-subtle">
									<c:forEach items="${listaCategorias}" var="categoria">
										<c:if test="${visita != null}">
											<c:set var="found" value="false" />
											<c:forEach items="${visita.categorias }" var="categoriaVisita">
												<c:if test="${categoria.idCategoria == categoriaVisita.idCategoria}">
													<option value="${categoria.idCategoria }" selected="selected">
														${categoria.nombre}
													</option>
													<c:set var="found" value="true" />
												</c:if>
											</c:forEach>
											<c:if test="${found == false}">
												<option value="${categoria.idCategoria }">
													${categoria.nombre}
												</option>
											</c:if>
										</c:if>
										<c:if test="${visita == null}">
											<option value="${categoria.idCategoria }">
												${categoria.nombre}
											</option>
										</c:if>
									</c:forEach>
								</select> 
							</td>
						</tr>
					</table>
				</div>
				</div>	
				</div>
				<div class="d-flex justify-content-center m-4">
					<button type="submit">Guardar</button>
					&nbsp;&nbsp;
					<button id="buttonCancel" type="button">Cancelar</button>				
				</div>
				<div class="image-input"><input type="file" id="imagenVisita" name="imagenVisita" src="${visita.imagenRuta}"/></div>
				<input type="hidden" value="false" id="imagenCambia" name="imagenCambia">
			</form>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$('#categorias').multiselect({
		      // Bootstrap 5 compatibility
		      templates: {
		        button: '<button type="button" class="multiselect dropdown-toggle btn btn-secondary" data-bs-toggle="dropdown" aria-expanded="false"><span class="multiselect-selected-text"></span></button>',
		      },
	          enableFiltering: true,
	          filterBehavior: 'text',
	          numberDisplayed: 2,
	          filterPlaceholder: 'Buscar...',
	          nonSelectedText: 'Elige categorias',
	          nSelectedText: ' seleccionadas',
	          allSelectedText: 'Todas seleccionadas',
	          enableCaseInsensitiveFiltering: true
		});
		$("#visitaForm").validate({
			rules: {
				nombre: "required",
				codPostal: "required",
				fecha: "required",
				tiempo: "required",
				horaInicio: "required",
				horaFin: "required",
				desplazamiento: "required",
				descripcion: "required",
				precio: "required",
			},
			
			messages: {
				
				nombre: "Por favor introduzca el nombre de la visita.",
				codPostal: "Por favor introduzca la localidad donde se llevará a cabo la visita.",
				fecha: "Por favor introduzca la fecha en la que realizará la visita.",
				tiempo: "Por favor introduzca la duración de las citas para esta visita en minutos.",
				horaInicio: "Por favor indique la hora en la que iniciará la visita.",
				horaFin: "Por favor indique la hora a la que finalizará la visita.",
				desplazamiento: "Por favor indique el tiempo estimado de desplazamiento entre citas.",
				descripcion: "Por favor introduzca una descripción.",
				precio: "Por favor introduzca el precio por cita.",
			}
		});

		$("#visitaForm").submit(function(event) {
			var horaInicio = $('#horaInicio').val();
			var horaFin = $('#horaFin').val();
			var horaInicioDate = new Date("1970-01-01 " + horaInicio);
			var horaFinDate = new Date("1970-01-01 " + horaFin);
			if(horaInicioDate.getTime() >= horaFinDate.getTime()) {
				event.preventDefault();
				 $("#horaInicio").after("<label id='hora-error' class='error' for='horaInicio'>La hora de inicio debe ser anterior a la de fin.</label>");
			} else {
				event.preventDefault = null;
			}
		});
		
		$("#tiempo").bind("keypress", function (e) {
	          var keyCode = e.which ? e.which : e.keyCode
	               
	          if (!(keyCode >= 48 && keyCode <= 57)) {
	        	  event.preventDefault();
	          }
	      });
		
		$("#imagenVisita").change(function() {
			$("#imagenCambia").val("true");
			showImageThumbnail(this);
		});

		$("#buttonCancel").click(function() {
			history.go(-1);
		});
		
	});	

	function showImageThumbnail(fileInput) {
        var file = fileInput.files.item(0);
        var reader = new FileReader();
		
        reader.onload = function(e) {
            $('#thumbnail').attr('src', e.target.result);
        };
 
        reader.readAsDataURL(file);
    }
/*
	require(['bootstrap-multiselect'], function(purchase){
		$('#categorias').multiselect();
	});*/
</script>
</html>