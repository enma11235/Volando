package com.volandoPuntoUY.controllers;



import com.volandoPuntoUY.model.TipoDocumento;

import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtClienteWeb;
import com.volandoPuntoUY.model.DtUsuario;
import com.volandoPuntoUY.model.IOException_Exception;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/modificarUsuario")
@MultipartConfig
public class modificarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String basePath = "";
	private static Publicador port;
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modificarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    public static LocalDate convertirCadenaAFecha(String fechaEnCadena) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fechaEnCadena, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //Función para validar la extensión de la imagen
    private boolean esFormatoValido(String ext) {
        // Lista de extensiones válidas para imágenes
        List<String> formatosValidos = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
        return formatosValidos.contains(ext.toLowerCase());
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	basePath = this.getServletContext().getRealPath("/");
		String nickname = (String) request.getParameter("nickname");
        String nombre = (String) request.getParameter("nombre");
        String email = (String) request.getParameter("email");
        String contraseña = (String) request.getParameter("contraseña");
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
		        System.err.println("modificarUsuario: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("modificarUsuario: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("modificarUsuario: Servicio buscado en " + serviceUrl);
		
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
			usu = port.obtenerInfoUsuario(nickname);
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        String imagenActual = usu.getImagen(); // Imagen que ya tiene el usuario   
        
     // Obtener imagen y procesarla
	    Part filePart = request.getPart("imagenPerfilNueva");
	    String rutaIMG = "no_image.jpg";  // Ruta de imagen predeterminada
	    byte[] imageData = null;

	    if (filePart != null && filePart.getSize() > 0) {
	        // Determinar la extensión del archivo
	        String ext = FilenameUtils.getExtension(filePart.getSubmittedFileName());
	        rutaIMG = "usuario" + nombre + "." + ext;

	        try (java.io.InputStream fileContent = filePart.getInputStream()) {
	            // Leer los bytes de la imagen
	            imageData = fileContent.readAllBytes();

	            // Guardar imagen en el servidor central
	            port.saveImage(imageData, rutaIMG);

	            // Guardar imagen localmente
	            String rutaRelativa = "media/imgs/";
	            String rutaAbsoluta = request.getServletContext().getRealPath("/") + rutaRelativa;

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
	        	request.getSession().setAttribute("error", "Error al cargar la imagen.");
	        	request.getRequestDispatcher("/WEB-INF/jsp/visitante/perfil.jsp").forward(request, response);
	            return;
	        } catch (IOException_Exception e) {
	        	request.getSession().setAttribute("error", "Error en el servidor al guardar la imagen.");
	        	request.getRequestDispatcher("/WEB-INF/jsp/visitante/perfil.jsp").forward(request, response);
	            return;
	        }
		} else {
	        // Si no se selecciona imagen mantenemos la anterior
			rutaIMG = imagenActual;
	    }

        try {
        	DtUsuario user = port.obtenerInfoUsuario(nickname); 

        	if(user instanceof DtClienteWeb){
	        	String apellido = (String) request.getParameter("apellido");
	        	String nacionalidad = (String) request.getParameter("nacionalidad");
		        String fechanacimiento = (String) request.getParameter("fechaNacimiento");
				TipoDocumento tipoDoc = TipoDocumento.fromValue(request.getParameter("tipoDocumento"));
				String numeroDocumento = (String) request.getParameter("numeroDocumento");

				LocalDate fecha = convertirCadenaAFecha(fechanacimiento);
				port.editarDatosCliente(nickname, nombre, apellido, contraseña, rutaIMG, fecha.toString(), nacionalidad, tipoDoc, numeroDocumento);
				System.out.println("Se modificaron los datos del usuario cliente");
        	}
        	else if(user instanceof DtAerolinea){
		
	        	String descripcion = (String) request.getParameter("descripcion");
	        	String sitioWeb = (String) request.getParameter("sitioWeb");

	        	port.editarDatosAereolinea(nickname, nombre, contraseña, rutaIMG, descripcion, sitioWeb);
	        	System.out.println("Se modificaron los datos del usuario aerolinea");

        	}
        	usu = port.obtenerInfoUsuario(nickname);
        	request.getSession().setAttribute("usuarioDT", usu);
	        response.sendRedirect(request.getContextPath() + "/datosUsuario?u=" + nickname);
        } catch (Exception e) {
        	e.printStackTrace();
        	String mensajeError = "Ocurrió un error al actualizar los datos del usuario cliente";
            request.setAttribute("mensajeError", mensajeError);
            response.sendRedirect(request.getContextPath() + "/datosUsuario?u=" + nickname);
        }

	}

}
