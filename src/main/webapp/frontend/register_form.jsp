<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>
		<c:if test="${usuario == null}">
			Registrarse
		</c:if>	
		<c:if test="${usuario != null}">
			Modificar cuenta
		</c:if>
	</title>
	<script src="https://kit.fontawesome.com/511c190d35.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://kit.fontawesome.com/511c190d35.css" crossorigin="anonymous">

	

	<c:if test="${usuario == null}">
		<link rel="stylesheet" href="css/layout.css">
		<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
		<link rel="stylesheet" href="css/bootstrap-select.css">
		<script type="text/javascript" src="js/bootstrap-select.js"></script>
		<script type="text/javascript" src="js/defaults-es_ES.js"></script>
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
		<link rel="stylesheet" href="../css/bootstrap.min.css">
		<script type="text/javascript" src="../js/bootstrap.bundle.min.js"></script>
		<link rel="stylesheet" href="../css/bootstrap-select.css">
		<script type="text/javascript" src="../js/bootstrap-select.js"></script>
		<script type="text/javascript" src="../js/defaults-es_ES.js"></script>
	</c:if>
</head>
<!-- IMPORTANTE La class de body para las páginas que se pasan de altura -->
<body class="d-flex flex-column min-vh-100">
	<c:if test="${usuario == null}">
			<jsp:directive.include file="/frontend/header.jsp"/>
	</c:if>	
	<c:if test="${usuario != null}">
		<jsp:directive.include file="../frontend/header_user.jsp"/>
	</c:if>

	<div class="container-fluid ov" style="min-height: 75vh">
		<c:if test="${usuario == null}">
			<form action="nuevo_usuario" method="post" id="usuarioForm">
		</c:if>	
		<c:if test="${usuario != null}">
			<form action="actualizar_usuario" method="post" id="usuarioForm"  enctype="multipart/form-data">
		</c:if>
		<div class="container-fluid d-lg-flex flex-row align-items-center justify-content-center">
			<div class="main-centered-register-items">
				<h2>Datos personales:</h2>
				<table>
					<tr>
						<td><label for="nombre" class="form-label">Nombre:</label></td>
						<td><label for="apellidos" class="form-label">Apellidos:</label></td>
					</tr>
					<tr>
						<td><input type="text" name="nombre" id="nombre" value="${usuario.nombre}" class="form-control border-dark-subtle" maxlength="30"/></td>
						<td><input type="text" name="apellidos" id="apellidos" value="${usuario.apellidos}" class="form-control border-dark-subtle" maxlength="70"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="telefono" class="form-label">Teléfono:</label></td>
						<c:if test="${usuario.telefono != 'null'}">
							<td><input type="tel" name="telefono" id="telefono" value="${usuario.telefono}" class="form-control border-dark-subtle"/></td>
						</c:if>
						<c:if test="${usuario.telefono == 'null'}">
							<td><input type="tel" name="telefono" id="telefono" class="form-control border-dark-subtle"/></td>
						</c:if>
					</tr>
					<c:if test="${rol != 'ADMINISTRADOR' && rol != 'SUPERVISOR'}">	
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="direccion" class="form-label">Dirección:</label></td>
						<c:if test="${usuario.localizacion != 'null'}">
							<td><input type="text" name="direccion" id="direccion" value="${usuario.localizacion}" class="form-control border-dark-subtle" maxlength="50"/></td>
						</c:if>
						<c:if test="${usuario.localizacion == 'null'}">
							<td><input type="text" name="direccion" id="direccion" class="form-control border-dark-subtle" maxlength="50"/></td>
						</c:if>
					</tr>
					
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${rol != 'ADMINISTRADOR' || rol != 'SUPERVISOR'}">
					<tr>
						<td align="right"><label for="codPostal" class="form-label">Municipio:</label> </td>
						<td>
							<select name="codPostal" id="codPostal" class="selectpicker myselect" data-live-search="true" >
								<c:forEach items="${listaLocalidades}" var="localidad">
									<c:if test="${localidad.codigoPostal == usuario.localidad.codigoPostal}">
										<option value="${localidad.codigoPostal}" selected="selected">
											${localidad.nombre}
										</option>
									</c:if>
									<c:if test="${localidad.codigoPostal != usuario.localidad.codigoPostal}">
										<option value="${localidad.codigoPostal}">
											${localidad.nombre}
										</option>
									</c:if>
									
								</c:forEach>
							</select> 
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					</c:if>	
					</c:if>	
					
					<c:if test="${rol == 'ADMINISTRADOR' || rol == 'SUPERVISOR'}">	
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="organismoCoordinador" class="form-label">Organismo coordinador:</label></td>
						<td><input type="text" name="organismoCoordinador" id="organismoCoordinadorhidden " value="${usuario.organismoCoordinador}" class="form-control border-dark-subtle" maxlength="70" disabled="disabled"/>
							<input type="hidden" name="organismoCoordinador" id="organismoCoordinador" value="${usuario.organismoCoordinador}" class="form-control border-dark-subtle" maxlength="70"  /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><label for="nss" class="form-label">Número de la seguridad social:</label> </td>
						<td><input type="text" name="nss" id="nsshidden" value="${usuario.nss}" class="form-control border-dark-subtle" maxlength="15" disabled="disabled"/>
						<input type="hidden" name="nss" id="nss" value="${usuario.nss}" class="form-control border-dark-subtle" maxlength="15"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					</c:if>
					<tr>
						<td align="right"><label for="fechaNac" class="form-label">Fecha de nacimiento:</label></td>
						<td><input type="date" name="fechaNac" id="fechaNac" value="${usuario.fechaNac}" class="form-control border-dark-subtle"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>

			<div class="main-centered-register-items">
				<h2>Datos de cuenta: </h2>
				<c:if test="${usuario != null}">
					<div class="m-2">
						<tr>
							<td rowspan="8">
								<img id="thumbnail" alt="" height="250" src="${usuario.imagenRuta}" class="rounded-circle"/>
								<label for="imagenPerfil" class="img-pencil">
									<i class="fa-solid fa-pen"></i>
								</label>
							</td>
						</tr>
					</div>
				</c:if>
				<table>
					<tr>
						<td><label for="email" class="form-label">Email:</label></td>
						<td><label for="login" class="form-label">Nombre de usuario:</label></td>
					</tr>
					<tr>
					<c:if test="${usuario == null}">
						<td><input type="text" name="email" id="email" class="form-control border-dark-subtle" maxlength="40"/></td>
						<td><input type="text" name="login" id="login" class="form-control border-dark-subtle" maxlength="20"/></td>
					</c:if>
					<c:if test="${usuario != null}">
						<td>
							<input type="text" name="email" id="showemail" class="form-control border-dark-subtle" value="${usuario.email}" disabled/>
							<input type="hidden" name="email" id="mail" class="form-control border-dark-subtle" value="${usuario.email}"/>
						</td>
						<td>
							<input type="text" name="login" id="showlogin" class="form-control border-dark-subtle" value="${usuario.login}" disabled/>
							<input type="hidden" name="login" id="login" class="form-control border-dark-subtle" value="${usuario.login}"/>
						</td>
					</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td><label for="password" class="form-label">Contraseña:</label></td>
					</tr>
					<tr>
						<c:if test="${usuario == null}">
						<td><input type="password" name="password" id="password" class="form-control border-dark-subtle" value="${usuario.password}" maxlength="30"/></td>
						</c:if>
						<c:if test="${usuario != null}">
						<td>
							<input type="password" name="password" id="showpassword" class="form-control border-dark-subtle" value="${usuario.password}" disabled/>
							<input type="hidden" name="password" id="password" size="25" value="${usuario.password}"/>
							<input type="hidden" name="comfirmPassword" id="comfirmPassword" size="25" value="${usuario.password}" />
						</td>
						<td>
							<button id="changePassword" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal">Cambiar contraseña</button>
						</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${usuario == null}">
					<tr>
						<td><label for="comfirmPassword" class="form-label">Confirme la contraseña:</label></td>
					</tr>
					<tr>
						<td><input type="password" name="comfirmPassword" id="comfirmPassword" class="form-control border-dark-subtle" maxlength="30"/></td>			
					</tr>
					</c:if>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
	
				<c:if test="${usuario == null}">
					<table>

					<tr align="left">
						<td align="left">
							<div>
								<label for="tipoCuenta" class="form-label">Por favor, elija el tipo de cuenta:</label>
							</div>	
							<div>
								<input type="radio" id="ciudadano" name="tipoCuenta" value="CIUDADANO" class="form-check-input border-dark-subtle ">
								<label for="ciudadano">Usuario</label><br>
								<input type="radio" id="profesional" name="tipoCuenta" value="PROFESIONAL" class="form-check-input border-dark-subtle">
								<label for="profesional">Profesional</label><br>
							</div>		
						</tr>
					</table>
				</c:if>
			</div>
			
			<c:if test="${rol != 'ADMINISTRADOR' && rol != 'SUPERVISOR'}">	
			<div class="main-centered-register-items">
				<h2>Ayúdanos a conocerte mejor:</h2>
				<table>
					<tr>
						<td align="right"><label for="sexo" class="form-label">Sexo:</label></td>
						<td>
							<select name="sexo" id="sexo" class="selectpicker myselect">
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
						<td align="right"><label for="estadoCivil" class="form-label">Estado civil:</label></td>
						<td>
							<select name="estadoCivil" id="estadoCivil" class="selectpicker myselect">
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
						<td><label for="formacion" class="form-label">Formación:</label></td>
					</tr>
					<tr>
						<c:if test="${usuario.formacion == 'null'}">
							<td>
								<textarea rows="5" cols="50" name="formacion" id="formacion" class="form-control border-dark-subtle" maxlength="150"></textarea>
							</td>
						</c:if>
						<c:if test="${usuario.formacion != 'null'}">
							<td>
								<textarea rows="5" cols="50" name="formacion" id="formacion" class="form-control border-dark-subtle" maxlength="150">${usuario.formacion}</textarea>
							</td>
						</c:if>
					</tr>
				</table>
				<c:if test="${rol == 'PROFESIONAL'}">
					<table>
						<tr>
							<td><label for="descripcion" class="form-label">Descripcion:</label></td>
						</tr>
						<tr>
							<c:if test="${usuario.descripcion == 'null'}">
								<td>
									<textarea rows="5" cols="50" name="descripcion" id="descripcion" class="form-control border-dark-subtle" maxlength="150"></textarea>
								</td>
							</c:if>
							<c:if test="${usuario.descripcion != 'null'}">
								<td>
									<textarea rows="5" cols="50" name="descripcion" id="descripcion"  class="form-control border-dark-subtle" maxlength="150">${usuario.descripcion}</textarea>
								</td>
							</c:if>
						</tr>
					</table>
				</c:if>
			</div>
			</c:if>
			</div>
			<div align="center">
				<c:if test="${message != null}">
				<div class="m-3">
					<h5>${message}</h5>
				</div>
				</c:if>
				<c:if test="${usuario == null}">
					<button type="submit">Crear cuenta</button>
				</c:if>
				<c:if test="${usuario != null}">
					<button type="submit">Modificar cuenta</button>	
					&nbsp;&nbsp;
					<button id="buttonCancel" type="button">Volver</button>
					<div class="m-3">
						<button type="button" id="eliminarCuenta">Eliminar cuenta</button>
					</div>
				</c:if>	
			</div>
			
			<div class="image-input"><input type="file" id="imagenPerfil" name="imagenPerfil" src="${usuario.imagenRuta}" accept="image/*"/></div>
			<input type="hidden" value="false" id="imagenCambia" name="imagenCambia">
			<input type="hidden" value="${rol}" id="rol" name="rol">
		</form>
	</div>
	
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">Cambiar contraseña</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	       <div class="form-container">
			  <form action="change_password" method="post"id="newDialogForm">
			  	<table>
					<tr>
						<td align="right">  <label for="newPassword" class="form-label">Nueva contraseña:</label></td>
						<td><input id="newPassword" name="newPassword" type="password" class="form-control border-dark-subtle newPasswordInputs" maxlength="30"><br/></td>
					</tr>
					<tr>
						<td align="right"> <label for="confirmNewPassword" class="form-label">Confirmar contraseña:</label></td>
						<td> <input id="confirmNewPassword" name="confirmNewPassword" type="password" class="form-control border-dark-subtle newPasswordInputs" maxlength="30"></td>
					</tr>
				</table>
			    <div class="modal-footer">
			        <button id="newConfirmar" type="button" class="btn btn-secondary">Confirmar</button>
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
			  	</div>      
			  </form>
			</div>
	      </div>
	    </div>
	  </div>
	</div>
	
	<jsp:directive.include file="/frontend/footer.jsp" />
