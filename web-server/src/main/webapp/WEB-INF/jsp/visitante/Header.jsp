<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.volandoPuntoUY.model.DtUsuario"%>
<%
boolean esUsuarioRegistrado = false;
String imagenPerfil = "";
String nickname = (String) request.getSession().getAttribute("usuario");

if (nickname == null || nickname.equals("")){
	nickname = "Anónimo";
	imagenPerfil = "perfil_invitado.svg";
}else {

	DtUsuario usuario = (DtUsuario) request.getSession().getAttribute("usuarioDT");
	if (usuario != null) {
		nickname = usuario.getNickname();
		if (usuario.getImagen() != null && usuario.getImagen() != "") {
	imagenPerfil = usuario.getImagen();
		}
		esUsuarioRegistrado = true;
	}
}

String contextPath = request.getContextPath();

String rutaCierre = contextPath + "/cierreSesion";
String rutaLogin = contextPath + "/login";
String rutaRegistro = contextPath + "/altaUsuario";
String rutaPerfil = contextPath + "/datosUsuario?u="+ nickname;

%>

<div class="header">
	<i class="fa-solid fa-bars menu-icon" data-bs-toggle="offcanvas"
		data-bs-target="#categoriesOffcanvas"
		aria-controls="categoriesOffcanvas"></i>

	<div class="logo">
		<span onclick="location='home'">volando.uy</span>
	</div>

	<div class="search-bar">
		<form action="buscar" method="POST">
			<input type="text" id="busqueda" name="busqueda" placeholder="Buscar rutas, destinos..."> <button style="background-color:transparent; border: 0px" type="submit"><i
				class="fa-solid fa-magnifying-glass search-icon" ></i></button>
		</form>
	</div>

	<div class="header-right">
		<span class="username me-2"><%=nickname%></span>

		<div class="dropdown">
			<img src="<%=request.getContextPath() + "/media/imgs/" + imagenPerfil%>" alt="User" class="profile-picture"
				data-bs-toggle="dropdown" aria-expanded="false">
			<ul class="dropdown-menu dropdown-menu-end">
				<%
				if (esUsuarioRegistrado) {
				%>
				<li><a class="dropdown-item" href="<%=rutaPerfil%>">Mi perfil</a></li>
				<li><a class="dropdown-item" href="<%=rutaCierre%>">Cerrar
						sesión</a></li>
				<%
				} else {
				%>
				<li><a class="dropdown-item" href="<%=rutaLogin%>">Iniciar
						sesión</a></li>
				<li><a class="dropdown-item" href="<%=rutaRegistro%>">Registrarme</a></li>
				<%
				}
				%>
			</ul>
		</div>
	</div>
</div>