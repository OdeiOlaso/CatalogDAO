<%@ include file="includes/cabecera.jsp" %>

<jsp:useBean id="usuario" scope="request" class="org.TIposSQL.Usuario"/>
	<form action="login" method="post">
		<fieldset>
			<label for="username">Nombre</label> 
			<input id="username" name="username" value="${usuario.username}" />
		</fieldset>
		<fieldset>
			<label for="pass">Contraseña</label> 
			<input type="password" id="pass" name="pass" />
		</fieldset>
		<fieldset>
			<input type="submit" value="Login" />
			<p class="errores">${usuario.errores}</p>
		</fieldset>
		<a href="alta?op=registro&rol=2">Registrarse</a> 
	</form>
<%@ include file="includes/pie.jsp" %>
