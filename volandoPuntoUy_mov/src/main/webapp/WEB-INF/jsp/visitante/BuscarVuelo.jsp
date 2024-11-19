<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWeb"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy - Buscar Paquete</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="media/css/style.css">
</head>
<body>
   	<%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>

    <div class="main-content">
    <div class="container">
        <h2>Vuelos Disponibles</h2>
        <% 
            DtVueloWebArray vuelos = (DtVueloWebArray) request.getAttribute("vuelos");
            if (vuelos != null) {%>
        <div class="d-flex justify-content-between align-items-center mb-4">
	                <div>Mostrando <%= vuelos.getItem().size() %> vuelos</div>
	            </div>
        
        <%
                for (DtVueloWeb vuelo : vuelos.getItem()) {
        %>
        
            <div class="panel d-flex align-items-center mb-3">
                <img src="<%=request.getContextPath() + "/media/imgs/" + vuelo.getImagen() %>" alt="Vuelo <%= vuelo.getNombre() %>" style="width: 120px; height: auto; border-radius: 8px;">
                <div class="ms-3">
                    <a href="datosVuelo?nomVuelo=<%= vuelo.getNombre() %>" class="link"><h5>Vuelo: <%= vuelo.getNombre() %></h5></a>
                    
                </div>
            </div>
      
        <% 
                }
            } else {
        %>
        <p>No hay vuelos disponibles.</p>
        <% } %>
    </div>
    </div>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const searchIcon = document.querySelector('.search-icon');
            const searchBar = document.querySelector('.search-bar');
            const searchInput = document.querySelector('.search-bar input');

            searchIcon.addEventListener('click', function () {
                searchBar.classList.toggle('active');
                if (searchBar.classList.contains('active')) {
                    searchInput.focus();
                }
            });
        });
    </script>
</body>
</html>
