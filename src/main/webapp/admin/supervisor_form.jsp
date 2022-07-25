<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Formulario de supervisor</title>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<form action="crear_supervisor" method="post" onsubmit="return validateFormInput()">
			<div>
				<table>
					<tr>
						<td>Nombre:</td>
						<td>Apellidos:</td>
					</tr>
					<tr>
						<td><input type="text" name="nombre" id="nombre" size="20" /></td>
						<td><input type="text" name="apellidos" id="apellidos" size="20" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Teléfono:</td>
					</tr>
					<tr>
						<td><input type="text" name="telefono" id="telefono" size="25" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Organismo coordinador:</td>
					</tr>
					<tr>
						<td><input type="text" name="organismoCoordinador" id="organismoCoordinador" size="25" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Nº de la Seguridad Social:</td>
					</tr>
					<tr>
						<td><input type="text" name="nss" id="nss" size="25" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Fecha de nacimiento:</td>
						<td><input type="date" name="fechaNac" id="fechaNac" size="25" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>

			<div>
				<table>
					<tr>
						<td>Email:</td>
						<td>Nombre de usuario:</td>
					</tr>
					<tr>
						<td><input type="text" name="email" id="email" size="20" /></td>
						<td><input type="text" name="login" id="login" size="20" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Contraseña:</td>
					</tr>
					<tr>
						<td><input type="password" name="password" id="password" size="25" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Confirme la contraseña:</td>
					</tr>
					<tr>
						<td><input type="password" name="comfirmPassword" id="comfirmPassword" size="25" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
	
				<table>
					<tr>
						<td>Por favor, elija el nivel de acceso:</td>
					</tr>
					<tr align="left">
						<td align="left">
							<input type="radio" id="general" name="nivelAcceso" value="General">   
							<label for="general">General</label><br>   
							<input type="radio" id="mancomunal" name="nivelAcceso" value="Mancomunal"> 
							<label for="mancomunal">Mancomunal</label><br>   
							<input type="radio" id="municipal" name="nivelAcceso" value="Municipal">   
							<label for="municipal">Municipal</label>
						</td>
					</tr>
				</table>
			</div>
			
			<div>
				<input type="submit" value="Guardar" onclick="javascript:history.go(-1)">
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
		//var fieldNivelAcceso = document.getElementById("nivelAcceso");
		
		

		if(fieldNombre.value.length == 0) {
			cont++;
			alert("Por favor introduzca el nombre.");
		}
		if(fieldApellidos.value.length == 0) {
			cont++;
			alert("Por favor introduzca los apellidos.");
		}
		if(fieldTelefono.value.length == 0) {
			cont++;
			alert("Por favor introduzca el número de teléfono.");
		}
		if(fieldOrgCoord.value.length == 0) {
			cont++;
			alert("Por favor introduzca el nombre de la organización en la que trabaja el supervisor.");
		}
		if(fieldNss.value.length == 0) {
			cont++;
			alert("Por favor introduzca el número de la seguridad social del supervisor.");
		}
		if(fieldFechaNac.value.length == 0) {
			cont++;
			alert("Por favor introduzca la fecha de nacimiento.");
		}
		if(fieldEmail.value.length == 0) {
			cont++;
			alert("Por favor introduzca el e-mail.");
		}
		if(fieldLogin.value.length == 0) {
			cont++;
			alert("Por favor introduzca el nombre de usuario.");
		}
		if(fieldPassword.value.length == 0) {
			cont++;
			alert("Por favor introduzca la contraseña.");
		}
		if(fieldConfirmPassword.value.length == 0) {
			cont++;
			alert("Por favor repita la contraseña.");
		}
	/*	if(fieldNivelAcceso.value.length == 0) {
			cont++;
			alert("Por favor indique un nivel de acceso.");
		}*/
	/*	if(fieldPassword.value === fieldConfirmPassword.value) {
			cont++;
			alert("La contraseña tiene que ser idéntica.");
		}*/
		if(cont > 0) {
			return false;
		} else {
			return true;
		}
	}
</script>
</html>