<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>Itinerant - Administración</title>
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
<style>
	#side-menu ul li:nth-child(4){ background-color: #e0e0e0 }
</style>
<body class="d-flex flex-column min-vh-100">
	<jsp:directive.include file="/frontend/header_user.jsp"/>
	<div class="wrapper">	
		<jsp:directive.include file="/frontend/side_menu.jsp"/>
		
		<div class="container mt-3" style="min-height: 75vh">
			<h2>Categorías:</h2>
			<c:if test="${message != null}">
				<div><h4>${message}</h4></div>
			</c:if>
			<div>
			<table border="1" class="table border table-hover text-center align-middle" style="max-width: 600px">
				<tr>
					<th>Índice</th>
					<th>Nombre</th>
					<th>Acciones</th>
				</tr>
				<c:forEach var="categoria" items="${categorias}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${categoria.nombre}</td>
						<td>
							<!-- <a href="#" class="changeBtn" idCategoria="${categoria.idCategoria}" nombre="${categoria.nombre}">Editar</a> | -->
								<a href="editar_categoria?id=${categoria.idCategoria}" class="btn btn-primary table-icon validateLink"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Editar categoria">
									<i class="fa-solid fa-pencil"></i>
								</a>
								<a href="javascript:void(0);" class="btn btn-danger table-icon deleteLink" id="${categoria.idCategoria}" nombre="${categoria.nombre}"
								data-bs-toggle="tooltip" data-bs-placement="top" title="Borrar categoria">
									<i class="fa-solid fa-trash-can"></i>
								</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td>
						<a href="nueva_categoria" class="btn btn-warning table-icon validateLink" 
								data-bs-toggle="tooltip" data-bs-placement="top" title="Añadir categoria">
									<i class="fa-solid fa-plus"></i>
								</a>
					</td>
				</tr>
			</table>
			<!-- 
			<dialog id="newDialog" class="dialog">
				<form method="dialog" id="newDialogForm" enctype="multipart/form-data">
				    <p>
				    <div class="image-input"><input type="file" id="imagenCategoria" name="imagenCategoria"/></div>
				    	<img id="thumbnail" alt="" height="250"/>
								<label for="imagenCategoria">
									<img src="../images/pencil.png"/>
								</label>
				      <label>Nombre a la categoria:
				        <input id="nombreCategoria" name="nombreCategoria">
				      </label>
				    </p>
				    <div>
				    	<button id="newConfirmar">Confirmar</button>
				      	<button class="cancelBtn">Cancelar</button>
				    </div>
				</form>
			</dialog>
			<dialog id="changeNameDialog" class="dialog">
				<form method="dialog" id="changeNameDialogForm" enctype="multipart/form-data">
				    <p>
				    <div class="image-input"><input type="file" id="imagenCategoria" name="imagenCategoria"/></div>
				    	<img id="thumbnail" alt="" height="250" src="${categoria.imagenRuta}"/>
								<label for="imagenCategoria">
									<img src="../images/pencil.png"/>
								</label>
				      <label>Nombre a la serie:
				        <input id="nuevoNombreCategoria" name="nuevoNombreCategoria">
				        <input type="hidden" id="idCategoria" name="idCategoria">
				      </label>
				    </p>
				    <div>
				    	<button id="changeConfirmar">Confirmar</button>
				      	<button class="cancelBtn">Cancelar</button>
				    </div>
				</form>
			</dialog>
			 -->
			</div>
		</div>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					idCategoria = $(this).attr("id");
					nombre = $(this).attr("nombre");
					if(confirm("¿Desea borrar la categoria " + nombre + "?")) {
						window.location = "borrar_categoria?id=" + idCategoria;
					}
				});
			});

			/*
			$("#crearBtn").on("click", function(){
				$("#newDialog").show();
			});

			$(".changeBtn").click(function() {
				var id = $(this).attr("idCategoria");
				var nombre = $(this).attr("nombre");
				$("#nuevoNombreCategoria").attr("value", nombre);
				$("#idCategoria").attr("value",id);
				$("#changeNameDialog").show();
			});
			
			$(".cancelBtn").on("click", function(){
				$(this).parent().parent().parent().hide();
			});
			
			$("#newConfirmar").on("click", function() {
				var nombre = $("#nombreCategoria").val();
				if(nombre != "") {
					window.location = "crear_categoria?nombre=" + nombre;
				}
			});

			$("#changeConfirmar").on("click", function() {
				var nombre = $("#nuevoNombreCategoria").val();
				var id = $("#idCategoria").val();
				if(nombre != "") {
					window.location = "actualizar_categoria?id=" + id + "&nombre=" + nombre;
				}
			});

			$("#newDialogForm").validate({
				rules: {
					nombreCategoria: "required",
					imagenCategoria: "required"
				},
				messages: {	
					nombreCategoria: "Por favor introduzca el nombre de la serie.",
					imagenCategoria: "Por favor seleccione una imagen."
				}
			});
			$("#changeNameDialogForm").validate({
				rules: {
					nuevoNombreCategoria: "required",
					imagenCategoria: "required"
				},
				messages: {	
					nuevoNombreCategoria: "Por favor introduzca el nuevo nombre de la serie.",
					imagenCategoria: "Por favor seleccione una imagen."
				}
			});
			*/
		});
	
	
</script>
</html>