<%@page import="com.volandoPuntoUY.model.DtAerolineaArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.volandoPuntoUY.model.DtUsuario"%>
<%@ page import="com.volandoPuntoUY.model.DtClienteWeb"%>
<%@ page import="com.volandoPuntoUY.model.DtAerolinea"%>
<%@page import="com.volandoPuntoUY.model.StringArray"%>

<%
DtUsuario usuarioSidebar = (DtUsuario) request.getSession().getAttribute("usuarioDT");
;

boolean esCliente = false;
boolean esAerolinea = false;

if (usuarioSidebar != null) {
	if (usuarioSidebar instanceof DtClienteWeb) {
		esCliente = true;
	} else if (usuarioSidebar instanceof DtAerolinea) {
		esAerolinea = true;
	}
}

String contextPathSidebar = request.getContextPath();
%>

<!-- Sidebar para escritorio -->
<div class="desktop-sidebar">
	<h5>MENÚ</h5>
	<ul class="category-list">
		<li><a href="<%=contextPathSidebar%>/listarVuelos" class="link">Buscar
				Vuelo</a></li>
		<%
		if (esCliente) {
		%>
		<li><a href="<%=contextPathSidebar%>/listarReservasSinCheckin"
			class="link">Realizar CheckIn</a></li>
		<li><a href="<%=contextPathSidebar%>/consultaReserva"
		class="link">Listar Reservas</a></li>
		<%
		}
		%>
	</ul>



<h5>CATEGORÍAS</h5>
<ul class="category-list">
	<%
	StringArray cats = (StringArray) request.getSession().getAttribute("categorias");
	if (cats != null) {
		for (String c : cats.getItem()) {
	%>
	<li><a href="home?cat=<%=c%>" class="link"><%=c%></a></li>
	<%
	}
	}
	%>
</ul>

<h5>AEROLÍNEAS</h5>
<ul class="category-list">
	<%
	DtAerolineaArray aeros = (DtAerolineaArray) request.getSession().getAttribute("aerolineasUsuarios");
	if (aeros != null) {
		for (DtAerolinea c : aeros.getItem()) {
	%>
	<li><a href="home?aero=<%=c.getNickname()%>" class="link"><%=c.getNickname()%></a></li>
	<%
	}
	}
	%>
</ul>
</div>

<!-- Offcanvas categorías (para móvil) -->
<div class="offcanvas offcanvas-start" tabindex="-1"
	id="categoriesOffcanvas" aria-labelledby="categoriesOffcanvasLabel">
	
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="categoriesOffcanvasLabel">MENÚ</h5>
		<button type="button" class="btn-close text-reset"
			data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
	</div>
	<div class="offcanvas-body">
		<ul class="category-list">
			<li><a href="<%=contextPathSidebar%>/listarVuelos" class="link">Buscar
					Vuelo</a></li>
			<%
			if (esCliente) {
			%>
				<li><a href="<%=contextPathSidebar%>/listarReservasSinCheckin"
				class="link">Realizar CheckIn</a></li>
				<li><a href="<%=contextPathSidebar%>/consultaReserva"
				class="link">Listar Reservas</a></li>
			<%
			}
			%>
		</ul>
	</div>
	<div class="custom-scrollbar">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="categoriesOffcanvasLabel">CATEGORÍAS</h5>
	</div>
	<div class="offcanvas-body">
		<ul class="category-list">
			<%
			if (cats != null) {
				for (String c : cats.getItem()) {
			%>
			<li><a href="home?cat=<%=c%>" class="link"><%=c%></a></li>
			<%
			}
			}
			%>
		</ul>
	</div>
	<div class="offcanvas-header">
		<h5>AEROLÍNEAS</h5>
	</div>
	<div class="offcanvas-body">
		<ul class="category-list">
			<%
			if (aeros != null) {
				for (DtAerolinea c : aeros.getItem()) {
			%>
			<li><a href="home?aero=<%=c.getNickname()%>" class="link"><%=c.getNickname()%></a></li>
			<%
			}
			}
			%>
		</ul>
	</div>
	</div>
</div>
