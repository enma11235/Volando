<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.lang.String"%>
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
                                                <h5>NickName:</h5>
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
                                                    <h5>Nro documento:</h5>
                                                </div>
                                                <div class="col-md-8 text-secondary mt-3" id="numeroDocumento">
                                                    <%= request.getAttribute("numeroDocumento") %>
                                                </div>
                                                
	              
                                            <% 
                                                } 
                                            %>                                         
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
			          <!-- Rutas confirmadas de la aerolinea -->
					<% if ("Aerolinea".equals(tipoUsuario)) { %>
					<div class="col-md-12 mt-4">
						    <div class="card mb-3 content">
						        <h1 class="m-3 pt-3">Rutas de Vuelo</h1>
						        <div class="card-body">
						            <ul>
						                <%DtRutaDeVueloWebArray rutas = (DtRutaDeVueloWebArray) request.getAttribute("rutas");
										if (!rutas.getItem().isEmpty()) {
										    for (DtRutaDeVueloWeb ruta : rutas.getItem()) { 
										%>																											
										<a href="datosRuta?ruta=<%= ruta.getNombre() %>" class="link">
							                <div class="panel d-flex align-items-center mb-3">
							                    <img src="<%=request.getContextPath() + "/media/imgs/" + ruta.getImagen() %>"
							                        alt="Ruta (<%= ruta.getNombre() %>)" style="width: 120px; height: auto; border-radius: 8px;">
	                   						 	<div class="ms-3">
		                       						 <h5>Ruta: (<%= ruta.getNombre() %>)</h5>
		                        					<p><%= ruta.getDescripcionCorta() %></p>
		                    					</div>
		                					</div>
		            					</a>
										<% 
										    }
										} else { 
										%>
										<li>No hay rutas disponibles para esta aerolinea.</li>
										<% 
										} 
										%>
						            </ul>
						        </div>
						    </div>
					</div>
					<% } %>
                </div>
            </ul>
        </div>
    </div>
</div>

</body>

</html>

