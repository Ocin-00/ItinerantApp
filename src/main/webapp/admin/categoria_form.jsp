<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
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
	
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
	
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="container-fluid d-flex justify-content-center align-content-center" style="min-height: 75vh">
		<c:if test="${categoria == null}">
			<form action="crear_categoria" method="post" id="categoriaForm" enctype="multipart/form-data">
		</c:if>	
		<c:if test="${categoria != null}">
			<form action="actualizar_categoria" method="post" id="categoriaForm" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${categoria.idCategoria}">
		</c:if>
				<div class = "">
					<div class="m-2">
							<td rowspan="8">
								<img id="thumbnail" alt="" height="250" src="${categoria.imagenRuta}"/>
								<label for="imagenCategoria" class="img-pencil" >
									<i class="fa-solid fa-pen"></i>
								</label>
							</td>
					</div>
					<table>
						
						<tr>
							<td><label for="nombre" class="form-label">Nombre:</label></td>
							<td colspan="2"><input type="text" name="nombre" id="nombre" class="form-control border-dark-subtle" value="${categoria.nombre}" maxlength="30"/></td>
						</tr>
					</table>
					<div class="d-flex justify-content-evenly">
						<button type="submit">Guardar</button>
						<button id="buttonCancel" type="button">Cancelar</button>				
					</div>
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