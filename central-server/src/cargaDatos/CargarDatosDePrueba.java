package cargaDatos;

import controller.*;
import service.*;
import excepciones.*;
import persistencia.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.*;
import service.*;
import excepciones.*;
import persistencia.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;

public class CargarDatosDePrueba {

	public static void insertarDatosPrueba(IControladorRutaDeVuelo ICRV, IControladorCiudadCategoria ICC,
			IControladorPaquete IP, IControladorUsuario IU) {

		String rutaCiudadesCSV = "csvs/2024Ciudades.csv";
		String rutaCategoriasCSV = "csvs/2024Categorias.csv";
		String rutaCheckinsCSV = "csvs/2024Checkin.csv";
		String rutaUsuariosBaseCSV = "csvs/2024Usuarios.csv";
		String rutaClientesCSV = "csvss/2024Usuarios-Clientes.csv";
		String rutaAerolineasCSV = "csvs/2024Usuarios-Aerolineas.csv";
		String rutaRutasVueloCSV = "csvs/2024RutasVuelosv1-2.csv";
		String rutaVuelosCSV = "csvs/2024Vuelosv1-2.csv";
		String rutaPaquetesCSV = "csvs/2024Paquetes.csv";
		String rutaRutasPaquetesCSV = "csvs/2024PaquetesRutas.csv";
		String rutaReservasCSV = "csvs/2024Reservas.csv";
		String rutaPasajesCSV = "csvs/2024Pasajes.csv";
		String rutaCompraPaqueteCSV = "csvs/2024ComprasPaquetes.csv";
		String rutaSeguidosCSV = "csvs/2024Seguidos.csv";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		Map<String, String[]> ciudades = new HashMap<>();
		// Ciudades
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaCiudadesCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String ref = datos[0].trim();
				String pais = datos[2].trim();
				String ciudad = datos[1].trim();
				String aeropuerto = datos[3].trim();
				String descripcion = datos[4].trim();
				String sitioWeb = datos[5].trim();
				LocalDate fechaAlta = LocalDate.parse(datos[6].trim(), formatter);
				ciudades.put(ref, datos);

				ICC.crearNuevaCiudad(pais, ciudad, aeropuerto, descripcion, sitioWeb, fechaAlta);

			}

		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV Ciudades: " + e.getMessage());
		}

		Map<String, String> categorias = new HashMap<>();
		// Categorías
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaCategoriasCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String nombre = datos[1].trim();
				
				categorias.put(datos[0],nombre);
				ICC.agregarCategoria(nombre);

			}

		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV Categorías: " + e.getMessage());
		}

		// Usuarios

		Map<String, String[]> usuarios = new HashMap<>(); // Hashmap temporal de usuarios
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaUsuariosBaseCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String ref = datos[0].trim();
				usuarios.put(ref, datos);
			}
		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV base de usuarios: " + e.getMessage());
		}
		// Usuarios-Clientes
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaClientesCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			DocumentType d;
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String ref = datos[0].trim();
				String[] datosBase = usuarios.get(ref); // Obtengo los datos que almacené antes
				if(datos[4].trim().contains("CI")) d = DocumentType.CedulaIdentidad;
				else if(datos[4].trim().contains("Pasaporte")) d = DocumentType.Pasaporte;
				else d = DocumentType.Extranjero;
				if (datosBase != null && datosBase[1].trim().equals("C")) {
					IU.altaCliente(datosBase[2].trim(), datosBase[3].trim(), datosBase[4].trim(), datosBase[5].trim(), datos[1].trim(),
							LocalDate.parse(datos[2].trim(), formatter), datos[3].trim(),
							d, datos[5].trim(), datosBase[6].trim());
				}
			}
		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV de clientes: " + e.getMessage());
			e.printStackTrace();
		}
		// Usuarios-Aerolineas
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaAerolineasCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String ref = datos[0].trim();
				String[] datosBase = usuarios.get(ref);

				if (datosBase != null && datosBase[1].trim().equals("A")) {
					IU.altaAereolinea(datosBase[2].trim(), datosBase[3].trim(), datosBase[4].trim(), datosBase[5].trim(), datos[1].trim(),
							datos[2].trim(), datosBase[6].trim());
				}
			}
		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV de aerolíneas: " + e.getMessage());
			e.printStackTrace();
		}
		
		

		// Rutas de Vuelo
		Map<String, String[]> rutas = new HashMap<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaRutasVueloCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String aerolinea = datos[1].trim();
				String nombre = datos[2].trim();
				String descripcion = datos[3].trim();
				String hora = datos[4].trim();
				String costoTurista = datos[5].trim();
				String costoEjecutivo = datos[6].trim();
				String costoEquipajeExtra = datos[7].trim();	
				String refOrigen = datos[8].trim();
				String refDestino = datos[9].trim();
				LocalDate fechaAlta = LocalDate.parse(datos[10].trim(), formatter);
				String[] catsRefs = datos[11].split(",\\s*");
				String [] cats = new String[catsRefs.length];
				String estadoRuta = datos[12].trim();
				String desCorta = datos[13].trim();
				String imagen = datos[14].trim();
				String video = datos[15].trim();
				LocalDate fechaFin = null;
				if(datos[16].trim() != "") fechaFin = LocalDate.parse(datos[16].trim(), formatter);
				
				int visitas = Integer.parseInt(datos[17].trim());
				
				
				for(int x=0; x<catsRefs.length; x++) {
					cats[x] = categorias.get(catsRefs[x]);
				}
				
				LocalTime time;
		        DateTimeFormatter formatterHMM = DateTimeFormatter.ofPattern("H:mm");
		        DateTimeFormatter formatterHHMM = DateTimeFormatter.ofPattern("HH:mm");
		        
		        try {
		            time = LocalTime.parse(hora, formatterHMM);
		        } catch (DateTimeParseException e) {
		            time = LocalTime.parse(hora, formatterHHMM);
		        }

				rutas.put(datos[0].trim(), datos);
				ICRV.agregarRutaDeVuelo(usuarios.get(aerolinea)[2].trim(), nombre, descripcion, desCorta, time, Float.parseFloat(costoTurista), Float.parseFloat(costoEjecutivo), Float.parseFloat(costoEquipajeExtra),
						construirClaveCiudad(ciudades.get(refOrigen)[2].trim(), ciudades.get(refOrigen)[1].trim()), construirClaveCiudad(ciudades.get(refDestino)[2].trim(), ciudades.get(refDestino)[1].trim()), fechaAlta, cats, imagen, video, visitas);
				FlightRoute rv = ManejadorRutaDeVuelo.getInstance().obtenerRutaDeVuelo(nombre);
				rv.setEstado(RouteState.fromString(estadoRuta));
				ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
				mrv.updateRutaDeVuelo(rv);
			}

		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV RutasVuelo: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		Map<String, String[]> vuelos = new HashMap<>();
		// Vuelos
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaVuelosCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String aerolinea = datos[1].trim();
				String ruta = datos[2].trim();
				String nombre = datos[3].trim();
				LocalDate fecha = LocalDate.parse(datos[4].trim(), formatter);
				String duracion = datos[5].trim();
				String horas = "";
				String minutos = "";
				boolean parseandoHoras = true;
				for(int i = 0; i < duracion.length(); i++) {
					char c = duracion.charAt(i);
					if(parseandoHoras) {
						if(c != ':') horas += c;
						else parseandoHoras = false;
					}
					if(!parseandoHoras) {
						if(c != ':') minutos += c;
					}		
				}
				int segundos = Integer.parseInt(horas)*3600 + Integer.parseInt(minutos)*60;
				Duration dur = Duration.ofSeconds(segundos);
				int cantTurista = Integer.parseInt(datos[6].trim());
				int cantEjecutivo = Integer.parseInt(datos[7].trim());		
				LocalDate fechaAlta = LocalDate.parse(datos[8].trim(), formatter);
				String imagen = datos[9].trim();
				ICRV.altaVuelo(rutas.get(ruta)[2].trim(), nombre, fecha, dur, cantTurista, cantEjecutivo, fechaAlta, imagen);
				vuelos.put(datos[0].trim(), datos);
			}

		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV Vuelos: ");
			e.printStackTrace();
		}
		
		//Paquetes
		Map<String, String[]> paquetes = new HashMap<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaPaquetesCSV), StandardCharsets.UTF_8))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] datos = line.split(";");
				String nombre = datos[1].trim();
				String desc = datos[2].trim();
				Duration duration = Duration.ofDays(Integer.parseInt(datos[3].trim()));
				Float descuento = Float.parseFloat(datos[4].trim());
				LocalDate fecha = LocalDate.parse(datos[5].trim(), formatter);
				String imagen = datos[7].trim();
				
				IP.crearPaqueteRutasDeVuelo(nombre, desc, duration, descuento, fecha, imagen);
				paquetes.put(datos[0].trim(), datos);
			}

		} catch (Exception e) {
			System.err.println("Error al leer el archivo CSV Paquetes: " + e.getMessage());
			e.printStackTrace();
		}
		
		//PaquetesRutas
				Map<String, String[]> paquetesRutas = new HashMap<>();
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(rutaRutasPaquetesCSV), StandardCharsets.UTF_8))) {
					String line;
					br.readLine();
					while ((line = br.readLine()) != null) {
						String[] datos = line.split(";");
						String refPaquete = datos[1].trim();
						String refRuta = datos[3].trim();
						int cantidad = Integer.parseInt(datos[4].trim());
						TipoAsiento ta;
						switch (datos[5].trim()) {
							case "Ejecutivo":
								ta = TipoAsiento.EJECUTIVO;
								break;
							case "Turista":
								ta = TipoAsiento.TURISTA;
								break;
							default:
								ta = TipoAsiento.TURISTA;
								break;
						}
						
						IP.agregarRutaAPaquete(rutas.get(refRuta)[2], paquetes.get(refPaquete)[1], ta, cantidad);
						paquetesRutas.put(datos[0].trim(), datos);
					}

				} catch (Exception e) {
					System.err.println("Error al leer el archivo CSV PaquetesRuta: " + e.getMessage());
					e.printStackTrace();
				}
				Map<String, String[]> pasajes = new HashMap<>();
				// Pasajes
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(rutaPasajesCSV), StandardCharsets.UTF_8))) {
					String line;
					br.readLine();
					while ((line = br.readLine()) != null) {
						String[] datos = line.split(";");
						pasajes.put(datos[0].trim(), datos);
					}

				} catch (Exception e) {
					System.err.println("Error al leer el archivo CSV Pasajes: " + e.getMessage());
					e.printStackTrace();
				}
		
				// Reservas
				Map<String, String[]> reservas = new HashMap<>();
				// Reservas
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(rutaReservasCSV), StandardCharsets.UTF_8))) {
					String line;
					br.readLine();
					while ((line = br.readLine()) != null) {
						String[] datos = line.split(";");
						TipoAsiento asiento;
						if(datos[5].trim().equals("Turista"))  asiento = TipoAsiento.TURISTA;
						else asiento = TipoAsiento.EJECUTIVO;
						int cantPas = Integer.parseInt(datos[6].trim());
						int cantEqEx = Integer.parseInt(datos[7].trim());
						LocalDate fecha = LocalDate.parse(datos[8].trim(), formatter);
						ArrayList<DTPasaje> pasRes = new ArrayList<DTPasaje>();
						for(String[] s:pasajes.values()) {
							if(s[1].trim().contains(datos[0].trim())) pasRes.add(new DTPasaje(s[2].trim(),s[3].trim(),Integer.parseInt(s[4].trim())));
						}
						reservas.put(datos[0].trim(), datos);
						ICRV.reservarVuelo(usuarios.get(datos[4].trim())[2].trim(), vuelos.get(datos[3].trim())[3].trim(), asiento, cantPas, cantEqEx, pasRes, fecha);

					}

				} catch (Exception e) {
					System.err.println("Error al leer el archivo CSV Reservas: " + e.getMessage());
					e.printStackTrace();
				}
		
				//ComprasPaquetes
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(rutaCompraPaqueteCSV), StandardCharsets.UTF_8))) {
					String line;
					br.readLine();
					while ((line = br.readLine()) != null) {
						
						String[] datos = line.split(";");
						
						String[] cliente = usuarios.get(datos[2]);

						String[] paquete = paquetes.get(datos[1]);
 				
						LocalDate fechaCompra = LocalDate.parse(datos[3].trim(), formatter);
						LocalDate validez = LocalDate.parse(datos[4].trim(), formatter);
						Float costo = Float.parseFloat(datos[5].trim());
																		
						IU.compraPaquetes(cliente[2], paquete[1], fechaCompra, validez, costo);  				
						
					}

				} catch (Exception e) {
					System.err.println("Error al leer el archivo CSV ComprasPaquetes: " + e.getMessage());
					e.printStackTrace();
				}
				
				//Checkins
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(rutaCheckinsCSV), StandardCharsets.UTF_8))) {
					String line;
					br.readLine();
					while ((line = br.readLine()) != null) {
						
						String[] datos = line.split(";");

						String[] reserva = reservas.get(datos[1]);
						
						LocalTime horaEm = LocalTime.parse(datos[2].trim());
						
						LocalDate fechaEm = LocalDate.parse(datos[3].trim(), formatter);
																		
						ICRV.hacerCheckin(usuarios.get(reserva[4].trim())[2].trim(), vuelos.get(reserva[3].trim())[3].trim(), false);
						
						ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
						Cliente clienteR = manUsr.obtenerCliente(usuarios.get(reserva[4].trim())[2].trim());
						List<Booking> reservasObj = clienteR.getAllBookings();
						for (Booking r : reservasObj) {
							System.out.println(r.getId());
							if (r.getVuelo().getNombre().equals(vuelos.get(reserva[3].trim())[3].trim())) {
								if(r.getEmbarque() != null)
								{
									Checkin checkin = r.getEmbarque();
									checkin.setFechaEmbarque(fechaEm);
									checkin.setHoraEmbarque(horaEm);
									manUsr.updateCheckin(checkin);
								}
							    break;
							}
						}
						
						
					}

				} catch (Exception e) {
					System.err.println("Error al leer el archivo CSV Checkin: " + e.getMessage());
					e.printStackTrace();
				}
	}
	
   private static String construirClaveCiudad(String pais, String ciudad) {
    	return pais+"-"+ciudad;
    }
	
}
