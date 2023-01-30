
 
 $(document).ready(function() {	

	var apikey='5b3ce3597851110001cf62488e46d28f49534f3094ceb181a7bfe9cc';
	var map = L.map('map').setView([39.469809, -0.376397], 7.5);
	
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
        maxZoom: 15
	}).addTo(map);
	
	var geocoder = L.Control.Geocoder.nominatim();
	var fecha = $("#fecha").val();
	$.ajax({
			type:"POST",
			data:  { fecha:fecha },
			url:'get_localidades',	
			success: function(result){
				let localidades = JSON.parse(result);
				var waypoints = [];
				//var javaWaypoints = [];
				
				for (var i = 0; i < localidades.length; i++) {
					var localidad = localidades[i];
					let direccion = localidad.nombre + ', ' + localidad.comarca + ', ' + localidad.provincia + ', Comunitat Valenciana, ' + localidad.codPostal + ', España';
				    geocoder.geocode(direccion, function(results) {
				        var coords = results[0].center;
				        waypoints.push(L.latLng(coords.lat, coords.lng));
				        //javaWaypoints.push(coords.lat + ',' + coords.lng)
				        
				        if (waypoints.length === localidades.length) {
								
				            var control = L.Routing.control({
					            waypoints: waypoints,
					            routeWhileDragging: false,
					            collapsible: true,
								show:false,
								addwaypoints: false,
								geocoder: geocoder,
								draggableWaypoints: false,
								language: 'es'
					        }).addTo(map);
					       
					       //Usar esto si se quiere usar el servidor de OpenRouteService, pero a día de hoy no soporta búsquedas en pueblos pequeños
					       /*
					       $.ajax({
							    type: "POST",
							    url: "get_ruta",
							    data: {
							        waypoints: javaWaypoints.join(';')
							    },
							    success: function(response) {
							        // The "response" variable contains the server response
							        // You can parse the response data and use it as needed
							        console.log(response);
							        var route = JSON.parse(response);
							        alert(response);
							        // For example, you could display the route on a map using leaflet-routing-machine
							        L.Routing.control({
							            waypoints: waypoints,
							            routeWhileDragging: false,
							            collapsible: true,
										show:false,
										addwaypoints: false,
										geocoder: geocoder,
										draggableWaypoints: false,
										language: 'es'
							        }).addTo(map).route(route);
							    }
							});
							*/
					    }
					});
				}
			},
			error: function(result){
				alert("error con los datos");
			}
		});
});


 