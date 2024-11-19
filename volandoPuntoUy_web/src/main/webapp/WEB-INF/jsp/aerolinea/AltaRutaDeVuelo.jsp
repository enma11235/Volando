<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.lang.String"%>
<%@page import="com.volandoPuntoUY.model.DtCiudadWeb"%>
<%@page import="com.volandoPuntoUY.model.DtCiudadWebArray"%>
<%@page import="com.volandoPuntoUY.model.StringArray"%>

<!DOCTYPE html>
<html lang="es">

<head>
    <!-- Meta y enlaces -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volando.uy - Alta Ruta de Vuelo</title>

    <!-- Enlaces a Bootstrap, Font Awesome y fuentes -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">

    <!-- Scripts de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="media/css/style.css">
    <script>
			document.addEventListener("DOMContentLoaded", function() {
				if("<%=request.getSession().getAttribute("error")%>" != "s")alert("<%=request.getSession().getAttribute("error")%>");
			});
	</script>
</head>

<body>
    <!-- Header -->
    <%@ include file="Header.jsp" %>

	<%@ include file="Sidebar.jsp" %>


    <!-- Contenedor principal -->
	<div class="main-content">
   		<div class="item-showcase">
			<div class="container">
				<div class="row">
					<h1 class="m-4 pt-3">Nueva Ruta</h1>
					<hr>
			        <form method="POST" enctype="multipart/form-data">
			        	<div class="form-group">
			            	<label for="nombre"><strong>Nombre:</strong></label>
			            	<input type="text" id="nombre" name="nombre" class="form-control" required>
			            </div>
			            <div class="form-group">
			            	<label for="descripcion"><strong>Descripción:</strong></label>
			            	<textarea id="descripcion" name="descripcion" class="form-control" rows="5" required></textarea>
			            </div>
			            <div class="form-group">
			            	<label for="descripcionC"><strong>Descripción Corta:</strong></label>
			            	<input type="text" id="descripcionC" name="descripcionC" class="form-control" required></input>
			            </div>
			            <div class="form-group">
			            	<label for="hora"><strong>Hora:</strong></label>
			            	<input type="time" id="hora" name="hora" class="form-control" required>
			            </div>
				        <div class="form-group">
			            	<label for="costoTurista"><strong>Turista:</strong></label>
			            	<input type="number" id="costoTurista" name="costoTurista" step="0.01" class="form-control" required>
			            </div>
			            <div class="form-group">
			            	<label for="costoEjecutivo"><strong>Ejecutivo:</strong></label>
			            	<input type="number" id="costoEjecutivo" name="costoEjecutivo" step="0.01" class="form-control" required>
			            </div>
			            <div class="form-group">
			            	<label for="costoEquipaje"><strong>Equipaje Extra:</strong></label>
			            	<input type="number" id="costoEquipaje" name="costoEquipaje" step="0.01" class="form-control" required>
			            </div>
			            
			            <div class="form-group">
			            	<label for="video"><strong>Video:</strong></label>
			            	<input type="text" id="video" name="video" class="form-control"></input>
			            </div>
			            
			            <div class="form-group">
			            	<label for="ciudadOrigen"><strong>Ciudad de Origen</strong>:</label>
			            	<select id="ciudadOrigen" name="ciudadOrigen" class="form-control" required>
			            	 <% 
								DtCiudadWebArray ciudades = (DtCiudadWebArray) request.getSession().getAttribute("ciudades");
				                if(ciudades !=  null){
				                	for(DtCiudadWeb c: ciudades.getItem()){
								%>
			            		<option value="<%= c.getPais() +","+ c.getNombre()%>"><%= c.getNombre() %>, <%= c.getPais() %></option>
			            	<%
				                	}
				                }
								%>
			            	</select>
			            </div>
			            <div class="form-group">
			            	<label for="ciudadDestino"><strong>Ciudad de Destino:</strong></label>
			            	<select id="ciudadDestino" name="ciudadDestino" class="form-control" required>
			            	 <% 
				                if(ciudades !=  null){
				                	for(DtCiudadWeb c: ciudades.getItem()){
								%>
			            		<option value="<%= c.getPais() +","+ c.getNombre()%>"><%= c.getNombre() %>, <%= c.getPais() %></option>
			            	<%
				                	}
				                }
								%>
			            	</select>
			            </div>
			            <div class="form-group">
			            	<label for="categorias"><strong>Categorías:</strong></label>
			            	<select id="categorias" name="categorias" class="form-control" multiple size="6" required>
			            	 <% 
								StringArray categs = (StringArray) request.getSession().getAttribute("categorias");
				                if(categs !=  null){
				                	for(String c: categs.getItem()){
								%>
			            		<option value="<%= c %>"><%= c %></option>
			            	<%
				                	}
				                }
								%>
			            	</select>
			            </div>
			            <!-- Campo para cargar una imagen -->
			            <div class="form-group">
			            	<label for="imagen"><strong>Cargar Imagen:</strong></label>
			            	<input type="file" id="imagen" name="imagen" class="form-control" accept="image/*">
			           	</div>
						<button type="submit" class="btn btn-primary btn-block mt-3">Agregar Ruta</button>
			        </form>
				</div>
			</div>
		</div>
	</div>
</body>

</html>    
    