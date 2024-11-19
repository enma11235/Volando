<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWeb"%>
<%@page import="com.volandoPuntoUY.model.DtRutaPaqueteWeb"%>
<%@page import="java.time.Duration"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy - Datos del Paquete</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <link rel="stylesheet" href="media/css/style.css">
    <style>
        .related-item .item-showcase {
            width: 90% !important;
        }
    </style>
    
</head>

<body>


  	<%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>

    <!-- Contenido principal -->
    <div class="main-content">
    
    	<% DtPaqueteWeb datosPaquete = (DtPaqueteWeb) request.getAttribute("datosPaquete"); %>
    	
    	

        <div class="item-showcase">
            <img src="<%=request.getContextPath() + "/media/imgs/" + datosPaquete.getImagen() %>" class="flight-image">

            <div class="details">
                <h2>Paquete: <%= datosPaquete.getNombre() %></h2>
                <p><strong>Descripción:</strong> <%= datosPaquete.getDescripcion() %></p>
                <p><strong>Validez:</strong> <%= Duration.parse(datosPaquete.getPeriodoValidez()).toDays() %> dias</p>
                <p><strong>Descuento:</strong> <%= datosPaquete.getDescuento() %>%</p>
                <p><strong>Costo:</strong> USD <%= (float)request.getAttribute("precioPaquete") %></p>
                <% 
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
                String fechaAlta = LocalDate.parse(datosPaquete.getFechaAlta()).format(formateador);
                %>
                <p><strong>Fecha de alta:</strong> <%= fechaAlta %></p>
                <%
                if(request.getAttribute("tipoUsuario") == "cliente") {
                %>
                <button class="vuy_button" id="miBoton">Comprar</button>
              	<script>
              		const boton = document.getElementById("miBoton");
              		boton.addEventListener("click", function() {
	                    const xhr = new XMLHttpRequest();
	                    xhr.open('GET', "comprarPaquete?nombre=<%=datosPaquete.getNombre()%>", true);
	
	                    xhr.onreadystatechange = function() {
	                        if (xhr.readyState === XMLHttpRequest.DONE) {
	                            if (xhr.status === 200) {
	                                // Aquí se espera que el servlet devuelva un atributo en el request
	                                const response = xhr.responseText;
	                                    alert(response);
	                            } else {
	                                alert('Hubo un problema con la solicitud');
	                            }
	                        }
	                    };
	
	                    xhr.send();
	                });
	                
                </script>
                <%	
                }
                %>

            </div>
        </div>

        <!-- Rutas de Vuelo Asociadas -->
        <div class="related-section">
        	<%
        	ArrayList<DtRutaPaqueteWeb> rutas = (ArrayList<DtRutaPaqueteWeb>) datosPaquete.getRutas();
        	if(rutas != null && !rutas.isEmpty()) {
        	%>
        		<h5>Rutas de Vuelo Incluidas:</h5>
                
                <%
                
                for(DtRutaPaqueteWeb rutaP : rutas) {
                %>
                	<div class="related-item">
    	               	<div class="item-showcase d-flex align-items-center">
    					   <img src="<%=request.getContextPath() + "/media/imgs/" + rutaP.getRuta().getImagen()%>" style="width: 210px; height: auto; border-radius: 8px; margin-right: 15px;">
    					   <div>
    					        <h6><a href="datosRuta?ruta=<%=rutaP.getRuta().getNombre()%>"><%= rutaP.getRuta().getNombre() %></a></h6>
    					        <p><strong>Tipo:</strong> <%= rutaP.getTipoAsiento() %></p>
    					        <p><strong>Cantidad:</strong> <%= rutaP.getCantidad() %></p>
    					    </div>
    					</div>
                	</div>
                <%
                }
        	} else {
        		%>
        		<h5>No hay Rutas de Vuelo</h5>
        		<%
        	}
        		%>

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