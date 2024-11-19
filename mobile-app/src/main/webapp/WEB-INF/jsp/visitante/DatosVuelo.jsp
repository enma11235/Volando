<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWeb"%>
<%@page import="com.volandoPuntoUY.model.DtUsuario"%>
<%@page import="com.volandoPuntoUY.model.DtClienteWeb"%>
<%@page import="com.volandoPuntoUY.model.DtAerolinea"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.Duration"%>
<%@page import="com.volandoPuntoUY.model.DtReservaWeb"%>

<%
DtUsuario usuarioDatosVuelo = (DtUsuario) request.getSession().getAttribute("usuarioDT");
String nickAero = (String) request.getAttribute("aerolineaVuelo");


boolean esClienteDatosVuelo = false;
boolean esAerolineaDatosVuelo = false;

if (usuarioDatosVuelo != null) {
	if (usuarioDatosVuelo instanceof DtClienteWeb) {
		esClienteDatosVuelo = true;
	} else if (usuarioDatosVuelo instanceof DtAerolinea) {
		esAerolineaDatosVuelo = true;
	}
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Volando.uy - Detalle del Vuelo</title>

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
		<%
		DtVueloWeb vuelo = (DtVueloWeb) request.getAttribute("vuelo");
		String nomRuta = (String) request.getAttribute("ruta");
		if (vuelo == null) {
		%>
		<script type="text/javascript">
			window.history.back();
		</script>
		<%
		} else {
		%>
		<!-- Datos del vuelo -->
		<div class="item-showcase">
			<img src="<%=request.getContextPath() + "/media/imgs/" + vuelo.getImagen()%>" alt="Imagen del vuelo"
				class="flight-image">

			<div class="details">
				<h2>
					Vuelo:
					<%=vuelo.getNombre()%></h2>
				<p>
					<strong>Ruta:</strong> <a href="datosRuta?ruta=<%=nomRuta%>"><%=nomRuta%></a>
				</p>
				<div>
					<div>
						<p>
							<strong>Fecha:</strong>
							<%=LocalDate.parse(vuelo.getFecha()).format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"))%></p>
						<p>
							<strong>Duración:</strong>
							<%=Duration.parse(vuelo.getDuracion()).toHoursPart()%>
							horas
							<%=Duration.parse(vuelo.getDuracion()).toMinutesPart()%>
							minutos
						</p>
						<p>
							<strong>Fecha de Alta:</strong>
							<%=LocalDate.parse(vuelo.getFechaAlta()).format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"))%></p>
					</div>
					<div>
						<p>
							<strong>Máx. Asientos Turista:</strong>
							<%=vuelo.getMaxTurista()%></p>
						<p>
							<strong>Máx. Asientos Ejecutivo:</strong>
							<%=vuelo.getMaxEjecutivo()%></p>
					</div>
				</div>
			</div>
		</div>

		<%
		List<DtReservaWeb> reservas = vuelo.getReservas();

		if (esClienteDatosVuelo) {
			DtReservaWeb reservaCliente = null;
			for(DtReservaWeb res : reservas){
				if(res.getNicknameCliente().equals(usuarioDatosVuelo.getNickname())) reservaCliente = res;
			}
			if (reservaCliente != null) {
		%>
		<div class="item-showcase">
			<div class="details">
				<h2>Mis Reservas:</h2>
				<div>
					<p>
						<a href="detallesReserva?vuelo=<%=reservaCliente.getNomVuelo()%>&nick=<%=usuarioDatosVuelo.getNickname()%>"><strong>Reserva en vuelo:</strong>
						<%=reservaCliente.getNomVuelo()%></p></a>
					<p>
						<strong>Tipo de Asiento:</strong>
						<%=reservaCliente.getTipoAsiento()%></p>
					<p>
						<strong>Equipaje:</strong>
						<%=reservaCliente.getCantEquipaje()%></p>
					<p>
						<strong>Pasajeros:</strong>
						<%=reservaCliente.getCantPasajeros()%></p>
					<p>
						<strong>Costo:</strong> $<%=reservaCliente.getCosto()%></p>
				</div>
			</div>
		</div>
		<%
		}
		}
		%>

		<%
		if (esAerolineaDatosVuelo) {
		%>
		<%
		if (reservas != null && nickAero.equals(usuarioDatosVuelo.getNickname())) {
		%><div class="item-showcase">
			<h2>Reservas del Vuelo:</h2>
			<%
			for (DtReservaWeb reserva : reservas) {
			%>

			<div class="details">

				<div>
					<p><a href="detallesReserva?vuelo=<%=reserva.getNomVuelo()%>&nick=<%=reserva.getNicknameCliente()%>">
						<strong>Reserva del vuelo:</strong>
						<%=reserva.getNomVuelo()%></a></p>
					<p>
						<strong>Cliente:</strong>
						<%=reserva.getNicknameCliente()%></p>
					<p>
						<strong>Tipo de Asiento:</strong>
						<%=reserva.getTipoAsiento()%></p>
					<p>
						<strong>Equipaje:</strong>
						<%=reserva.getCantEquipaje()%></p>
					<p>
						<strong>Pasajeros:</strong>
						<%=reserva.getCantPasajeros()%></p>
					<p>
						<strong>Costo:</strong> $<%=reserva.getCosto()%></p>
					<hr>
					<%
					}
					}
					%>
				</div>
			</div>
		</div>
		<%
		}
		%>
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
<%
}
%>
