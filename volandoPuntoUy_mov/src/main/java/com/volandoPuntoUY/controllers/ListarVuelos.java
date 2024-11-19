package com.volandoPuntoUY.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtAerolineaArray;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.DtVueloWeb;
import com.volandoPuntoUY.model.DtVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoEsAerolineaExcepcion_Exception;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;
import com.volandoPuntoUY.model.VueloNoExisteException_Exception;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listarVuelos")
public class ListarVuelos extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private static Publicador port;
	
    public ListarVuelos() {
        super();
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
		        System.err.println("listarVuelos: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("listarVuelos: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("listarVuelos: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();		
        
        DtVueloWebArray dtVuelos = new DtVueloWebArray();
        DtVueloWebArray dtVuelosAer = null;
        String rutaImagen = null;
        try {
            DtAerolineaArray aerolineas = port.listarAerolineasDataWeb();
            for (DtAerolinea aero : aerolineas.getItem()) {
                try {
                    DtRutaDeVueloWebArray rutas = port.listarRutasConfirmadasDTWeb(aero.getNickname());
                    for (DtRutaDeVueloWeb ruta : rutas.getItem()) {
                        try {
                        	dtVuelosAer = port.listarVuelosDTWeb(aero.getNickname(), ruta.getNombre());
                        	for(DtVueloWeb vuel : dtVuelosAer.getItem()) {
                        		dtVuelos.getItem().add(vuel);
        		    			rutaImagen = guardarImagen(vuel.getImagen(), getServletContext());
                        	}
                        } catch (VueloNoExisteException_Exception e) {
                            
                        }
                    }
                } catch (UsuarioNoEsAerolineaExcepcion_Exception e) {
                }
            }
        } catch (UsuarioNoExisteException_Exception e) {
        }

        req.setAttribute("vuelos", dtVuelos);

        req.getRequestDispatcher("/WEB-INF/jsp/visitante/BuscarVuelo.jsp").forward(req, resp);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
