<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.volandoPuntoUY.model.TipoDocumento"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Volando.uy Login</title>
<link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">
<link rel="stylesheet" href="media/css/login_style.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
	<div id="particles-js"></div>
	<div class="glass-container">
		<div class="login-form">
			<div style="text-align: center;">
				<h2 class="logo">
					<span onclick="location='home'">volando.uy</span>
				</h2>

				<%
				String tipoUsuario = request.getParameter("tipoUsuario");
				String nick = request.getParameter("nickname");
				String nombre = request.getParameter("nombre");
				String email = request.getParameter("email");

				String descripcionAerolinea = null;
				String sitioWebAerolinea = null;

				String apellidoCliente = null;
				String fechaNacimiento = null;
				LocalDate fNacimiento = null;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				String nacionalidad = null;
				TipoDocumento tipoDoc = null;
				String numDoc = null;

				if ("Aerolinea".equals(tipoUsuario)) {
					descripcionAerolinea = request.getParameter("descripcion");
					sitioWebAerolinea = request.getParameter("sitioWeb");
				} else {
					fechaNacimiento = request.getParameter("fechaNacimiento");
					nacionalidad = request.getParameter("nacionalidad");
				}
				%>
				<%
				String mensajeError = (String) request.getAttribute("alert");
				if (mensajeError != null && !mensajeError.isEmpty()) {
				%>
				<div class="alert alert-danger"><%=mensajeError%></div>
				<%
				}
				%>

				<form action="altaUsuario" method="POST"
					enctype="multipart/form-data">
					<!-- Botones pra elegir el tipo de usuario -->
					<div>
						<input type="radio" id="Cliente" name="tipoUsuario"
							value="Cliente"
							<%=("Cliente".equals(tipoUsuario)) ? "checked" : ""%>> <label
							for="cliente">Cliente</label> <input type="radio" id="Aerolinea"
							name="tipoUsuario" value="Aerolinea"
							<%=("Aerolinea".equals(tipoUsuario)) ? "checked" : ""%>>
						<label for="aerolinea">Aerolínea</label>
					</div>

					<!-- Info del user -->
					<input type="text" id="nickname-input" name="nickname"
						placeholder="Nickname" required
						value="<%=nick != null ? nick : ""%>">
					<br>
					<span id="nicknameStatus" class="status-message"></span> <input
						type="text" id="nombre-input" name="nombre" placeholder="Nombre"
						required value="<%=nombre != null ? nombre : ""%>"> <input
						type="email" id="email-input" name="email" placeholder="Email"
						required value="<%=email != null ? email : ""%>">
					<br>
					<span id="emailStatus" class="status-message"></span> <br><input
						type="password" id="contraseña-input" name="contraseña"
						placeholder="Contraseña" required> <input type="password"
						id="confirmar-contraseña-input" name="confirmar-contraseña"
						placeholder="Confirmar contraseña" required>
					<br>
					<!-- User aerolinea-->
					<div id="campos-Aerolinea" style="display: none;">
						<div class="form-group">
							<textarea class="form-control" rows="5" id="descripcion-input"
								name="descripcionAerolinea" placeholder="Descripcion" required
								></textarea>
						</div>
						<input type="url" id="sitio-web-input" name="sitioWebAerolinea"
							placeholder="Sitio Web "
							value="<%=sitioWebAerolinea != null ? sitioWebAerolinea : ""%>">
					</div>

					<!-- User cliente-->
					<div id="campos-Cliente" style="display: none;">
						<input type="text" id="apellido-input" name="apellidoCliente"
							placeholder="Apellido" required
							value="<%=apellidoCliente != null ? apellidoCliente : ""%>">
						<div>
							<label for="fechanac"><strong>Fecha de
									nacimiento:</strong></label> <input type="date" id="fecha-nacimiento-input"
								name="fechaNacimiento" required
								value="<%=fechaNacimiento != null ? fechaNacimiento : ""%>">
						</div>
						<input type="text" id="nacionalidad-input" name="nacionalidad"
							placeholder="Nacionalidad" required
							value="<%=nacionalidad != null ? nacionalidad : ""%>"> <select
							class="form-select form-select-lg mb-3" name="tipoDocumento"
							style="width: 80%">
							<option name="Pasaporte">Pasaporte</option>
							<option name="CedulaIdentidad" selected>CedulaIdentidad</option>
							<option name="Extranjero" selected>Extranjero</option>
						</select> <input type="text" id="numDoc-input" name="numDoc"
							placeholder="Documento" required
							value="<%=numDoc != null ? numDoc : ""%>">
					</div>

					<div class="form-control">
						<label for="imagen"><strong>Imagen usuario:</strong></label> <input
							type="file" id="imagen-input" name="imagen" class="form-control"
							accept="image/*">
					</div>

					<button class="vuy_button" type="submit">Registrar</button>
					<p>
						<a href="<%=request.getContextPath()%>/login">Login</a>
					</p>
				</form>
			</div>
		</div>
		<div class="image-side"></div>
	</div>

</body>

