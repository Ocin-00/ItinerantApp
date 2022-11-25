<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Itinerant - Generalitat Valenciana</title>
	<link href="/ItinerantApp/css/layout.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div id="main-centered">
		<div>
			<h1 align="center">Programa Itinerant</h1>
		</div>
		<div class="main-centered-items" style="align-content: center;">
			<div>
				<img src="images/Itinerant-mapa.jpg" height="500"/>
			</div>
			<div class="main-centered-text">
				<div>
					<h4>¿Qué es Itinerant?</h4>
					<label>Es un programa de servicios, promovido por la DG de la Agenda AVANT y la Federación Valenciana de Municipios y Provincias, con la colaboración de las Mancomunidades y municipios AVANT. El objetivo de este programa, es la investigación y movilización de profesionales itinerantes para prestar servicios básicos necesarios en los diferentes municipios</label>
				</div>	
				<div>
					<h4>¿Qué intenta la aplicación?</h4>
					<label>Poner en contacto a los profesionales dispuestos a prestar sus servicios a domicilio en municipios AVANT con potenciales clientes residentes en dichos muncipios. La aplicación pretende ayudar en la implantación y popularización del programa para así facilitar el acceso a servicios en los municipios en riesgo de despoblación.</label>
				</div>	
				<div>
					<h4>¿Dónde se ha implantado?</h4>
					<label>En el mapa de la izquierda se pueden observar los municipios categorizacos como AVANT, es decir, en riesgo de despoblación. A su vez también son visibles los municpios en los que el programa Itinerant ya se ha implantado el programa. El objetivo es que Itinerant sea una realidad en todos los municipios que lo precisan.</label>
					<label>En cuanto a la aplicación, todavía está en fase de desarrollo y aún no se ha podido poner en marcha.</label>
				</div>	
			</div>	
		</div>
	</div>
	<jsp:directive.include file="footer.jsp"/>
</body>
</html>