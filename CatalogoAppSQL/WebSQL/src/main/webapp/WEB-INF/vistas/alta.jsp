<%@ include file="includes/cabecera.jsp" %>

<jsp:useBean id="usuario" scope="request" class="org.TIposSQL.Usuario"/>
	<form action="alta" method="post">
		<fieldset>
			<label for="nombre">Nombre de Usuario</label> 
			<input id="nombre" name="nombre" value="${usuario.username}" />
		</fieldset>
		<fieldset>
			<label for="pass">Contraseña</label> 
			<input type="password" id="pass" name="pass" />
		</fieldset>
		<fieldset>
			<label for="pass2">Repite la contraseña</label> 
			<input type="password" id="pass2" name="pass2" />
		</fieldset>
		<fieldset>
			<label for="nombre">Nombre Completo, Descripcion</label> 
			<input id="nombre" name="nombre" value="${usuario.nombre_completo}" />
		</fieldset>
		<fieldset>
			<input type="submit" value="Alta" />
			<p class="errores">${usuario.errores}</p>
		</fieldset>
		<a href="login">Iniciar Sesion</a>
		</form>
<%@ include file="includes/pie.jsp" %>
