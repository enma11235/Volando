package com.volandoPuntoUY.controllers;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.volandoPuntoUY.model.DtAerolinea;
import com.volandoPuntoUY.model.DtAerolineaArray;
import com.volandoPuntoUY.model.DtClienteWeb;
import com.volandoPuntoUY.model.DtReservaWeb;
import com.volandoPuntoUY.model.DtPasaje;
import com.volandoPuntoUY.model.DtPasajeArray;
import com.volandoPuntoUY.model.DtReservaWebArray;
import com.volandoPuntoUY.model.DtRutaDeVueloWeb;
import com.volandoPuntoUY.model.DtRutaDeVueloWebArray;
import com.volandoPuntoUY.model.DtVueloWeb;
import com.volandoPuntoUY.model.DtVueloWebArray;
import com.volandoPuntoUY.model.Publicador;
import com.volandoPuntoUY.model.PublicadorService;
import com.volandoPuntoUY.model.ReservaNoExisteException_Exception;
import com.volandoPuntoUY.model.StringArray;
import com.volandoPuntoUY.model.UsuarioNoEsAerolineaExcepcion_Exception;
import com.volandoPuntoUY.model.UsuarioNoExisteException_Exception;
import com.volandoPuntoUY.model.VueloNoExisteException_Exception;

/**
 * Servlet implementation class DetallesReserva
 */

@WebServlet("/detallesReserva")
public class DetallesReserva extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Publicador port;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetallesReserva() {
        super();
        // TODO Auto-generated constructor stub
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

    	HttpSession session = req.getSession();

        String nomVuelo = (String)req.getParameter("vuelo");
        String nick = (String)req.getParameter("nick");
        DtReservaWebArray resvs = null;
        DtPasajeArray pasajeros = (DtPasajeArray)req.getAttribute("pasajeros"); 
        DtAerolineaArray aerolineas = null;
        DtRutaDeVueloWebArray rutasConfirmadas = null;
        StringArray vuelos = null;
        Boolean encontrada = false;
        DtAerolinea a = null;
        DtRutaDeVueloWeb rdt = null;
        DtVueloWebArray vuelosDT = null;
        DtVueloWeb vDT = null;
        
