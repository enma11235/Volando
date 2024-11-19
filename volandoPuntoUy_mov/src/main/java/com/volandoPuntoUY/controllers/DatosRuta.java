package com.volandoPuntoUY.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.volandoPuntoUY.model.DtPaqueteWeb;
import com.volandoPuntoUY.model.DtPaqueteWebArray;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtVueloWeb;
import com.volandoPuntoUY.model.DtVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.RutaDeVueloNoExisteException_Exception;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.VueloNoExisteException_Exception;

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
@WebServlet ("/datosRuta")
public class DatosRuta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Publicador port;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatosRuta() {
        super();
    }

    /**
	 * inicializa la sesión si no estaba creada 
	 * @param request 
	 */
	public static void initSession(HttpServletRequest request) {
		
		
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
		HttpSession session = req.getSession();
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}
		// Obtener el directorio home del usuario y construir la ruta a config.properties
		String homeDir = System.getProperty("user.home");
		File configFile = new File(homeDir + File.separator + "volandoUy" + File.separator + "config.properties");

		// Valores predeterminados
		String defaultIp = "localhost";
		String defaultPort = "9129";
		String ipStr = defaultIp;
		String portStr = defaultPort;
		String rutaImagen = null;

		// Verificar si el archivo de configuración existe y cargar sus propiedades si está presente
		Properties config = new Properties();
		if (configFile.exists()) {
		    try (FileInputStream input = new FileInputStream(configFile)) {
		        config.load(input);
		        // Si la IP o el puerto están definidos en el archivo, utilizarlos
		        ipStr = config.getProperty("publicador.ip", defaultIp);
		        portStr = config.getProperty("publicador.port", defaultPort);
		    } catch (IOException e) {
		        System.err.println("datosRuta: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("datosRuta: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("datosRuta: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();		
		
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}
		
		String nomRuta = (String) req.getParameter("ruta");
		port.sumarVisita(nomRuta);
		session.getAttribute("ruta");
		try {
			DtRutaDeVueloWeb dtrv = port.obtenerInfoRutaDeVueloWeb(nomRuta);
			session.setAttribute("ruta", dtrv);
			rutaImagen = guardarImagen(dtrv.getImagen(), getServletContext());
		} catch (RutaDeVueloNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DtVueloWebArray vuel = null;
		session.getAttribute("vuelos");
		try {
			vuel = port.listarVuelosDTWeb(port.obtenerNicknameAerolineaDeRuta(nomRuta), nomRuta);
			if (vuel != null) {
				for(DtVueloWeb vdt : vuel.getItem()) {
	    			rutaImagen = guardarImagen(vdt.getImagen(), getServletContext());
	        	}
			}
		} catch (VueloNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("vuelos", vuel);
		StringArray paque = port.listarPaquetesRuta(nomRuta);
		DtPaqueteWebArray paquetes = new DtPaqueteWebArray();
		for(String p : paque.getItem()) {
			DtPaqueteWeb dtp = port.obtenerInfoPaquete(p);
			paquetes.getItem().add(dtp);
			rutaImagen = guardarImagen(dtp.getImagen(), getServletContext());
		}
		session.getAttribute("paquetes");
		session.setAttribute("paquetes", paquetes);
		req.getRequestDispatcher("/WEB-INF/jsp/visitante/datosRutaDeVuelo.jsp").
		forward(req, resp);
		
	}
	
	//Funcion para recuperar imagenes y guardarlas en media/imgs realtivo 
    public String guardarImagen(String nombreArchivo, ServletContext context) {
        // Obtener la ruta absoluta del directorio
        String rutaRelativa = "media/imgs/";
        String rutaAbsoluta = context.getRealPath("/") + rutaRelativa;

        File dir = new File(rutaAbsoluta);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Directorio creado: " + dir.getAbsolutePath());
            } else {
                System.out.println("No se pudo crear el directorio: " + dir.getAbsolutePath());
                return null;  // Salir si no se puede crear el directorio
            }
        }

        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            nombreArchivo = "no_image.jpg";  // Nombre por defecto
        }

        byte[] archivoBytes = null;
        try {
            archivoBytes = port.getFile(nombreArchivo);
            if (archivoBytes == null) {
                throw new IOException("No se pudo recuperar el archivo.");
            }

            File archivoGuardado = new File(rutaAbsoluta + File.separator + nombreArchivo);
            try (FileOutputStream fos = new FileOutputStream(archivoGuardado)) {
                fos.write(archivoBytes);
            }

            //System.out.println("Archivo guardado correctamente: " + archivoGuardado.getAbsolutePath());
            return rutaAbsoluta;  // Devolver la ruta completa del archivo guardado

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al guardar el archivo: " + nombreArchivo);
            return null;
        }
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
