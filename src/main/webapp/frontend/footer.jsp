<div id="footer">	
	<c:if test="${sessionScope.userLogin == null}">
		<div class="footer-pictures">
			<img src="images/logo-fvmp.png" height="100"/>
		</div>
	</c:if>
	<c:if test="${sessionScope.userLogin != null}">
		<div class="footer-pictures">
			<img src="../images/logo-fvmp.png" height="100"/>
		</div>
	</c:if>
	<div class="footer-items">
		<a href=".jsp">Quiénes somos</a> |
		<a href=".jsp">Qué hacemos</a> |
		<a href=".jsp">Preguntas frecuentes</a>
	</div>	
</div>