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
	         	<a href="estadisticas_generales">Generales</a> 
		    </li>
		    <li class="nav-menu-item">
		      	<a href="estadisticas_mancomunales">Mancomunales</a>  
		    </li>
		    <li class="nav-menu-item">
		       	<a href="estadisticas_municipales">Municipales</a>  
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
