<div>
	<h3 style="margin-left: 5px">Resultados de búsqueda: ${keyword}</h3>
	<c:forEach var="visita" items="${listaVisitas}" varStatus="status">
		<div class="search-results">
			<a href="visita?id=${visita.idVisita}">
			<c:set var="imagenRuta" value="${visita.imagenRuta}"></c:set>
				<table>
					<tr>
						<td rowspan="4"><img id="thumbnail" alt="" height="150"
							src="<%String path = request.getContextPath();
		 						 out.println(path + pageContext.getAttribute("imagenRuta").toString().substring(2)); 
								%>" /></td>
						<td>${visita.nombre} | ${visita.precio} euros</td>
					</tr>
					<tr>
						<td>${visita.descripcion}</td>
					</tr>
					<tr>
						<td>${visita.fecha} de <c:set var="hora" value="${visita.horaInicio}"></c:set>
										<%SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										String hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%> a 
										<c:set var="hora" value="${visita.horaFin}"></c:set>
										<%hora = format.format(pageContext.getAttribute("hora"));
										out.println(hora);  
										%></td>
					</tr>
					<tr>
						<td>${visita.localidad.nombre}</td>
					</tr>
				</table>
			</a>
		</div>
	</c:forEach>
	<c:forEach var="profesional" items="${listaProfesionales}" varStatus="status">
	<div class="search-results">
		<a href="xx">
			<table>			
				<tr>
					<td rowspan="4"><img id="thumbnail" alt="" height="150"
						src="${profesional.fotoPerfil}" /></td>
					<td>${profesional.nombre} ${profesional.apellidos}</td>
				</tr>
				<tr>
					<td>${visita.descripcion}</td>
				</tr>
				<tr>
					<td>${visita.formacion}</td>
				</tr>
				<tr>
					<td>${visita.localidad.nombre}</td>
				</tr>				
			</table>
		</a>
	</div>
	</c:forEach>
</div>