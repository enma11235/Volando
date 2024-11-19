<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page import="java.util.List" %>
<%@page import="java.lang.String"%>
<%@page import="java.time.Duration"%>
<%@page import="com.volandoPuntoUY.model.DtAerolinea"%>
<%@page import="com.volandoPuntoUY.model.DtAerolineaArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWeb"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWebArray"%>
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
	    <title>Volando.uy Reserva Vuelo</title>
	
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
	    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">
	
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	    <link rel="stylesheet" href="media/css/style.css">
	    
	    <script>
			document.addEventListener("DOMContentLoaded", function() {
				if("<%=request.getAttribute("error")%>" != "s")alert("<%=request.getAttribute("error")%>");
			});
		</script>
	</head>
	
	<body>
	    <%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>

		<!-- Contenedor principal -->  
		<div class="main-content">  
			<div class="item-showcase">
		    <h2>Datos de la reserva</h2>  
			<hr>
		    <form method="post" action="reservarVuelo" id="formReserva">
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
				                   onchange="document.getElementById('formReserva').submit()" 
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
				    <p><strong>Rutas disponibles: </strong></p>
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
				                   onchange="document.getElementById('formReserva').submit()"
				                   <%= isChecked ? "checked" : "" %> 
				                   style="margin-right: 15px;">
				            
				            <!-- Imagen de la ruta a la derecha del radio button -->
				            <img src="<%=request.getContextPath() + "/media/imgs/" + ruta.getImagen() %>" alt="Ruta <%= ruta.getNombre() %>" 
				                 style="width: 120px; height: auto; border-radius: 8px; margin-right: 15px;">
				            
				            <!-- Información de la ruta al lado de la imagen -->
				            <div class="w-100">
					            <div class="d-flex justify-content-between">
					                    <div>
					                        <label for="rutaSeleccionada"><strong>Ruta:</strong> <%= ruta.getNombre() %></label>
					                        <p><strong>Estado: </strong> <%= ruta.getEstado() %></p>
					                    </div>
					             </div>
					          
					            <!-- Detalles adicionales de la ruta -->
						        <div id="detalles-<%= ruta.getNombre() %>" class="detalles-ruta mt-3" style="display: <%= isChecked ? "block" : "none" %>;">
						        	<div>
						        		<p><strong>Descripcion: </strong><%= ruta.getDescripcion() %></p>
							            <p><strong>Hora: </strong> <%= ruta.getHora() %></p>
							            <p><strong>Costo Turista: </strong> <%= ruta.getCostoTurista() %></p>
							            <p><strong>Costo Ejecutivo: </strong> <%= ruta.getCostoEjecutivo() %></p>
							            <p><strong>Costo Equipaje Extra: </strong> <%= ruta.getCostoEquipajeExtra() %></p>
							            <p><strong>Ciudad de Origen: </strong> <%= ruta.getCiudadOrigen().getNombre() %></p>
							            <p><strong>Ciudad de Destino: </strong> <%= ruta.getCiudadDestino().getNombre() %></p>
						            </div>
						            
						        </div>
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
				                   onchange="document.getElementById('formReserva').submit(); mostrarDetallesAutomaticamente('<%= vuelo.getNombre() %>')" 
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
				
				                <!-- Detalles del vuelo que se pueden expandir/contraer dentro del mismo cuadro -->
				                <div id="detalles-<%= vuelo.getNombre() %>" class="detalles-vuelo mt-3" 
				                     style="display: <%= isChecked ? "block" : "none" %>;">
				                    <div>
				                    	<p><strong>Duración:</strong> <%= Duration.parse(vuelo.getDuracion()).toHoursPart() %> horas y <%= Duration.parse(vuelo.getDuracion()).toMinutesPart() %> minutos </p>
				                    	<p><strong>Asientos turista Maximo:</strong> <%= vuelo.getMaxTurista() %></p>
				                    	<p><strong>Asientos turista Disponibles:</strong> <%= vuelo.getCantTuristaDisponible() %></p>
				                    	<p><strong>Asientos ejecutivo Maximo:</strong> <%= vuelo.getMaxEjecutivo() %></p>
				                    	<p><strong>Asientos ejecutivo Disponibles:</strong> <%= vuelo.getCantEjecutivoDisponible() %></p>
				                    </div>
				                </div>
				            </div>
				        </div>
				    </div>
				    <% 
				            }
				        } else { 
				    %>
				    <p>No hay vuelos disponibles para la ruta seleccionada.</p>
				    <% } %>
				</div>

				<!-- Contenedor para Tipo de Asiento -->
				<div class="form-group mb-3">  
				    <label for="tipoA">Tipo de asiento:</label>  
				    <div class="form-control">
				        <input type="radio" name="asiento" id="ejecutivo" value="ejecutivo" 
				               <%= "ejecutivo".equals(request.getParameter("asiento")) ? "checked" : "" %> required>Ejecutivo
				        <input type="radio" name="asiento" id="turista" value="turista" 
				               <%= "turista".equals(request.getParameter("asiento")) ? "checked" : "" %> required>Turista
				    </div>
				</div>  
				
				<!-- Contenedor para Cantidad de Pasajes -->
				<div class="form-group mb-3">  
				    <label for="pasajes">Cantidad de pasajes:</label>  
				    <input type="number" id="cantPasajes" name="cantPasajes" class="form-control" min="1" required 
				           placeholder="1" value="<%= request.getAttribute("cantPasajes") != null ? request.getAttribute("cantPasajes") : "1" %>">
				</div>   
								
				<!-- Contenedor para Cantidad de Equipaje Extra -->
				<div class="form-group mb-3">  
				    <label for="equipaje">Cantidad de equipaje extra:</label>  
				    <input type="number" id="equipaje" name="equipaje" class="form-control" min="0" required 
				           placeholder="0" value="<%= request.getAttribute("equipaje") != null ? request.getAttribute("equipaje") : "0" %>">  
				</div>

				
				<!-- Contenedor para Nombres y Apellidos de los Pasajeros -->
				<div class="form-group mb-3">
				    <label for="pasajesList">Nombres y apellidos de los pasajeros:</label> 
				    <div class="form-control" style="border: 1px solid #ddd; padding: 15px; border-radius: 8px;">
				        <div class="custom-scrollbar" id="tablaPasajes">
				            <div class="row">
				                <div class="col">
				                    Nombre
				                </div>
				                <div class="col">
				                    Apellido
				                </div>
				            </div>
				            <%
				                // Recuperar la lista de nombres y apellidos desde la sesión
				                StringArray nombres = (StringArray) request.getAttribute("nombres");
				            	StringArray apellidos = (StringArray) request.getAttribute("apellidos");
				            	 
				                if (nombres != null && !nombres.getItem().isEmpty() && apellidos != null && !apellidos.getItem().isEmpty()) {
				                    for (int i = 0; i < nombres.getItem().size(); i++) {
				                    	//System.out.println("Pasajero jsp: " + nombres.getItem().get(i) + " " + apellidos.getItem().get(i));
				            %>
				            <div class="row">
			                    <div class="col">
			                        <input type="text" class="form-control" name="nombre<%= i %>" value="<%= nombres.getItem().get(i) %>" placeholder="Nombre" required>
			                    </div>
			                    <div class="col">
			                        <input type="text" class="form-control" name="apellido<%= i %>" value="<%= apellidos.getItem().get(i) %>" placeholder="Apellido" required>
			                    </div>
			                </div>
				            <%
				                    }
				                }
				            %>
				        </div>
				    </div>
				</div>
				
				<!-- Contenedor de la forma de pago -->
				<div class="form-group mb-3">
				    <label><strong>Forma de Pago:</strong></label>
				    <div class="form-control" style="border: 1px solid #ddd; padding: 15px; border-radius: 8px;">
				        <div class="switch">
				            <% 
				                String paqueteSeleccionado = request.getParameter("paqueteSeleccionado");
				            %>
				            
				            <input type="radio" id="pagoGeneral" name="formaPago" value="general" 
				                   onchange="togglePaquetes();" 
				                   <%= ("general".equals(request.getParameter("formaPago"))) ? "checked" : "" %> required>
				            <label for="pagoGeneral"><strong>Pago general</strong></label>
				
				            <input type="radio" id="pagoConPaquete" name="formaPago" value="paquete" 
				                   onchange="togglePaquetes();" 
				                   <%= ("paquete".equals(request.getParameter("formaPago"))) ? "checked" : "" %> required>
				            <label for="pagoConPaquete"><strong>Pago con paquete</strong></label>
				        </div>
				    </div>
				</div>
				
				<!-- Contenedor de los paquetes -->
				<div class="custom-scrollbar" id="paquetes" style="display: <%= (paqueteSeleccionado != null) ? "block" : "none" %>;">
				    <p>Paquetes disponibles:</p>
				    <% 
				        DtPaqueteWebArray paquetes = (DtPaqueteWebArray) request.getAttribute("paquetes");
				    %>
				    <% 
				        if (paquetes != null && !paquetes.getItem().isEmpty()) {
				            for (DtPaqueteWeb paquete : paquetes.getItem()) {
				                boolean isChecked = paquete.getNombre().equals(paqueteSeleccionado); 
				    %>
				    <div class="panel mb-3" style="border: 1px solid #ddd; padding: 10px; border-radius: 8px;">
				        <div class="d-flex align-items-start">
				            <input type="radio" name="paqueteSeleccionado" value="<%= paquete.getNombre() %>" 
				                   <%= isChecked ? "checked" : "" %> style="margin-right: 15px;">
				            <img src="<%=request.getContextPath() + "/media/imgs/" + paquete.getImagen() %>" alt="Paquete <%= paquete.getNombre() %>" 
				                 style="width: 120px; height: auto; border-radius: 8px; margin-right: 15px;">
				            <div class="w-100">
				                <div class="d-flex justify-content-between">
				                    <div>
				                        <label><strong>Paquete:</strong> <%= paquete.getNombre() %></label>
				                        <p><strong>Descripción:</strong> <%= paquete.getDescripcion() %></p>
				                        <p><strong>Periodo de Validez:</strong> <%= Duration.parse(paquete.getPeriodoValidez()).toDays() %></p>
				                        <p><strong>Descuento:</strong> <%= paquete.getDescuento() %>%</p>
				                    </div>
				                </div>
				                <div id="detalles-paquete-<%= paquete.getNombre() %>" class="detalles-paquete mt-3" 
				                     style="display: <%= isChecked ? "block" : "none" %>;">
				                    <p><strong>Fecha de Alta:</strong> <%= paquete.getFechaAlta() %></p>
				                </div>
				            </div>
				        </div>
				    </div>
				    <% 
				            }
				        } else { 
				    %>
				    <p>No hay paquetes disponibles en este momento.</p>
				    <% } %>
				</div>
				
