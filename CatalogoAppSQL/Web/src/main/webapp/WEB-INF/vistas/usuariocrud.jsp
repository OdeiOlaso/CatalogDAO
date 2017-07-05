><%@ include file="includes/cabecera.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2>Matenimiento de Usuarios</h2>
<table>
	<thead>
		<tr>
			<th>Opciones</th><th>Usuario</th><th>Contraseña</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${usuarios}" var="usuario">
		<tr>
			<td>
			<a href="?op=modificar&id=${usuario.nombre}">Modificar </a>
			<a href="?op=borrar&id=${usuario.nombre}">Borrar</a></td>
			<td>${usuario.nombre}</td>
			<td>${usuario.pass}</td>
		</tr>
</c:forEach>
	</tbody>
</table>

<a href="usuariocrud?op=alta">Alta</a>

<%@ include file="includes/pie.jsp" %>