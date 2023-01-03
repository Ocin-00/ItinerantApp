<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
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
</head>
<body>
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div id="main">
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
							<td>Titulo:</td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="titulo" id="titulo" size="40" value="${certificado.titulo}"/></td>					
						</tr>
						<tr>
							<td>Entidad emisora:</td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="entidadEmisora" id="entidadEmisora" size="40" value="${certificado.entidadEmisora}"/></td>					
						</tr>
						<tr>
							<td>Año: ${certificado.anyo}</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="number" name="anyo" id="anyo" size="40" value="${certificado.anyo}"/>
							</td>
						</tr>
						<c:if test="${certificado == null}">
							<tr>
								<td>Introduzca su certificado en formato PDF:</td>
							</tr>
							<tr>
								<td><input type="file" id="certificadoFile" name="certificadoFile"/></td>
							</tr>
						</c:if>
					</table>
				</div>
				
				<div align="center">
					<button type="submit">Guardar</button>
					&nbsp;&nbsp;
					<button id="buttonCancel">Cancelar</button>				
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
				anyo: "required",
				certificadoFile: "required",
			},
			
			messages: {
				
				titulo: "Por favor introduzca el título de su certificado.",
				entidadEmisora: "Por favor introduzca la entidad que emition el certificado.",
				anyo: "Por favor introduzca el año en el que se expedió.",
				certificadoFile: "Por favor introduzca el certificado en formato PDF.",
			}
		});
		/*
		$("#imagenVisita").change(function() {
			showImageThumbnail(this);
		});
*/
		$("#buttonCancel").click(function() {
			history.go(-1);
		});
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