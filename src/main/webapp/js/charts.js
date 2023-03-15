/**
 * 
 */
 
 $(document).ready(function(){
		var tieneAcceso = $("#tieneAcceso").val();
		if(tieneAcceso == 'true') {
			var ambito = $("#ambito").val();
			var lugar = $("#lugar").val();
			var tipo = $('input[name="tipo"]:checked').val();
			initCharts(ambito, lugar, tipo);
		}
		
		$('input[name="tipo"]').change(function(){
			var tipo = $( 'input[name="tipo"]:checked' ).val();
			var ambito = $("#ambito").val();
			var lugar = $("#lugar").val();
			initCharts(ambito, lugar, tipo);
		});
	});
	
function initCharts(ambito, lugar, tipo) {
	$.ajax({
				type:"POST",
				data:  { ambito:ambito, id:lugar, tipo:tipo},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				url:'get_datos',	
				success: function(result){
					let datos = JSON.parse(result);
					let datosGenero = datos[0];
					let datosEstadoCivil = datos[1];
					let datosRoles = datos[2];
					let datosEdades = datos[3];
					let datosLocalidades = decodeLabel(datos[4].sort(compareDataPointYAscend));
					let datosOferta = decodeLabel(datos[5].sort(compareDataPointYAscend));
					let datosDemanda = decodeLabel(datos[6].sort(compareDataPointYAscend));
					let datosCitas = decodeLabel(datos[7].sort(compareDataPointYAscend));
					//alert(decodeURIComponent(encodeURI("hací")));
					//alert(decodeURIComponent(datosOferta[1].label));
					var optionsGender = {
							title: {
								text: "Género"
							},
							subtitles: [{
								text: ""
							}],
							/*legend:{
								horizontalAlign: "right",
								verticalAlign: "center"
							},*/
							animationEnabled: true,
							data: [{
								type: "pie",
								//startAngle: 40,
								toolTipContent: "<b>{label}</b>: {y} (#percent%)",
								showInLegend: "true",
								legendText: "{label}",
								indexLabelFontSize: 16,
								indexLabel: "{label}",
								indexLabelPlacement: "inside",
								dataPoints: datosGenero
							}]
					};
					$("#chartGender").CanvasJSChart(optionsGender);
	
					var optionsEstadoCivil = {
							title: {
								text: "Estado civil"
							},
							subtitles: [{
								text: ""
							}],
							/*legend:{
								horizontalAlign: "right",
								verticalAlign: "center"
							},*/
							animationEnabled: true,
							data: [{
								type: "pie",
								//startAngle: 40,
								toolTipContent: "<b>{label}</b>: {y} (#percent%)",
								showInLegend: "true",
								legendText: "{label}",
								indexLabelFontSize: 16,
								indexLabel: "{label}",
								indexLabelPlacement: "inside",
								dataPoints: datosEstadoCivil
							}]
					};
					$("#chartEstadoCivil").CanvasJSChart(optionsEstadoCivil);
	
					var optionsRoles = {
							title: {
								text: "Rol"
							},
							subtitles: [{
								text: ""
							}],
							/*legend:{
								horizontalAlign: "right",
								verticalAlign: "center"
							},*/
							animationEnabled: true,
							data: [{
								type: "pie",
								//startAngle: 40,
								toolTipContent: "<b>{label}</b>: {y} (#percent%)",
								showInLegend: "true",
								legendText: "{label}",
								indexLabelFontSize: 16,
								indexLabel: "{label}",
								indexLabelPlacement: "inside",
								dataPoints: datosRoles
							}]
					};
					$("#chartRoles").CanvasJSChart(optionsRoles);
	
					var optionsEdad = {
							title: {
								text: "Edades"              
							},
							animationEnabled: true,
							data: [              
							{
								// Change type to "doughnut", "line", "splineArea", etc.
								type: "column",
								dataPoints: datosEdades
							}
							]
						};
	
						$("#chartEdad").CanvasJSChart(optionsEdad);
						
						var optionsLocalidad = {
							title: {
								text: "Localización"              
							},
							animationEnabled: true,
							data: [              
							{
								/*indexLabelFontSize: 26,
								indexLabel: "{label}",
								toolTipContent: "<span>{label}:</span> <span><strong>{y}</strong></span>",
								indexLabelPlacement: "inside",
								indexLabelFontColor: "white",
								indexLabelFontWeight: 600,
								indexLabelFontFamily: "Verdana",*/
								type: "bar",
								dataPoints: datosLocalidades
							}
							]
						};
	
						$("#chartLocalidad").CanvasJSChart(optionsLocalidad);
						
						var optionsOferta = {
							title: {
								text: "Servicios más ofertados"              
							},
							animationEnabled: true,
							data: [              
							{
								indexLabelFontSize: 26,
								indexLabel: "{label}",
								toolTipContent: "<span>{label}:</span> <span><strong>{y}</strong></span>",
								indexLabelPlacement: "inside",
								indexLabelFontColor: "white",
								indexLabelFontWeight: 600,
								indexLabelFontFamily: "Verdana",
								color: "#62C9C3",
								type: "bar",
								dataPoints: datosOferta
							}
							]
						};
	
						$("#chartOferta").CanvasJSChart(optionsOferta);
						
						var optionsDemanda = {
							title: {
								text: "Servicios más demandados"              
							},
							animationEnabled: true,
							data: [              
							{
								indexLabelFontSize: 26,
								indexLabel: "{label}",
								toolTipContent: "<span>{label}:</span> <span><strong>{y}</strong></span>",
								indexLabelPlacement: "inside",
								indexLabelFontColor: "white",
								indexLabelFontWeight: 600,
								indexLabelFontFamily: "Verdana",
								color: "#62C9C3",
								type: "bar",
								dataPoints: datosDemanda
							}
							]
						};
	
						$("#chartDemanda").CanvasJSChart(optionsDemanda);
						
						var optionsCitas = {
							title: {
								text: "Municipios con más citas"              
							},
							animationEnabled: true,
							data: [              
							{
								type: "bar",
								dataPoints: datosCitas
							}
							]
						};
	
						$("#chartCitas").CanvasJSChart(optionsCitas);
						
				},
				error: function(result){
					alert("error con los datos");
				}
			});
}

function compareDataPointYAscend(dataPoint1, dataPoint2) {
		return dataPoint1.y - dataPoint2.y;
}

function decodeLabel(dataPoints) {
	for(let i = 0; i < dataPoints.length; i++) {
		let str = decodeURI(dataPoints[i].label);
		dataPoints[i].label = str;
	}
	return dataPoints;
}