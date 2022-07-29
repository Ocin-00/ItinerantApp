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
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center" id="horizontalsplit">
		<c:if test="${supervisor == null}">
			<form action="crear_supervisor" method="post" onsubmit="return validateFormInput()">
		</c:if>	
		<c:if test="${supervisor != null}">
			<form action="actualizar_supervisor" method="post" onsubmit="return validateFormInput()">
		</c:if>
			<div class="left">
				<table align="center">
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
				<table align="center">
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
				<input type="submit" value="Guardar">
				&nbsp;&nbsp;
				<input type="button" value="Cancelar" onclick="javascript:history.go(-1)">
			</div>
		</form>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp" />
</body>
<script type="text/javascript">
	function validateFormInput(){
		var cont = 0;
		var fieldNombre = document.getElementById("nombre");
		var fieldApellidos = document.getElementById("apellidos");
		var fieldTelefono = document.getElementById("telefono");
		var fieldOrgCoord= document.getElementById("organismoCoordinador");
		var fieldNss = document.getElementById("nss");	
		var fieldFechaNac = document.getElementById("fechaNac");
		var fieldEmail = document.getElementById("email");
		var fieldLogin = document.getElementById("login");
		var fieldPassword = document.getElementById("password");
		var fieldConfirmPassword = document.getElementById("comfirmPassword");
		var nivelAcceso = document.getElementById("nivelAcceso");
		var fieldNivelAcceso;
		//var fieldNivelAcceso = $('input[name="nivelAcceso"]:checked').val();;	

		if(fieldNombre.value.length == 0) {
			alert("Por favor introduzca el nombre.");
			fieldNombre.focus();
			return false;
		}
		if(fieldApellidos.value.length == 0) {
			alert("Por favor introduzca los apellidos.");
			fieldApellidos.focus();
			return false;
		}
		if(fieldTelefono.value.length == 0) {
			alert("Por favor introduzca el número de teléfono.");
			fieldTelefono.focus();
			return false;
		}
		if(fieldOrgCoord.value.length == 0) {
			alert("Por favor introduzca el nombre de la organización en la que trabaja el supervisor.");
			fieldOrgCoord.focus();
			return false;
		}
		if(fieldNss.value.length == 0) {
			alert("Por favor introduzca el número de la seguridad social del supervisor.");
			fieldNss.focus();
			return false;
		}
		if(fieldFechaNac.value.length == 0) {
			alert("Por favor introduzca la fecha de nacimiento.");
			fieldFechaNac.focus();
			return false;
		}
		if(fieldEmail.value.length == 0) {
			alert("Por favor introduzca el e-mail.");
			fieldEmail.focus();
			return false;
		}
		if(fieldLogin.value.length == 0) {
			alert("Por favor introduzca el nombre de usuario.");
			fieldLogin.focus();
			return false;
		}
		if(fieldPassword.value.length == 0) {
			alert("Por favor introduzca la contraseña.");
			fieldPassword.focus();
			return false;
		}
		if(fieldConfirmPassword.value.length == 0) {
			alert("Por favor repita la contraseña.");
			fieldConfirmPassword.focus();
			return false;
		}
               
        for(var i = 0; i < nivelAcceso.length; i++){
            if(nivelAcceso[i].checked){
            	fieldNivelAcceso = nivelAcceso[i].value;
            }
        }

        if(fieldNivelAcceso.value.length == 0) {
			alert("Por favor indique un nivel de acceso.");
			return false;
		}
	/*	if(fieldPassword.value === fieldConfirmPassword.value) {
			cont++;
			alert("La contraseña tiene que ser idéntica.");
			return false;
		}*/
		return true;
	}
/*
	function initalizeRadioButton(selectedRadio) {
		document.querySelectorAll('input[type="radio"]').forEach(radio =>{		    
			if ( radio.id === selectedRadio ){
				radio.checked = true;
			}
		})
	}*/
</script>
</html>