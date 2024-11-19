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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Properties;


import org.apache.commons.lang3.StringUtils;
//import org.apache.tomcat.jakartaee.commons.lang3.StringUtils;

import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.DtPaqueteWeb;
import com.volandoPuntoUY.model.DtPaqueteWebArray;

/**
 * Servlet implementation class Home
 */

@MultipartConfig
@WebServlet ("/buscar")
public class Buscar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String basePath = "";
	private static Publicador port;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Buscar() {
        super();
    }

    /**
	 * inicializa la sesión si no estaba creada 
	 * @param request 
	 */
	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("error", "s");
		if (session.getAttribute("usuario") == null) {
			session.setAttribute("usuario", "");
		}
		if (session.getAttribute("orden") == null) {
			request.getSession().setAttribute("orden", "Fecha");
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
	
	private void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DtPaqueteWebArray paquetesA = (DtPaqueteWebArray) req.getSession().getAttribute("paquetesList");
		DtRutaDeVueloWebArray rutas = (DtRutaDeVueloWebArray) req.getSession().getAttribute("rutasList");
		String orden = (String)req.getSession().getAttribute("orden");
		if(orden.equals("Nombre")) {
			req.getSession().setAttribute("orden", "Fecha");
			paquetesA.getItem().sort(new Comparator<DtPaqueteWeb>() {
										    public int compare(DtPaqueteWeb o1, DtPaqueteWeb o2) {
										        return LocalDate.parse(o2.getFechaAlta()).compareTo(LocalDate.parse(o1.getFechaAlta()));
										    }
										});
			rutas.getItem().sort(new Comparator<DtRutaDeVueloWeb>() {
				    public int compare(DtRutaDeVueloWeb o1, DtRutaDeVueloWeb o2) {
				        return LocalDate.parse(o2.getFechaAlta()).compareTo(LocalDate.parse(o1.getFechaAlta()));
				    }
				});
		}
		else {
			req.getSession().setAttribute("orden", "Nombre");
			paquetesA.getItem().sort(new Comparator<DtPaqueteWeb>() {
										    public int compare(DtPaqueteWeb o1, DtPaqueteWeb o2) {
										        return o1.getNombre().compareToIgnoreCase(o2.getNombre());
										    }
										});
			rutas.getItem().sort(new Comparator<DtRutaDeVueloWeb>() {
				    public int compare(DtRutaDeVueloWeb o1, DtRutaDeVueloWeb o2) {
				        return o1.getNombre().compareToIgnoreCase(o2.getNombre());
				    }
				});
		}
		req.getSession().setAttribute("paquetesList", paquetesA);
		req.getSession().setAttribute("rutasList", rutas);
		
		req.getRequestDispatcher("/WEB-INF/jsp/visitante/listado.jsp").forward(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		initSession(req);
		req.getSession().setAttribute("orden", "Fecha");
    	
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
		        System.err.println("buscar: Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("buscar: Archivo de configuración no encontrado. Usando valores predeterminados.");
		}

		// Construir la URL del servicio
		String serviceUrl = "http://" + ipStr + ":" + portStr + "/publicador?wsdl";
		System.out.println("buscar: Servicio buscado en " + serviceUrl);
		
		// Crear cliente del servicio web usando la URL generada
		PublicadorService service = null;
		try {
			service = new PublicadorService(new URL(serviceUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = service.getPublicadorPort();				

		String busqueda = (String) req.getParameter("busqueda");
		DtRutaDeVueloWebArray rutas = port.listarTodasRutasDeVueloConfirmadasDTWeb();
		StringArray nombresPaquetes = (StringArray) port.listarPaquetes();
		DtPaqueteWebArray setPaquetes = new DtPaqueteWebArray();
		
		for(String paq : nombresPaquetes.getItem()) {
			setPaquetes.getItem().add(port.obtenerInfoPaquete(paq));
		}
		DtRutaDeVueloWebArray rutasAMostrar = new DtRutaDeVueloWebArray();
		busqueda = StringUtils.stripAccents(busqueda.toLowerCase().trim());
		String[] palabrasClave = busqueda.split(" ");
		boolean agregar = true;
		for(DtRutaDeVueloWeb r : rutas.getItem()) {
			agregar = true;
			for(String s : palabrasClave) {
				agregar = agregar&&(StringUtils.stripAccents(r.getDescripcion().toLowerCase()).contains(s) || StringUtils.stripAccents(r.getDescripcionCorta().toLowerCase()).contains(s) || StringUtils.stripAccents(r.getNombre().toLowerCase()).contains(s));
			}
			if(agregar) {
				rutasAMostrar.getItem().add(r);
				rutaImagen = guardarImagen(r.getImagen(), getServletContext());
			}
		}
		
		DtPaqueteWebArray paquetesAMostrar= new DtPaqueteWebArray();
		for(DtPaqueteWeb p : setPaquetes.getItem()) {
			agregar = true;
			for(String s : palabrasClave) {
					agregar = agregar&&(StringUtils.stripAccents(p.getDescripcion().toLowerCase()).contains(s) || StringUtils.stripAccents(p.getNombre().toLowerCase()).contains(s));
			}
			if(agregar) {
				paquetesAMostrar.getItem().add(p);
				rutaImagen = guardarImagen(p.getImagen(), getServletContext());
				}
			}
		paquetesAMostrar.getItem().sort(new Comparator<DtPaqueteWeb>() {
										    public int compare(DtPaqueteWeb o1, DtPaqueteWeb o2) {
										        return LocalDate.parse(o2.getFechaAlta()).compareTo(LocalDate.parse(o1.getFechaAlta()));
										    }
										});
		rutasAMostrar.getItem().sort(new Comparator<DtRutaDeVueloWeb>() {
			    public int compare(DtRutaDeVueloWeb o1, DtRutaDeVueloWeb o2) {
			        return LocalDate.parse(o2.getFechaAlta()).compareTo(LocalDate.parse(o1.getFechaAlta()));
			    }
			});
		
		req.getSession().setAttribute("paquetesList", paquetesAMostrar);
		req.getSession().setAttribute("rutasList", rutasAMostrar);
		
		req.getRequestDispatcher("/WEB-INF/jsp/visitante/listado.jsp").forward(req, resp);
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
		initSession(request);
		processGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		basePath = this.getServletContext().getRealPath("/");
		processRequest(request, response);
	}

}
