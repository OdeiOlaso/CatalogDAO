<%@ include file="includes/cabecera.jsp" %>

<jsp:useBean id="usuario" scope="request" class="ipartek.formacion.odei.Tipos.Usuario"/>
	<form action="login" method="post">
		<fieldset>
			<label for="nombre">Nombre</label> 
			<input id="nombre" name="nombre" value="${usuario.nombre}" />
		</fieldset>
		<fieldset>
			<label for="pass">Contraseña</label> 
			<input type="password" id="pass" name="pass" />
		</fieldset>
		<fieldset>
			<input type="submit" value="Login" />
			<p class="errores">${usuario.errores}</p>
		</fieldset>
		<a href="alta?op=registro">Registrarse</a> 
	</form>
<%@ include file="includes/pie.jsp" %>
