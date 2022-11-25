<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	
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
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<c:if test="${visita == null}">
			<form action="crear_visita" method="post" id="visitaForm" enctype="multipart/form-data">
		</c:if>	
		<c:if test="${visita != null}">
			<form action="actualizar_visita" method="post" id="visitaForm" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${visita.idVisita}">
		</c:if>
				<div>
					<table>
						<tr>
							<td rowspan="8">
								<c:if test="${visita != null}">
									<img id="thumbnail" alt="" height="250" src="${visita.imagenRuta}"/>
								</c:if>
								<c:if test="${visita == null}">
									<img id="thumbnail" alt="" height="250"/>
								</c:if>	
								<label for="imagenVisita">
									<img src="../images/pencil.png"/>
								</label>
							</td>
							<td>Nombre:</td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="nombre" id="nombre" size="40" value="${visita.nombre}"/></td>					
						</tr>
						<tr>
							<td>Localidad: ${visita.localidad}</td>
							<c:if test="${visita != null}">
								<input type="hidden" value="${visita.localidad.codigoPostal}" id="codPostal" name="codPostal"/>
							</c:if>
						</tr>
						<c:if test="${visita == null}">
						<tr>
							<td colspan="2">
								<select name="codPostal" id="codPostal">
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
							<td>Fecha:</td>
							<td>Tiempo de cita (min):</td>
						</tr>
						<tr>
							<c:if test="${visita == null}">
								<td><input type="date" name="fecha" id="fecha" size="17" value="${visita.fecha}"/></td>
							</c:if>
							<c:if test="${visita != null}">
								<td>
									<input type="date" name="fecha" id="fecha" size="17" value="${visita.fecha}" disabled="disabled"/>
									<input type="hidden" name="fecha" id="fecha" size="17" value="${visita.fecha}"/>
								</td>
							</c:if>
							<td><input type="number" name="tiempo" id="tiempo" size="17" value="${visita.duracionCitas}"/></td>
						</tr>
						<tr>
							<td>Hora de inicio:</td>
							<td>Hora de fin:</td>
						</tr>
						<tr>
							<td><input type="time" name="horaInicio" id="horaInicio" size="17" value="${visita.horaInicio.hours}:${visita.horaInicio.minutes}:00"/></td>
							<td><input type="time" name="horaFin" id="horaFin" size="17" value="${visita.horaFin.hours}:${visita.horaFin.minutes}:00"/></td>
						</tr>
					</table>
				</div>
				<div>
					<table>
						<tr>
							<td>Tiempo medio de desplazamiento:</td>
							<td><input type="number" name="desplazamiento" id="desplazamiento" size="17" value="${visita.duracionDesplazamiento}"/></td>
						</tr>
					</table>
				</div>
				<div>
					<table>
						<tr>
							<td>Descripci�n</td>
						</tr>
						<tr>
							<td><textarea rows="5" cols="50" name="descripcion" id="descripcion">${visita.descripcion}</textarea></td>
						</tr>
					</table>
					<table>
						<tr>
							<td>Precio:</td>
							<td><input type="number" name="precio" id="precio" size="5" value="${visita.precio}"/></td>
						</tr>
					</table>
				</div>
			
				<div>
					<table>
						<tr>
						<!-- REPASAR POR CUESTIONES DE EFICIENCIA Y SENCILLEZ -->
							<td>Categor�as:</td>
							<td>
								<select multiple="multiple" name="categorias" id="categorias">
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
				
				<div align="center">
					<button type="submit">Guardar</button>
					&nbsp;&nbsp;
					<button id="buttonCancel">Cancelar</button>				
				</div>
				<div><input type="file" id="imagenVisita" name="imagenVisita"/></div>
			</form>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function(){
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
				codPostal: "Por favor introduzca la localidad donde se llevar� a cabo la visita.",
				fecha: "Por favor introduzca la fecha en la que realizar� la visita.",
				tiempo: "Por favor introduzca la duraci�n de las citas para esta visita en minutos.",
				horaInicio: "Por favor indique la hora en la que iniciar� la visita.",
				horaFin: "Por favor indique la hora a la que finalizar� la visita.",
				desplazamiento: "Por favor indique el tiempo estimado de desplazamiento entre citas.",
				descripcion: "Por favor introduzca una descripci�n.",
				precio: "Por favor introduzca el precio por cita.",
			}
		});
		
		$("#imagenVisita").change(function() {
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
</script>
</html>