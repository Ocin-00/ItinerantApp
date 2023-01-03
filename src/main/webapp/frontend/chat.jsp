<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Itinerant - Resultado de búsqueda</title>
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
	
	<div id = "main-chat">
		
		<div id="discussions">
			<div class="header-discussions">
				<div class="header-discussions-title">
					<h3>Mis chats</h3>
				</div>
				<div id="home-button" idRol="${sessionScope.rol}">
					<button type="button" class="home-icon" id="home-icon" name="home-icon">
						<img alt="" src="../images/home.png" height="45" class="home-icon" id="home-icon" name="home-icon">
					</button>
				</div>
			</div>
		</div>
		<div class="chat">
		    <c:if test="${recipient != null}">
		    	<div class="header-chat">	    	
		    		<img class="photo" src="${recipient.fotoPerfil}" alt="foto">
		    		 
		    		<h2 class="name">${recipient.nombre} ${recipient.apellidos}</h2>
		    	</div>
				<hr>
			    <div id="messages-chat"></div>
			    <div class="footer-chat">
			    	 <input type="text" class="write-message">
			    	 <div class="send-button">
			    	 	<button type="button" class="send-icon">
			   		 		<img alt="" src="../images/send_icon.png" height="45" class="send-icon" id="send-icon" name="send-icon">
			   		 	</button>
			    	 </div>
			    </div>
		    </c:if>
		</div>
	</div>
	<jsp:directive.include file="/frontend/footer.jsp"/>
