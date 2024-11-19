<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWebArray"%>
<%@page import="com.volandoPuntoUY.model.DtPaqueteWeb"%>

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


    <div class="main-content">
    <div class="container">
    	<%
    	DtPaqueteWebArray paquetes = (DtPaqueteWebArray) request.getAttribute("paquetes");
       	if (paquetes != null && !paquetes.getItem().isEmpty()) {
       	%>
       	<div class="d-flex justify-content-between align-items-center mb-4"></div>
       	<h2>Paquetes Disponibles</h2>
       	<div class="d-flex justify-content-between align-items-center mb-4">
	                <div>Mostrando <%= paquetes.getItem().size() %> paquetes</div>
	            </div>
       	<%
       		for (DtPaqueteWeb paq : paquetes.getItem()) {
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
	    	<h2>No hay paquetes Disponibles</h2>
	   	<%
	    }
	    %>
	</div>
    </div>
    
    <nav aria-label="Page navigation" class="mt-4">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled"><a class="page-link" href="#">Anterior</a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">Siguiente</a></li>
        </ul>
    </nav>
</body>
</html>