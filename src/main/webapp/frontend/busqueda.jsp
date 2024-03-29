<div class="container-fluid m-3 overflow-auto" style="max-height: 100vh;">
	<h3>Resultados de b�squeda: ${keyword}</h3>
	<%
	boolean yaPasadas = false;
	boolean futuras = false;
	%>
	<c:forEach var="visita" items="${listaVisitas}" varStatus="status">
	<c:set var="fecha" value="${visita.horaInicio}"></c:set>
	<%
	Date ahora = new Date();
	Date fecha = (Date) pageContext.getAttribute("fecha");
	if(!yaPasadas && ahora.after(fecha)) {
		%><h4 class="m-3">Visitas pasadas:</h4> <%
		yaPasadas = true;
	} else if(!futuras && ahora.before(fecha)) {
		%><h4 class="m-3">Visitas futuras:</h4> <%
		futuras = true;
	}
	%>
		<div class="search-results">
			<a href="visita?id=${visita.idVisita}">
			<c:set var="imagenRuta" value="${visita.imagenRuta}"></c:set>	
				<table>
					<tr>
						<td rowspan="4"><img alt="No hay imagen" class="img-thumbnail img-search"
							src="<%String path = request.getContextPath();
								   Object imagenRuta = pageContext.getAttribute("imagenRuta");
								   if(imagenRuta != null) {
									   out.println(path + imagenRuta.toString().substring(2)); 
								   }
								%>" style="min-width: 100px"/></td>
						<td>${visita.nombre} | ${visita.precio} &euro;</td>
					</tr>
					<tr class="d-none d-lg-block">
						<td>${visita.descripcion}</td>
					</tr>
					<tr>
						<td><fmt:formatDate value="${visita.fecha}" pattern="dd-MM-yyyy" /> de <c:set var="hora" value="${visita.horaInicio}"></c:set>
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
		<a href="ver_profesional?id=${profesional.login}">
		<c:set var="imagenProfesional" value="${profesional.imagenRuta}"></c:set>
			<table>			
				<tr>
					<td rowspan="4"><img alt="No hay imagen" class="img-thumbnail img-search rounded-circle"
						src="<%String path = request.getContextPath();
								Object imagenProf = pageContext.getAttribute("imagenProfesional");
								   if(imagenProf != null) {
									   out.println(path + imagenProf.toString().substring(2)); 
								   }
								%>" /></td>
					<td>${profesional.nombre} ${profesional.apellidos}</td>
				</tr>
				<tr class="d-none d-lg-block">
					<td>${profesional.descripcion}</td>
				</tr>		
			</table>
		</a>
	</div>
	</c:forEach>
	
	<c:forEach var="usuario" items="${listaUsuarios}" varStatus="status">
	<div class="search-results">
		<a href="ver_usuario?id=${usuario.login}">
		<c:set var="imagenUsuario" value="${usuario.imagenRuta}"></c:set>
			<table>			
				<tr>
					<td rowspan="4"><img alt="No hay imagen" class="img-thumbnail img-search rounded-circle"
						src="<%String path = request.getContextPath();
								Object imagenProf = pageContext.getAttribute("imagenUsuario");
								   if(imagenProf != null) {
									   out.println(path + imagenProf.toString().substring(2)); 
								   }
								%>" /></td>
					<td>${usuario.nombre} ${usuario.apellidos}</td>
				</tr>
				<tr class="d-none d-lg-block">
					<td>${usuario.rol}</td>
				</tr>		
			</table>
		</a>
	</div>
	</c:forEach>
</div>