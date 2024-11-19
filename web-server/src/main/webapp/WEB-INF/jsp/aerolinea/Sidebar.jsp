<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.volandoPuntoUY.model.DtUsuario"%>
<%@ page import="com.volandoPuntoUY.model.DtClienteWeb"%>
<%@ page import="com.volandoPuntoUY.model.DtAerolinea"%>
<%@page import="com.volandoPuntoUY.model.StringArray"%>

<%
DtUsuario usuarioSidebar = (DtUsuario) request.getSession().getAttribute("usuarioDT");;
	

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
        <li><a href="<%= contextPathSidebar %>/listarVuelos" class="link">Buscar Vuelo</a></li>
        <li><a href="<%= contextPathSidebar %>/consultaPaquete" class="link">Buscar Paquete</a></li>
        <li><a href="<%= contextPathSidebar %>/consultaUsuario" class="link">Buscar Usuario</a></li>

        <% if (esCliente) { %>
            <li><a href="<%= contextPathSidebar %>/reservarVuelo" class="link">Reservar Vuelo</a></li>
            <li><a href="<%= contextPathSidebar %>/consultaPaquete" class="link">Comprar Paquete</a></li>
        <% } else if (esAerolinea) { %>
            <li><a href="<%= contextPathSidebar %>/altaRuta" class="link">Alta Ruta</a></li>
            <li><a href="<%= contextPathSidebar %>/altaVuelo" class="link">Alta Vuelo</a></li>
        <% } %>
    </ul>

    <h5>CATEGORÍAS</h5>
	        <ul class="category-list">
	        <% 
				StringArray cats = (StringArray) request.getSession().getAttribute("categorias");
                if(cats !=  null){
                	for(String c: cats.getItem()){
				%>
           		<li><a href="home?cat=<%= c %>" class="link"><%= c %></a></li>
           	<%
                	}
                }
				%>
	        </ul>
</div>

<!-- Offcanvas categorías (para móvil) -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="categoriesOffcanvas"
        aria-labelledby="categoriesOffcanvasLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="categoriesOffcanvasLabel">MENÚ</h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
    </div>
    <div class="offcanvas-body">
        <ul class="category-list">
            <li><a href="<%= contextPathSidebar %>/listarVuelos" class="link">Buscar Vuelo</a></li>
            <li><a href="<%= contextPathSidebar %>/consultaPaquete" class="link">Buscar Paquete</a></li>

            <li><a href="<%= contextPathSidebar %>/consultaUsuario" class="link">Buscar Usuario</a></li>

            <% if (esCliente) { %>
                <li><a href="<%= contextPathSidebar %>/reservarVuelo" class="link">Reservar Vuelo</a></li>
                <li><a href="<%= contextPathSidebar %>/consultaPaquete" class="link">Comprar Paquete</a></li>
            <% } else if (esAerolinea) { %>
                <li><a href="<%= contextPathSidebar %>/altaRuta" class="link">Alta Ruta</a></li>
                <li><a href="<%= contextPathSidebar %>/altaVuelo" class="link">Alta Vuelo</a></li>
            <% } %>
        </ul>
    </div>
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="categoriesOffcanvasLabel">CATEGORÍAS</h5>
    </div>
    <div class="offcanvas-body">
	        <ul class="category-list">
	        <% 
                if(cats !=  null){
                	for(String c: cats.getItem()){
				%>
           		<li><a href="home?cat=<%= c %>" class="link"><%= c %></a></li>
           	<%
                	}
                }
				%>
	        </ul>
    </div>
</div>
