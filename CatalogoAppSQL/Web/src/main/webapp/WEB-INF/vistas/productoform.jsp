<%@ include file="includes/cabecera.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Formulario de Productos</h2>

<jsp:useBean id="producto" scope="request" class="ipartek.formacion.odei.Tipos.Producto"/>
	<form action="${pageContext.request.contextPath}/admin/productoform" method="post">
	<c:if test="${param.op == 'borrar' or param.ops == 'borrar'}">
			<h2>¿Seguro que quires borrar ${producto.nombre}?</h2>
			 </c:if>
		<fieldset>
			<label for="id">ID</label> 
			<input id="id" name="id"  
			 value="${producto.id}"
			 disabled="disabled" 
			 />
		</fieldset>
		<fieldset>
			<label for="nombre">Nombre</label> 
			<input id="nombre" name="nombre"  
			<c:if test="${param.op != 'alta'}">
			 value="${producto.nombre}"
			 </c:if>
			 />
		</fieldset>
		<fieldset>
			<label for="descripcion">Descripcion</label> 
			<input id="descripcion" name="descripcion" 
			<c:if test="${param.op != 'alta'}">
			 value="${producto.descripcion}"
			 </c:if>
			 />
		</fieldset>
		<fieldset>
			<label for="precio">Precio</label> 
			<input type="number" id="precio" name="precio"
			<c:if test="${param.op != 'alta'}">
			 value="${producto.precio}"
			 </c:if>
			/>
		</fieldset>
		<fieldset>
		
			<input type="submit" value="${fn:toUpperCase(param.op)}${fn:toUpperCase(param.ops)}"/>
			<c:if test="${param.op == 'borrar' or param.ops == 'borrar'}">
			
			<a href="${pageContext.request.contextPath}/tiendacrud">Cancelar"</a>
			 </c:if>
			<p class="errores">${producto.errores}</p>
			
			<input type="hidden" name="ops" value="${param.op}${param.ops}" />
			
			<input type="hidden" name="id" value="${producto.id}" />
			
			<input type="hidden" name="precioviejo" id="precioviejo" value="${producto.precio}" />
		</fieldset>
		</form>
	
	
	
<%@ include file="includes/pie.jsp" %>
