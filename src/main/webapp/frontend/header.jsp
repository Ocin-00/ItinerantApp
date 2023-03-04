
<!-- 
<div id="header">
	<div class="home">
		<img src="images/itinerant.jpg" height="100" />
	</div>

	<div id="header-center">
		<div class="header-pictures">
			<a href="https://www.gva.es/va/inicio/presentacion"><img src="images/logo_gva.png" height="50"/></a>
		</div>
		<div class="header-pictures">
			<a href="https://avant.gva.es/es/"><img src="images/Logo AVANT.png" height="50"/></a>
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
	<div class="header-items">
		<button onclick="location.href='login';">Iniciar sesión</button>
		<button onclick="location.href='register';">Registrarse</button>
	</div>
</div>
 -->
 
 <nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom border-dark py-0">
  <div class="col navbar-brand home">
		<img src="images/itinerant.jpg" height="100" />
	</div>
	
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
  
	<div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
	<div class="d-flex align-items-center ml-auto navbar-items">
	    <div class="mx-auto d-flex align-items-center"> <!-- Added mx-auto class here -->
	      <div class="nav-item">
	        <a href="https://www.gva.es/va/inicio/presentacion"><img src="images/logo_gva.png" height="50"/></a>
	      </div>
	      <div class="nav-item">
	        <a href="https://avant.gva.es/es/">
	          <img src="images/Logo AVANT.png" height="50"/>
	        </a>
	      </div>
	    </div>
  	</div>
	
	<div class="d-flex align-items-center mx-auto my-2">
	    <form action="buscar" method="get" id="buscarForm" class="input-group flex-nowrap w-100">
	        <input type="search" placeholder="Buscar..." name="keyword" size="40" id="keyword"  class="form-control border-dark-subtle">
	        <button type="submit" class="btn btn-outline-secondary"><i class="fa-solid fa-magnifying-glass"></i></button>
	    </form>
	</div>
	
	<div class="d-flex align-items-center ml-auto navbar-items">
		 <div class="mx-auto d-flex align-items-center">
		     <div class="nav-item">
	        	<button onclick="location.href='login';" class="ms-auto">Iniciar sesión</button>
			</div>
		    <div class="nav-item">
		    	<button onclick="location.href='register';" class="me-auto">Registrarse</button>
		    </div>
	    </div>
  	</div>
  </div>
</nav>
<script type="text/javascript">
	$(document).on("click", ".home", function(){
		window.location = "${pageContext.request.contextPath}";
	});
</script>

