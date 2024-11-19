<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page import="java.util.List" %>
<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.DtAerolinea"%>
<%@page import="com.volandoPuntoUY.model.DtAerolineaArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWeb"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWeb"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtReservaWeb"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWeb"%>
<%@page import="com.volandoPuntoUY.model.StringArray"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy - Consulta de reserva</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="media/css/style.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>

		
    <!-- Contenedor principal -->  
		<div class="main-content">  
			<div class="item-showcase">
		    <h2>Datos de la reserva</h2>  
			<hr>
		    <form method="post" action="consultaReserva" id="formConsultaReserva">
			    <!-- Lista de Aerolíneas -->
				<div class="custom-scrollbar">
				    <p><strong>Aerolinea:</strong></p>
				    <% 
				    	DtAerolineaArray aerolineas = (DtAerolineaArray) request.getAttribute("aerolineas");
				    	String aerolineaSeleccionada = request.getParameter("aerolineaSeleccionada"); // Obtenemos la aerolínea seleccionada
				        
				    	if (aerolineas != null && !aerolineas.getItem().isEmpty()) {
				            for (DtAerolinea aerolinea : aerolineas.getItem()) {
				                boolean isChecked = aerolinea.getNickname().equals(aerolineaSeleccionada); // Comprobamos si es la seleccionada
				    %>
				    <!-- Mostrar cada aerolínea como opción de radio -->
				    <div class="panel mb-3" style="border: 1px solid #ddd; padding: 10px; border-radius: 8px;">
				        <div class="d-flex align-items-start">
				            <!-- Radio button a la izquierda de la imagen -->				            
				            <input type="radio" name="aerolineaSeleccionada" value="<%= aerolinea.getNickname() %>" required 
				                   onchange="document.getElementById('formConsultaReserva').submit()" 
				                   <%= isChecked ? "checked" : "" %> 
				                   style="margin-right: 15px;"> <!-- Añadir margen a la derecha para espacio entre el radio y la imagen -->
				
				            <!-- Imagen de la aerolínea a la derecha del radio button -->
				            <img src="<%=request.getContextPath() + "/media/imgs/" + aerolinea.getImagen() %>" alt="Aerolinea (<%= aerolinea.getNickname() %>)" 
				                 style="width: 90px; height: auto; border-radius: 8px; margin-right: 15px;">
				
				            <!-- Información de la aerolínea al lado de la imagen -->
				            <div class="w-100">
				                <h5>Aerolinea: <%= aerolinea.getNombre() %></h5>
				                <p><%= aerolinea.getDescripcion() %></p>
				            </div>
				        </div>
				    </div>
				    <% 
				            }
				        }
				    %>
				</div>
				
				<!-- Lista de Rutas Dinámicas (se carga después de seleccionar una aerolínea) -->
				<div class="custom-scrollbar">
				    <p><strong>Rutas Confirmadas:</strong></p>
				    <% 
				    // Obtener las rutas que el servlet pasó como atributo
					DtRutaDeVueloWebArray rutas = (DtRutaDeVueloWebArray) request.getAttribute("rutas");
				    String rutaSeleccionada = request.getParameter("rutaSeleccionada"); // Obtenemos la ruta seleccionada
			        
				    if (rutas != null && !rutas.getItem().isEmpty()) {
				            for (DtRutaDeVueloWeb ruta : rutas.getItem()) {
				                boolean isChecked = ruta.getNombre().equals(rutaSeleccionada); // Comprobamos si es la seleccionada
				    %>
				    <!-- Mostrar cada ruta como opción de radio -->
				    <div class="panel mb-3" style="border: 1px solid #ddd; padding: 10px; border-radius: 8px;">
				        <div class="d-flex align-items-start">
				            <!-- Radio button a la izquierda de la imagen -->
				            <input type="radio" name="rutaSeleccionada" value="<%= ruta.getNombre() %>" required 
				                   onchange="document.getElementById('formConsultaReserva').submit()" 
				                   <%= isChecked ? "checked" : "" %> 
				                   style="margin-right: 15px;"> <!-- Añadir margen a la derecha para espacio entre el radio y la imagen -->
				
				            <!-- Imagen de la ruta a la derecha del radio button -->
				            <img src="<%=request.getContextPath() + "/media/imgs/" + ruta.getImagen() %>" alt="Ruta <%= ruta.getNombre() %>" 
				                 style="width: 120px; height: auto; border-radius: 8px; margin-right: 15px;">
				
				            <!-- Información de la ruta al lado de la imagen -->
				            <div class="w-100">
				                <h5>Ruta: <%= ruta.getNombre() %></h5>
				                <p><%= ruta.getDescripcion() %></p>
				            </div>
				        </div>
				    </div>
				    <% 
				            }
				        } else {
				    %>
				    <p>No hay rutas disponibles para la aerolínea seleccionada.</p>
				    <% } %>
				</div>

				<!-- Lista de Vuelos Asociados a la Ruta Seleccionada (se carga después de seleccionar una ruta) -->
				<div class="custom-scrollbar">
				    <p><strong>Vuelos disponibles:</strong></p>
				    <% 
				        // Obtener los vuelos que el servlet pasó como atributo
				        DtVueloWebArray vuelos = (DtVueloWebArray) request.getAttribute("vuelos");
				        String vueloSeleccionado = request.getParameter("vueloSeleccionado"); // Obtenemos el vuelo seleccionado
				        
				        if (vuelos != null && !vuelos.getItem().isEmpty()) {
				            for (DtVueloWeb vuelo : vuelos.getItem()) {
				                boolean isChecked = vuelo.getNombre().equals(vueloSeleccionado); // Comprobamos si es el seleccionado
				    %>
				    <!-- Mostrar cada vuelo como opción de radio -->
				    <div class="panel mb-3" style="border: 1px solid #ddd; padding: 10px; border-radius: 8px;">
				        <div class="d-flex align-items-start">
				            <!-- Radio button a la izquierda de la imagen -->
				            <input type="radio" name="vueloSeleccionado" value="<%= vuelo.getNombre() %>" required 
				                   onchange="document.getElementById('formConsultaReserva').submit(); mostrarDetallesAutomaticamente('<%= vuelo.getNombre() %>')" 
				                   <%= isChecked ? "checked" : "" %> 
				                   style="margin-right: 15px;"> <!-- Añadir margen a la derecha para espacio entre el radio y la imagen -->
				
				            <!-- Imagen del vuelo a la derecha del radio button, más pequeña -->
				            <img src="<%=request.getContextPath() + "/media/imgs/" + vuelo.getImagen() %>" alt="Vuelo <%= vuelo.getNombre() %>" 
				                 style="width: 120px; height: auto; border-radius: 8px; margin-right: 15px;">
				
				            <!-- Información del vuelo al lado de la imagen -->
				            <div class="w-100">
				                <div class="d-flex justify-content-between">
				                    <div>
				                        <label for="vueloSeleccionado"><strong>Vuelo:</strong> <%= vuelo.getNombre() %></label>
				                        <p><strong>Fecha:</strong> <%= vuelo.getFecha() %></p>
				                    </div>
				                </div>
				            </div>
				        </div>
				    </div>
				    <% 
				            }
				        } else{ 
				    %>
				    <p>No hay vuelos disponibles para la ruta seleccionada.</p>
				    <% } %>
				</div>
				
				<!-- Lista de Reservas Asociadas al Vuelo (se carga después de seleccionar un Vuelo) -->
				<div class="custom-scrollbar">
				    <p><strong>Reservas:</strong></p>
				    <% 
				        // Obtener los vuelos que el servlet pasó como atributo
				        StringArray reservas = (StringArray) request.getAttribute("reservas");
				        String reservaSeleccionada = request.getParameter("reservaSeleccionada"); // Obtenemos el vuelo seleccionado
				        
				        if (reservas != null && !reservas.getItem().isEmpty()) {
				            for (String reserva : reservas.getItem()) {
				                boolean isChecked = reserva.equals(reservaSeleccionada); // Comprobamos si es el seleccionado
				    %>
				    <!-- Mostrar cada reserva como opción de radio -->
				    <div class="panel mb-3" style="border: 1px solid #ddd; padding: 10px; border-radius: 8px;">
				        <a href='detallesReserva?vuelo=<%= vueloSeleccionado %>&nick=<%= reserva %>'><strong><%= reserva %></strong></a>
				    </div>
				    <% 
				            }
				        } else if (rutaSeleccionada != null) { 
				    %>
				    <p>No hay reservas disponibles para el vuelo seleccionado.</p>
				    <% } %>
				</div>
			</form>
			</div>

    </div>
    <!-- Script para expandir automáticamente detalles al seleccionar el vuelo -->
		<script>
		    function mostrarDetallesAutomaticamente(vueloNombre) {
		        var detalles = document.querySelectorAll('.detalles-vuelo');
		        
		        // Oculta todos los detalles primero
		        detalles.forEach(function(detalle) {
		            detalle.style.display = 'none';
		        });
		        
		        // Muestra el detalle del vuelo seleccionado
		        var detallesSeleccionado = document.getElementById('detalles-' + vueloNombre);
		        detallesSeleccionado.style.display = 'block';
		    }
		
		    // Mantener los detalles del vuelo seleccionado visibles al recargar la página
		    window.onload = function() {
		        var vueloSeleccionado = "<%= vueloSeleccionado != null ? vueloSeleccionado : "" %>";
		        if (vueloSeleccionado !== "") {
		            mostrarDetallesAutomaticamente(vueloSeleccionado);
		        }
		    }
		</script>
</body>
</html>
