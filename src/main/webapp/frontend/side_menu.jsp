<!-- 

<div id="side-menu" class="d-none d-lg-block">
	<h3>Menú de trabajo</h3>
	<a href="listar_visitas">Mis visitas</a> 
	<a href="listar_citas">Mis citas</a> 
	<a href="listar_certificados" class="active">Mis certificados</a> 
	<a href="listar_jornadas">Mis jornadas</a>
</div>

 -->
 
<nav id="side-menu" class="bg-white d-none d-lg-block">
        <div class="side-menu-title m-2">
            <c:if test="${sessionScope.rol == 'ADMINISTRADOR'}">
				 <h3>Administración</h3>
			</c:if>
			<c:if test="${sessionScope.rol == 'PROFESIONAL'}">
				 <h3>Menú de trabajo</h3>
			</c:if>
			<c:if test="${sessionScope.rol == 'CIUDADANO'}">
				 <h3>Tus citas</h3>
			</c:if>
			<c:if test="${sessionScope.rol == 'SUPERVISOR'}">
				 <h3>Estadísticas</h3>
			</c:if>
        </div>

        <ul class="list-unstyled components">
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
	         		<a href="lista_categorias">Categorías</a> 
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
        </ul>
    </nav>