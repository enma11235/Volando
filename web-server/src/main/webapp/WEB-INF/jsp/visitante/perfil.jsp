<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWeb"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtReservaWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtReservaWeb"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWeb"%>



<!DOCTYPE html>
<html lang="es">

	<head>
	
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Volando.uy</title>
	
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
	    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">
	
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	
	    <link rel="stylesheet" href="media/css/style.css">

	</head>
	
	<body>
	<%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>
	    
	 <!-- Contenedor principal -->
		<div class="main-content">
    <!-- Sección de categorías relacionadas -->
    <div class="item-showcase">
        <div class="related-section" id="info" style="display: block;">
            <ul>
             
                <!-- Datos basicos -->
                <div class="container">
                    <div class="main">
                        <div class="row">
                            <div class="col-md-4 mt-1">
                                <div class="card text-center sidebar">
                                    <div class="card-body">
                                        <img src="<%=request.getContextPath() + "/media/imgs/" + request.getAttribute("imagenPerfil")%>" class="rounded-circle" width="250">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-8 mt-1">
                                <div class="card mb-3 content">
                                    <h1 class="m-3 pt-3">Información</h1>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-4 mt-3">
                                                <h5>Nombre:</h5>
                                            </div>
                                            <div class="col-md-8 text-secondary mt-3" id="nombre">
                                                <%= request.getAttribute("nombre") %>  
                                            </div>
                                            <div class="col-md-4 mt-3">
                                                <h5>Nickname:</h5>
                                            </div>
                                            <div class="col-md-8 text-secondary mt-3" id="nickname">
                                                <%= request.getAttribute("nickname") %>
                                            </div>
                                            <div class="col-md-4 mt-3">
                                                <h5>Email:</h5>
                                            </div>
                                            <div class="col-md-8 text-secondary mt-3" id="email">
                                                <%= request.getAttribute("email") %>
                                            </div>
                                             <div class="col-md-4 mt-3">
                                                <h5>Contraseña:</h5>
                                            </div>                                         
											<div class="col-md-8 text-secondary mt-3 d-flex align-items-center">
											    <!-- Campo de contraseña -->
											    <input type="password" id="contraseña" 
											           value="<%= request.getAttribute("contraseña") %>" 
											           class="form-control-plaintext me-2" readonly>
											
											    <!-- Boton para alternar entre mostrar/ocultar -->
											    <button type="button" class="btn btn-sm btn-outline-secondary" 
											            onclick="togglePassword()">Mostrar</button>
											</div>
                                            
                                            <% 
                                                String tipoUsuario = (String) request.getAttribute("tipoUsuario");
                                                if ("Aerolinea".equals(tipoUsuario)) { 
                                            %>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Descripción:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="descripcion">
                                                    <%= request.getAttribute("descripcion") %>
                                                </div>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Sitio Web:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="sitioWeb">
                                                    <%= request.getAttribute("sitioWeb") %>
                                                </div>
                                            <% 
                                                } else if ("Cliente".equals(tipoUsuario)) { 
                                            %>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Apellido:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="apellido">
                                                    <%= request.getAttribute("apellido") %>
                                                </div>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Nacionalidad:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="apellido">
                                                    <%= request.getAttribute("nacionalidad") %>
                                                </div>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Fecha de nacimiento:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="fechaNacimiento">
                                                    <%= request.getAttribute("fechaNacimiento") %>
                                                </div>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Tipo documento:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="tipoDocumento">
                                                    <%= request.getAttribute("tipoDocumento") %>
                                                </div>
                                                <div class="col-md-4 mt-3">
                                                    <h5>Numero documento:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="numeroDocumento">
                                                    <%= request.getAttribute("numeroDocumento") %>
                                                </div>
                                            <% 
                                                } 
                                            %>                                         
                                        </div>
                    					<!--Boton para abrir modal -->
										<div class="text-end mt-3">
										    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editarUsuarioModal">
										        Editar Perfil
										    </button>
										</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
			          <!-- Rutas de la aerolinea -->
					<% if ("Aerolinea".equals(tipoUsuario)) { %>
					<div class="col-md-12 mt-4">
						    <div class="card mb-3 content">
						        <h1 class="m-3 pt-3">Rutas de Vuelo</h1>
						        <div class="card-body">
						            <ul>
						               <% DtRutaDeVueloWebArray rutas = (DtRutaDeVueloWebArray) request.getAttribute("rutasTotales");
										if (!rutas.getItem().isEmpty()) {
										    for (DtRutaDeVueloWeb ruta : rutas.getItem()) { 
										    	//System.out.println("Archivo recuperado correctamente: " + request.getContextPath() + "/media/imgs/" + ruta.getImagen());
										%>																				
										<a href="datosRuta?ruta=<%= ruta.getNombre() %>" class="link">
							                <div class="panel d-flex align-items-center mb-3">
													<img src="<%= request.getContextPath() + "/media/imgs/" + ruta.getImagen() %>" 
     													 alt="Ruta (<%= ruta.getNombre() %>)" style="width: 120px; height: auto; border-radius: 8px;">
	                   						 	<div class="ms-3">
		                       						 <h5>Ruta: <%= ruta.getNombre() %></h5>
		                       						 <h6>Estado: <%= ruta.getEstado().value() %></h6>		                       						 
		                        					<p><%= ruta.getDescripcionCorta() %></p>
		                    					</div>
		                					</div>
		            					</a>
										<% 
										    }
										} else { 
										%>
										<p>No hay rutas disponibles para esta aerolinea.</p>
										<% 
										} 
										%>
						            </ul>
						        </div>
						    </div>
					</div>
					<% } %>
					
					     <!-- Reservas del cliente -->
					<% if ("Cliente".equals(tipoUsuario)) { %>
					<div class="col-md-12 mt-4">
						    <div class="card mb-3 content">
						        <h1 class="m-3 pt-3">Reservas del cliente</h1>
						        <div class="card-body">
						            <ul>
						               <%DtReservaWebArray reservas = (DtReservaWebArray) request.getAttribute("reservas");
										if (reservas != null && !reservas.getItem().isEmpty()) {
										    for (DtReservaWeb reserva : reservas.getItem()) { 
										%>																											
												<a href="detallesReserva?vuelo=<%= reserva.getNomVuelo() %>&nick=<%= request.getAttribute("nickname") %>" class="link"> <!-- Le paso al servlet detalleReserva el nombre del vuelo y el nick del user que reserva -->
									                <div class="panel d-flex align-items-center mb-3">
									                    <img src="<%=request.getContextPath() + "/media/imgs/no_image.jpg"/*reserva.getVuelo().getImagen()*/ %>"
									                        alt="Ruta (<%= reserva.getNomVuelo() %>)" style="width: 120px; height: auto; border-radius: 8px;">
			                   						 	<div class="ms-3">
				                       						 <h5>Vuelo: <%= reserva.getNomVuelo() %></h5>
				                        					 <p>Fecha: <%= reserva.getFecha() %></p>
				                    					</div>
				                					</div>
				            					</a>
										<% 
										    }
										} else { 
										%>
											<p>No existen reservas asociadas para este cliente.</p>
										<% 
										} 
										%>
						            </ul>
						        </div>
						    </div>
					</div>
																				
					     <!-- Paquetes del cliente -->
					<div class="col-md-12 mt-4">
						    <div class="card mb-3 content">
						        <h1 class="m-3 pt-3">Paquetes del cliente</h1>
						        <div class="card-body">
						            <ul>
						                <%DtPaqueteWebArray paq = (DtPaqueteWebArray) request.getAttribute("paquetes");
										if (!paq.getItem().isEmpty()) {
										    for (DtPaqueteWeb paquete : paq.getItem()) { 
										%>																											
			                                	<a href="consultaPaquete?nombre=<%= paquete.getNombre() %>" class="link">
												    <div class="panel d-flex align-items-center mb-3">
												        <img src="<%=request.getContextPath() + "/media/imgs/" + (String) paquete.getImagen() %>"
												            alt="<%= paquete.getNombre() %>" style="width: 120px; height: auto; border-radius: 8px;">
												        <div class="ms-3">
												            <h5> <%= paquete.getNombre() %></h5>
												            <p> <%= paquete.getDescripcion() %></p>
												        </div>
												    </div>
												</a>
										<% 
										    }
										} else { 
										%>
		                                	 <p>No existen paquetes comprados por el cliente.</p>
										<% 
										} 
										%>
						            </ul>
						        </div>
						    </div>
					</div>
					<% } %>
			

					<!-- Modal para editar datos -->
					<div class="modal fade" id="editarUsuarioModal" tabindex="-1" aria-labelledby="editarUsuarioModalLabel" aria-hidden="true">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <h5 class="modal-title" id="editarUsuarioModalLabel">Editar Datos del Usuario</h5>
					                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					            </div>
					            <form method="POST" action="modificarUsuario" enctype="multipart/form-data">
					                <div class="modal-body">
					                	 <!-- Campo oculto para el nickname -->
					                    <input type="hidden" name="nickname" value="<%= request.getAttribute("nickname") %>">
					                     <!-- Campo oculto para el email -->
					                    <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
					                    
					                    <div class="mb-3">
										    <label for="imagenPerfilNueva" class="form-label">Imagen de Perfil</label>
										    <input type="file" class="form-control" id="imagenPerfilNueva" name="imagenPerfilNueva" accept="image/*">
										</div>
					                    <div class="mb-3">
					                        <label for="nombre" class="form-label">Nombre</label>
					                        <input type="text" class="form-control" id="nombre" name="nombre" 
					                               value="<%= request.getAttribute("nombre") %>" required>
					                    </div>
					
					                    <div class="mb-3">
					                        <label for="contraseña" class="form-label">Contraseña</label>
					                        <input type="text" class="form-control" id="contraseña" name="contraseña" 
					                               value="<%= request.getAttribute("contraseña") %>" required>
					                    </div>
					
					                    <% if ("Cliente".equals(request.getAttribute("tipoUsuario"))) { %>
					                        <div class="mb-3">
					                            <label for="apellido" class="form-label">Apellido</label>
					                            <input type="text" class="form-control" id="apellido" name="apellido" 
					                                   value="<%= request.getAttribute("apellido") %>" required>
					                        </div>
					                        <div class="mb-3">
					                            <label for="nacionalidad" class="form-label">Nacionalidad</label>
					                            <input type="text" class="form-control" id="nacionalidad" name="nacionalidad" 
					                                   value="<%= request.getAttribute("nacionalidad") %>" required>
					                        </div>
					                         <div class="mb-3">
					                            <label for="fechaNacimiento" class="form-label">Fecha de nacimiento</label>
					                            <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" 
					                                   value="<%= request.getAttribute("fechaNacimiento") %>" required>
					                        </div>
					                         <div class="mb-3">
						                         <label for="tipoDocumentoSelect" class="form-label">Tipo de Documento</label>
						                         <select class="form-select" id="tipoDocumentoSelect" name="tipoDocumento">
						                             <option value="CedulaIdentidad" 
						                                 <%= "CedulaIdentidad".equals(request.getAttribute("tipoDocumento")) ? "selected" : "" %>>Cédula de Identidad</option>
						                             <option value="Pasaporte" 
						                                 <%= "Pasaporte".equals(request.getAttribute("tipoDocumento")) ? "selected" : "" %>>Pasaporte</option>
						                             <option value="Extranjero" 
						                                 <%= "Extranjero".equals(request.getAttribute("tipoDocumento")) ? "selected" : "" %>>Extranjero</option>
						                         </select>
						                     </div>
					                         <div class="mb-3">
					                            <label for="numeroDocumento" class="form-label">Numero documento</label>
					                            <input type="text" class="form-control" id="numeroDocumento" name="numeroDocumento" 
					                                   value="<%= request.getAttribute("numeroDocumento") %>" required>
					                        </div>
					                    <% } else { %>
					                        <div class="mb-3">
					                            <label for="descripcion" class="form-label">Descripción</label>
					                            <textarea class="form-control" id="descripcion" name="descripcion" rows="3" 
					                                      required><%= request.getAttribute("descripcion") %></textarea>
					                        </div>
					                        <div class="mb-3">
					                            <label for="sitioWeb" class="form-label">Sitio web</label>
					                            <input type="text" class="form-control" id="sitioWeb" name="sitioWeb" value="<%= request.getAttribute("sitioWeb") %>"
					                                      required>
					                        </div>
					                    <% } %>
					                </div>
					                <div class="modal-footer">
					                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
					                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>
					                </div>
					            </form>
					        </div>
					    </div>
					</div>
                </div>
            </ul>
        </div>
    </div>
</div>

<script>
    function togglePassword() {
        const passwordField = document.getElementById("contraseña");
        const toggleButton = event.target;

        // Cambiar entre tipo 'password' y 'text'
        if (passwordField.type == "password") {
            passwordField.type = "text";
            toggleButton.textContent = "Ocultar";
        } else {
            passwordField.type = "password";
            toggleButton.textContent = "Mostrar";
        }
    }
</script>

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
