<!-- 
<div id="header" class="navbar">
	<div class="home" idRol="${sessionScope.rol}">
		<img src="../images/itinerant.jpg" height="100" />
	</div>

	<div id="header-center">
		<div class="header-pictures">
			<a href="https://www.gva.es/va/inicio/presentacion"><img src="../images/logo_gva.png" height="50"/></a>
		</div>
		<div class="header-pictures">
			<a href="https://avant.gva.es/es/"><img src="../images/Logo AVANT.png" height="50"/></a>
		</div>
		<div class="header-items">
			<form action="buscar" method="get" id="buscarForm">
				<div  id="header-searchbar">
					<input type="search" placeholder="Buscar.." name="keyword" size="50" id="keyword">
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
				<a href="modificar_cuenta">Modificar cuenta</a>
				<hr width="80%" />
				<a href="../logout">Cerrar sesión</a>
			</div>
		</div>
	</div>
</div>
 -->
 <!-- 
  <nav id="" class="navbar navbar-expand-lg bg-white border-bottom border-dark py-0">
 <div class="container-fluid">
 	
 
 	<div class="container">
 		<div class="row">
	 		<div class="col navbar-brand home" idRol="${sessionScope.rol}">
				<img src="../images/itinerant.jpg" height="100" />
			</div>
		 	<div class="col">
			 	<div class="row">
				 	<div class="col navbar-brand" >
						<a href="https://www.gva.es/va/inicio/presentacion">
							<img src="../images/logo_gva.png" height="50"/>
						</a>
					</div>
					<div class="col navbar-brand">
						<a href="https://avant.gva.es/es/">
							<img src="../images/Logo AVANT.png" height="50"/>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
 	
 <div class="collapse navbar-collapse" id="navbarSupportedContent">
	

	<div id="">
		<div class="">
			<form action="buscar" method="get" id="buscarForm" class="navbar-form">
				<div  id="" class="form-group">
					<input type="search" placeholder="Buscar.." name="keyword" size="50" id="keyword">
					
				</div>
				<button type="submit" class="btn btn-default">Buscar</button>
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
				<a href="modificar_cuenta">Modificar cuenta</a>
				<hr width="80%" />
				<a href="../logout">Cerrar sesión</a>
			</div>
		</div>
		 </div>
	</div>
	</div>
</nav>
-->
<!-- 
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom border-dark py-0">
  <div class="col navbar-brand home" idRol="${sessionScope.rol}">
		<img src="../images/itinerant.jpg" height="100" />
	</div>
	
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
  <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a href="https://www.gva.es/va/inicio/presentacion"><img src="../images/logo_gva.png" height="50"/></a>
      </li>
      <li class="nav-item">
        <a href="https://avant.gva.es/es/">
			<img src="../images/Logo AVANT.png" height="50"/>
		</a>
      </li>
	</ul>
	
	<div class="d-flex align-items-center form-inline mx-auto">
	    <form action="buscar" method="get" id="buscarForm" class="input-group flex-nowrap">
			<input type="search" placeholder="Buscar.." name="keyword" size="40" id="keyword">		
			<button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
		</form>
  	</div>
  	<!-- 
    <form action="buscar" method="get" id="buscarForm" class="form-inline mx-auto">
		<div  id="" class="form-group">
			<input type="search" placeholder="Buscar.." name="keyword" size="40" id="keyword">		
			<button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
		</div>
	</form>
	 -->
	 <!--  
	<div class="d-flex align-items-center ml-auto">
    <ul class="navbar-nav ml-auto .d-flex flex-nowrap">
      <li class="nav-item d-flex align-items-center">
        <div id="notifications">
			<button type="button" class="notification-bell" id="notification-bell" name="notification-bell">
				<img alt="" src="../images/Notificaciones.png" height="45" class="notification-bell" id="notification-bell" name="notification-bell">
				<span class="notification-bell-badge" id="notification-bell-badge">${numAlertasNoVistas}</span>
			</button>
			<div id="notifications-tab"></div>
		</div>
      </li>
      <li class="nav-item d-flex align-items-center">
       <div id="chat-button">
			<button type="button" class="chat-icon" id="chat-icon" name="chat-icon" onclick="location.href='chat'">
				<img alt="" src="../images/Chat.png" height="45" class="chat-icon" id="chat-icon" name="chat-icon">
				<span class="chat-icon-badge" id="chat-icon-badge"></span>
			</button>
		</div>
      </li>
      <li class="nav-item dropdown d-flex align-items-center">
			<!--  <button id="iconoUsuario" class="dropdown-toggle drowpdown-button"></button>-->
			<!-- 
			<a class="nav-link" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <img src="../images/Usuario.png" height="50" />
          	</a>
			<ul class="dropdown-menu dropdown-menu-lg-end">
	            <li><a class="dropdown-item" href="modificar_cuenta">Modificar cuenta</a></li>
	            <li><hr class="dropdown-divider"></li>
				<li><a class="dropdown-item" href="../logout">Cerrar sesión</a></li>
         	 </ul>
      </li>
    </ul>
    </div>
    </div>

