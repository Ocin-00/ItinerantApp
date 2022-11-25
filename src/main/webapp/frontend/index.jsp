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
					<h4>�Qu� es Itinerant?</h4>
					<label>Es un programa de servicios, promovido por la DG de la Agenda AVANT y la Federaci�n Valenciana de Municipios y Provincias, con la colaboraci�n de las Mancomunidades y municipios AVANT. El objetivo de este programa, es la investigaci�n y movilizaci�n de profesionales itinerantes para prestar servicios b�sicos necesarios en los diferentes municipios</label>
				</div>	
				<div>
					<h4>�Qu� intenta la aplicaci�n?</h4>
					<label>Poner en contacto a los profesionales dispuestos a prestar sus servicios a domicilio en municipios AVANT con potenciales clientes residentes en dichos muncipios. La aplicaci�n pretende ayudar en la implantaci�n y popularizaci�n del programa para as� facilitar el acceso a servicios en los municipios en riesgo de despoblaci�n.</label>
				</div>	
				<div>
					<h4>�D�nde se ha implantado?</h4>
					<label>En el mapa de la izquierda se pueden observar los municipios categorizacos como AVANT, es decir, en riesgo de despoblaci�n. A su vez tambi�n son visibles los municpios en los que el programa Itinerant ya se ha implantado el programa. El objetivo es que Itinerant sea una realidad en todos los municipios que lo precisan.</label>
					<label>En cuanto a la aplicaci�n, todav�a est� en fase de desarrollo y a�n no se ha podido poner en marcha.</label>
				</div>	
			</div>	
		</div>
	</div>
	<jsp:directive.include file="footer.jsp"/>
</body>
</html>