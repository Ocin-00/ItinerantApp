<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>
		<c:if test="${usuario == null}">
			Registrarse
		</c:if>	
		<c:if test="${usuario != null}">
			Editar cuenta
		</c:if>
	</title>
	<link rel="stylesheet" href="css/layout.css">
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="/frontend/header.jsp"/>

	<div id="main-centered">
		<c:if test="${usuario == null}">
			<form action="nuevo_usuario" method="post" id="usuarioForm">
		</c:if>	
		<c:if test="${usuario != null}">
			<form action="actualizar_usuario" method="post" id="usuarioForm"">
		</c:if>
		<div class="main-centered-register">
			<div class="main-centered-register-items">
				<h2>Datos personales:</h2>
				<table>
					<tr>
						<td>Nombre:</td>
						<td>Apellidos:</td>
					</tr>
					<tr>
						<td><input type="text" name="nombre" id="nombre" size="20" value="${usuario.nombre}" /></td>
						<td><input type="text" name="apellidos" id="apellidos" size="20" value="${usuario.apellidos}" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Teléfono:</td>
						<td><input type="tel" name="telefono" id="telefono" size="20" value="${usuario.telefono}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Dirección:</td>
						<td><input type="text" name="direccion" id="direccion" size="20" value="${usuario.direccion}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Municipio: </td>
						<td>
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
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Fecha de nacimiento:</td>
						<td><input type="date" name="fechaNac" id="fechaNac" size="20" value="${usuario.fechaNac}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>

			<div class="main-centered-register-items"class="main-centered-register-items">
				<h2>Datos de cuenta: </h2>
				<table>
					<tr>
						<td>Email:</td>
						<td>Nombre de usuario:</td>
					</tr>
					<tr>
					<c:if test="${usuario == null}">
						<td><input type="text" name="email" id="email" size="20" value="${usuario.email}"/></td>
						<td><input type="text" name="login" id="login" size="20" value="${usuario.login}"/></td>
					</c:if>
					<c:if test="${usuario != null}">
						<td>
							<input type="text" name="email" id="email" size="20" value="${usuario.email}" disabled/>
							<input type="hidden" name="email" id="email" size="20" value="${usuario.email}"/>
						</td>
						<td>
							<input type="text" name="login" id="login" size="20" value="${usuario.login}" disabled/>
							<input type="hidden" name="login" id="login" size="20" value="${usuario.login}"/>
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
						<c:if test="${usuario == null}">
						<td><input type="password" name="password" id="password" size="25" value="${usuario.password}"/></td>
						</c:if>
						<c:if test="${supervisor != null}">
						<td>
							<input type="password" name="password" id="password" size="25" value="${usuario.password}" disabled/>
							<input type="hidden" name="password" id="password" size="25" value="${usuario.password}"/>
						</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${usuario == null}">
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
	
				<c:if test="${usuario == null}">
					<table>
					<tr>
						<td>Por favor, elija el tipo de cuenta:</td>
					</tr>
					<tr align="left">
						<td align="left">
							<input type="radio" id="ciudadano" name="tipoCuenta" value="CIUDADANO">
							<label for="ciudadano">Usuario</label><br>
							<input type="radio" id="profesional" name="tipoCuenta" value="PROFESIONAL">
							<label for="profesional">Profesional</label><br>
						</tr>
					</table>
				</c:if>
			</div>
			
			<div class="main-centered-register-items">
				<h2>Ayúdanos a conocerte mejor:</h2>
				<table>
					<tr>
						<td align="right">Sexo:</td>
						<td>
							<select name="sexo" id="sexo">
								<c:forEach items="${listaSexo}" var="sexo">
									<option value="${sexo}">
										${sexo}
									</option>
								</c:forEach>
							</select> 
						</td>
					</tr>
					<tr>
						<td align="right">Estado civil:</td>
						<td>
							<select name="estadoCivil" id="estadoCivil">
								<c:forEach items="${listaEstadosCiviles}" var="estadoCivil">
									<option value="${estadoCivil}">
										${estadoCivil}
									</option>
								</c:forEach>
							</select> 
						</td>
					</tr>
					</table>
					<table>
					<tr>
						<td>Formación:</td>
					</tr>
					<tr>
						<td>
							<textarea rows="5" cols="50" name="formacion" id="formacion">${usuario.formacion}</textarea>
						</td>
					</tr>
				</table>
			</div>
			</div>
			<div align="center">
				<button type="submit">Crear cuenta</button>
			</div>
			<c:if test="${message != null}">
				<div align = "center">
					${message}
				</div>
			</c:if>
		</form>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp" />
</body>
<script type="text/javascript">

	$(document).ready(function(){
		$("#usuarioForm").validate({
				rules: {
					email: {
						required: true,
						email: true
					},
					nombre: "required",
					apellidos: "required",
					telefono: {
						required: {
							depends: function(element) {
								return $("#profesional").is(":checked");
							}
						}				
					},
					formacion: {
						required: {
							depends: function(element) {
								return $("#profesional").is(":checked");
							}
						}	
					},
					codPostal: "required",
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
					tipoCuenta: "required"
				},
				
				messages: {
					
					email: {
						required: "Por favor introduzca su e-mail.",
						email: "Por favor introduzca un e-mail válido."
					},
					nombre: "Por favor introduzca su nombre.",
					apellidos: "Por favor introduzca sus apellidos.",
					telefono: "Por favor introduzca el número de teléfono.",
					formacion: "Por favor indique su formación.",
					codPostal: "Por favor introduzca el municipio en el que vive.",
					fechaNac: "Por favor introduzca su fecha de nacimiento.",
					login: "Por favor introduzca un nombre de usuario válido.",
					password: {
						required: "Por favor introduzca la contraseña.",
						minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres.")
					},
					comfirmPassword: {
						required: "Por favor repita la contraseña.",
						minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres."),
						equalTo: "Los campos no coinciden."
					},
					tipoCuenta: "Por favor indique qué tipo de cuenta desea."
				}
			});
	}); 
/*
	$(document).ready(function(){
		$("#usuarioForm").validate({
				rules: {
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
			        formacion: {
				        required:{
				        	depends: function(element) {
				        		return $("#profesional").is(":checked");
							}
					    }
					},
				},
				
				messages: {
					login: "Por favor introduzca un nombre de usuario válido.",
					password: {
						required: "Por favor introduzca la contraseña.",
						minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres.")
					},
					comfirmPassword: {
						required: "Por favor repita la contraseña.",
						minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres."),
						equalTo: "Los campos no coinciden."
					},
					formacion: {
						required: "Por favor repita la contraseña."
					},
				}
			});
	});*/
	$("#buttonCancel").click(function() {
		history.go(-1);
	});
</script>
</html>