        try {
			aerolineas = port.listarAerolineasDataWeb();
			if (aerolineas != null) {
				for(DtAerolinea aero: aerolineas.getItem()) {
					try {
						rutasConfirmadas = port.listarRutasConfirmadasDTWeb(aero.getNickname());
						if (rutasConfirmadas != null) {
							for(DtRutaDeVueloWeb ruta: rutasConfirmadas.getItem()) {
								if (ruta.getAerolinea().getNickname().equals(aero.getNickname())) {
									vuelosDT = port.listarVuelosDTWeb(aero.getNickname(), ruta.getNombre());
									if (vuelosDT != null) {
										for(DtVueloWeb v: vuelosDT.getItem()) {
											if (v.getNombre().equals(nomVuelo)) {
												vDT = v;
												encontrada = true;
												System.out.println("Vuelo encontrado: "+v.getNombre());
												break;
											}
										}
										if (encontrada) {
											rdt = ruta;
											break;
										}
									}
								}
							}
						}
					} catch (UsuarioNoEsAerolineaExcepcion_Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (encontrada) {
						a = aero;
						break;
					}
				}
					
			}
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VueloNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if (pasajeros == null) {
        	pasajeros = new DtPasajeArray(); 
	    }
        try {
			resvs = port.listarReservasClienteWeb(nick);
		} catch (ReservaNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DtReservaWeb res = null;
        if (resvs.getItem() != null && !resvs.getItem().isEmpty()) {
        	for(DtReservaWeb r: resvs.getItem()) {
            	if(r.getNomVuelo().equals(nomVuelo)) {
            		res = r;
            		if (res.getPasajes() != null) {
            			for(DtPasaje p: res.getPasajes()) {
            				//System.out.println("Pasajero: " + p.getNombre() + " " + p.getApellido());
            				pasajeros.getItem().add(p);
            			}
            			 
            			req.getSession().setAttribute("pasajeros",pasajeros);
            		}
            		
            	}		
            }
        }
        
        DtClienteWeb datosC = null;
        try {
			datosC = (DtClienteWeb) port.obtenerInfoUsuario(nick);
		} catch (UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        session.getAttribute("reserva");
        session.getAttribute("cliente");
        session.getAttribute("pasajeros");
        session.setAttribute("reserva", res);
        session.setAttribute("vuelo", vDT);
        session.setAttribute("cliente", datosC);
        session.setAttribute("aerolinea", a);
        session.setAttribute("ruta", rdt);

        req.getRequestDispatcher("/WEB-INF/jsp/cliente/detallesReserva.jsp").forward(req, resp);
    }
    
    // Manejar la generación del PDF desde este servlet
    @SuppressWarnings("unused")
	protected void doDownloadTarjetaEmbarque(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DtReservaWeb reserva = (DtReservaWeb) request.getSession().getAttribute("reserva");
		DtClienteWeb cliente = (DtClienteWeb) request.getSession().getAttribute("cliente");
		DtAerolinea aerolinea = (DtAerolinea) request.getSession().getAttribute("aerolinea");
		DtRutaDeVueloWeb ruta = (DtRutaDeVueloWeb) request.getSession().getAttribute("ruta");
		DtVueloWeb vuelo = (DtVueloWeb) request.getSession().getAttribute("vuelo");
		
        if (reserva == null || cliente == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se pudo obtener la reserva o el cliente.");
            return;
        }

        // Crear el PDF de la tarjeta de embarque
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Título principal centrado
            Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph titulo = new Paragraph("TARJETA DE EMBARQUE\n", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph("==========================================================================\n"));
            document.add(titulo);
            document.add(new Paragraph("==========================================================================\n"));
            
            // Obtener la fecha y hora actual
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String fechaHoraFormateada = fechaHoraActual.format(formato);

            // Añadir la fecha y hora al documento
            document.add(new Paragraph("Fecha y hora de emisión: " + fechaHoraFormateada + "\n"));

            
            Font fuenteSeccion = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPTable tablaInfo;

            // Información de la Aerolínea
            Paragraph parrafoAerolinea = new Paragraph("Aerolínea:\n", fuenteSeccion);
            parrafoAerolinea.setSpacingAfter(10); // Espacio después del título
            document.add(parrafoAerolinea);

            tablaInfo = new PdfPTable(2);
            tablaInfo.setWidthPercentage(100);
            tablaInfo.setWidths(new int[]{30, 70});

            if (aerolinea != null) {
                tablaInfo.addCell("Nombre");
                tablaInfo.addCell(aerolinea.getNombre());
                tablaInfo.addCell("Sitio Web");
                tablaInfo.addCell(aerolinea.getSitioWeb());
                tablaInfo.addCell("Email de Contacto");
                tablaInfo.addCell(aerolinea.getEmail());
            } else {
                document.add(new Paragraph("No hay información de la aerolínea disponible.\n"));
            }
            document.add(tablaInfo);

            // Información de la Ruta
            Paragraph parrafoRuta = new Paragraph("Ruta de Vuelo:\n", fuenteSeccion);
            parrafoRuta.setSpacingAfter(10); // Espacio después del título
            document.add(parrafoRuta);

            tablaInfo = new PdfPTable(2);
            tablaInfo.setWidthPercentage(100);
            tablaInfo.setWidths(new int[]{30, 70});

            if (ruta != null) {
                tablaInfo.addCell("Estado");
                tablaInfo.addCell(ruta.getEstado().toString());
                tablaInfo.addCell("Nombre");
                tablaInfo.addCell(ruta.getNombre());
                tablaInfo.addCell("Descripción");
                tablaInfo.addCell(ruta.getDescripcionCorta());
                tablaInfo.addCell("Hora");
                tablaInfo.addCell(ruta.getHora());
                tablaInfo.addCell("Ciudad de Origen");
                tablaInfo.addCell(ruta.getCiudadOrigen().getNombre());
                tablaInfo.addCell("Aeropuerto de Origen");
                tablaInfo.addCell(ruta.getCiudadOrigen().getAeropuerto());
                tablaInfo.addCell("Ciudad de Destino");
                tablaInfo.addCell(ruta.getCiudadDestino().getNombre());
                tablaInfo.addCell("Aeropuerto de Destino");
                tablaInfo.addCell(ruta.getCiudadDestino().getAeropuerto());
            } else {
                document.add(new Paragraph("No hay información de la ruta disponible.\n"));
            }
            document.add(tablaInfo);

            // Información del Vuelo
            Paragraph parrafoVuelo = new Paragraph("Vuelo:\n", fuenteSeccion);
            parrafoVuelo.setSpacingAfter(10); // Espacio después del título
            document.add(parrafoVuelo);

            tablaInfo = new PdfPTable(2);
            tablaInfo.setWidthPercentage(100);
            tablaInfo.setWidths(new int[]{30, 70});

            if (vuelo != null) {
                tablaInfo.addCell("Nombre");
                tablaInfo.addCell(vuelo.getNombre());
                tablaInfo.addCell("Fecha");
                tablaInfo.addCell(vuelo.getFecha().toString());
                tablaInfo.addCell("Duración");
                tablaInfo.addCell(vuelo.getDuracion().toString());
            } else {
                document.add(new Paragraph("No hay información del vuelo disponible.\n"));
            }
            document.add(tablaInfo);

            // Información de la Reserva
            Paragraph parrafoReserva = new Paragraph("Reserva:\n", fuenteSeccion);
            document.add(parrafoReserva);

            tablaInfo = new PdfPTable(2);
            tablaInfo.setWidthPercentage(100);
            tablaInfo.setWidths(new int[]{30, 70});

            if (reserva != null) {
                tablaInfo.addCell("Fecha de Reserva");
                tablaInfo.addCell(reserva.getFecha().toString());
                tablaInfo.addCell("Cantidad de Pasajeros");
                tablaInfo.addCell(String.valueOf(reserva.getCantPasajeros()));
                tablaInfo.addCell("Equipaje Extra");
                tablaInfo.addCell(String.valueOf(reserva.getCantEquipaje()));
                tablaInfo.addCell("Tipo de Asiento");
                tablaInfo.addCell(reserva.getTipoAsiento().toString());
                tablaInfo.addCell("Costo");
                tablaInfo.addCell(String.format("%.2f", reserva.getCosto()));
            } else {
                document.add(new Paragraph("No hay información de la reserva disponible.\n"));
            }
            document.add(tablaInfo);
            
            // Lista de Pasajeros
            if (reserva != null && reserva.getPasajes() != null) {
                Paragraph parrafoPasajeros = new Paragraph("Pasajeros: \n", fuenteSeccion);
                parrafoPasajeros.setSpacingAfter(10); // Espacio después del título
                document.add(parrafoPasajeros);

                int i = 1;
                tablaInfo = new PdfPTable(2);
                tablaInfo.setWidthPercentage(100);
                tablaInfo.setWidths(new int[]{30, 70});

                for (DtPasaje p : reserva.getPasajes()) {
                    tablaInfo.addCell("Pasajero " + i );
                    tablaInfo.addCell(p.getNombre() + " " + p.getApellido());
                    tablaInfo.addCell("Número de Asiento");
                    tablaInfo.addCell(String.valueOf(i));
                    i++;
                }
                document.add(tablaInfo);
            }

            document.add(new Paragraph("==========================================================================\n"));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el PDF.");
        }

        // Configuración para la descarga del PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tarjeta_embarque.pdf");
        response.setContentLength(baos.size());

        OutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
        baos.close();
    }
    
    protected void realizarCheckIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    	DtReservaWeb reserva = (DtReservaWeb) request.getSession().getAttribute("reserva");
		port.realizarCheckin(reserva.getNicknameCliente(), reserva.getNomVuelo());
		reserva = port.obtenerInfoReservaWeb(reserva.getNomVuelo(), reserva.getNicknameCliente());
		request.getSession().setAttribute("reserva", reserva);
		request.getRequestDispatcher("/WEB-INF/jsp/cliente/detallesReserva.jsp").forward(request, response);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Primero, cargar los datos de la reserva y el cliente
        if (!"descargar".equals(action) && !"checkin".equals(action)) {
            processRequest(request, response);
        }
        
        

        // Si la acción es "descargar", generar el PDF
        if ("descargar".equals(action)) {
            doDownloadTarjetaEmbarque(request, response);
        }
        
        //
        if ("checkin".equals(action)) {
        	realizarCheckIn(request, response);
        }
    }

}
