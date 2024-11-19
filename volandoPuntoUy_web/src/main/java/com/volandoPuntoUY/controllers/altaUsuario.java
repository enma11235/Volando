package com.volandoPuntoUY.controllers;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import com.volandoPuntoUY.model.TipoDocumento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.volandoPuntoUY.model.IOException_Exception;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.UsuarioRepetidoException_Exception;

@MultipartConfig
@WebServlet("/altaUsuario")
public class altaUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String basePath = "";
    private static Publicador port;

    public altaUsuario() {
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
		        System.err.println("altaUsuario: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("altaUsuario: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("altaUsuario: Servicio buscado en " + serviceUrl);
		
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

    private static LocalDate convertirCadenaAFecha(String fechaEnCadena) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fechaEnCadena, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initSession(req);
        
        System.out.println("holaaa");

        String tipoUsuario = req.getParameter("tipoUsuario");
        String nick = req.getParameter("nickname");
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String contrasena = req.getParameter("contraseña");

        String descripcionAerolinea = null;
        String sitioWebAerolinea = null;
        String apellidoCliente = null;
        String fechaNacimiento = null;
        LocalDate fecha = null;
        String nacionalidad = null;
        String tipoDocString = null;
        String numDoc = null;

        
        // Obtener imagen y procesarla
	    Part filePart = req.getPart("imagen");
	    String rutaIMG = "no_image.jpg";  // Ruta de imagen predeterminada
	    byte[] imageData = null;

	    if (filePart != null && filePart.getSize() > 0) {
	        // Determinar la extensión del archivo
	        String ext = FilenameUtils.getExtension(filePart.getSubmittedFileName());
	        rutaIMG = "usu" + nombre + "." + ext;

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


        try {
            System.out.println("Tipo de usuario recibido: " + tipoUsuario);
            
            if ("Aerolinea".equals(tipoUsuario)) {
                descripcionAerolinea = req.getParameter("descripcionAerolinea");
                sitioWebAerolinea = req.getParameter("sitioWebAerolinea");


                port.altaAereolinea(nick, nombre, email, contrasena, descripcionAerolinea, sitioWebAerolinea, rutaIMG);
            } else {
                fechaNacimiento = req.getParameter("fechaNacimiento");
                fecha = convertirCadenaAFecha(fechaNacimiento);
                nacionalidad = req.getParameter("nacionalidad");
                apellidoCliente = req.getParameter("apellidoCliente");
                tipoDocString = req.getParameter("tipoDocumento");
                numDoc = req.getParameter("numDoc");
                

                port.altaCliente(nick, nombre, email, contrasena, apellidoCliente, fecha.toString(), nacionalidad, TipoDocumento.fromValue(tipoDocString), numDoc, rutaIMG);
            }

            System.out.println("Usuario creado correctamente, iniciando sesión...");
            Home.initSession(req); 
            req.getRequestDispatcher("/WEB-INF/jsp/visitante/login.jsp").forward(req, resp);
        } catch (UsuarioRepetidoException_Exception e) {
            System.out.println("Error: Usuario ya existe.");
            req.getSession().setAttribute("error", "El usuario ya existe");
            req.getRequestDispatcher("/WEB-INF/jsp/visitante/registro.jsp").forward(req, resp);
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(); 
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initSessionGet(request);
        request.getRequestDispatcher("/WEB-INF/jsp/visitante/registro.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        basePath = this.getServletContext().getRealPath("/");
        processRequest(request, response);
    }
}
