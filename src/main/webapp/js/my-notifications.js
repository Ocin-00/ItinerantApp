/**
 * 
 */
$(document).ready(function(){
	
		checkNotifications(true); //Lo ejecuta nada más cargar la página
		checkMessageNumber();
		setInterval(function() { checkNotifications(false); }, 5000); //Vuelve a comprobarlo cada 5 segs
		setInterval(function() { checkMessageNumber(); }, 1000);
		
		function checkNotifications(firstLoad) {
			$.ajax({
				type:"GET",
		        url:'../get_notifications',	
		        success: function(result){
					let listaAlertas = JSON.parse(result);
					let active = $( "#notifications-tab" ).accordion( "option", "active" );
					let alertasNoVistas = 0;
					let htmlText = "";
					let hayNuevas = false;
					for(let alerta of listaAlertas) {
						if(alerta.visto == false) {
							alertasNoVistas++;
							htmlText += "<h3 class='notification-title' idAlerta=" + alerta.idAlerta 
							+ "><span class='notification-title-unread' id='notification-title-unread"+alerta.idAlerta+"'>!</span>" 
							+ alerta.titulo + "</h3><div><p>"+ alerta.cuerpo + "</p>"
							+ "<button class='delete-button' idAlerta='"+alerta.idAlerta+"'>Borrar</button></div>";
						} else {
							htmlText += "<h3 class='notification-title' idAlerta=" + alerta.idAlerta + ">" + alerta.titulo + "</h3><div><p>"+ alerta.cuerpo + "</p>"
							+ "<button class='delete-button' idAlerta="+alerta.idAlerta+">Borrar</button></div>";							
						}
						hayNuevas = hayNuevas || alerta.nuevo;
					}
					htmlText += "<hr><button id='delete-all-button'>Limpiar todo</button>";
					if(hayNuevas || firstLoad) {
						$("#notifications-tab").html(htmlText);
						$("#notifications-tab").accordion("refresh");
						$("#notifications-tab").accordion( "option", "active", active );
					}
					if(alertasNoVistas == 0) {
						$("#notification-bell-badge").hide();
					} else {
						$("#notification-bell-badge").show();
						$("#notification-bell-badge").text(alertasNoVistas);
					}
		  		},
		  		error: function(result){
		            alert("error con los datos");
		        }
			});
		}
		
		function checkMessageNumber() {
			$.ajax({
				type:"GET",
		        url:'../get_number_messages',	
		        success: function(result){
					let aux = JSON.parse(result);
					
					mensajesNoVistos = aux[0].numeroMensajes
					if(mensajesNoVistos == 0) {
						$(".chat-icon-badge").hide();
					} else {
						$(".chat-icon-badge").show();
						$(".chat-icon-badge").text(mensajesNoVistos);
					}
		  		},
		  		error: function(result){
		            alert("error con los datos");
		        }
			});
		}
		
		$("#notifications-tab").accordion({
			  active: false,
			  animate: 200,
			  collapsible: true,
			  event: "click",
			  header: "h3",
			  heightStyle: "content",
			  icons: { "header": "ui-icon-plus", "activeHeader": "ui-icon-minus" }
		});

		$('#notification-bell').click(function(){
			$("#notifications-tab").toggle();
		});
});
	$(document).on("click", ".notification-title", function(){
		id = $(this).attr("idAlerta");
		$.ajax({
			type:"POST",
			data:  { idAlerta:id },
			url:'../marcar_visto',	
			success: function(result){
				let listaAlertas = JSON.parse(result);
				let alertasNoVistas = 0;
				for(let alerta of listaAlertas) {
					if(alerta.visto == false) {
						alertasNoVistas++;
					}
				}
				if(alertasNoVistas == 0) {
					$("#notification-bell-badge").hide();
				} else {
					$("#notification-bell-badge").show();
					$("#notification-bell-badge").text(alertasNoVistas);
				}
				$("#notification-title-unread"+id).hide();
			},
			error: function(result){
				alert("error con los datos");
			}
		});
	});
	
	$(document).on("click", "#delete-all-button", function(){
		$.ajax({
			type:"GET",
			url:'../limpiar_todo',	
			success: function(result){
				let listaAlertas = JSON.parse(result);
				
				let htmlText = "<hr><button class='delete-all-button'>Limpiar todo</button>";
				
				$("#notifications-tab").html(htmlText);
				$("#notifications-tab").accordion("refresh");
				$("#notifications-tab").accordion( "option", "active", active );
				
				$("#notification-bell-badge").hide();
			},
			error: function(result){
				alert("error con los datos");
			}
		});
	});
	
	$(document).on("click", ".delete-button", function(){
		id = $(this).attr("idAlerta");
		$.ajax({
			type:"POST",
			data:  { idAlerta:id },
			url:'../borrar_alerta',	
			success: function(result){
				let listaAlertas = JSON.parse(result);
				let alertasNoVistas = 0;
				let htmlText = "";
				for(let alerta of listaAlertas) {
					if(alerta.visto == false) {
						alertasNoVistas++;
						htmlText += "<h3 class='notification-title' idAlerta=" + alerta.idAlerta 
							+ "><span class='notification-title-unread' id='notification-title-unread"+alerta.idAlerta+"'>!</span>" 
							+ alerta.titulo + "</h3><div><p>"+ alerta.cuerpo + "</p>"
							+ "<button class='delete-button' idAlerta='"+alerta.idAlerta+"'>Borrar</button></div>";

					} else {
						htmlText += "<h3 class='notification-title' idAlerta=" + alerta.idAlerta + ">" + alerta.titulo + "</h3><div><p>"+ alerta.cuerpo + "</p>"
							+ "<button class='delete-button' idAlerta="+alerta.idAlerta+">Borrar</button></div>";									
					}
				}
				htmlText += "<hr><button id='delete-all-button'>Limpiar todo</button>";
				$("#notifications-tab").html(htmlText);
				$("#notifications-tab").accordion("refresh");
				$("#notifications-tab").accordion( "option", "active", active );
				
				if(alertasNoVistas == 0) {
					$("#notification-bell-badge").hide();
				} else {
					$("#notification-bell-badge").show();
					$("#notification-bell-badge").text(alertasNoVistas);
				}
			},
			error: function(result){
				alert("error con los datos");
			}
		});
	});