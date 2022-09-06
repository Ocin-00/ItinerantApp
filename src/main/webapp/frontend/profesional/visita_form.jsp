<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Crear visita</title>
	<link rel="stylesheet" href="../../css/style.css">
	<script type="text/javascript" src="../../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div>
		<form action="crear_visita" method="post" id="visitaForm" enctype="multipart/form-data">
			<div>
				<table>
					<tr>
						<td rowspan="8">
							<img id="thumbnail" alt="" height="250"/>
							<label for="imagenVisita">
								<img src="../../images/pencil.png"/>
							</label>
						</td>
						<td>Nombre:</td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" name="nombre" id="nombre" size="40" /></td>
					</tr>
					<tr>
						<td>Localidad:</td>
					</tr>
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
					<tr>
						<td>Fecha:</td>
						<td>Tiempo de cita (min):</td>
					</tr>
					<tr>
						<td><input type="date" name="fecha" id="fecha" size="17" /></td>
						<td><input type="number" name="tiempo" id="tiempo" size="17" /></td>
					</tr>
					<tr>
						<td>Hora de inicio:</td>
						<td>Hora de fin:</td>
					</tr>
					<tr>
						<td><input type="time" name="horaInicio" id="horaInicio" size="17" /></td>
						<td><input type="time" name="horaFin" id="horaFin" size="17" /></td>
					</tr>
				</table>
			</div>
			<div>
				<table>
					<tr>
						<td>Tiempo medio de desplazamiento:</td>
						<td><input type="number" name="desplazamiento" id="desplazamiento" size="17" /></td>
					</tr>
				</table>
			</div>
			<div>
				<table>
					<tr>
						<td>Descripción</td>
					</tr>
					<tr>
						<td><textarea rows="5" cols="50" name="descripcion" id="descripcion"></textarea></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>Precio:</td>
						<td><input type="number" name="precio" id="precio" size="5" /></td>
					</tr>
				</table>
			</div>
		
			<div>
				<table>
					<tr>
						<td>Categorías:</td>
						<td>
							<select multiple="multiple" name="categorias" id="categorias">
								<c:forEach items="${listaCategorias}" var="categoria">
									<option value="${categoria.idCategoria }">
										${categoria.nombre}
									</option>
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