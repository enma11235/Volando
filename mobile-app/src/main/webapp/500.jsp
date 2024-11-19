<!DOCTYPE html>
<html>
<head>
    <title>Error 500 - Server Error</title>
</head>
<body>
    <h1>Error 500 - Ocurrió un problema en el servidor</h1>
    <p>Lo sentimos, ha ocurrido un error en el servidor. Por favor, intenta nuevamente más tarde.</p>
    
    <!-- Puedes agregar detalles para el desarrollador durante la fase de pruebas -->
    <% 
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
    %>
    <h3>Detalles del error:</h3>
    <pre><%= exception.getMessage() %></pre>
    <% } %>
</body>
</html>