</nav>
 -->
 <!-- 
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom border-dark py-0">
  <div class="col navbar-brand home" idRol="${sessionScope.rol}">
		<img src="../images/itinerant.jpg" height="100" />
	</div>
	
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
  
	<div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
	<div class="d-flex align-items-center ml-auto navbar-items">
	    <div class="mx-auto d-flex align-items-center">
	      <div class="nav-item">
	        <a href="https://www.gva.es/va/inicio/presentacion"><img src="../images/logo_gva.png" height="50"/></a>
	      </div>
	      <div class="nav-item">
	        <a href="https://avant.gva.es/es/">
	          <img src="../images/Logo AVANT.png" height="50"/>
	        </a>
	      </div>
	    </div>
 	</div>
	
	<div class="d-flex align-items-center mx-auto my-2">
	    <form action="buscar" method="get" id="buscarForm" class="input-group flex-nowrap w-100">
	        <input type="search" placeholder="Buscar..." name="keyword" size="40" id="keyword" class="form-control">
	        <button type="submit" class="btn btn-outline-secondary"><i class="fa-solid fa-magnifying-glass"></i></button>
	    </form>
	</div>
		<div class="d-flex align-items-center navbar-items">
		    <div class="mr-auto">
		      <div id="notifications">
		        <button type="button" class="notification-bell" id="notification-bell" name="notification-bell">
						<img alt="" src="../images/Notificaciones.png" height="45" class="notification-bell" id="notification-bell" name="notification-bell">
						<span class="notification-bell-badge" id="notification-bell-badge">${numAlertasNoVistas}</span>
					</button>
					<div id="notifications-tab"></div>
		      </div>
		    </div>
		    <div class="mx-auto">
		      <div id="chat-button">
		       <button type="button" class="chat-icon" id="chat-icon" name="chat-icon" onclick="location.href='chat'">
						<img alt="" src="../images/Chat.png" height="45" class="chat-icon" id="chat-icon" name="chat-icon">
						<span class="chat-icon-badge" id="chat-icon-badge"></span>
					</button>
		      </div>
		    </div>
		    <div class="ml-auto">
		      <div class="dropdown">
		       <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            <img src="../images/Usuario.png" height="50" />
		          	</a>
					<ul class="dropdown-menu dropdown-menu-lg-end">
			            <li><a class="dropdown-item" href="modificar_cuenta">Modificar cuenta</a></li>
			            <li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="../logout">Cerrar sesión</a></li>
		         	 </ul>
		      </div>
	    	</div>
	  	</div>
	 </div>
