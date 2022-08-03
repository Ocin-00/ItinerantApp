<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>
		<c:if test="${supervisor == null}">
			Crear supervisor
		</c:if>	
		<c:if test="${supervisor != null}">
			Editar supervisor
		</c:if>
	</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center" id="horizontalsplit">
		<c:if test="${supervisor == null}">
			<form action="crear_supervisor" method="post" id="supervisorForm">
		</c:if>	
		<c:if test="${supervisor != null}">
			<form action="actualizar_supervisor" method="post" id="supervisorForm">
		</c:if>
			<div class="left">
				<table align="center" class="form">
					<tr>
						<td>Nombre:</td>
						<td>Apellidos:</td>
					</tr>
					<tr>
						<td><input type="text" name="nombre" id="nombre" size="20" value="${supervisor.nombre}" /></td>
						<td><input type="text" name="apellidos" id="apellidos" size="20" value="${supervisor.apellidos}" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Teléfono:</td>
					</tr>
					<tr>
						<td><input type="text" name="telefono" id="telefono" size="25" value="${supervisor.telefono}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Organismo coordinador:</td>
					</tr>
					<tr>
						<td><input type="text" name="organismoCoordinador" id="organismoCoordinador" size="25" value="${supervisor.organismoCoordinador}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Nº de la Seguridad Social:</td>
					</tr>
					<tr>
						<td><input type="text" name="nss" id="nss" size="25" value="${supervisor.nss}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Fecha de nacimiento:</td>
						<td><input type="date" name="fechaNac" id="fechaNac" size="25" value="${supervisor.fechaNac}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>

			<div class="right">
				<table align="center" class="form">
					<tr>
						<td>Email:</td>
						<td>Nombre de usuario:</td>
					</tr>
					<tr>
					<c:if test="${supervisor == null}">
						<td><input type="text" name="email" id="email" size="20" value="${supervisor.email}"/></td>
						<td><input type="text" name="login" id="login" size="20" value="${supervisor.login}"/></td>
					</c:if>
					<c:if test="${supervisor != null}">
						<td>
							<input type="text" name="email" id="email" size="20" value="${supervisor.email}" disabled/>
							<input type="hidden" name="email" id="email" size="20" value="${supervisor.email}"/>
						</td>
						<td>
							<input type="text" name="login" id="login" size="20" value="${supervisor.login}" disabled/>
							<input type="hidden" name="login" id="login" size="20" value="${supervisor.login}"/>
						</td>
					</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Contraseña:</td>
					</tr>
					<tr>
						<c:if test="${supervisor == null}">
						<td><input type="password" name="password" id="password" size="25" value="${supervisor.password}"/></td>
						</c:if>
						<c:if test="${supervisor != null}">
						<td>
							<input type="password" name="password" id="password" size="25" value="${supervisor.password}" disabled/>
							<input type="hidden" name="password" id="password" size="25" value="${supervisor.password}"/>
						</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${supervisor == null}">
					<tr>
						<td>Confirme la contraseña:</td>
					</tr>
					<tr>
						<td><input type="password" name="comfirmPassword" id="comfirmPassword" size="25" /></td>			
					</tr>
					</c:if>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
	
				<table align="left">
					<tr>
						<td>Por favor, elija el nivel de acceso:</td>
					</tr>
					<tr align="left">
						<td align="left">
							<c:if test="${supervisor.nivelAcceso == 'General'}">
								<input type="radio" id="general" name="nivelAcceso" value="General" checked="checked">
							</c:if>
							<c:if test="${supervisor.nivelAcceso != 'General'}">
								<input type="radio" id="general" name="nivelAcceso" value="General">
							</c:if>
							<label for="general">General</label><br>  
							<c:if test="${supervisor.nivelAcceso == 'Mancomunal'}">
								<input type="radio" id="mancomunal" name="nivelAcceso" value="Mancomunal" checked="checked">
							</c:if>
							<c:if test="${supervisor.nivelAcceso != 'Mancomunal'}">
								<input type="radio" id="mancomunal" name="nivelAcceso" value="Mancomunal"> 
							</c:if> 
							<label for="mancomunal">Mancomunal</label><br>   
							<c:if test="${supervisor.nivelAcceso == 'Municipal'}">
								<input type="radio" id="municipal" name="nivelAcceso" value="Municipal" checked="checked">
							</c:if>
							<c:if test="${supervisor.nivelAcceso != 'Municipal'}">
								<input type="radio" id="municipal" name="nivelAcceso" value="Municipal"> 
							</c:if> 
							<label for="municipal">Municipal</label>
						</td>
					</tr>
				</table>
			</div>
			
			<div align="center">
				<button type="submit">Guardar</button>
				&nbsp;&nbsp;
				<button id="buttonCancel">Cancelar</button>
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
					password: "required",
					comfirmPassword: "required",
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
					password: "Por favor introduzca la contraseña.",
					comfirmPassword: "Por favor repita la contraseña.",
					nivelAcceso: "Por favor indique un nivel de acceso."
				}
			});
	});

	$("#buttonCancel").click(function() {
		history.go(-1);
	});
</script>
</html>