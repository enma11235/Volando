<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWeb"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWeb"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtVueloWeb"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.Duration"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy - Ruta ZL1502</title>

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



        <!-- Contenido de la pÃ¡gina -->

		<%
				DtRutaDeVueloWeb ruta = (DtRutaDeVueloWeb) request.getSession().getAttribute("ruta");
		%>


        <div class="item-showcase">
            <img src="<%=request.getContextPath() + "/media/imgs/" + ruta.getImagen() %>" alt="<%= ruta.getNombre() %>" class="flight-image">

            <div class="details">
                <h2>Ruta: <%= ruta.getDescripcionCorta() %></h2>
                <p><strong>Descripción:</strong> <%= ruta.getDescripcion() %></p>

                <div class="flight-info">
                    <div>
                        <p><strong>Origen:</strong> <%= ruta.getCiudadOrigen().getPais() %>, <%= ruta.getCiudadOrigen().getNombre() %></p>
                        <p><strong>Destino:</strong> <%= ruta.getCiudadDestino().getPais() %>, <%= ruta.getCiudadDestino().getNombre() %></p>
                    </div>
                    <div>
                        <p><strong>Costo Turista:</strong> USD <%= ruta.getCostoTurista() %></p>
                        <p><strong>Costo Ejecutivo:</strong> USD <%= ruta.getCostoEjecutivo() %></p>
                        <p><strong>Costo Equipaje Extra:</strong> USD <%= ruta.getCostoEquipajeExtra() %></p>
                    </div>
                    <div>
                        <p><strong>Fecha de Alta:</strong> <%= LocalDate.parse(ruta.getFechaAlta()).getDayOfMonth()%>/<%= LocalDate.parse(ruta.getFechaAlta()).getMonthValue() %>/<%= LocalDate.parse(ruta.getFechaAlta()).getYear() %></p>
                        <p><strong>Estado:</strong> <span class="status-<%= ruta.getEstado().value() %>"><%= ruta.getEstado().value() %></span></p>
                    </div>
                </div>
            </div>
        </div>

         <div class="related-section">
            <h5>Vuelos Asociados</h5>
            <%
				DtVueloWebArray vuelos = (DtVueloWebArray) request.getSession().getAttribute("vuelos");
            	for(DtVueloWeb v :vuelos.getItem()){
			%>
            <div class="related-item">
			     <div class="item-showcase d-flex align-items-center">
					<img src="<%=request.getContextPath() + "/media/imgs/" + v.getImagen() %>" alt="<%= v.getNombre() %>" style="width: 210px; height: auto; border-radius: 8px; margin-right: 15px;">
				     <div>
				        <h6><a href="datosVuelo?nomVuelo=<%=v.getNombre()%>">Vuelo: <%= v.getNombre() %></a></h6>
				        <p>Duración: <%= Duration.parse(v.getDuracion()).toHoursPart()%> horas <%= Duration.parse(v.getDuracion()).toMinutesPart() %> minutos</p>
				    </div>
        		</div>
			</div>
			<%
            	}
			%>
        </div>
        
        

        <!-- SecciÃ³n de paquetes asociados -->
		<div class="related-section">
		    <h5>Paquetes Asociados</h5>
		    <%
				DtPaqueteWebArray paquetes = (DtPaqueteWebArray) request.getSession().getAttribute("paquetes");
            	for(DtPaqueteWeb p :paquetes.getItem()){
			%>
		    <div class="related-item">
			     <div class="item-showcase d-flex align-items-center">
					<img src="<%=request.getContextPath() + "/media/imgs/" + p.getImagen() %>" alt="Paquete <%= p.getNombre() %>" style="width: 210px; height: auto; border-radius: 8px; margin-right: 15px;">
				     <div>
				        <h6><a href="consultaPaquete?nombre=<%= p.getNombre()%>">Paquete: <%= p.getNombre()%></h6></a>
				        <p><%= p.getDescripcion() %></p>
				    </div>
        		</div>
			</div>
			<%
            	}
			%>
		</div>
		<%if (ruta.getVideo().contains("youtu")) {
			String rutaVideo = ruta.getVideo();
			String[] partes = rutaVideo.split("/");
			rutaVideo = partes[0] + "/" + "/" + partes[2] + "/embed/" + partes[3].split("\\?")[0];
					%>
			<div class="related-section">
			<h5>Video</h5>
				 <iframe width="888" height="500"
				src="<%= rutaVideo.replace("youtu.be", "www.youtube.com") %> ">
				</iframe> 
			</div>
		<%}
				%>
		<%if (paquetes.getItem().isEmpty() && ruta.getEstado().value().equals("Confirmada") && ruta.getAerolinea().getNickname().equals(session.getAttribute("usuario"))) {
			%>
		<form action="datosRuta?ruta=<%= ruta.getNombre() %>" method="POST">
	      	<button type="submit" class="btn btn-primary float-end" name="accion" id="confirmarBtn">Finalizar Ruta</button>
	   	</form>
	   	<%}
				%>
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
