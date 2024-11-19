<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWeb"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtRutaDeVueloWeb"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>

<!DOCTYPE html>
<html>
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
	    <!-- Header -->
    <%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>

	<%
    	String orden = (String)request.getSession().getAttribute("orden");
	%>
    <div class="main-content">
    <form action="buscar?orden=<%=request.getSession().getAttribute("orden")%>" method="GET">
      	<button type="submit" class="btn btn-primary float-end" name="accion" id="confirmarBtn"><%= orden %></button>
   	</form>
    <div class="container">
    	<%
    	DtPaqueteWebArray paquetesA = (DtPaqueteWebArray) request.getSession().getAttribute("paquetesList");
    	ArrayList<DtPaqueteWeb> paquetes =(ArrayList<DtPaqueteWeb>) paquetesA.getItem();
       	if (paquetes != null && !paquetes.isEmpty()) {
       	%>
       	
       	<h2>Paquetes Encontrados</h2>
       	
       	<div class="d-flex justify-content-between align-items-center mb-4">
	                <div>Mostrando <%= paquetes.size() %> paquetes</div>
	            </div>
       	<%
       		for (DtPaqueteWeb paq : paquetes) {
       	%>
	         
	              	
	              		<div class="panel d-flex align-items-center mb-3">
	           				<img src="<%=request.getContextPath() + "/media/imgs/" + paq.getImagen() %>" style="width: 120px; height: auto; border-radius: 8px;">
	           				<div class="ms-3">
	           					<!-- aqui puede haber un problema si el nombre del paquete tiene espacios -->
			               		<a href="consultaPaquete?nombre=<%= paq.getNombre() %>"><h5>Paquete: <%=paq.getNombre()%></h5></a>
			               		<p><%=paq.getDescripcion()%></p>
	           				</div>
	       				</div>
	              	
	             
	   	<%
	    	}
	    } else {
	    %>
	    	<h2>Paquetes Encontrados</h2>
	    	<div>Mostrando <%= paquetes.size() %> paquetes</div>
	   	<%
	    }
	    %>
	</div>
	
	<div class="container">
			<h2>Rutas Encontradas</h2>
			<% DtRutaDeVueloWebArray rutas = (DtRutaDeVueloWebArray) request.getSession().getAttribute("rutasList");
			
			if (rutas != null) { 
				
			%>
			
			<div class="d-flex justify-content-between align-items-center mb-4">
	                <div>Mostrando <%= rutas.getItem().size() %> rutas</div>
	            </div>
			

			<%
			
				for (DtRutaDeVueloWeb ruta : rutas.getItem()) {
			%>
			
				<div class="panel d-flex align-items-center mb-3">
					<img src="<%=request.getContextPath() + "/media/imgs/" + ruta.getImagen()%>"
						alt="Ruta (<%=ruta.getNombre()%>)"
						style="width: 120px; height: auto; border-radius: 8px;">
					<div class="ms-3">
						<h5>
							<a href="datosRuta?ruta=<%=ruta.getNombre()%>" class="link">Ruta: (<%=ruta.getNombre()%>)</a>
						</h5>
						<p><%=ruta.getDescripcionCorta()%></p>
					</div>
				</div>
			
			<%
			}
			}
			%>

		</div>
    </div>
</body>
</html>