<script>
		    const clienteRadio = document.getElementById('Cliente');
		    const aerolineaRadio = document.getElementById('Aerolinea');
		    const aerolineaFields = document.getElementById('campos-Aerolinea');
		    const clienteFields = document.getElementById('campos-Cliente');
		   
		    clienteRadio.addEventListener('change', toggleFields);
		    aerolineaRadio.addEventListener('change', toggleFields);
		    clienteRadio.checked = false;
		    aerolineaRadio.checked = false;

		    function toggleFields() {
		        if (aerolineaRadio.checked) {
		        	document.getElementById('descripcion-input').required = true;
		        	document.getElementById('apellido-input').required = false;
		        	document.getElementById('fecha-nacimiento-input').required = false;
		        	document.getElementById('nacionalidad-input').required = false;
		        	document.getElementById('numDoc-input').required = false;
		            aerolineaFields.style.display = 'block';
		            clienteFields.style.display = 'none';
		        }
		        if (clienteRadio.checked) {
		        	document.getElementById('descripcion-input').required = false;
		        	document.getElementById('apellido-input').required = true;
		        	document.getElementById('fecha-nacimiento-input').required = true;
		        	document.getElementById('nacionalidad-input').required = true;
		        	document.getElementById('numDoc-input').required = true;
		            aerolineaFields.style.display = 'none';
		            clienteFields.style.display = 'block';
		        }
		    }
		</script>

<!-- Validacion de campos-->
<script>
			document.querySelector("form").addEventListener("submit", function(event) {
		  	const contraseña = document.getElementById("contraseña-input").value;
		  	const confirmcontraseña = document.getElementById("confirmar-contraseña-input").value;
			
			  if (contraseña !== confirmcontraseña) {
			      event.preventDefault(); // Evita el envio del formulario
			      alert("Las contraseñas no coinciden.");
			  }
		 });
			
			
		</script>

<script>
		    document.getElementById("nickname-input").addEventListener("input", function () {
			    let nickname = document.getElementById("nickname-input").value;
			    if (nickname.length > 0) {
			        fetch("validacionUsuario?tipo=nickname&valor=" + encodeURIComponent(nickname))
			            .then(response => response.json())
			            .then(data => {
			                const nicknameStatus = document.getElementById("nicknameStatus");
			                if (data.isAvailable) {
			                    nicknameStatus.innerText = "Nickname disponible";
			                    nicknameStatus.style.color = "green";
			                } else {
			                    nicknameStatus.innerText = "Nickname no disponible";
			                    nicknameStatus.style.color = "red";
			                }
			            })
			            .catch(error => console.error("Error:", error));
			    } else {
			        document.getElementById("nicknameStatus").innerText = "";
			    }
			});

		
		    document.getElementById("email-input").addEventListener("input", function () {
			    let email = document.getElementById("email-input").value;
			    if (email.length > 0) {
			        fetch("validacionUsuario?tipo=email&valor=" + encodeURIComponent(email))
			            .then(response => response.json())
			            .then(data => {
			                const nicknameStatus = document.getElementById("emailStatus");
			                if (data.isAvailable) {
			                    nicknameStatus.innerText = "Email disponible";
			                    nicknameStatus.style.color = "green";
			                } else {
			                    nicknameStatus.innerText = "Email no disponible";
			                    nicknameStatus.style.color = "red";
			                }
			            })
			            .catch(error => console.error("Error:", error));
			    } else {
			        document.getElementById("emailStatus").innerText = "";
			    }
			});
		</script>




<script
	src="https://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js"></script>
<script>
	        particlesJS('particles-js', {
	            "particles": {
	                "number": {
	                    "value": 80,
	                    "density": {
	                        "enable": true,
	                        "value_area": 800
	                    }
	                },
	                "color": {
	                    "value": "#007bff"
	                },
	                "shape": {
	                    "type": "circle",
	                    "stroke": {
	                        "width": 0,
	                        "color": "#000000"
	                    },
	                    "polygon": {
	                        "nb_sides": 5
	                    },
	                    "image": {
	                        "src": "img/github.svg",
	                        "width": 100,
	                        "height": 100
	                    }
	                },
	                "opacity": {
	                    "value": 0.5,
	                    "random": false,
	                    "anim": {
	                        "enable": false,
	                        "speed": 1,
	                        "opacity_min": 0.1,
	                        "sync": false
	                    }
	                },
	                "size": {
	                    "value": 3,
	                    "random": true,
	                    "anim": {
	                        "enable": false,
	                        "speed": 40,
	                        "size_min": 0.1,
	                        "sync": false
	                    }
	                },
	                "line_linked": {
	                    "enable": true,
	                    "distance": 150,
	                    "color": "#007bff",
	                    "opacity": 0.12794882047181133,
	                    "width": 1.3
	                },
	                "move": {
	                    "enable": true,
	                    "speed": 3,
	                    "direction": "none",
	                    "random": false,
	                    "straight": false,
	                    "out_mode": "out",
	                    "bounce": false,
	                    "attract": {
	                        "enable": false,
	                        "rotateX": 3198.720511795283,
	                        "rotateY": 1200
	                    }
	                }
	            },
	            "interactivity": {
	                "detect_on": "canvas",
	                "events": {
	                    "onhover": {
	                        "enable": false,
	                        "mode": "repulse"
	                    },
	                    "onclick": {
	                        "enable": false,
	                        "mode": "push"
	                    },
	                    "resize": true
	                },
	                "modes": {
	                    "grab": {
	                        "distance": 400,
	                        "line_linked": {
	                            "opacity": 1
	                        }
	                    },
	                    "bubble": {
	                        "distance": 400,
	                        "size": 40,
	                        "duration": 2,
	                        "opacity": 8,
	                        "speed": 3
	                    },
	                    "repulse": {
	                        "distance": 200,
	                        "duration": 0.4
	                    },
	                    "push": {
	                        "particles_nb": 4
	                    },
	                    "remove": {
	                        "particles_nb": 2
	                    }
	                }
	            },
	            "retina_detect": true
	        });
	    </script>

</html>