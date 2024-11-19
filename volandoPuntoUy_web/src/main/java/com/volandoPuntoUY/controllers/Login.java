package com.volandoPuntoUY.controllers;
import com.volandoPuntoUY.model.DtCategoria;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.DtUsuario;
import com.volandoPuntoUY.model.DtUsuarioArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Publicador port;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    /**
	 * inicializa la sesión si no estaba creada 
	 * @param request 
	 */
	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.getAttribute("error");
		session.setAttribute("error", "s");
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}
	}
	
	/**
	 * Devuelve el estado de la sesión
	 * @param request
	 * @return 
	 */
	public static String getEstado(HttpServletRequest request)
	{
		return (String) request.getSession().getAttribute("estado_sesion");
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		initSession(req);
		String nickUsado = req.getParameter("nick");
		String passUsado = req.getParameter("pass");
		DtUsuario usu = null;
		// Obtener el directorio home del usuario y construir la ruta a config.properties
		String homeDir = System.getProperty("user.home");
		File configFile = new File(homeDir + File.separator + "volandoUy" + File.separator + "config.properties");

		// Valores predeterminados
		String defaultIp = "localhost";
		String defaultPort = "9129";
		String ipStr = defaultIp;
		String portStr = defaultPort;

		// Verificar si el archivo de configuración existe y cargar sus propiedades si está presente
		Properties config = new Properties();
		if (configFile.exists()) {
		    try (FileInputStream input = new FileInputStream(configFile)) {
		        config.load(input);
		        // Si la IP o el puerto están definidos en el archivo, utilizarlos
		        ipStr = config.getProperty("publicador.ip", defaultIp);
		        portStr = config.getProperty("publicador.port", defaultPort);
		    } catch (IOException e) {
		        System.err.println("login: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("login: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("login: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();		
		
		try {
			usu = port.obtenerInfoUsuario(nickUsado);
		} catch (UsuarioNoExisteException_Exception e) {
			try {
				DtUsuarioArray aux = port.listarUsuarios();
				for(DtUsuario u : aux.getItem()) {
					if(u.getEmail().equals(nickUsado)) {
						nickUsado = u.getNickname();
						usu = port.obtenerInfoUsuario(nickUsado);
						break;
					}
				}
				if(usu == null) {
					req.getSession().setAttribute("error", "El nickname o la contraseña son incorrectos");
					req.getRequestDispatcher("/WEB-INF/jsp/visitante/login.jsp").forward(req, resp);
					return;
				}
			} catch (UsuarioNoExisteException_Exception e1) {
				req.getSession().setAttribute("error", "El nickname o la contraseña son incorrectos");
				req.getRequestDispatcher("/WEB-INF/jsp/visitante/login.jsp").forward(req, resp);
				return;
			}
		}
		if(usu != null && usu.getPass().equals(passUsado)) {
			req.getSession().setAttribute("usuario", usu.getNickname());
			req.getSession().getAttribute("usuarioDT");
			req.getSession().setAttribute("usuarioDT", usu);
			Home.initSession(req);
			resp.sendRedirect("home");
			//req.getRequestDispatcher("/WEB-INF/jsp/visitante/index.jsp").
			//forward(req, resp);
		}
		else {
			req.getSession().setAttribute("error", "El nickname o la contraseña son incorrectos");
			req.getRequestDispatcher("/WEB-INF/jsp/visitante/login.jsp").forward(req, resp);
			return;
		}
		
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initSession(request);
		request.getRequestDispatcher("/WEB-INF/jsp/visitante/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
