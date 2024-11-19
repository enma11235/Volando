<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="com.volandoPuntoUY.model.StringArray"%>
<!DOCTYPE html>
<html lang="es">
<head>
<!-- Meta y enlaces -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Volando.uy - Alta Ruta de Vuelo</title>

<!-- Enlaces a Bootstrap, Font Awesome y fuentes -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
	rel="stylesheet">
<link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">

<!-- Scripts de Bootstrap -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="media/css/style.css">
<script>
			document.addEventListener("DOMContentLoaded", function() {
				if("<%=request.getSession().getAttribute("error")%>" != "s")alert("<%=request.getSession().getAttribute("error")%>");
			});
	</script>
</head>
<body>
	<%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>
	<div class="main-content">
		<div class="item-showcase">
			<h2>Datos del vuelo</h2>
			<hr>
			<form method="post" enctype="multipart/form-data">
				<div class="form-group mb-3">
					<label for="ruta">Ruta:</label> <select id="ruta" name="ruta"
						class="form-control" required>
						<%
						request.getSession().setAttribute("error", "s");
						StringArray rutas = (StringArray) request.getSession().getAttribute("rutas");
						if (rutas != null) {
							for (String r : rutas.getItem()) {
						%>
						<option value="<%=r%>"><%=r%></option>
						<%
						}
						}
						%>
					</select>
				</div>

				<div class="form-group mb-3">
					<label for="nombre">Nombre:</label> <input type="text" id="nombre"
						name="nombre" class="form-control"
						placeholder="Ingrese nombre del vuelo" required>
				</div>

				<div class="form-group mb-3">
					<label for="fechaVuelo">Fecha de Vuelo:</label> <input type="date"
						class="form-control" id="fechaVuelo" name="fechaVuelo" required>
				</div>

				<div class="form-group mb-3">
					<label for="duracion">Duración del Vuelo (en minutos):</label> <input
						type="number" id="duracion" name="duracion" class="form-control"
						min="1" required placeholder="Ej. 3">
				</div>

				<div class="form-group mb-3">
					<label for="asientosTuristas">Cantidad máxima de asientos
						turistas:</label> <input type="number" id="asientosTuristas"
						name="asientosTuristas" class="form-control" min="1" required
						placeholder="Ej. 100">
				</div>

				<div class="form-group mb-3">
					<label for="asientosEjecutivos">Cantidad máxima de asientos
						ejecutivos:</label> <input type="number" id="asientosEjecutivos"
						name="asientosEjecutivos" class="form-control" min="0" required
						placeholder="Ej. 20">
				</div>

				<div class="form-group mb-3">
					<label for="imagen">Imagen (opcional):</label> <input type="file"
						id="imagen" name="imagen" class="form-control" accept="image/*">
				</div>

				<button type="submit" class="btn btn-primary">Confirmar</button>
				<a href="index.html" class="btn btn-secondary">Cancelar</a>

			</form>
		</div>
	</div>
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