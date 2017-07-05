><%@ include file="includes/cabecera.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2>Carrito</h2>
 <h5>Carrito de: ${sessionScope.iniciado}</h5>
<table>
	<thead>
		<tr>
			<th>ID</th><th>Producto</th>
			<th>Descripcion</th><th>Precio</th>
			<th>Adquisiciones</th>
			<th>Modificar</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${productosCarrito}" var="cantidadProducto">
		<tr>
			<td>${cantidadProducto.producto.id}</td>
			<td>${cantidadProducto.producto.nombre}</td>
			<td>${cantidadProducto.producto.descripcion}</td>
			<td>${cantidadProducto.producto.precio}</td>
			<td>${cantidadProducto.cantidad}</td>
			<td><a href="?op=menos&id=${cantidadProducto.producto.id}">-1</a> 
			<a href="?op=quitar&id=${cantidadProducto.producto.id}">Borrar</a></td>
		</tr>
</c:forEach>
	</tbody>
</table>

<%@ include file="includes/pie.jsp" %>