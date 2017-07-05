<%@ include file="includes/cabecera.jsp" %>
	<h1>Principal</h1>
	<jsp:useBean id="usuario" scope="session" class="org.TIposSQL.Usuario"/>
	<h2>Bienvenido ${usuario.nombre}</h2> <a href="login?opcion=logout">Logout</a></h2>
	<h3><%= new java.util.Date() %></h3>
<%@ include file="includes/pie.jsp" %>
