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
			Modificar cuenta
		</c:if>
	</title>
	<c:if test="${usuario == null}">
		<link rel="stylesheet" href="css/layout.css">
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		</c:if>	
	<c:if test="${usuario != null}">
		<link rel="stylesheet" href="../css/layout.css">
		<link rel="stylesheet" href="../css/side-bar-style.css">
		<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/notify.js"></script>
	    <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="../js/my-notifications.js"></script>
	    <script type="text/javascript" src="js/general.js"></script>
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
		<link href="../css/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	</c:if>
</head>
<body>
	<c:if test="${usuario == null}">
			<jsp:directive.include file="/frontend/header.jsp"/>
	</c:if>	
	<c:if test="${usuario != null}">
		<jsp:directive.include file="../frontend/header_user.jsp"/>
	</c:if>

	<div id="main-centered">
		<c:if test="${usuario == null}">
			<form action="nuevo_usuario" method="post" id="usuarioForm">
		</c:if>	
		<c:if test="${usuario != null}">
			<form action="actualizar_usuario" method="post" id="usuarioForm"  enctype="multipart/form-data">
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
						<c:if test="${usuario.telefono != 'null'}">
							<td><input type="tel" name="telefono" id="telefono" size="20" value="${usuario.telefono}"/></td>
						</c:if>
						<c:if test="${usuario.telefono == 'null'}">
							<td><input type="tel" name="telefono" id="telefono" size="20"/></td>
						</c:if>
					</tr>
					<c:if test="${rol != 'ADMINISTRADOR' || 'SUPERVISOR'}">	
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Dirección:</td>
						<c:if test="${usuario.localizacion != 'null'}">
							<td><input type="text" name="direccion" id="direccion" size="20" value="${usuario.localizacion}"/></td>
						</c:if>
						<c:if test="${usuario.localizacion == 'null'}">
							<td><input type="text" name="direccion" id="direccion" size="20"/></td>
						</c:if>
					</tr>
					
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${usuario == null}">
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
					</c:if>	
					</c:if>	
					
					<c:if test="${rol == 'ADMINISTRADOR' || 'SUPERVISOR'}">	
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Organismo coordinador:</td>
						<td><input type="text" name="organismoCoordinador" id="organismoCoordinador" size="20" value="${usuario.organismoCoordinador}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Número de la seguridad social: </td>
						<td><input type="text" name="nss" id="nss" size="20" value="${usuario.nss}"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					</c:if>
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
					<c:if test="${usuario != null}">
						<tr>
							<td rowspan="8">
								<img id="thumbnail" alt="" height="250" src="${usuario.imagenRuta}"/>
								<label for="imagenPerfil">
									<img src="../images/pencil.png"/>
								</label>
							</td>
						</tr>
					</c:if>
					<tr>
						<td>Email:</td>
						<td>Nombre de usuario:</td>
					</tr>
					<tr>
					<c:if test="${usuario == null}">
						<td><input type="text" name="email" id="email" size="20"/></td>
						<td><input type="text" name="login" id="login" size="20"/></td>
					</c:if>
					<c:if test="${usuario != null}">
						<td>
							<input type="text" name="email" id="showemail" size="20" value="${usuario.email}" disabled/>
							<input type="hidden" name="email" id="mail" size="20" value="${usuario.email}"/>
						</td>
						<td>
							<input type="text" name="login" id="showlogin" size="20" value="${usuario.login}" disabled/>
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
						<c:if test="${usuario != null}">
						<td>
							<input type="password" name="password" id="showpassword" size="25" value="${usuario.password}" disabled/>
							<input type="hidden" name="password" id="password" size="25" value="${usuario.password}"/>
							<input type="hidden" name="comfirmPassword" id="comfirmPassword" size="25" value="${usuario.password}" />
						</td>
						<td>
							<button id="changePassword" type="button">Cambiar contraseña</button>
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
			
			<c:if test="${rol != 'ADMINISTRADOR' || 'SUPERVISOR'}">	
			<div class="main-centered-register-items">
				<h2>Ayúdanos a conocerte mejor:</h2>
				<table>
					<tr>
						<td align="right">Sexo:</td>
						<td>
							<select name="sexo" id="sexo">
								<c:forEach items="${listaSexo}" var="sexo">
								<c:if test="${usuario.sexo == sexo}">
									<option value="${sexo}" selected="selected">
										${sexo}
									</option>
								</c:if>
								<c:if test="${usuario.sexo != sexo}">
									<option value="${sexo}">
										${sexo}
									</option>
								</c:if>
								</c:forEach>
							</select> 
						</td>
					</tr>
					<tr>
						<td align="right">Estado civil:</td>
						<td>
							<select name="estadoCivil" id="estadoCivil">
								<c:forEach items="${listaEstadosCiviles}" var="estadoCivil">
									<c:if test="${usuario.estadoCivil == estadoCivil}">
										<option value="${estadoCivil}" selected="selected">
											${estadoCivil}
										</option>
									</c:if>
									<c:if test="${usuario.estadoCivil != estadoCivil}">
										<option value="${estadoCivil}">
											${estadoCivil}
										</option>
									</c:if>
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
						<c:if test="${usuario.formacion == 'null'}">
							<td>
								<textarea rows="5" cols="50" name="formacion" id="formacion"></textarea>
							</td>
						</c:if>
						<c:if test="${usuario.formacion != 'null'}">
							<td>
								<textarea rows="5" cols="50" name="formacion" id="formacion">${usuario.formacion}</textarea>
							</td>
						</c:if>
					</tr>
				</table>
				<c:if test="${rol == 'PROFESIONAL'}">
					<table>
						<tr>
							<td>Descripcion:</td>
						</tr>
						<tr>
							<c:if test="${usuario.descripcion == 'null'}">
								<td>
									<textarea rows="5" cols="50" name="descripcion" id="descripcion"></textarea>
								</td>
							</c:if>
							<c:if test="${usuario.descripcion != 'null'}">
								<td>
									<textarea rows="5" cols="50" name="descripcion" id="descripcion">${usuario.descripcion}</textarea>
								</td>
							</c:if>
						</tr>
					</table>
				</c:if>
			</div>
			</c:if>
			</div>
			<div align="center">
				<c:if test="${usuario == null}">
					<button type="submit">Crear cuenta</button>
				</c:if>
				<c:if test="${usuario != null}">
					<button type="submit">Modificar cuenta</button>	
					&nbsp;&nbsp;
					<button id="buttonCancel" type="button">Cancelar</button>
					<div>
						<button type="button" id="eliminarCuenta">Eliminar cuenta</button>
					</div>
				</c:if>	
			</div>
			<c:if test="${message != null}">
				<div align = "center">
					${message}
				</div>
			</c:if>
			<div class="image-input"><input type="file" id="imagenPerfil" name="imagenPerfil"/></div>
			<input type="hidden" value="${rol}" id="rol">
		</form>
	</div>
				<dialog id="newDialog" class="dialog">
				  <form method="dialog" id="newDialogForm">
				    <p align="right">
				      <label>Nueva contraseña:
				        <input id="newPassword" name="newPassword" type="password">
				      </label>
				     </p>
				     <p align="right">
				      <label>Confirmar contraseña:
				        <input id="comfirmNewPassword" name=comfirmNewPassword type="password">
				      </label>
				    </p>
				    <div align="center">
				    	<button id="newConfirmar" type="button">Confirmar</button>
				      	<button class="cancelBtn"  type="button">Cancelar</button>
				    </div>
				  </form>
				</dialog>
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
								return $("#rol").val() != 'CIUDADANO';
							}
						}				
					},
					formacion: {
						required: {
							depends: function(element) {
								return $("#rol").val() == 'PROFESIONAL';
							}
						}	
					},
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
			        organismoCoordinador: {
						required: {
							depends: function(element) {
								return $("#rol").val() == 'ADMINISTRADOR' || 'SUPERVISOR';
							}
						}				
					},
					nss: {
						required: {
							depends: function(element) {
								return $("#rol").val() == 'ADMINISTRADOR' || 'SUPERVISOR';
							}
						}				
					},
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
					organismoCoordinador: "Por favor introduzca su organismo coordinador.",
					nss: "Por favor introduzca su número de la seguridad social",
				}
			});

		$("#imagenPerfil").change(function() {
			showImageThumbnail(this);
		});

		$("#buttonCancel").click(function() {
			history.go(-1);
		});

		$("#newDialogForm").validate({
			rules: {
				newPassword: {
		            required: true,
		            minlength: 5
		        },
		        comfirmNewPassword: {
		            required: true,
		            minlength: 5,
		            equalTo: "#newPassword"
		        },
			},
			messages: {	
				newPassword: {
					required: "Por favor introduzca la contraseña.",
					minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres.")
				},
				comfirmNewPassword: {
					required: "Por favor repita la contraseña.",
					minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres."),
					equalTo: "Los campos no coinciden."
				},
			}
		});

		$("#changePassword").on("click", function(){
			$("#newDialog").show();
		});
		
		$(".cancelBtn").on("click", function(){
			$(this).parent().parent().parent().hide();
		});

		$("#newConfirmar").on("click", function() {
			$("#password").val($("#newPassword").val());
			$("#confirmPassword").val($("#confirmNewPassword").val());
			$(this).parent().parent().parent().hide();
		});

		$("#eliminarCuenta").on("click", function() {
			if(confirm("¿Desea eliminar su cuenta?")) {
				window.location = "eliminar_cuenta";
			}
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
</script>
</html>