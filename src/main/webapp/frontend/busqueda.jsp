<div>
	<h3 style="margin-left: 5px">Resultados de búsqueda: ${keyword}</h3>
	<c:forEach var="visita" items="${listaVisitas}" varStatus="status">
		<div class="search-results">
			<a href="visita?id=${visita.idVisita}">
				<table>
					<tr>
						<td rowspan="4"><img id="thumbnail" alt="" height="150"
							src="${visita.imagenRuta}" /></td>
						<td>${visita.nombre}| ${visita.precio} euros</td>
					</tr>
					<tr>
						<td>${visita.descripcion}</td>
					</tr>
					<tr>
						<td>${visita.fecha} de ${visita.horaInicio.hours}:${visita.horaInicio.minutes} a ${visita.horaFin.hours}:${visita.horaFin.minutes}</td>
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