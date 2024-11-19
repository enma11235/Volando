<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ page import="com.volandoPuntoUY.model.DtReservaWeb" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy - Reservas</title>

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
            <h2>Reservas sin check-in</h2>
            <%
                List<DtReservaWeb> reservas = (List<DtReservaWeb>) request.getAttribute("reservas");
                if (reservas != null && !reservas.isEmpty()) {
            %>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>Mostrando <%= reservas.size() %> reservas sin check-in</div>
            </div>

            <%
                for (DtReservaWeb reserva : reservas) {
            %>
                <div class="panel d-flex align-items-center mb-3">
                    <div class="ms-3">
                    <p>
						<a href="detallesReserva?vuelo=<%=reserva.getNomVuelo()%>&nick=<%=reserva.getNicknameCliente()%>"><strong>Reserva en vuelo:</strong>
						<%=reserva.getNomVuelo()%></a></p>
                        <p>Fecha: <%= reserva.getFecha() %></p>
                    </div>
                </div>
            <%
                }
            } else {
            %>
            <p>No hay reservas sin check-in.</p>
            <% } %>
        </div>
    </div>
</body>
</html>
