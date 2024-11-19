package com.volandoPuntoUY.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;


import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.volandoPuntoUY.model.IOException_Exception;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.RutaDeVueloRepetidaException_Exception;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoEsAerolineaExcepcion_Exception;

/**
 * Servlet implementation class Home
 */

@MultipartConfig
@WebServlet ("/altaRuta")
public class AltaRutaDeVuelo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String basePath = "";
	private static Publicador port;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaRutaDeVuelo() {
        super();
    }

    /**
	 * inicializa la sesión si no estaba creada 
	 * @param request 
	 */
	public static void initSession(HttpServletRequest request) {
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
		        System.err.println("altaRuta: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("altaRuta: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("altaRuta: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();		

		HttpSession session = request.getSession();
		session.setAttribute("error", "s");
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}
	}
	
	public static void initSessionGet(HttpServletRequest request) {	
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
		        System.err.println("altaRuta: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("altaRuta: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("altaRuta: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();		
		
		HttpSession session = request.getSession();
		session.getAttribute("error");
		session.setAttribute("error", "s");
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}
		session.getAttribute("categorias");
		session.setAttribute("categorias", port.listarCategorias());
		session.getAttribute("ciudades");
		session.setAttribute("ciudades", port.listarCiudades());
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
	    String nombre = req.getParameter("nombre");
	    String desc = req.getParameter("descripcion");
	    String descCorta = req.getParameter("descripcionC");
	    String hora = req.getParameter("hora");
	    String video = req.getParameter("video");

	    // Parsear la hora en distintos formatos
	    DateTimeFormatter formatterHMM = DateTimeFormatter.ofPattern("H:mm");
	    DateTimeFormatter formatterHHMM = DateTimeFormatter.ofPattern("HH:mm");
	    LocalTime h;
	    try {
	        h = LocalTime.parse(hora, formatterHMM);
	    } catch (DateTimeParseException e) {
	        h = LocalTime.parse(hora, formatterHHMM);
	    }
	    
	    // Obtener y parsear costos
	    float costoTurista = Float.parseFloat(req.getParameter("costoTurista"));
	    float costoEjecutivo = Float.parseFloat(req.getParameter("costoEjecutivo"));
	    float costoEquipaje = Float.parseFloat(req.getParameter("costoEquipaje"));
	    
	    // Definir claves de ciudades origen y destino
	    String[] ciudadOrigen = req.getParameter("ciudadOrigen").split(",\\s*");
	    String[] ciudadDestino = req.getParameter("ciudadDestino").split(",\\s*");
	    String claveCiudadO = ciudadOrigen[0] + "-" + ciudadOrigen[1];
	    String claveCiudadD = ciudadDestino[0] + "-" + ciudadDestino[1];
	    
	    if (claveCiudadO.equals(claveCiudadD)) {
	        req.getSession().setAttribute("error", "Las ciudades de origen y destino son iguales");
	        req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaRutaDeVuelo.jsp").forward(req, resp);
	        return;
	    }
	    
	    // Categorías
	    String[] cats = req.getParameterValues("categorias");
	    StringArray categs = new StringArray();
	    for (String cat : cats) {
	        categs.getItem().add(cat);
	    }
	    
	    // Obtener imagen y procesarla
	    Part filePart = req.getPart("imagen");
	    String rutaIMG = "no_image.jpg";  // Ruta de imagen predeterminada
	    byte[] imageData = null;

	    if (filePart != null && filePart.getSize() > 0) {
	        // Determinar la extensión del archivo
	        String ext = FilenameUtils.getExtension(filePart.getSubmittedFileName());
	        rutaIMG = "ruta" + nombre + "." + ext;

	        try (java.io.InputStream fileContent = filePart.getInputStream()) {
	            // Leer los bytes de la imagen
	            imageData = fileContent.readAllBytes();

	            // Guardar imagen en el servidor central
	            port.saveImage(imageData, rutaIMG);

	            // Guardar imagen localmente
	            String rutaRelativa = "media/imgs/";
	            String rutaAbsoluta = req.getServletContext().getRealPath("/") + rutaRelativa;

	            // Crear el directorio si no existe
	            File directorio = new File(rutaAbsoluta);
	            if (!directorio.exists()) {
	                if (!directorio.mkdirs()) {
	                    throw new IOException("No se pudo crear el directorio: " + rutaAbsoluta);
	                }
	            }

	            // Escribir la imagen en el directorio local
	            File archivoImagen = new File(rutaAbsoluta + rutaIMG);
	            try (FileOutputStream fos = new FileOutputStream(archivoImagen)) {
	                fos.write(imageData);
	            }

	            System.out.println("Imagen guardada en: " + archivoImagen.getAbsolutePath());
	        } catch (IOException e) {
	            req.getSession().setAttribute("error", "Error al cargar la imagen.");
	            req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaRutaDeVuelo.jsp").forward(req, resp);
	            return;
	        } catch (IOException_Exception e) {
	            req.getSession().setAttribute("error", "Error en el servidor al guardar la imagen.");
	            req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaRutaDeVuelo.jsp").forward(req, resp);
	            return;
	        }
	    }

	    // Llamada a agregarRutaDeVuelo en el servicio
	    try {
	        port.agregarRutaDeVuelo((String) req.getSession().getAttribute("usuario"), nombre, desc, descCorta, h.toString(), 
	                                costoTurista, costoEjecutivo, costoEquipaje, claveCiudadO, claveCiudadD, 
	                                LocalDate.now().toString(), categs, rutaIMG, video);
	    } catch (RutaDeVueloRepetidaException_Exception e) {
	        req.getSession().setAttribute("error", "La ruta ya existe");
	        req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaRutaDeVuelo.jsp").forward(req, resp);
	        return;
	    } catch (UsuarioNoEsAerolineaExcepcion_Exception e) {
	        req.getSession().setAttribute("error", "El usuario no tiene permisos para agregar rutas de vuelo.");
	        req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaRutaDeVuelo.jsp").forward(req, resp);
	        return;
	    }

	    // Redirigir al usuario a la página de éxito o a la página principal
	    req.getRequestDispatcher("/WEB-INF/jsp/visitante/index.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initSessionGet(request);
		request.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaRutaDeVuelo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		basePath = this.getServletContext().getRealPath("/");
		processRequest(request, response);
	}

}
