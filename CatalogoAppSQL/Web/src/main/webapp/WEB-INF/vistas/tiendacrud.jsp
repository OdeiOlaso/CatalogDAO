><%@ include file="includes/cabecera.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2>Matenimiento de Usuarios</h2>
 <h5>Usuario iniciado: ${sessionScope.iniciado}</h5>
<table>
	<thead>
		<tr>
		<c:if test="${sessionScope.iniciado == 'admin'}">
			<th>Opciones</th>
			</c:if>
			<th>ID</th><th>Producto</th>
			<th>Descripcion</th><th>Precio</th>
			<th>Adquisiciones</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${productos}" var="producto">
		<tr>
			<c:if test="${sessionScope.iniciado == 'admin'}">
				<td>
				<a href="?op=modificar&id=${producto.id}">Modificar </a>
				<a href="?op=borrar&id=${producto.id}">Borrar</a></td>
			</c:if>
			<td>${producto.id}</td>
			<td>${producto.nombre}</td>
			<td>${producto.descripcion}</td>
			<td>${producto.precio}</td>
			<td><a href="?op=comprar&id=${producto.id}">Comprar</a></td>
		</tr>
</c:forEach>
	</tbody>
</table>

<c:if test="${param.iniciado == 'admin'}">
	<a href="tiendacrud?op=alta">Alta</a>
</c:if>

<%@ include file="includes/pie.jsp" %>