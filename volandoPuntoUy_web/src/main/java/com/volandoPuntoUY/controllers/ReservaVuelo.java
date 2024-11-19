package com.volandoPuntoUY.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Properties;

import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtAerolineaArray;
import com.volandoPuntoUY.model.DtClienteWeb;
import com.volandoPuntoUY.model.DtPaqueteWeb;
import com.volandoPuntoUY.model.DtPaqueteWebArray;
import com.volandoPuntoUY.model.DtPasaje;
import com.volandoPuntoUY.model.DtPasajeArray;
import com.volandoPuntoUY.model.DtReservaWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.DtVueloWeb;
import com.volandoPuntoUY.model.DtVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;
import com.volandoPuntoUY.model.TipoAsiento;

/**
 * Servlet implementation class reservaVuelo
 */

@MultipartConfig
@WebServlet ("/reservarVuelo")
public class ReservaVuelo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String basePath = "";
	private static Publicador port;

    public ReservaVuelo() {
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
		if (request.getAttribute("error") == null) {
			request.setAttribute("error", "s");
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
		        System.err.println("reservarVuelo: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("reservarVuelo: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("reservarVuelo: Servicio buscado en " + serviceUrl);
		
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
		request.getAttribute("error");
        request.setAttribute("error", "s");
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}

		session.getAttribute("aerolineas");
        
		try {
			request.setAttribute("aerolineas", port.listarAerolineasDataWeb());
			//System.out.println("Aerolinea cargada");
			//System.out.println("------------------");
			// **Vaciar rutas y vuelos anteriores si se selecciona una nueva aerolínea**
			request.removeAttribute("rutas");  // Eliminar rutas anteriores
			request.removeAttribute("vuelos"); // Eliminar vuelos anteriores
			request.removeAttribute("pasajes"); //Eliminar los pasajes anteriores
			request.removeAttribute("paquetes"); //Eliminar los paquetes anteriores
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
		return (String) request.getAttribute("estado_sesion");
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		initSession(req);
	    
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
		        System.err.println("reservarVuelo: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("reservarVuelo: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("reservarVuelo: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();
		
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
	    
	    // Recuperar parámetros del formulario
	    String aerolineaSeleccionada = req.getParameter("aerolineaSeleccionada");
	    String rutaSeleccionada = req.getParameter("rutaSeleccionada");
	    String vueloSeleccionado = req.getParameter("vueloSeleccionado");
	    String paqueteSeleccionado = req.getParameter("paqueteSeleccionado");
	    String asientoSeleccionado = req.getParameter("asiento");
	    String pasajesStr = req.getParameter("cantPasajes");
	    String equipajeStr = req.getParameter("equipaje");
	    String formaPago = req.getParameter("formaPago"); 
	    StringArray listaNombres = null; 
	    StringArray listaApellidos = null; 
	    DtRutaDeVueloWebArray rutas= null;
	    DtVueloWebArray vuelos = null;
	    DtPaqueteWebArray paquetes = null;
	    DtPaqueteWebArray paquetesRet = new DtPaqueteWebArray();
	    StringArray paquetesRuta = null;
	    DtRutaDeVueloWeb ruta = null;
	    DtPasajeArray pasRes = null;
	    TipoAsiento tipoAsiento = null;
	    String nombrePaqueteCliente = "";
	    DtPasaje dtp = null;

	    float costoReservaInt = 0;
	    int pasajesInt = 1;
	    int equipajeInt = 0;

	    if (listaNombres == null) {
	        listaNombres = new StringArray(); 
	    }
	    if (listaApellidos == null) {
	        listaApellidos = new StringArray();
	    }
	    if (pasRes == null) {
	    	pasRes = new DtPasajeArray();
	    }
	    
	    // Manejo de selección de rutas
	    if (aerolineaSeleccionada != null && !aerolineaSeleccionada.isEmpty()) {
	    	 //System.out.println("Aerolinea:"+aerolineaSeleccionada);
	    	 
	    	 // **Vaciar rutas y vuelos anteriores si se selecciona una nueva aerolínea**
	    	 req.removeAttribute("rutas");  // Eliminar rutas anteriores
	    	 req.removeAttribute("vuelos"); // Eliminar vuelos anteriores
	    	 req.removeAttribute("pasajes"); //Eliminar los pasajes anteriores
	    	 req.removeAttribute("paquetes"); //Eliminar los paquetes anteriores
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
	    
	    if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
	    	//System.out.println("Ruta:"+rutaSeleccionada);
	    	req.removeAttribute("vuelos"); // Eliminar vuelos anteriores
	    	req.removeAttribute("pasajes"); //Eliminar los pasajes anteriores
	    	req.removeAttribute("paquetes"); //Eliminar los paquetes anteriores
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

	    // Manejo de selección de paquetes
	    if (vueloSeleccionado != null && !vueloSeleccionado.isEmpty()) {
	    	//System.out.println("Vuelo:"+vueloSeleccionado);
	    	req.removeAttribute("paquetes"); // Eliminar paquetes anteriores
	    	req.removeAttribute("pasajes"); //Eliminar los pasajes anteriores
	        try {
	        	// Paquetes que tiene el cliente
	        	paquetes = port.listarPaquetesCompradosClienteWeb((String) req.getSession().getAttribute("usuario"));
	        	// Nombres de paquetes que tiene la ruta seleccionada
	        	paquetesRuta = port.listarPaquetesRuta(rutaSeleccionada);

	        	// Verificar si algún nombre de los paquetes del cliente está en la lista de paquetes de la ruta
	        	boolean paqueteCoincide = false;

	        	if (paquetes != null && paquetesRuta != null) {
	        	    for (DtPaqueteWeb paqueteCliente : paquetes.getItem()) {
	        	    	LocalDate fechaExpiracion = LocalDate.parse(paqueteCliente.getFechaAlta()).plusDays(Duration.parse(paqueteCliente.getPeriodoValidez()).toDays()); 
	        	        boolean esValidoHoy = true;
	        	    	if(!esValidoHoy)
		            		continue;
	        	        nombrePaqueteCliente = paqueteCliente.getNombre();  // Obtener el nombre del paquete del cliente

	        	        // Verificar si el nombre del paquete del cliente está en la lista de paquetes de la ruta
	        	        if (paquetesRuta.getItem().contains(nombrePaqueteCliente)) {
	        	            paqueteCoincide = true;
	        	            //System.out.println("Paquete coincidente encontrado: " + nombrePaqueteCliente);
	        	            paquetesRet.getItem().add(paqueteCliente);
	        	            if (!paqueteCoincide) {
	    	        	        //System.out.println("No hay paquetes coincidentes.");
	    	        	        req.removeAttribute("paquetes");
	    	        	        rutaImagen = guardarImagen(paqueteCliente.getImagen(), getServletContext());
	    	        	    }
	    	        	    else {
	    	        	    	//paquetes.getItem().clear();
	    	        	    	//paquetes.getItem().add(port.obtenerInfoPaquete(nombrePaqueteCliente));
	    	        	    	req.setAttribute("paquetes", paquetesRet); // Guardar los paquetes en la sesión
	       	        	    }
	        	        }
	        	    }

	        	    if (!paqueteCoincide) {
	        	        //System.out.println("No hay paquetes coincidentes.");
	        	        req.removeAttribute("paquetes");
	        	    }
	        	    else {
	        	    	//paquetes.getItem().clear();
	        	    	//paquetes.getItem().add(port.obtenerInfoPaquete(nombrePaqueteCliente));
	        	    	req.setAttribute("paquetes", paquetesRet); // Guardar los paquetes en la sesión
   	        	    }
	        	} else {
	        	    System.out.println("La lista de paquetes del cliente o de la ruta está vacía.");
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("mensaje", "Error al obtener los paquetes.");
	        }
	    }
	    if (pasajesStr!="1") {
	    	pasajesInt = Integer.parseInt(pasajesStr);
	    	req.setAttribute("cantPasajes", pasajesInt);
    	}

	    if(equipajeStr!="0") {
	    	equipajeInt = Integer.parseInt(equipajeStr);
		    req.setAttribute("equipaje", equipajeInt);
	    }
	    
	  //Boton Confirmar reserva
	    String accion = req.getParameter("accion");
	    if ("confirmar".equals(accion)) {
	    	
	        //System.out.println("Confirmar!");
	        try {	        	
	        	DtClienteWeb cliente = (DtClienteWeb)port.obtenerInfoUsuario((String) req.getSession().getAttribute("usuario"));
    	        dtp = port.crearPasaje(cliente.getNombre(), cliente.getApellido());
    	        pasRes.getItem().add(dtp);
    	        //System.out.println("Pasajero: " + cliente.getNombre() + " " + cliente.getApellido());
    	        
	        	for (int i = 0; i < pasajesInt - 1; i++) {
    	            String nombre = req.getParameter("nombre" + i);
    	            String apellido = req.getParameter("apellido" + i);
    	            if (nombre != null && !nombre.isEmpty() && apellido != null && !apellido.isEmpty()) {
    	                // Agrega el pasaje a la lista de pasRes
    	            	dtp = port.crearPasaje(nombre, apellido);
    	            	if (dtp != null) {
    	            		pasRes.getItem().add(dtp);
    	            	}
            	        listaNombres.getItem().add(nombre);
            	        listaApellidos.getItem().add(apellido);
            	        //System.out.println("Pasajero: " + nombre + " " + apellido);
    	            }
    	        }
	        	req.setAttribute("pasajes", pasRes);
    	        req.setAttribute("nombres", listaNombres);
    	        req.setAttribute("apellidos", listaApellidos);

	        	
	            if (asientoSeleccionado != null && !asientoSeleccionado.isEmpty()) {
	                tipoAsiento = TipoAsiento.valueOf(asientoSeleccionado.toUpperCase()); 
	                DtVueloWeb vuelo = port.obtenerInfoVueloWeb(rutaSeleccionada, vueloSeleccionado);
	                
		            if(tipoAsiento.equals(tipoAsiento.EJECUTIVO))
		            	if(pasajesInt > vuelo.getCantEjecutivoDisponible()) {
		            		req.setAttribute("error", "No hay asientos disponibles");
		            		req.getRequestDispatcher("/WEB-INF/jsp/cliente/reservarVuelo.jsp").forward(req, resp);
		            		return;
		            	}
		            
		            if(tipoAsiento.equals(tipoAsiento.TURISTA))
		            	if(pasajesInt > vuelo.getCantTuristaDisponible()) {
		            		req.setAttribute("error", "No hay asientos disponibles");
		            		req.getRequestDispatcher("/WEB-INF/jsp/cliente/reservarVuelo.jsp").forward(req, resp);
		            		return;
		            	}		
	                
	                if ("paquete".equals(formaPago) && paqueteSeleccionado != null && !paqueteSeleccionado.isEmpty()) {
	                    
	                    DtPaqueteWeb paquete = port.obtenerInfoPaquete(paqueteSeleccionado);
	                    float descuentoPaquete = paquete.getDescuento();
	                    port.reservarVueloConPaquete((String) req.getSession().getAttribute("usuario"), 
                                vueloSeleccionado, tipoAsiento.toString(), pasajesInt, equipajeInt, pasRes, LocalDate.now().toString(), descuentoPaquete);
	                   //System.out.println("Reservado con paquete!");
	                } else if ("general".equals(formaPago)) {
	                	port.reservarVuelo((String) req.getSession().getAttribute("usuario"), 
	                                       vueloSeleccionado, tipoAsiento.toString(), pasajesInt, equipajeInt, pasRes, LocalDate.now().toString());
	                    //System.out.println("Reservado sin paquete!");
	                }        
	                ruta = port.obtenerInfoRutaDeVueloWeb(rutaSeleccionada);
	              
	                if (tipoAsiento.equals(TipoAsiento.TURISTA)) {
	                    costoReservaInt = ruta.getCostoTurista() * pasajesInt;
	                } else if (tipoAsiento.equals(TipoAsiento.EJECUTIVO)) {
	                    costoReservaInt = ruta.getCostoEjecutivo() * pasajesInt;
	                }

	               
	                costoReservaInt += ruta.getCostoEquipajeExtra() * equipajeInt;
	                req.setAttribute("costoReserva", costoReservaInt);

	                DtReservaWeb res = port.obtenerInfoReservaWeb(vueloSeleccionado, (String) req.getSession().getAttribute("usuario"));
	                req.setAttribute("error", "¡Reserva realizada con éxito! Costo: USD" + res.getCosto());
	            } else {
	                req.setAttribute("mensaje", "Por favor seleccione un tipo de asiento.");
	            }
	        } catch (Exception e) {
	            req.setAttribute("error", "Ya existe una reserva suya para este vuelo");
		        req.getRequestDispatcher("/WEB-INF/jsp/cliente/reservarVuelo.jsp").forward(req, resp);
		        return;
	        }
	    }
	    req.getRequestDispatcher("/WEB-INF/jsp/cliente/reservarVuelo.jsp").forward(req, resp);
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
	    request.getRequestDispatcher("/WEB-INF/jsp/cliente/reservarVuelo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    basePath = this.getServletContext().getRealPath("/");
	    processRequest(request, response);
	}
}