<!-- 				
<!-- 				<div class="form-group mb-3"> -->
<!-- 				    <label><strong>Total a pagar:</strong></label> -->
<!-- 				    <div class="form-control" style="border: 1px solid #ddd; padding: 15px; border-radius: 8px;"> -->
<!-- 				        <label for="costoReserva">Costo reserva:</label>   -->
<!-- 				        <input type="number" id="costoReserva" name="costoReserva" class="form-control" min="1" required  -->
<%-- 				               value="<%= request.getSession().getAttribute("costoReserva") != null ? request.getSession().getAttribute("costoReserva") : "0" %>"  --%>
<!-- 				               readonly> Atributo readonly hace que no sea editable -->
<!-- 				    </div> -->
<!-- 				</div> -->
<!-- 				--> 
				
				<!-- Botones de Acción -->
				<div class="form-group">
				    <button type="submit" class="btn btn-primary" name="accion" value="confirmar" id="confirmarBtn">Confirmar</button>
				    <a href="home" class="btn btn-secondary">Cancelar</a>
				</div>  
	        </form>
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
	    <script>
			const radioContainer = document.getElementById('formReserva');
			const v1Container = document.getElementById('datosVuelo1');
			const v2Container = document.getElementById('datosVuelo2');
			const vuelosContainer = document.getElementById('vuelos');
			const paquetesContainer = document.getElementById('paquetes')
			const checkCompra = document.getElementById('compra');
			vuelosContainer.hidden = true;
			v1Container.hidden = true;
			v2Container.hidden = true;
			paquetesContainer.hidden = true;
			checkCompra.checked = false;
			radioContainer.addEventListener('click', (e) => {
			  const tgt = e.target;
			  if(tgt.matches('[id="Vuelo 1"]')) {
				  v1Container.hidden = false;
				  v2Container.hidden = true;
			  }
			  if(tgt.matches('[id="Vuelo 2"]')) {
				  v1Container.hidden = true;
				  v2Container.hidden = false;
			  }
			  if(tgt.matches('[id="Ruta 2"]')) {
				  vuelosContainer.hidden = true;
				  v1Container.hidden = true;
				  v2Container.hidden = true;
			  }
			  if(tgt.matches('[id="Ruta 1"]')) {
				  vuelosContainer.hidden = false;
			  }
			  if(tgt.matches('[id="compra"]')) {
				  paquetesContainer.hidden = !tgt.checked;
			  }
			});
	    </script>
	    
	   <script>
	    var tablaPasajes = document.getElementById('tablaPasajes');
	    const contadorPasajes = document.getElementById('cantPasajes');
	
	    contadorPasajes.addEventListener("change", (ev) => {
	        // Añadir campos de nombre y apellido según el número de pasajeros
	        for (var i = tablaPasajes.childElementCount - 1; i < contadorPasajes.value - 1; i++) {
	            let row = document.createElement("div");
	            row.setAttribute("class", "row");
	            tablaPasajes.appendChild(row);
	
	            // Campo de Nombre
	            let cell = document.createElement("div");
	            cell.setAttribute("class", "col");
	            row.appendChild(cell);
	            let input = document.createElement("input");
	            input.setAttribute("type", "text");
	            input.setAttribute("name", "nombre" + i);
	            input.setAttribute("class", "form-control");
	            input.required = true;
	            input.placeholder = "Nombre";
	            cell.appendChild(input);
	
	            // Campo de Apellido
	            let cell1 = document.createElement("div");
	            cell1.setAttribute("class", "col");
	            row.appendChild(cell1);
	            let input1 = document.createElement("input");
	            input1.setAttribute("type", "text");
	            input1.setAttribute("name", "apellido" + i);
	            input1.setAttribute("class", "form-control");
	            input1.required = true;
	            input1.placeholder = "Apellido";
	            cell1.appendChild(input1);
	        }
	
	        // Remover campos sobrantes si el número de pasajeros se reduce
	        let rows = tablaPasajes.childElementCount - 1;
	        let childs = tablaPasajes.children;
	        for (var i = rows; i > contadorPasajes.value - 1; i--) {
	            tablaPasajes.removeChild(childs[i]);
	        }
	    });
	</script>
	    
	     <!-- Script para expandir automáticamente detalles al seleccionar la ruta -->
	    <script>
		    function handleRutaSeleccionada(rutaNombre) {
		        // Ocultar todos los detalles de rutas
		        var detalles = document.querySelectorAll('.detalles-ruta');
		        detalles.forEach(function(detalle) {
		            detalle.style.display = 'none';
		        });
		
		        // Mostrar el detalle de la ruta seleccionada
		        var detallesSeleccionado = document.getElementById('detalles-' + rutaNombre);
		        detallesSeleccionado.style.display = 'block';
		
		        // Enviar el formulario
		        document.getElementById('formReserva').submit();
		    }
		
		    // Mantener los detalles de la ruta seleccionada visibles al recargar la página
		    window.onload = function() {
		        var rutaSeleccionada = "<%= request.getSession().getAttribute("rutaSeleccionada") != null ? request.getSession().getAttribute("rutaSeleccionada") : "" %>";
		        if (rutaSeleccionada !== "") {
		            handleRutaSeleccionada(rutaSeleccionada);
		        }
		    }
		</script>
	    
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
		
		<script>
		function togglePaquetes() {
		    var pagoConPaquete = document.getElementById('pagoConPaquete');
		    var paquetesDiv = document.getElementById('paquetes');
		    var primerPaq = paquetesDiv.firstElementChild;
		
		    // Mostrar la lista de paquetes si se selecciona "Pago con paquete"
		    paquetesDiv.style.display = pagoConPaquete.checked ? 'block' : 'none';
		
		    // Configurar el atributo required para los radio buttons de paquetes
		    while (primerPaq) {
		        var radioButton = primerPaq.querySelector('input[type="radio"]');
		        if (radioButton) {
		            radioButton.required = pagoConPaquete.checked;
		            if (!pagoConPaquete.checked) {
		                radioButton.checked = false; // Deseleccionamos los paquetes si no es pago con paquete
		            }
		        }
		        primerPaq = primerPaq.nextElementSibling; // Pasamos al siguiente paquete
		    }
		}
		</script>
		    
</body>

</html>    
    