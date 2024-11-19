<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.StringArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWeb"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Volando.uy</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
	rel="stylesheet">
<link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<link rel="stylesheet" href="media/css/style.css">

</head>

<body>

	<%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>


	<div class="main-content">

		<!-- Contenido de la página QUE SE DESPLIEGA -->

		<div class="container">
			<h2>Rutas Disponibles</h2>
			<% DtRutaDeVueloWebArray rutas = (DtRutaDeVueloWebArray) request.getSession().getAttribute("rutas");
			if (rutas != null) { %>
			<div class="d-flex justify-content-between align-items-center mb-4">
	                <div>Mostrando <%= rutas.getItem().size() %> rutas</div>
	            </div>
			

			<%
			
				for (DtRutaDeVueloWeb ruta : rutas.getItem()) {
			%>
			
				<div class="panel d-flex align-items-center mb-3">
					<img src="<%=request.getContextPath() + "/media/imgs/" + ruta.getImagen()%>"
						alt="Ruta (<%=ruta.getNombre()%>)"
						style="width: 120px; height: auto; border-radius: 8px;">
					<div class="ms-3">
						<h5>
							<a href="datosRuta?ruta=<%=ruta.getNombre()%>" class="link">Ruta: (<%=ruta.getNombre()%>)</a>
						</h5>
						<p><%=ruta.getDescripcionCorta()%></p>
					</div>
				</div>
			
			<%
			}
			}
			%>

		</div>
	</div>

	<!-- Barra de búsqueda flotante para móvil -->
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			const searchIcon = document.querySelector('.search-icon');
			const searchBar = document.querySelector('.search-bar');
			const searchInput = document.querySelector('.search-bar input');

			searchIcon.addEventListener('click', function() {
				searchBar.classList.toggle('active');
				if (searchBar.classList.contains('active')) {
					searchInput.focus();
				}
			});
		});
	</script>

</body>

</html>