package com.volandoPuntoUY.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
import com.volandoPuntoUY.model.DtRutaPaqueteWeb;
import com.volandoPuntoUY.model.DtUsuario;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;

@WebServlet("/consultaPaquete")
public class ConsultaPaquete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Publicador port;
	
    public ConsultaPaquete() {
        super();
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		        System.err.println("consultaPaquete: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("consultaPaquete: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("consultaPaquete: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();				

        String nombrePaquete = request.getParameter("nombre");
        String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
        
        if (nombrePaquete == null) {
        	//si no se envio ningun parametro, devolver todos los paquetes
        	StringArray nombresPaquetes = (StringArray) port.listarPaquetes();
    		DtPaqueteWebArray setPaquetes = new DtPaqueteWebArray();
    		for(String paq : nombresPaquetes.getItem()) {
    			DtPaqueteWeb dtp = port.obtenerInfoPaquete(paq);
    			setPaquetes.getItem().add(dtp);
				rutaImagen = guardarImagen(dtp.getImagen(), getServletContext());
    		}
            request.setAttribute("paquetes", setPaquetes);
            
            request.getRequestDispatcher("/WEB-INF/jsp/consultaPaquete.jsp").forward(request, response);
            
        } else {
        	// Si se envio el  parámetro nombre, enviar los datos de ese paquete específico
        	DtPaqueteWeb datosPaquete = port.obtenerInfoPaquete(nombrePaquete);
            request.setAttribute("datosPaquete", datosPaquete);
            
            float precio = 0; 
			if (!datosPaquete.getRutas().isEmpty()) {
				for (DtRutaPaqueteWeb RP : datosPaquete.getRutas()){
					precio = precio + RP.getCosto(); 
					rutaImagen = guardarImagen(RP.getRuta().getImagen(), getServletContext());
				}
			}
			request.setAttribute("precioPaquete", precio);
            String nickname = (String) request.getSession().getAttribute("usuario");
            DtUsuario usuario = null;
            if (nickname != null && !nickname.equals("")){
        		try {
					usuario = port.obtenerInfoUsuario(nickname);
				} catch (UsuarioNoExisteException_Exception e) {
					e.printStackTrace();
				}
        	}

            if (usuario != null) {
                if (usuario instanceof DtClienteWeb) {
                	request.setAttribute("tipoUsuario", "cliente");
                } else if (usuario instanceof DtAerolinea) {
                	request.setAttribute("tipoUsuario", "aerolinea");
                } 
            } else {
            	request.setAttribute("tipoUsuario", "visitante");
            }
            request.getRequestDispatcher("/WEB-INF/jsp/datosPaquete.jsp").forward(request, response);
  
        }
       
	}

}
