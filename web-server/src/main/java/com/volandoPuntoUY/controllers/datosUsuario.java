package com.volandoPuntoUY.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtClienteWeb;
import com.volandoPuntoUY.model.DtPaqueteWeb;
import com.volandoPuntoUY.model.DtPaqueteWebArray;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.DtUsuario;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.StringArray;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/datosUsuario")
public class datosUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Publicador port;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public datosUsuario() {
        super();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	//----------------------------------------------------------------
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
		        System.err.println("datosUsuario: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("datosUsuario: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("datosUsuario: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();		
        //----------------------------------------------------------------
		String nickUsuarioLogueado = (String) request.getSession().getAttribute("usuario");
		String nickUser = (String) request.getParameter("u");
		/*List<DTRutaDeVuelo> rutasConfirmadas = ICRV.listarRutasConfirmadas(nickuser); */
        try {       
    		DtUsuario usuario = port.obtenerInfoUsuario(nickUser);
			request.setAttribute("nickname", usuario.getNickname());
			request.setAttribute("nombre", usuario.getNombre());
			request.setAttribute("email", usuario.getEmail());
			request.getAttribute("imagenPerfil");
			request.setAttribute("imagenPerfil", usuario.getImagen());
			request.setAttribute("contraseña", usuario.getPass());
			rutaImagen = guardarImagen(usuario.getImagen(), getServletContext());
			
			if (usuario instanceof DtAerolinea) {
				DtAerolinea aerolinea = (DtAerolinea) usuario;
				request.setAttribute("tipoUsuario", "Aerolinea"); 
				request.setAttribute("descripcion", aerolinea.getDescripcion());
				request.setAttribute("sitioWeb", aerolinea.getSitioWeb());
				request.setAttribute("rutas", port.listarRutasConfirmadasDTWeb(nickUser));
				StringArray totalRutas = port.listarRutasDeVueloWeb(nickUser);
				DtRutaDeVueloWebArray rutasDeAerolinea = new DtRutaDeVueloWebArray(); 
					
				for(String ruta : totalRutas.getItem()) {
					DtRutaDeVueloWeb dtrvw = port.obtenerInfoRutaDeVueloWeb(ruta);
					rutasDeAerolinea.getItem().add(dtrvw);
					rutaImagen = guardarImagen(dtrvw.getImagen(), getServletContext());
				}
				request.setAttribute("rutasTotales", rutasDeAerolinea);//Para el perfil */

            } else if (usuario instanceof DtClienteWeb) {
				DtClienteWeb cliente = (DtClienteWeb) usuario;
				request.setAttribute("tipoUsuario", "Cliente"); 
				request.setAttribute("apellido", cliente.getApellido());
				request.setAttribute("fechaNacimiento", cliente.getNacimiento());
				request.setAttribute("nacionalidad", cliente.getNacionalidad());
				request.setAttribute("tipoDocumento", cliente.getTipoDocumento().value());
				request.setAttribute("numeroDocumento", cliente.getNumDocumento());
				request.setAttribute("reservas",port.listarReservasClienteWeb(nickUser));
				DtPaqueteWebArray dtpa = port.listarPaquetesCompradosClienteWeb(nickUser);
				request.setAttribute("paquetes",dtpa);
				for(DtPaqueteWeb paquete : dtpa.getItem()) {
					rutaImagen = guardarImagen(paquete.getImagen(), getServletContext());
				}
				if (rutaImagen != null) {
			        // Guardar la ruta en el request para usarla en el JSP
			        request.setAttribute("rutaImagen", rutaImagen);
			    } else {
			        request.setAttribute("error", "No se pudo guardar la imagen.");
			    }
            }
			
		
			if (nickUser.equals(nickUsuarioLogueado)) {
				request.getRequestDispatcher("/WEB-INF/jsp/visitante/perfil.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/WEB-INF/jsp/visitante/datosUsuario.jsp").forward(request, response);			
			}

        } catch (Exception e) {
            // Si no existe user, pagina de errorrrr
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El usuario no existe.");
        }
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