</body>
<script type="text/javascript">

	$(document).ready(function(){

		/*var radios = document.forms["usuarioForm"].elements["tipoCuenta"];
		  for(radio in radios) {
		    radio.onclick = function() {
		       alert(radio.value);
		    }
		}*/
		$('.selectpicker').selectpicker();
		
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
								return $("#profesional").is(":checked") || ($("#rol").val() != 'CIUDADANO' && $("#rol").val() != '');
							}
						}				
					},
					formacion: {
						required: {
							depends: function(element) {
								return $("#rol").val() == 'PROFESIONAL' || $("#profesional").is(":checked");;
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
					
					tipoCuenta: {
						required: {
							depends: function(element) {
								return $("#rol").val() == '';
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
					tipoCuenta: "Por favor, seleccione un tipo de cuenta.",
				},
				 errorPlacement: function(error, element) {
			            error.appendTo(element.parent());
			        }
			});

		$("#imagenPerfil").change(function() {
			$("#imagenCambia").val("true");
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
		        confirmNewPassword: {
		            required: true,
		            minlength: 5,
		            equalTo: "#newPassword"
		        },
			},
			messages: {	
				newPassword: {
					required: "Por favor introduzca la contraseña.",
					//minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres.")
				},
				confirmNewPassword: {
					required: "Por favor repita la contraseña.",
					//minlength: jQuery.validator.format("Se requieren por lo menos {0} caracteres."),
					equalTo: "Los campos no coinciden."
				},
			},
			 errorPlacement: function(error, element) {
		            error.appendTo(element.parent());
		        }
		});
/*
		$("#changePassword").on("click", function(){
			$("#newDialog").show();
		});
		
		$(".cancelBtn").on("click", function(){
			$(this).parent().parent().parent().hide();
		});
	*/

		$("#newConfirmar").on("click", function() {
			$("#password").val($("#newPassword").val());
			$("#confirmPassword").val($("#confirmNewPassword").val());
			
			if($("#newDialogForm").valid() == true) {
				$('#exampleModal').modal('hide');
				$("#newDialogForm").submit();
			}
			
		});
	
		$('.newPasswordInputs').keydown(function(event) {
	    	if (event.which == 13) {
	    		$("#newConfirmar").click();
	           // event.preventDefault();
	         }
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