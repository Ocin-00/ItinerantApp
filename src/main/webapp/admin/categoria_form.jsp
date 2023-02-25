<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>
		<c:if test="${categoria == null}">
			Itinerant - Crear categoria
		</c:if>	
		<c:if test="${categoria != null}">
			Itinerant - Editar categoria
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
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
		<c:if test="${categoria == null}">
			<form action="crear_categoria" method="post" id="categoriaForm" enctype="multipart/form-data">
		</c:if>	
		<c:if test="${categoria != null}">
			<form action="actualizar_categoria" method="post" id="categoriaForm" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${categoria.idCategoria}">
		</c:if>
				<div>
					<table>
						<tr>
							<td rowspan="8">
								<img id="thumbnail" alt="" height="250" src="${categoria.imagenRuta}"/>
								<label for="imagenCategoria">
									<img src="../images/pencil.png"/>
								</label>
							</td>
						</tr>
						<tr>
							<td>Nombre:</td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="nombre" id="nombre" size="40" value="${categoria.nombre}"/></td>					
						</tr>
					</table>
				</div>
				
				<div align="center">
					<button type="submit">Guardar</button>
					&nbsp;&nbsp;
					<button id="buttonCancel" type="button">Cancelar</button>				
				</div>
				<div class="image-input"><input type="file" id="imagenCategoria" name="imagenCategoria"/></div>
			</form>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#certificadoForm").validate({
			rules: {
				nombre: "required",
				imagenCategoria: "required",
			},
			
			messages: {
				
				nombre: "Por favor introduzca el nombre de la categoría.",
				imagenCategoria: "Por favor introduzca la imagen que representará la categoría.",
			}
		});
		
		$("#imagenCategoria").change(function() {
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