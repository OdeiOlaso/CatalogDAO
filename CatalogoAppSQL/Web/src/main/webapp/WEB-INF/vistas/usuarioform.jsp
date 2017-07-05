<%@ include file="includes/cabecera.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Formulario de Usuarios</h2>

<jsp:useBean id="usuario" scope="request" class="ipartek.formacion.odei.Tipos.Usuario"/>
	<form action="${pageContext.request.contextPath}/admin/usuarioform" method="post">
		<fieldset>
			<label for="nombre">Nombre</label> 
			<input id="nombre" name="nombre" value="${usuario.nombre}" 
			<c:if test="${param.op == 'modificar'}">
			 disabled="disabled" readonly="readonly" 
			 </c:if>
			 />
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
			<input type="submit" value="${fn:toUpperCase(param.op)}" />
			<p class="errores">${usuario.errores}</p>
			
			<input type="hidden" name="op" value="${param.op}" />
		<c:if test="${param.op == 'borrar' or param.ops == 'borrar'}">
			
			<a href="${pageContext.request.contextPath}/tiendacrud">Cancelar"</a>
			 </c:if>
			<p class="errores">${producto.errores}</p>
			
			<input type="hidden" name="ops" value="${param.op}${param.ops}" />
		</fieldset>
		</form>
	
	
<%@ include file="includes/pie.jsp" %>
