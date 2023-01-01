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
				<span class="notification-bell-badge" id="notification-bell-badge">${numAlertasNoVistas}</span>
			</button>
			<div id="notifications-tab"></div>
		</div>
		<div id="chat-button">
			<button type="button" class="chat-icon" id="chat-icon" name="chat-icon" onclick="location.href='chat'">
				<img alt="" src="../images/Chat.png" height="45" class="chat-icon" id="chat-icon" name="chat-icon">
				<span class="chat-icon-badge" id="chat-icon-badge"></span>
			</button>
		</div>
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
