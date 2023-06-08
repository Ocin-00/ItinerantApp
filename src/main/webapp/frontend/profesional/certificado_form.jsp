<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>
		<c:if test="${certificado == null}">
			Itinerant - Crear certificado
		</c:if>	
		<c:if test="${certificado != null}">
			Itinerant - Editar certificado
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
		<c:if test="${certificado == null}">
			<form action="crear_certificado" method="post" id="certificadoForm" enctype="multipart/form-data">
		</c:if>	
		<c:if test="${certificado != null}">
			<form action="actualizar_certificado" method="post" id="certificadoForm" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${certificado.idCertificado}">
		</c:if>
				<div>
					<table>
						<tr>
							<td><label for="titulo" class="form-label">Titulo:</label></td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="titulo" id="titulo" class="form-control border-dark-subtle" value="${certificado.titulo}" maxlength="30"/></td>					
						</tr>
						<tr>
							<td><label for="entidadEmisora" class="form-label">Entidad emisora:</label></td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="entidadEmisora" id="entidadEmisora" class="form-control border-dark-subtle" value="${certificado.entidadEmisora}" maxlength="30"/></td>					
						</tr>
						<tr>
							<td><label for="anyo" class="form-label">Año:</label> ${certificado.anyo}</td>
							<c:if test="${certificado != null}">
							<input type="hidden" name="anyo" id="anyo" value="${certificado.anyo}"/>
							</c:if>
						</tr>
						<tr>
							<td colspan="2">
								<c:if test="${certificado == null}">
									<input type="number" name="anyo" id="anyo" class="form-control border-dark-subtle" value="${certificado.anyo}" maxlength="4"/>
								</c:if>
							</td>
						</tr>
						<c:if test="${certificado == null}">
							<tr>
								<td><label for="certificadoFile" class="form-label">Introduzca su certificado en formato PDF:</label></td>
							</tr>
							<tr>
								<td><input type="file" id="certificadoFile" name="certificadoFile" class="form-control border-dark-subtle" accept=".pdf"/></td>
							</tr>
						</c:if>
					</table>
				</div>
				
				<div class="d-flex justify-content-center m-4">
					<button type="submit">Guardar</button>
					&nbsp;&nbsp;
					<button id="buttonCancel" type="button">Cancelar</button>			
				</div>
			</form>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#certificadoForm").validate({
			rules: {
				titulo: "required",
				entidadEmisora: "required",
				anyo: {
		            required: true,
		            minlength: 4
		        },
				certificadoFile: "required",
			},
			
			messages: {
				
				titulo: "Por favor introduzca el título de su certificado.",
				entidadEmisora: "Por favor introduzca la entidad que emition el certificado.",
				anyo: {
					required: "Por favor introduzca el año en el que se expedió.",
					minlength: jQuery.validator.format("Introduzca un año válido.")
				},
				certificadoFile: "Por favor introduzca el certificado en formato PDF.",
			}
		});

		$("#buttonCancel").click(function() {
			history.go(-1);
		});
		/*
		$("#imagenVisita").change(function() {
			showImageThumbnail(this);
		});
	*/
	});

/*
	function showImageThumbnail(fileInput) {
        var file = fileInput.files.item(0);
        var reader = new FileReader();
		
        reader.onload = function(e) {
            $('#thumbnail').attr('src', e.target.result);
        };
 
        reader.readAsDataURL(file);
    }*/
</script>
</html>