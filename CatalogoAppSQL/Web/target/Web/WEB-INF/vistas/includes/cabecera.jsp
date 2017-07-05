<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Ejemplo MVC</title>
<link rel="stylesheet" href="css/estilos.css" />
<script src="js/funciones.js"></script>
</head>
<body>
	<header>
		<h1>TIENDA Too GUAPA</h1>
		<p>Ejemplo de como hacer una Tienda de pulpa Madre</p>
	</header>
	<nav>
		<ul>
			<li><a href="${pageContext.request.contextPath}/login">Login</a></li>
			<li><a href="${pageContext.request.contextPath}/alta?op=registro">Alta</a></li>
			<li><a href="${pageContext.request.contextPath}/login?opcion=logout">Salir</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/usuariocrud">Mantenimiento usuarios</a></li>
			<li><a href="${pageContext.request.contextPath}/carrito.jsp">Carrito</a></li>
		</ul>
	</nav>