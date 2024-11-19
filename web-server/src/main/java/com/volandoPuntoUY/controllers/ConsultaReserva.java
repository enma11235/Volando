package com.volandoPuntoUY.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtAerolineaArray;
import com.volandoPuntoUY.model.DtClienteWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.DtUsuario;
import com.volandoPuntoUY.model.DtVueloWeb;
import com.volandoPuntoUY.model.DtVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;

/**
 * Servlet implementation class consultaReserva
 */

@MultipartConfig
@WebServlet ("/consultaReserva")
public class ConsultaReserva extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String basePath = "";
    private static Publicador port;

    public ConsultaReserva() {
        super();
    }

    // Método para inicializar el servlet
    @Override
    public void init() throws ServletException {
        super.init(); 
    }
       
    /**
	 * inicializa la sesión si no estaba creada 
	 * @param request 
	 */
	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
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
		        System.err.println("consultaReserva: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("consultaReserva: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		//System.out.println("consultaReserva: Servicio buscado en " + serviceUrl);
		
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
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}

		session.getAttribute("aerolineas");

        try {
        	request.setAttribute("aerolineas",port.listarAerolineasDataWeb());
			//System.out.println("Aerolinea cargada");
			//System.out.println("------------------");			   
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	    String rutaImagen = null;
	    try {
        	DtAerolineaArray dtaeroa = port.listarAerolineasDataWeb();
        	req.setAttribute("aerolineas",dtaeroa);
        	for(DtAerolinea dta : dtaeroa.getItem()) {
    			rutaImagen = guardarImagen(dta.getImagen(), getServletContext());
        	}
			//System.out.println("Aerolinea cargada");
			//System.out.println("------------------");			   
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
        	req.setAttribute("aerolineas",port.listarAerolineasDataWeb());			   
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Recuperar parámetros del formulario
	    String aerolineaSeleccionada = req.getParameter("aerolineaSeleccionada");
	    String rutaSeleccionada = req.getParameter("rutaSeleccionada");
	    String vueloSeleccionado = req.getParameter("vueloSeleccionado");
	    
	    DtRutaDeVueloWebArray rutas= null;
	    DtVueloWebArray vuelos = null;
	    StringArray reservas = null;

	    if (aerolineaSeleccionada != null && !aerolineaSeleccionada.isEmpty()) {
	    	 //System.out.println("Aerolinea:"+aerolineaSeleccionada);
	    	 
	    	 // **Vaciar rutas y vuelos anteriores si se selecciona una nueva aerolínea**
	    	 req.removeAttribute("rutas");  // Eliminar rutas anteriores
	    	 req.removeAttribute("vuelos"); // Eliminar vuelos anteriores
	    	 req.removeAttribute("reservas"); // Eliminar reservas anteriores
	        try {
	        	aerolineaSeleccionada = req.getParameter("aerolineaSeleccionada");
	            // Obtener la lista de rutas para la aerolínea seleccionada
	            rutas = port.listarRutasConfirmadasDTWeb(aerolineaSeleccionada);
	            // Verificar que se hayan encontrado rutas
		        if (rutas.getItem() != null && !rutas.getItem().isEmpty()) {
		        	req.setAttribute("rutas", rutas);
		        	for(DtRutaDeVueloWeb rdt : rutas.getItem()) {
		    			rutaImagen = guardarImagen(rdt.getImagen(), getServletContext());
		        	}
		        } else {
		        	req.setAttribute("mensaje", "No hay rutas disponibles para la aerolínea seleccionada.");
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("mensaje", "Error al obtener las rutas para la aerolínea seleccionada.");
	            System.out.println(req.getAttribute("mensaje"));
	        }   
	    } else {
	        req.setAttribute("mensaje", "Por favor seleccione una aerolínea.");
	    }
	    
	    if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty() && aerolineaSeleccionada != null) {
	    	//System.out.println("Ruta:"+rutaSeleccionada);
	    	req.removeAttribute("vuelos"); // Eliminar vuelos anteriores
	    	req.removeAttribute("reservas"); // Eliminar reservas anteriores
	        try {
	            // Obtener la lista de vuelos para la ruta seleccionada
	            vuelos = port.listarVuelosDTWeb(aerolineaSeleccionada, rutaSeleccionada);
	            // Verificar que se hayan encontrado vuelos
		        if (vuelos.getItem() != null && !vuelos.getItem().isEmpty()) {
		        	req.setAttribute("vuelos", vuelos); // Guardar los vuelos en la sesión para usarlas en el JSP
		        	for(DtVueloWeb vdt : vuelos.getItem()) {
		    			rutaImagen = guardarImagen(vdt.getImagen(), getServletContext());
		        	}
		        } else {
		        	req.setAttribute("mensaje", "No hay vuelos disponibles para la ruta seleccionada.");
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("mensaje", "Error al obtener los vuelos para la ruta seleccionada.");
	        } 
	    }

	    // Manejo de selección de reservas
	    if (vueloSeleccionado != null && !vueloSeleccionado.isEmpty()) {
	    	//System.out.println("Vuelo:"+vueloSeleccionado);
	    	req.removeAttribute("reservas"); // Eliminar reservas anteriores
	        try {
	        	// Obtener los detalles de las reservas seleccionado o la lista de paquetes disponibles
	            reservas = port.listarReservasWeb(vueloSeleccionado);
	            // Verificar que se hayan encontrado paquetes
		        if (reservas.getItem() != null && !reservas.getItem().isEmpty()) {
		        	DtUsuario usrDT = port.obtenerInfoUsuario((String) req.getSession().getAttribute("usuario"));
		            if (usrDT.getClass().equals(DtClienteWeb.class)) {
		            	reservas.getItem().removeIf(reserva -> !reserva.equals((String) req.getSession().getAttribute("usuario")));
		            } 
		        	req.setAttribute("reservas", reservas); // Guardar los paquetes en la sesión
		        } else {
		        	req.setAttribute("mensaje", "No hay reservas disponibles.");
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("mensaje", "Error al obtener la reserva del cliente.");
	        } 
	    }
	    req.getRequestDispatcher("/WEB-INF/jsp/cliente/datosReserva.jsp").forward(req, resp);
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
	    initSessionGet(request);
	    request.getRequestDispatcher("/WEB-INF/jsp/cliente/datosReserva.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    basePath = this.getServletContext().getRealPath("/");
	    processRequest(request, response);
	}
}