</nav>
 -->
 
 <nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom border-dark py-0" >
  <div class="col navbar-brand home" idRol="${sessionScope.rol}">
		<img src="../images/itinerant.jpg" height="100" />
	</div>
	
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
  
	<div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
	<div class="d-flex align-items-center ml-auto navbar-items">
	    <div class="mx-auto d-flex align-items-center">
	      <div class="nav-item">
	        <a href="https://www.gva.es/va/inicio/presentacion"><img src="../images/logo_gva.png" height="50"/></a>
	      </div>
	      <div class="nav-item">
	        <a href="https://avant.gva.es/es/">
	          <img src="../images/Logo AVANT.png" height="50"/>
	        </a>
	      </div>
	    </div>
	    
	    
 	</div>
	
	<div class="d-flex align-items-center mx-auto my-2">
	    <form action="buscar" method="get" id="buscarForm" class="input-group flex-nowrap w-100">
	        <input type="search" placeholder="Buscar..." name="keyword" size="40" id="keyword" class="form-control border-dark-subtle">
	        <button type="submit" class="btn btn-outline-secondary"><i class="fa-solid fa-magnifying-glass"></i></button>
	    </form>
	</div>

	<ul class="navbar-nav me-auto mb-2 mb-lg-0 d-block d-lg-none mb-5">
	
		<c:if test="${sessionScope.rol == 'ADMINISTRADOR'}">
			<li class="nav-menu-item">
         		<a href="lista_profesionales">Profesionales</a>  
	        </li>
	        <li class="nav-menu-item">
         		<a href="lista_certificados" class="active">Certificados</a>
	        </li>
	        <li class="nav-menu-item">
	        	<a href="lista_supervisores">Supervisores</a>  
	        </li>
	        <li class="nav-menu-item">
         		<a href="lista_categorias">Categorias</a> 
	        </li>
	        <li class="nav-menu-item">
	        	<a href="lista_sanciones">Sanciones</a> 
	        </li>
		</c:if>
		<c:if test="${sessionScope.rol == 'PROFESIONAL'}">
			<li class="nav-menu-item">
         		<a href="listar_visitas">Mis visitas</a> 
	        </li>
	        <li class="nav-menu-item">
	        	<a href="listar_citas">Mis citas</a>  
	        </li>
	        <li class="nav-menu-item">
         		<a href="listar_certificados" class="active">Mis certificados</a> 
	        </li>
	        <li class="nav-menu-item">
	        	<a href="listar_jornadas">Mis jornadas</a>  
	        </li>
		</c:if>
		<c:if test="${sessionScope.rol == 'CIUDADANO'}">
			<li class="nav-menu-item">
         		<a href="citas_pendientes">Citas pendientes</a> 
	        </li>
	        <li class="nav-menu-item">
	        	<a href="historial_citas">Historial de citas</a>  
	        </li>
		</c:if>
		<c:if test="${sessionScope.rol == 'SUPERVISOR'}">
			<li class="nav-menu-item">
         		<a href="citas_pendientes">Citas pendientes</a> 
	        </li>
	        <li class="nav-menu-item">
	        	<a href="historial_citas">Historial de citas</a>  
	        </li>
		</c:if>
        <li class="nav-menu-item">
        	<a href="chat" class="d-flex">Chat  <span class="chat-icon-badge" ></span></a> 
        	
        </li>
        <li class="nav-menu-item">
        	<a href="modificar_cuenta">Modificar cuenta</a> 
        </li>
        <li class="nav-menu-item">
        	<a href="../logout">Cerrar sesión</a> 
        </li>
      </ul>

		<div class="d-flex align-items-center navbar-items">
		    <div class="ml-auto">
		      <div id="notifications">
		        <button type="button" class="notification-bell" id="notification-bell" name="notification-bell">
						<img alt="" src="../images/Notificaciones.png" height="45" class="notification-bell" id="notification-bell" name="notification-bell">
						<span class="notification-bell-badge" id="notification-bell-badge">${numAlertasNoVistas}</span>
					</button>
					<div id="notifications-tab"></div>
		      </div>
	    	</div>
	    	 <div class="mx-auto d-none d-lg-block">
		      <div id="chat-button">
		       <button type="button" class="chat-icon" id="chat-icon" name="chat-icon" onclick="location.href='chat'">
						<img alt="" src="../images/Chat.png" height="45" class="chat-icon" id="chat-icon" name="chat-icon">
						<span class="chat-icon-badge" id="chat-icon-badge"></span>
					</button>
		      </div>
		    </div>
		    <div class="ml-auto d-none d-lg-block">
		      <div class="dropdown">
		       <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            <img  class="rounded-circle" src="<c:out value="${sessionScope.fotoPerfil}"></c:out>" height="65" width="65"/>
		          	</a>
					<ul class="dropdown-menu dropdown-menu-lg-end">
			            <li><a class="dropdown-item" href="modificar_cuenta">Modificar cuenta</a></li>
			            <li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="../logout">Cerrar sesión</a></li>
		         	 </ul>
		      </div>
	  	</div>
	 </div>
</nav>
 
<script type="text/javascript">
	$(document).on("click", ".home", function(){
		rol = $(this).attr("idRol");
		if(rol == 'ADMINISTRADOR') {
			window.location = "../admin/";
		} else if (rol == 'PROFESIONAL') {
			window.location = "../profesional/";
		} else if (rol == 'CIUDADANO') {
			window.location = "../inicio/";
		} else if (rol == 'SUPERVISOR') {
			window.location = "../supervisor/";
		} else {
			alert("Error");
		}
	});
</script>
