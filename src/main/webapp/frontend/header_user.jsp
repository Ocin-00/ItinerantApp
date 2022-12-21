<div id="header">
	<div>
		<img src="../images/itinerant.jpg" height="100" />
		
		
	</div>

	<div id="header-center">
		<div class="header-pictures">
			<img src="../images/logo_gva.png" height="50"/>
		</div>
		<div class="header-pictures">
			<img src="../images/Logo AVANT.png" height="50"/>
		</div>
		<div class="header-items">
			<form action="buscar" method="get">
				<div  id="header-searchbar">
					<input type="search" placeholder="Buscar.." name="keyword" size="50">
					<button type="submit">Buscar</button>
				</div>
			</form>
		</div>
	</div>

	
	<div>
		<div id="notifications">
			<button type="button" class="notification-bell" id="notification-bell" name="notification-bell">
				<img alt="" src="../images/Notificaciones.png" height="45" class="notification-bell" id="notification-bell" name="notification-bell">
				<!--
				<input type="image" src="../images/Notificaciones.png" height="45" class="notification-bell" id="notification-bell" name="notification-bell"/> 
				
				<span class="material-icons">notifications</span>
				
				<c:if test="${numAlertasNoVistas > 0}">
					<span class="notification-bell-badge" id="notification-bell-badge">${numAlertasNoVistas}</span>
				</c:if>
				-->
				<span class="notification-bell-badge" id="notification-bell-badge">${numAlertasNoVistas}</span>
			</button>
			<div id="notifications-tab">
			<!-- 
				<c:forEach var="alerta" items="${misAlertas}" varStatus="status">
						<h3 class="notification-title" idAlerta="${alerta.idAlerta}">${alerta.titulo}</h3>
	  					<div>
							<p>${alerta.cuerpo}</p>
							<button class='delete-button' idAlerta='${alerta.idAlerta}'>Borrar</button>
						</div>
				</c:forEach>
				<hr>
				<a href="../limpiar_todo">Limpiar todo</a>
			 -->
			</div>
		</div>
		<img src="../images/Chat.png" height="45" />
		<div class="dropdown">
			<button id="iconoUsuario" class="dropdown-button"></button>
			<label for="iconoUsuario"> 
				<img src="../images/Usuario.png" height="50" />
			</label>
			<div class="dropdown-content">
				<a href="#">Configuración</a>
				<hr width="80%" />
				<a href="../logout">Cerrar sesión</a>
			</div>
		</div>
		<a href="../logout"></a>
	</div>
</div>
