<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.DtUsuarioArray"%>
<%@page import="com.volandoPuntoUY.model.DtUsuario"%>
<!DOCTYPE html>
<html lang="es">

	<head>
	
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Volando.uy - Consulta de Usuarios</title>
	
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
		
		    <!-- Contenido de la p�gina QUE SE DESPLIEGA -->
		
			<div class="container">
				<h2>Usuarios registrados</h2>
				
		            <!-- LISTADO DE USUARIOS -->
		            
		              <%DtUsuarioArray users = (DtUsuarioArray) request.getAttribute("usuarios");
	                if(users !=  null){
	                	for(DtUsuario u: users.getItem()){
					%>
					<a href="datosUsuario?u=<%= u.getNickname() %>" class="link">
		                
		                <div class="panel d-flex align-items-center mb-3">
		                    <img src="<%=request.getContextPath() + "/media/imgs/" + u.getImagen() %>"
		                        alt="Usuario (<%= u.getNombre() %>)" style="width: 120px; height: auto; border-radius: 8px;">
		                    <div class="ms-3">
		                        <h5> <%= u.getNombre() %> </h5>
		                        <p><%= u.getEmail() %></p>
		                    </div>
		                </div>
		            </a>
		            <% }
	                	} %>
				   
				</div>
		    
		</div>
	
	    <!-- Barra de b�squeda flotante para m�vil -->
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