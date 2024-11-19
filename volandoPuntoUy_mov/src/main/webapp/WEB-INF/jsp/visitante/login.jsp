<!DOCTYPE html>
<html lang="es">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Volando.uy Login</title>
	    <link href="https://fonts.cdnfonts.com/css/poppins" rel="stylesheet">
	    <link rel="stylesheet" href="media/css/login_style.css">
	    <script>
			document.addEventListener("DOMContentLoaded", function() {
				if("<%=request.getSession().getAttribute("error")%>" != "s")alert("<%=request.getSession().getAttribute("error")%>");
			});
		</script>
	</head>

	<body>
	    <div id="particles-js"></div>
	
	    <div class="glass-container">
	        <div class="login-form">
	            <center>
					<form action="login" method="post">
		                <h2 class="logo"><span onclick="location='home'" >volando.uy</span></h2>
		                <input type="text" placeholder="Nickname" name="nick" required>
		                <input type="password" placeholder="Contraseņa" name="pass" required>
		                <button class="vuy_button" type="submit">Login</button>
		             
		            </form>
	            </center>
	        </div>
	        <div class="image-side"></div>
	    </div>
	
	    <script src="https://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js"></script>
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
	</body>

</html>