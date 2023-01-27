/**
 * 
 */
 
// window.onload = init;
 //import {useGeographic} from 'ol/proj.js';
 
 $(document).ready(function() {	
	init();
});
 
 function init() {
	//useGeographic();
	const map = new ol.Map({
		view: new ol.View({
			center: [-41930.07270846405, 4789890.48683087],
			//center: [39.5662412, -1.2015959],
			zoom: 7.5
		}),
		layers: [
			new ol.layer.Tile({
				source: new ol.source.OSM()
			})
		],
		target: 'js-map'
	})
	/*
	map.on("click", function(e){
		console.log(e.coordinate);
	});*/
}