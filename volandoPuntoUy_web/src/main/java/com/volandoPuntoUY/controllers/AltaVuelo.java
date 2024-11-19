package com.volandoPuntoUY.controllers;


import jakarta.servlet.ServletException;
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
import java.time.Duration;
import java.time.LocalDate;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.volandoPuntoUY.model.IOException_Exception;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.UsuarioNoEsAerolineaExcepcion_Exception;
import com.volandoPuntoUY.model.VueloRepetidoException_Exception;

@WebServlet("/altaVuelo")
@MultipartConfig
public class AltaVuelo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String basePath = "";
    private static Publicador port;

    public AltaVuelo() {
        super();
    }

    public static void initSession(HttpServletRequest request) {
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
		        System.err.println("altaVuelo: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("altaVuelo: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("altaVuelo: Servicio buscado en " + serviceUrl);
		
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

        session.getAttribute("rutas");
        try {
            String nicknameAerolinea = (String) session.getAttribute("usuario");
            session.setAttribute("rutas", port.listarRutasDeVueloWeb(nicknameAerolinea));
        } catch (UsuarioNoEsAerolineaExcepcion_Exception e) {
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initSession(req);
        String nombre = req.getParameter("nombre");
        String fechaVueloStr = req.getParameter("fechaVuelo");
        String duracionStr = req.getParameter("duracion");
        String cantAsientosTuristaStr = req.getParameter("asientosTuristas");
        String cantAsientosEjecutivoStr = req.getParameter("asientosEjecutivos");
        String nomRuta = req.getParameter("ruta");
        LocalDate fechaAlta = LocalDate.now();

        LocalDate fechaVuelo = LocalDate.parse(fechaVueloStr);
        Duration duracion = Duration.ofMinutes(Long.parseLong(duracionStr));
        int cantAsientosTurista = Integer.parseInt(cantAsientosTuristaStr);
        int cantAsientosEjecutivo = Integer.parseInt(cantAsientosEjecutivoStr);
        
        // Obtener imagen y procesarla
	    Part filePart = req.getPart("imagen");
	    String rutaIMG = "no_image.jpg";  // Ruta de imagen predeterminada
	    byte[] imageData = null;

	    if (filePart != null && filePart.getSize() > 0) {
	        // Determinar la extensión del archivo
	        String ext = FilenameUtils.getExtension(filePart.getSubmittedFileName());
	        rutaIMG = "vuelo" + nombre + "." + ext;

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
	            req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaVuelo.jsp").forward(req, resp);
	            return;
	        } catch (IOException_Exception e) {
	            req.getSession().setAttribute("error", "Error en el servidor al guardar la imagen.");
	            req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaVuelo.jsp").forward(req, resp);
	            return;
	        }
	    }

        try {
            port.altaVuelo(nomRuta, nombre, fechaVuelo.toString(), duracion.toString(), cantAsientosTurista, cantAsientosEjecutivo, fechaAlta.toString(), rutaIMG);
        } catch (VueloRepetidoException_Exception e) {
            req.getSession().setAttribute("error", "El vuelo ya existe");
            req.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaVuelo.jsp").forward(req, resp);
            return;
        }

        Home.initSession(req);
        req.getRequestDispatcher("/WEB-INF/jsp/visitante/index.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initSessionGet(request);
        request.getRequestDispatcher("/WEB-INF/jsp/aerolinea/AltaVuelo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        basePath = this.getServletContext().getRealPath("/");
        processRequest(request, response);
    }
}