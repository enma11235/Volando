<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.DtClienteWeb"%>
<%@page import="com.volandoPuntoUY.model.DtReservaWeb"%>
<%@page import="com.volandoPuntoUY.model.DtPasaje"%>
<%@page import="com.volandoPuntoUY.model.DtPasajeArray"%>
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
			<h2>Informacion de la reserva</h2>  
			<hr>
		      <div class="details">
				<% 
				        DtReservaWeb res = (DtReservaWeb) request.getSession().getAttribute("reserva");
						DtClienteWeb cliente = (DtClienteWeb) request.getSession().getAttribute("cliente");
				%>
				<p><strong>Vuelo: <%= res.getNomVuelo() %></strong> 
					<a href="datosVuelo.html"></a>
				</p>
				<!-- Info del cliente que hace la reserva -->
				<hr>
				<h4>Datos del cliente</h4>  
				<hr>
				<p><strong>Nombre:</strong> <%= cliente.getNombre() %></p>
				<p><strong>Apellido:</strong> <%= cliente.getApellido() %></p>
				<p><strong>Tipo de asiento:</strong> <%= res.getTipoAsiento().name() %></p>
				<p><strong>Cantidad Pasajeros :</strong> <%= res.getCantPasajeros() %></p>
				<p><strong>Cant. Equipaje Extra :</strong> <%= res.getCantEquipaje() %></p>
				<p><strong>Fecha Reserva :</strong> <%= res.getFecha() %></p>
				<p><strong>Costo :</strong> <%= res.getCosto() %></p>
				<%if (res.getEmbarque() != null) {
					%>
				<form action="${pageContext.request.contextPath}/detallesReserva?action=descargar" method="get">
				    <button type="submit" class="btn btn-primary" name="action" value="descargar" id="descargarBtn">Descargar PDF</button>
				</form>
				<%}
					%>

		      </div>
		  </div>

        <!-- Info. Pasajeros -->
        <div class="related-section">
            <h4>Datos de los pasajeros</h4>	
			<hr>		
	        <table class="table-passengers">
	            <thead>
	                <tr>
					    <th>Nombre</th>
						<th>Apellido</th>
				    </tr>
				</thead>
				<tbody>
					<% 
						DtPasajeArray pasajes = (DtPasajeArray) request.getSession().getAttribute("pasajeros");
					
					if (pasajes != null && !pasajes.getItem().isEmpty()) {
						for (int i = 0; i < pasajes.getItem().size(); i++) {
							//System.out.println("Pasajero jsp: " + pasajes.getItem().get(i).getNombre() + " " + pasajes.getItem().get(i).getApellido());

					%>
					<tr>
					    <td><%= pasajes.getItem().get(i).getNombre() %> </td>
						<td><%= pasajes.getItem().get(i).getApellido() %></th>
					</tr>
					<% 
						}
					}
					%>						
	            </tbody>
	        </table>					
        </div>
		</div>

    </div>
</body>
</html>
