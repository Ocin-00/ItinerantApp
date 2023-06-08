
 
 $(document).ready(function() {	

	var apikey='5b3ce3597851110001cf62488e46d28f49534f3094ceb181a7bfe9cc';
	var map = L.map('map', {
		fullscreenControl: true,
	  	fullscreenControlOptions: {
	    	position: 'topleft'
	  	}
	}).setView([39.469809, -0.376397], 7.5);
	
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
        //maxZoom: 15
	}).addTo(map);
	
	var geocoder = L.Control.Geocoder.nominatim();
	var fecha = $("#fecha").val();
	// Esto es para usar el geocoder de nominatim
	$.ajax({
			type:"POST",
			data:  { fecha:fecha },
			url:'get_localidades',	
			success: function(result){
				let localidades = JSON.parse(result);
				var waypoints = [];
				let orderedWaypoints = Array(localidades.length);
				
				for (let i = 0; i < localidades.length; i++) {
					(function(index) { //asegura que los orderedWaypoints y las localidades estén en el mismo orden
						let localidad = localidades[index];
						let direccion = localidad.nombre + ", VC, España";
					    geocoder.geocode(direccion, function(results) {
					        let coords = results[0].center;
					        waypoints.push(L.latLng(coords.lat, coords.lng));
					        orderedWaypoints[index] = coords.lng + ',' + coords.lat;
					        let marker = new L.Marker([coords.lat, coords.lng]).addTo(map);
					        
					        if (waypoints.length === localidades.length) {
						
								/*	//Esto es para usar leaflet-routing-machine con algún servidor como Mapbox, GraphHopper, etc
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
						        */
						       //Esto es para usar el servidor de OpenRouteService					       
						       $.ajax({
								    type: "POST",
								    url: "get_ruta",
								    data: {
								        waypoints: orderedWaypoints.join(';')
								    },
								    success: function(response) {
								        console.log(response); 
								        
								        var polyline = L.polyline(L.GeoJSON.coordsToLatLngs(response.features[0].geometry.coordinates,0,false), {color: 'red'}).addTo(map);
										map.fitBounds(polyline.getBounds());
								    }
								});
								
						    }
						});
					})(i);
				}
			},
			error: function(result){
				alert("error con los datos");
			}
		});
	
	/* //Esto es para usar el geocoder de OpenRouteService
	$.ajax({
			type:"POST",
			data:  { fecha:fecha },
			url:'get_localidades',	
			success: function(result){
				let localidades = JSON.parse(result);
				var javaWaypoints = [];
				
				for (var i = 0; i < localidades.length; i++) {
					var coords = localidades[i];
				    javaWaypoints.push(coords.lng + ',' + coords.lat);
				    var marker = new L.Marker([coords.lat, coords.lng]).addTo(map);
				    
					if (javaWaypoints.length === localidades.length) {
						$.ajax({
							type: "POST",
							url: "get_ruta",
							data: {
								waypoints: javaWaypoints.join(';')
							},
							success: function(response) {
								console.log(response); 
						      
								var polyline = L.polyline(L.GeoJSON.coordsToLatLngs(response.features[0].geometry.coordinates,0,false), {color: 'red'}).addTo(map);
								map.fitBounds(polyline.getBounds());
							}
						});
					 }
				}
			},
			error: function(result){
				alert("error con los datos");
			}
		});
		*/
});


 