</body>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	/*
    var socket = new WebSocket("ws://localhost:8080/ItinerantApp/chat");
    var login = $("#recipient-select").val();

    socket.onmessage = function(event) {
        var data = event.data.split(":");
        var from = data[0];
        var msg = data[1];
        if (from === "all" || from === login) {
            $("#message-display").append("<p>" + msg + "</p>");
        }
    }

    $("#message-form").submit(function(event) {
        event.preventDefault();
        var recipient = $("#recipient-select").val();
        var message = $("#message-input").val();
        socket.send(recipient + ":" + message);
        $("#message-input").val("");
    });*/
    getUserChats();
    setInterval(getNewUserChats, 1000);

   	getMensajesChat();
    setInterval(getNewMensajesChat, 1000);

    $(".send-button").click(sendMensaje);

    $('.write-message').keydown(function(event) {
    	if (event.which == 13) {
    		sendMensaje();
            event.preventDefault();
         }
    });
    
	function sendMensaje() {
	  var mensaje = $(".write-message").val();
  	  var idChat = '${idChat}';
  	  var usuario = '${recipient.login}'
  	  $.ajax({
  	    type: "POST",
  	    url: "../send_mensaje",
  	    data: { mensaje: mensaje, id:idChat, usuario:usuario  },
  	    success: function(result){
				getMensajesChat();
				$(".write-message").val("");
      	},
      	error: function(result){
	            alert("error con los datos");
	        }
  	  });
	}
	
    function getMensajesChat() {
        var idChat = '${idChat}';
    	$.ajax({
			type:"POST",
	        url:'../get_mensajes',	
	        data: { id:idChat },
	        success: function(result){
	        	let listaMensajes = JSON.parse(result);
	        	let htmlText = changeMessageDisplay(listaMensajes);
		        $("#messages-chat").html(htmlText);
		        $("#messages-chat").animate({ scrollTop: 20000000 }, "slow");
	  		},
	  		error: function(result){
	            alert("error con los datos");
	        }
		});
    }
    
    function getNewMensajesChat() {
        var idChat = '${idChat}';
        var login = '${sessionScope.userLogin}';
    	$.ajax({
			type:"POST",
	        url:'../get_mensajes',	
	        data: { id:idChat },
	        success: function(result){
	        	let listaMensajes = JSON.parse(result);
	        	let hayNuevos = false;
	        	for(let mensaje of listaMensajes) {
	            	if(mensaje.recipient == login && mensaje.status != 'READ') {
	            		hayNuevos = true;
	    	        }
	            }
		        if(hayNuevos) {
		        	let htmlText = changeMessageDisplay(listaMensajes);
		        	$("#messages-chat").html(htmlText);
		        	$("#messages-chat").animate({ scrollTop: 20000000 }, "slow");
			    }
	  		},
	  		error: function(result){
	            alert("error con los datos");
	        }
		});
    }

	function changeMessageDisplay(listaMensajes) {
		var login = '${sessionScope.userLogin}';
		let htmlText = "";
    	for(let mensaje of listaMensajes) {
        	if(mensaje.sender == login) {
        		htmlText += "<p class='sent-message'>" + mensaje.cuerpo + "</p>";
	        } else if(mensaje.recipient == login) {
	        	htmlText += "<p class='received-message'>" + mensaje.cuerpo + "</p>";
		    }
        }
        return htmlText;
	}
    
    function getUserChats() {
    	$.ajax({
			type:"GET",
	        url:'../get_chats',	
	        success: function(result){
	        	let listaChats = JSON.parse(result);
	        	let htmlText = changeUsersDisplay(listaChats);
	        	$("#discussions").html(htmlText);
	        	for(let chat of listaChats) {
	        		if((chat.mensajes_pendientes < 1)) {
	        			$("#mensajes_pendientes"+chat.idChat).hide();
	        			$("#mensajes_pendientes"+chat.idChat).text("");
		        	} else {
			        	$("#mensajes_pendientes"+chat.idChat).show();
		        		//$("#mensajes_pendientes"+chat.idChat).text(chat.mensajes_pendientes+" mensajes nuevos.");
				    }
	    	    }
	  		},
	  		error: function(result){
	            alert("error con los datos");
	        }
		});
    }
    
    function getNewUserChats() {
    	var login = '${sessionScope.userLogin}';
    	$.ajax({
			type:"GET",
	        url:'../get_chats',	
	        success: function(result){
	        	let listaChats = JSON.parse(result);
	        	var hayNuevos = false;
	        	for(let chat of listaChats) {
		        	//alert(chat.sender_id != login);
		        	//alert(chat.nuevo == true);
		        	//alert(chat.mensajes_chat > 0);
	        		if(chat.sender_id != login && chat.nuevo == true && chat.mensajes_chat > 0) {
		        		hayNuevos = true;
		        	}
	        		if((chat.mensajes_pendientes < 1)) {
	        			//alert("hola")
	        			$("#mensajes_pendientes"+chat.idChat).hide();
	        			$("#mensajes_pendientes"+chat.idChat).text("");
		        	} else {
			        	$("#mensajes_pendientes"+chat.idChat).show();
		        		$("#mensajes_pendientes"+chat.idChat).text(chat.mensajes_pendientes+" mensajes nuevos.");
				    }
	    	    }
	        	if(hayNuevos) {
	        		let htmlText = changeUsersDisplay(listaChats);
	        		$("#discussions").html(htmlText);
			    }
	  		},
	  		error: function(result){
	            alert("error con los datos");
	        }
		});
    }

    function changeUsersDisplay(listaChats) {
    	var idChat = '${idChat}';
    	let htmlText = $("#discussions").html();
    	for(let chat of listaChats) {
        	if(idChat == chat.idChat) {
        		htmlText += "<div class='discussion-message-active' idChat='"+chat.idChat+"' usuario='"+chat.login +"'>"
    	        + "<img class='photo' src='"+chat.foto_perfil+"' alt='foto'>"
    	        + "<div class='contact-desc'>"
    			+ "<p class = 'name'>"+chat.nombre + " "+ chat.apellidos +"</p>"
    			+ "<span class='mensajes_pendientes' id='mensajes_pendientes"+chat.idChat+"'></span></div></div>"
            } else {
            	htmlText += "<div class='discussion-message' idChat='"+chat.idChat+"' usuario='"+chat.login +"'>"
    	        + "<div><img class='photo' src='"+chat.foto_perfil+"' alt='foto'></div>"
    	        + "<div class='contact-desc'>"
    			+ "<p class = 'name'>"+chat.nombre + " "+ chat.apellidos +"</p>"
    			+ "<span class='mensajes_pendientes' id='mensajes_pendientes"+chat.idChat+"'></span></div></div>"
            }
	    }
	    return htmlText;
     }
});

$(document).on("click", ".discussion-message", function(){
	id = $(this).attr("idChat");
	login = $(this).attr("usuario");
	window.location = "chat?id="+id+"&usuario="+login;
});

$(document).on("click", "#home-button", function(){
	rol = $(this).attr("idRol");
	if(rol == 'ADMINISTRADOR') {
		window.location = "../admin/";
	} else if (rol == 'PROFESIONAL') {
		window.location = "../profesional/";
	} else if (rol == 'CIUDADANO') {
		window.location = "../inicio/";
	} else if (rol == 'SUPERVISOR') {
		window.location = "../supervisor/";
	} else {
		alert("Error");
	}
});
</script>
</html>