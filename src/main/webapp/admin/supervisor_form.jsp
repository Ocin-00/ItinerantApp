<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>
		<c:if test="${supervisor == null}">
			Crear supervisor
		</c:if>	
		<c:if test="${supervisor != null}">
			Editar supervisor
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
	<jsp:directive.include file="../frontend/header_user.jsp"/>

	<div class="container-fluid ov" style="min-height: 75vh">
		<c:if test="${supervisor == null}">
			<form action="crear_supervisor" method="post" id="supervisorForm">
		</c:if>	
		<c:if test="${supervisor != null}">
			<form action="actualizar_supervisor" method="post" id="supervisorForm">
		</c:if>
			<div class="container-fluid d-lg-flex flex-row align-items-center justify-content-center mt-3">
				<div class="main-centered-register-items">
				<table>
					<tr>
						<td><label for="nombre" class="form-label">Nombre:</label></td>
						<td><label for="apellidos" class="form-label">Apellidos:</label></td>
					</tr>
					<tr>
						<td><input type="text" name="nombre" id="nombre" class="form-control border-dark-subtle" value="${supervisor.nombre}" maxlength="320"/></td>
						<td><input type="text" name="apellidos" id="apellidos" class="form-control border-dark-subtle" value="${supervisor.apellidos}" maxlength="70"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="telefono" class="form-label">Teléfono:</label></td>
						<td><input type="tel" name="telefono" id="telefono" class="form-control border-dark-subtle" value="${supervisor.telefono}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="organismoCoordinador" class="form-label">Organismo coordinador:</label></td>
						<td><input type="text" name="organismoCoordinador" id="organismoCoordinador" class="form-control border-dark-subtle" value="${supervisor.organismoCoordinador}" maxlength="70"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="nss" class="form-label">Nº de la Seguridad Social:</label></td>
						<td><input type="text" name="nss" id="nss" class="form-control border-dark-subtle" value="${supervisor.nss}" maxlength="15"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="fechaNac" class="form-label">Fecha de nacimiento:</label></td>
						<td><input type="date" name="fechaNac" id="fechaNac" class="form-control border-dark-subtle" value="${supervisor.fechaNac}"/></td>
					</tr>
				</table>
			</div>

			<div class="main-centered-register-items">
				<table>
					<tr>
						<td><label for="email" class="form-label">Email:</label></td>
						<td><label for="login" class="form-label">Nombre de usuario:</label></td>
					</tr>
					<tr>
					<c:if test="${supervisor == null}">
						<td><input type="text" name="email" id="email" class="form-control border-dark-subtle" value="${supervisor.email}" maxlength="40"/></td>
						<td><input type="text" name="login" id="login" class="form-control border-dark-subtle" value="${supervisor.login}"  maxlength="20"/></td>
					</c:if>
					<c:if test="${supervisor != null}">
						<td>
							<input type="text" name="email" id="email" class="form-control border-dark-subtle" value="${supervisor.email}" disabled/>
							<input type="hidden" name="email" id="email" class="form-control border-dark-subtle" value="${supervisor.email}"/>
						</td>
						<td>
							<input type="text" name="login" id="login" class="form-control border-dark-subtle" value="${supervisor.login}" disabled/>
							<input type="hidden" name="login" id="login" class="form-control border-dark-subtle" value="${supervisor.login}"/>
						</td>
					</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="password" class="form-label">Contraseña:</label></td>
						<c:if test="${supervisor == null}">
						<td><input type="password" name="password" id="password" class="form-control border-dark-subtle" value="${supervisor.password}" maxlength="30"/></td>
						</c:if>
						<c:if test="${supervisor != null}">
						<td>
							<input type="password" name="password" id="password" class="form-control border-dark-subtle" value="${supervisor.password}" disabled/>
							<input type="hidden" name="password" id="password" class="form-control border-dark-subtle" value="${supervisor.password}"/>
						</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${supervisor == null}">
					<tr>
						<td align="right"><label for="comfirmPassword" class="form-label">Confirme la contraseña:</label></td>
						<td><input type="password" name="comfirmPassword" id="comfirmPassword" class="form-control border-dark-subtle" maxlength="30"/></td>			
					</tr>
					</c:if>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
	
				<table>
					<tr>
						<td>Por favor, elija el nivel de acceso:</td>
					</tr>
					<tr >
						<td  class="form-check m-2">
							<div>
							<c:if test="${supervisor.nivelAcceso == 'General'}">
								<input type="radio" id="general" name="nivelAcceso" value="General" checked="checked" class="form-check-input border-dark-subtle ">
							</c:if>
							<c:if test="${supervisor.nivelAcceso != 'General'}">
								<input type="radio" id="general" name="nivelAcceso" value="General" class="form-check-input border-dark-subtle ">
							</c:if>
							<label for="general">General</label>
							</div>
							<div>
							<c:if test="${supervisor.nivelAcceso == 'Mancomunal'}">
								<input type="radio" id="mancomunal" name="nivelAcceso" value="Mancomunal" checked="checked" class="form-check-input border-dark-subtle ">
							</c:if>
							<c:if test="${supervisor.nivelAcceso != 'Mancomunal'}">
								<input type="radio" id="mancomunal" name="nivelAcceso" value="Mancomunal" class="form-check-input border-dark-subtle "> 
							</c:if> 
							<label for="mancomunal">Mancomunal</label>
							</div>
							<div>
							<c:if test="${supervisor.nivelAcceso == 'Municipal'}">
								<input type="radio" id="municipal" name="nivelAcceso" value="Municipal" checked="checked" class="form-check-input border-dark-subtle ">
							</c:if>
							<c:if test="${supervisor.nivelAcceso != 'Municipal'}">
								<input type="radio" id="municipal" name="nivelAcceso" value="Municipal" class="form-check-input border-dark-subtle "> 
							</c:if> 
							<label for="municipal">Municipal</label>
							</div>
						</td>
					</tr>
				</table>
			</div>
			</div>
			<div align="center" class="mb-3">
				<button type="submit">Guardar</button>
				&nbsp;&nbsp;
				<button id="buttonCancel" type="button">Cancelar</button>
			</div>
		
		</form>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp" />
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#supervisorForm").validate({
				rules: {
					email: {
						required: true,
						email: true
					},
					nombre: "required",
					apellidos: "required",
					telefono: "required",
					organismoCoordinador: "required",
					nss: "required",
					fechaNac: "required",
					login: "required",
					password: {
			            required: true,
			            minlength: 5
			        },
			        comfirmPassword: {
			            required: true,
			            minlength: 5,
			            equalTo: "#password"
			        },
					nivelAcceso: "required"
				},
				
				messages: {
					
					email: {
						required: "Por favor introduzca el e-mail.",
						email: "Por favor introduzca un e-mail válido."
					},
					nombre: "Por favor introduzca el nombre.",
					apellidos: "Por favor introduzca los apellidos.",
					telefono: "Por favor introduzca el número de teléfono.",
					organismoCoordinador: "Por favor introduzca el nombre de la organización en la que trabaja el supervisor.",
					nss: "Por favor introduzca el número de la seguridad social del supervisor.",
					fechaNac: "Por favor introduzca la fecha de nacimiento.",
					login: "Por favor introduzca un nombre de usuario válido.",
					password: {
						required: "Por favor introduzca la contraseña.",
						minlength: "Por favor, asegúrese de que la contraña tenga al menos 5 caracteres."
					},
					comfirmPassword: {
						required: "Por favor repita la contraseña.",
						minlength: "Por favor, asegúrese de que la contraña tenga al menos 5 caracteres.",
						equalTo: "Los campos no coinciden."
					},
					nivelAcceso: "Por favor indique un nivel de acceso."
				},
				 errorPlacement: function(error, element) {
			            error.appendTo(element.parent());
			        }
			});
	});

	$("#buttonCancel").click(function() {
		history.go(-1);
	});
</script>
</html>