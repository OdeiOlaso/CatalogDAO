><%@ include file="includes/cabecera.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2>Carrito</h2>
 <h5>Usuario iniciado: ${sessionScope.iniciado}</h5>
<table>
	<thead>
		<tr>
			<th>ID</th><th>Producto</th>
			<th>Descripcion</th><th>Precio</th>
			<th>Adquisiciones</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${carrito}" var="producto">
		<tr>
			<td>${producto.id}</td>
			<td>${producto.nombre}</td>
			<td>${producto.descripcion}</td>
			<td>${producto.precio}</td>
		</tr>
</c:forEach>
	</tbody>
</table>

<%@ include file="includes/pie.jsp" %>