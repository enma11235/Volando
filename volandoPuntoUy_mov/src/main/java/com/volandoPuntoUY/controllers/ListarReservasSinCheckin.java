package com.volandoPuntoUY.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtAerolineaArray;
import com.volandoPuntoUY.model.DtReservaWeb;
import com.volandoPuntoUY.model.DtReservaWebArray;
import com.volandoPuntoUY.model.DtVueloWeb;
import com.volandoPuntoUY.model.DtVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.ReservaNoExisteException_Exception;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoEsAerolineaExcepcion_Exception;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;
import com.volandoPuntoUY.model.VueloNoExisteException_Exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listarReservasSinCheckin")
public class ListarReservasSinCheckin extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private static Publicador port;

    public ListarReservasSinCheckin() {
        super();
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String usuario = (String) req.getSession().getAttribute("usuario");

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
        
        DtReservaWebArray allReservas = null;
		try {
			allReservas = port.listarReservasClienteWeb(usuario);
		} catch (ReservaNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List<DtReservaWeb> reservasSinCheckin = new ArrayList<>();
        for (DtReservaWeb reserva : allReservas.getItem()) {
            if (reserva.getEmbarque() == null) { 
                reservasSinCheckin.add(reserva);
            }
        }
        
        req.setAttribute("reservas", reservasSinCheckin);

        req.getRequestDispatcher("/WEB-INF/jsp/cliente/listarReservas.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}