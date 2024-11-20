 package controller;
 import controller.*;
 import service.*;
 import excepciones.*;
 import persistencia.*;
 import datatype.*;
 import database.*;
 import factory.*;
 import model.*;


import java.util.List;

import com.itextpdf.text.log.SysoCounter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Duration;
import excepciones.ReservaYaExisteException;
import excepciones.RutaDeVueloNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloNoExisteException;
import excepciones.VueloRepetidoException;

/**
 * Controlador de Ruta de Vuelo.
 * @author TProg2024
 *
 */

public class ControladorRutaDeVuelo implements IControladorRutaDeVuelo {
	
	//constructor
	public ControladorRutaDeVuelo() {}
	
	public void agregarRutaDeVuelo(String nicknameAerolinea, String nombre, String descripcion, String descripcionCorta, LocalTime hora, Float costoTurista, Float costoEjecutivo,
			Float costoEquipajeExtra, String claveCiudadOrigen, String claveCiudadDestino, LocalDate fechaAlta, String[]categorias, String imagen, String video, int visitas) throws RutaDeVueloRepetidaException, UsuarioNoEsAerolineaExcepcion {
		ManejadorRutaDeVuelo manejadorR = ManejadorRutaDeVuelo.getInstance();
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Ciudad origen = manejadorR.obtenerCiudad(claveCiudadOrigen);
		Ciudad destino = manejadorR.obtenerCiudad(claveCiudadDestino);
		List<Categoria> arrlistCategorias = new ArrayList<Categoria>();
		Airline usr = manejadorU.obtenerAerolinea(nicknameAerolinea);
		if (usr == null) throw new UsuarioNoEsAerolineaExcepcion("No existe el usuario, o no es una aerolinea");
		
		if (categorias != null) {
			for (int x=0; x<categorias.length; x++) {
				arrlistCategorias.add(manejadorR.buscarCategoria(categorias[x]));
			}
		}

		boolean rutaV = manejadorR.existeRuta(nombre);
		if (rutaV)
			throw new RutaDeVueloRepetidaException("La ruta de vuelo " + nombre + " ya esta registrada");
		
		FlightRoute rv1 = new FlightRoute(nombre, descripcion, descripcionCorta, hora, costoTurista, costoEjecutivo, costoEquipajeExtra, origen, destino, fechaAlta, arrlistCategorias, imagen, video, visitas);
		manejadorR.addRutaDeVuelo(rv1);
		usr.addFlightRoute(rv1);
		manejadorU.updateUsuario(usr);
	}
	
	public List<String> listarRutasDeVuelo(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		return aero.listFlightRoutes();
	}
	
	public String[] listarRutasDeVueloWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		ArrayList<String> rutas = (ArrayList<String>)aero.listFlightRoutes();
		String[] rutasStr = null;
		if (rutas != null) {
			rutasStr = new String[rutas.size()];
			for(int x = 0; x<rutas.size(); x++) {
				rutasStr[x] = rutas.get(x);
			}
		}
		return rutasStr;
	}
	
	public List<DTRutaDeVuelo> listarTodasRutasDeVueloConfirmadasDT(){
		ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
		List<String> rutas = manejadorU.listarRutasDeVuelo();
		List<DTRutaDeVuelo> res = new ArrayList<DTRutaDeVuelo>();
		DTRutaDeVuelo rut = null;
		for (String ruta :rutas) {
			rut = manejadorU.obtenerRutaDeVuelo(ruta).getData();
			if (rut.getEstado().equals(RouteState.Confirmada)) res.add(rut);
		}
		return res;
	}
	
	public DTRutaDeVueloWeb[] listarTodasRutasDeVueloConfirmadasDTWeb(){
		ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
		ArrayList<String> rutas = (ArrayList<String>)manejadorU.listarRutasDeVuelo();
		ArrayList<DTRutaDeVuelo> res = new ArrayList<DTRutaDeVuelo>();
		DTRutaDeVuelo rut = null;
		DTRutaDeVueloWeb[] resW = null;
		if (rutas != null) {
			for (String ruta :rutas) {
				rut = manejadorU.obtenerRutaDeVuelo(ruta).getData();
				System.out.println(rut.getEstado());
				if (rut.getEstado().equals(RouteState.Confirmada)) res.add(rut);
			}
			System.out.println(res.size());
			resW = new DTRutaDeVueloWeb[res.size()];
			for(int i = 0; i < res.size(); i++) {
				DTCiudadWeb ciudadOrigenDTWeb = new DTCiudadWeb(res.get(i).getCiudadOrigen().getPais(), 
						res.get(i).getCiudadOrigen().getNombre(), 
						res.get(i).getCiudadOrigen().getAeropuerto(), 
						res.get(i).getCiudadOrigen().getDescripcion(),
						res.get(i).getCiudadOrigen().getSitioWeb(),
						res.get(i).getCiudadOrigen().getFechaAlta().toString());
				DTCiudadWeb ciudadDestinoDTWeb = new DTCiudadWeb(res.get(i).getCiudadDestino().getPais(), 
						res.get(i).getCiudadDestino().getNombre(), 
						res.get(i).getCiudadDestino().getAeropuerto(), 
						res.get(i).getCiudadDestino().getDescripcion(),
						res.get(i).getCiudadDestino().getSitioWeb(),
						res.get(i).getCiudadDestino().getFechaAlta().toString());

				resW[i] = new DTRutaDeVueloWeb(res.get(i).getNombre(),
						res.get(i).getDescripcion(),
						res.get(i).getDescripcionCorta(), 
						res.get(i).getHora().format(DateTimeFormatter.ofPattern("HH:mm")).toString(), 
						res.get(i).getCostoTurista(), 
						res.get(i).getCostoEjecutivo(), 
						res.get(i).getCostoEquipajeExtra(), 
						ciudadOrigenDTWeb, ciudadDestinoDTWeb, 
						res.get(i).getFecha().toString(), 
						res.get(i).getCategorias(), 
						res.get(i).getEstado(), 
						res.get(i).getImagen(),
						res.get(i).getVideo(),
						res.get(i).getAerolinea(),
						res.get(i).getVisitas());
			}
		}
		
		return resW;
	}
	
	public List<String> listarVuelos(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nomAerolinea);
		List<Flight> vuelos = null;
		List<String> nomVuelos = null;
		if (aero == null) {
			return new ArrayList<String>();			
		} else {
			FlightRoute rtv = aero.getFlightRoute(nomRutaDeVuelo); //Busco la ruta con nombre "nomRutaDeVuelo"
			if (rtv != null) {
				vuelos = rtv.getVuelos(); 
			    //Devuelvo los vuelos
			    nomVuelos = new ArrayList<String>();
			    
			    if (vuelos != null) {
			    	for (Flight vuelo : vuelos) { 
				        nomVuelos.add(vuelo.getName()); 
				    }
				} else {
					throw new VueloNoExisteException("No existen vuelos para la ruta de vuelo: "+nomRutaDeVuelo);
				}
			}
			return nomVuelos;
		}
	}
	
	public String[] listarVuelosWeb(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nomAerolinea);
		ArrayList<Flight> vuelos = null;
		ArrayList<String> nomVuelos = new ArrayList<String>();
		String[] nomVuelosStr = null;
		if (aero == null) {
			return nomVuelosStr;			
		} else {
			FlightRoute rtv = aero.getFlightRoute(nomRutaDeVuelo); //Busco la ruta con nombre "nomRutaDeVuelo"
			if (rtv != null) {
				vuelos = (ArrayList<Flight>)rtv.getVuelos(); 
			    //Devuelvo los vuelos
			    nomVuelos = new ArrayList<String>();
			    
			    if (vuelos != null) {
			    	for (Flight vuelo : vuelos) { 
				        nomVuelos.add(vuelo.getName()); 
				    }
			    	nomVuelosStr = new String[nomVuelos.size()];
			    	for(int x = 0; x<nomVuelos.size(); x++) {
			    		nomVuelosStr[x] = nomVuelos.get(x);
					}
				} else {
					throw new VueloNoExisteException("No existen vuelos para la ruta de vuelo: "+nomRutaDeVuelo);
				}
			}
			return nomVuelosStr;
		}
	}	
	
	public List<DTVuelo> listarVuelosDT(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nomAerolinea);
		List<Flight> vuelos = null;
		List<DTVuelo> nomVuelos = null;
		if (aero == null) {
			return new ArrayList<DTVuelo>();			
		} else {
			FlightRoute rtv = aero.getFlightRoute(nomRutaDeVuelo); //Busco la ruta con nombre "nomRutaDeVuelo"
			if (rtv != null) {
				vuelos = rtv.getVuelos(); 
			    //Devuelvo los vuelos
			    nomVuelos = new ArrayList<DTVuelo>();
			    
			    if (vuelos != null) {
			    	for (Flight vuelo : vuelos) { 
				        nomVuelos.add(vuelo.getDTO()); 
				    }
				} else {
					throw new VueloNoExisteException("No existen vuelos para la ruta de vuelo: "+nomRutaDeVuelo);
				}
			}
			return nomVuelos;
		}
	}
	
	public DTVueloWeb[] listarVuelosDTWeb(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nomAerolinea);
		ArrayList<Flight> vuelos = null;
		ArrayList<DTVuelo> nomVuelos =  new ArrayList<DTVuelo>();
		ArrayList<DTReservaWeb> resDTWeb = new ArrayList<DTReservaWeb>();
		ArrayList<DTReserva> reservas = null;
		DTVueloWeb[] vuelosDTStr = null;
		DTReservaWeb dtrWeb = null;
		if (aero == null) {
			return null;			
		} else {
			FlightRoute rtv = aero.getFlightRoute(nomRutaDeVuelo); //Busco la ruta con nombre "nomRutaDeVuelo"
			if (rtv != null) {
				vuelos = (ArrayList<Flight>)rtv.getVuelos(); 
				if (vuelos != null) {
					vuelosDTStr = new DTVueloWeb[vuelos.size()];
				    
				    int i = 0;
					for (Flight vuelo : vuelos) { 
						reservas = (ArrayList<DTReserva>)vuelo.getDTO().getReservas();
						if (reservas != null) {
							for (DTReserva res : reservas) { 
								dtrWeb = new DTReservaWeb(res.getTipoAsiento(),
										res.getCantEquipaje(),
										res.getCantPasajeros(),
										res.getCosto(), 
										res.getFecha().toString(), 
										res.getNomVuelo(),
										res.getNicknameCliente(),
										res.getPasajes(),
										res.getEmbarque());

								resDTWeb.add(dtrWeb); 
						    }
						}
						vuelosDTStr[i] = new DTVueloWeb(vuelo.getName(),
					    		vuelo.getDate().toString(),
								vuelo.getDuration().toString(),
								vuelo.getTouristSeats(),
								vuelo.getExecutiveSeats(),
								vuelo.getAvailableRouristSeats(),
								vuelo.getAvailableExecutiveSeats(),
								vuelo.getRegistrationDate().toString(),
								resDTWeb,
								vuelo.getImage());
					    	i++;	
					}
				}
				
			}
			return vuelosDTStr;
		}
	}
	
	public void altaVuelo(String nomRuta, String nomVuelo, LocalDate fechaVuelo, Duration duracion, Integer cantAsTurista, Integer cantAsEjecutivo, LocalDate fechaAlta, String imagen) throws VueloRepetidoException {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance(); 
		
		FlightRoute rtv = mrv.obtenerRutaDeVuelo(nomRuta); 
		 boolean vuelo = mrv.existeVuelo(nomVuelo);
	        if (vuelo)
	            throw new VueloRepetidoException("El vuelo " + nomVuelo + " ya esta registrado"); //Se corta el flujo
	
		Flight vueloR = new Flight(nomVuelo, fechaVuelo, duracion, cantAsTurista, cantAsEjecutivo,  fechaAlta, imagen); 
		rtv.addVuelo(vueloR);
		vueloR.setRoute(rtv);
		
		mrv.updateRutaDeVuelo(rtv);
		mrv.addVuelo(vueloR);
	}
	
	public float reservarVuelo(String nickCliente, String nombreVuelo, SeatType tipo, Integer cantPasajes, Integer cantEqExtra, List<DTPasaje> pasajerosExtra, LocalDate fechaReserva) throws ReservaYaExisteException {
		float costo= 0;
		//buscar el cliente:
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		Cliente clienteR = manUsr.obtenerCliente(nickCliente);
		
		//buscar el vuelo:
		ManejadorRutaDeVuelo manRutVuelo = ManejadorRutaDeVuelo.getInstance();
		Flight vueloR = manRutVuelo.obtenerVuelo(nombreVuelo);
		
		//iteramos sobre las reservas del cliente
		//para verificar que no cuenta con una 
		//reserva para el mismo vuelo
		
		ArrayList<Booking> reservas = (ArrayList<Booking>)clienteR.getAllBookings();
		boolean reservaYaExiste = false;
		
		for (Booking r : reservas) {
			if (r.getVuelo().getName().equals(nombreVuelo)) {
				reservaYaExiste = true;
				break;
			}
		}
		
		if (reservaYaExiste) {
			throw new ReservaYaExisteException("Ya existe una reserva de este cliente para este vuelo");
			
		/*} else if((tipo == TipoAsiento.TURISTA && cantPasajes > v.getCantAsientosTurista()) || (tipo == TipoAsiento.EJECUTIVO && cantPasajes > v.getCantAsientosEjecutivo())){
			throw new ReservaYaExisteException("La cantidad de pasajes supera la cantidad de asientos disponibles");*/
		}else {
			
			//calculamos el monto de la reserva
			if (tipo.equals(SeatType.TOURIST)) {
				costo = vueloR.getRoute().getCostoTurista() * cantPasajes;
			} else {
				costo = vueloR.getRoute().getCostoEjecutivo() * cantPasajes;
			}
			
			costo += vueloR.getRoute().getCostoEquipajeExtra() * cantEqExtra;

			//creamos una nueva reserva
			
			Booking nuevaReserva = new Booking(tipo, cantEqExtra, cantPasajes, fechaReserva, costo);
			nuevaReserva.setVuelo(vueloR);
			nuevaReserva.setCliente(clienteR);
			
			
			//inserto reserva en DB:
			System.out.println("inserto reserva");
			Long idReserva = manUsr.addReserva(nuevaReserva);
			nuevaReserva.setId(idReserva);
			System.out.println("insertada con idreserva "+idReserva);
			
			//agregamos los pasajes a la reserva
			if (pasajerosExtra != null) {
				for (DTPasaje p : pasajerosExtra) {
					Pasaje pasaje = new Pasaje(p.getNombre(), p.getApellido(), p.getAsiento());
					pasaje.setReserva(nuevaReserva);
					Long idPasaje = manUsr.addPasaje(pasaje);
					pasaje.setId(idPasaje);
					nuevaReserva.addPasaje(pasaje);
				}
			}
			
			System.out.println("actualizao reserva");
			manUsr.updateReserva(nuevaReserva);
			
			//hacemos los links correspondientes
			clienteR.addBooking(nuevaReserva);
			manUsr.updateUsuario(clienteR);
			
			
			vueloR.addBooking(nuevaReserva);
			System.out.println("****************** Asientos disponibles*********************");
			System.out.println(vueloR.getTouristSeats());
			System.out.println(vueloR.getAvailableRouristSeats());
			System.out.println(vueloR.getExecutiveSeats());
			System.out.println(vueloR.getAvailableExecutiveSeats());
			manRutVuelo.updateVuelo(vueloR);
			
		}
		return costo;
	}
	
	public float reservarVueloConPaquete(String nickCliente, String nombreVuelo, SeatType tipo, Integer cantPasajes, Integer cantEqExtra, List<DTPasaje> pasajerosExtra, LocalDate fechaReserva, float descuento) throws ReservaYaExisteException {
		float costo= 0;
		//buscar el cliente:
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		Cliente clienteR = manUsr.obtenerCliente(nickCliente);
		
		//buscar el vuelo:
		ManejadorRutaDeVuelo manRutVuelo = ManejadorRutaDeVuelo.getInstance();
		Flight vueloR = manRutVuelo.obtenerVuelo(nombreVuelo);
		
		//iteramos sobre las reservas del cliente
		//para verificar que no cuenta con una 
		//reserva para el mismo vuelo
		
		ArrayList<Booking> reservas = (ArrayList<Booking>)clienteR.getAllBookings();
		boolean reservaYaExiste = false;
		
		for (Booking r : reservas) {
			if (r.getVuelo().getName().equals(nombreVuelo)) {
				reservaYaExiste = true;
				break;
			}
		}	
		if (reservaYaExiste) {
			throw new ReservaYaExisteException("Ya existe una reserva de este cliente para este vuelo");
			
		/*} else if((tipo == TipoAsiento.TURISTA && cantPasajes > v.getCantAsientosTurista()) || (tipo == TipoAsiento.EJECUTIVO && cantPasajes > v.getCantAsientosEjecutivo())){
			throw new ReservaYaExisteException("La cantidad de pasajes supera la cantidad de asientos disponibles");*/
		}else {
			
			//calculamos el monto de la reserva
			if (tipo.equals(SeatType.TOURIST)) {
				costo = vueloR.getRoute().getCostoTurista() * cantPasajes;
			} else {
				costo = vueloR.getRoute().getCostoEjecutivo() * cantPasajes;
			}					
			costo = costo * (1 - (descuento / 100.0f));
			costo += vueloR.getRoute().getCostoEquipajeExtra() * cantEqExtra;
			
			
			
			
			//creamos una nueva reserva
			Booking nuevaReserva = new Booking(tipo, cantEqExtra, cantPasajes, fechaReserva, costo);
			nuevaReserva.setVuelo(vueloR);
			nuevaReserva.setCliente(clienteR);
			
			
			//inserto reserva en DB:
			Long idReserva = manUsr.addReserva(nuevaReserva);
			nuevaReserva.setId(idReserva);
			
			List<Long> pasajesIds = new ArrayList<Long>();
			
			//agregamos los pasajes a la reserva
			if (pasajerosExtra != null) {
				for (DTPasaje p : pasajerosExtra) {
					Pasaje pasaje = new Pasaje(p.getNombre(), p.getApellido(), 0);
					pasaje.setReserva(nuevaReserva);
					Long idPasaje = manUsr.addPasaje(pasaje);
					pasaje.setId(idPasaje);
					nuevaReserva.addPasaje(pasaje);
				}
			}
			
			manUsr.updateReserva(nuevaReserva);
			
			//hacemos los links correspondientes
			clienteR.addBooking(nuevaReserva);
			manUsr.updateUsuario(clienteR);
			
			
			vueloR.addBooking(nuevaReserva);
			manRutVuelo.updateVuelo(vueloR);
			
		}
		return costo;
	}
	
	public DTPasaje crearPasaje(String nombre, String apellido) {
		return new DTPasaje(nombre,apellido, 0);
	}
	
    public void hacerCheckin(String nickCliente, String nomVuelo, boolean asignarAsientos) {
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		ManejadorRutaDeVuelo rv = ManejadorRutaDeVuelo.getInstance(); 
		Cliente clienteR = manUsr.obtenerCliente(nickCliente);
		ArrayList<Booking> reservas = (ArrayList<Booking>)clienteR.getAllBookings();
		for (Booking r : reservas) {
			if (r.getVuelo().getName().equals(nomVuelo)) {
			    Checkin checkinNuevo = new Checkin(LocalDate.now(), LocalTime.now());
			    Long idCheckin = manUsr.addCheckin(checkinNuevo);
			    checkinNuevo.setId(idCheckin);
			    r.setEmbarque(checkinNuevo);
			    manUsr.updateReserva(r);
			    if(asignarAsientos) {
				    for(Pasaje p : r.getPasajes()) {
				    	System.out.println(p.getId());
				    	p.setAsiento(r.getVuelo().getSeatNumber());
				    	r.getVuelo().setSeatNumber(r.getVuelo().getSeatNumber() + 1);
				    	rv.updateVuelo(r.getVuelo());
				    	manUsr.updatePasaje(p);
				    }
			    }
			    break;
			}
		}
//		manUsr.updateUsuario(clienteR);
    }
    
	
	public DTReserva obtenerInfoReserva(String nombreVuelo, String nickCliente) {
		Booking reserva = null;
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		Cliente clienteR = manUsr.obtenerCliente(nickCliente);
		List<Booking> reservas = clienteR.getAllBookings();
		for (Booking r : reservas) {
			if (r.getVuelo().getName().equals(nombreVuelo)) {
			    reserva = r;
			    break;
			}
		}
		if (reserva==null)
			return null;
		else
			return reserva.getData();
	}
	
	public DTReservaWeb obtenerInfoReservaWeb(String nombreVuelo, String nickCliente) {
		DTReservaWeb reservaWeb = null;
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		Cliente clienteR = manUsr.obtenerCliente(nickCliente);
		ArrayList<Booking> reservas = (ArrayList<Booking>)clienteR.getAllBookings();
		for (Booking r : reservas) {
			if (r.getVuelo().getName().equals(nombreVuelo)) {
			    reservaWeb = new DTReservaWeb(r.getData().getTipoAsiento(), 
			    		r.getData().getCantEquipaje(), 
			    		r.getData().getCantPasajeros(), 
			    		r.getData().getCosto(), 
			    		r.getData().getFecha().toString(),
			    		r.getData().getNomVuelo(),
			    		nickCliente,
			    		r.getData().getPasajes(),
						r.getData().getEmbarque());
			    break;
			}
		}
		if (reservaWeb==null)
			return null;
		else
			return reservaWeb;
	}
	
	public List<String> listarReservas(String nombreVuelo){
		ManejadorRutaDeVuelo manRutVuelo = ManejadorRutaDeVuelo.getInstance();
		Flight vueloR = manRutVuelo.obtenerVueloConReservas(nombreVuelo);
		List<String> res = null;
		if (vueloR.getBookings() != null) {
			res = new ArrayList<String>();
			for (Booking r:vueloR.getBookings()) {
				res.add(r.getNicknameCliente());
			}
		}
		return res;
	}
	
	public String[] listarReservasWeb(String nombreVuelo){
		ManejadorRutaDeVuelo manRutVuelo = ManejadorRutaDeVuelo.getInstance();
		Flight vueloR = manRutVuelo.obtenerVueloConReservas(nombreVuelo);
		ArrayList<String> res = null;
		String[] resStr = null;
		if (vueloR.getBookings() != null) {
			res = new ArrayList<String>();
			resStr = new String[vueloR.getBookings().size()];
			for (Booking r:vueloR.getBookings()) {
				res.add(r.getNicknameCliente());
			}
			for(int x = 0; x<res.size(); x++) {
				resStr[x] = res.get(x);
				System.out.println("Reserva listar:"+resStr[x]);
			}
		}
		return resStr;
	}
	
	public DTVuelo obtenerInfoVuelo(String nombreRuta, String nombreVuelo) {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		FlightRoute rutaV = mrv.obtenerRutaDeVuelo(nombreRuta);
		if (rutaV != null) {
			Flight vueloV = rutaV.getVuelo(nombreVuelo);
			return vueloV.getDTO();
		} else {
			return null;
		}	
	}
	
	public DTVueloWeb obtenerInfoVueloWeb(String nombreRuta, String nombreVuelo) {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		FlightRoute rutaV = mrv.obtenerRutaDeVuelo(nombreRuta);
		ArrayList<Flight> vuelos = null;
		ArrayList<DTVuelo> nomVuelos =  new ArrayList<DTVuelo>();
		ArrayList<DTReservaWeb> resDTWeb = new ArrayList<DTReservaWeb>();
		ArrayList<DTReserva> reservas = new ArrayList<DTReserva>();
		DTVueloWeb vueloDTStr = null;
		DTReservaWeb dtrWeb = null;
		if (rutaV != null) {
			Flight vueloV = rutaV.getVuelo(nombreVuelo);
			if (vueloV != null) {
				reservas = (ArrayList<DTReserva>)vueloV.getDTO().getReservas();
				if (reservas != null) {
					for (DTReserva res : reservas) { 
		    			dtrWeb = new DTReservaWeb(res.getTipoAsiento(),
		    					res.getCantEquipaje(),
		    					res.getCantPasajeros(),
		    					res.getCosto(), 
		    					res.getFecha().toString(), 
		    					res.getNomVuelo(),
		    					res.getNicknameCliente(),
		    					res.getPasajes(),
								res.getEmbarque());

		    			resDTWeb.add(dtrWeb); 
				    }
	    		}	
				vueloDTStr = new DTVueloWeb(vueloV.getName(),
	    				vueloV.getDate().toString(),
	    				vueloV.getDuration().toString(),
	    				vueloV.getTouristSeats(),
	    				vueloV.getExecutiveSeats(),
	    				vueloV.getAvailableRouristSeats(),
	    				vueloV.getAvailableExecutiveSeats(),
	    				vueloV.getRegistrationDate().toString(),
	    				resDTWeb,
	    				vueloV.getImage());
				}
    		return vueloDTStr;
		} else {
			return null;
		}	
	}
	
	public DTRutaDeVuelo obtenerInfoRutaDeVuelo(String nombre) throws RutaDeVueloNoExisteException {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		FlightRoute rutaV = mrv.obtenerRutaDeVuelo(nombre);
		if (rutaV != null)
			return rutaV.getData();
		else
			throw new RutaDeVueloNoExisteException("La Ruta de Vuelo " + nombre + " no existe");
	}
	
	public DTRutaDeVueloWeb obtenerInfoRutaDeVueloWeb(String nombre, DTAerolinea aero) throws RutaDeVueloNoExisteException {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		FlightRoute rutaV = mrv.obtenerRutaDeVuelo(nombre);
		DTCiudadWeb ciudadOrigenDTWeb = null;
		DTCiudadWeb ciudadDestinoDTWeb = null;
		DTRutaDeVueloWeb rutaW = null;
		if (rutaV != null)
		{
			ciudadOrigenDTWeb = new DTCiudadWeb(rutaV.getCiudadOrigen().getPais(), 
					rutaV.getCiudadOrigen().getNombre(), 
					rutaV.getCiudadOrigen().getAeropuerto(), 
					rutaV.getCiudadOrigen().getDescripcion(),
					rutaV.getCiudadOrigen().getSitioWeb(),
					rutaV.getCiudadOrigen().getFechaAlta().toString());
			ciudadDestinoDTWeb = new DTCiudadWeb(rutaV.getCiudadDestino().getPais(), 
					rutaV.getCiudadDestino().getNombre(), 
					rutaV.getCiudadDestino().getAeropuerto(), 
					rutaV.getCiudadDestino().getDescripcion(),
					rutaV.getCiudadDestino().getSitioWeb(),
					rutaV.getCiudadDestino().getFechaAlta().toString());
			rutaW = new DTRutaDeVueloWeb(rutaV.getNombre(),
					rutaV.getDescripcion(),
					rutaV.getDescripcionCorta(),
					rutaV.getHora().toString(),
					rutaV.getCostoTurista(),
					rutaV.getCostoEjecutivo(),
					rutaV.getCostoEquipajeExtra(),
					ciudadOrigenDTWeb,ciudadDestinoDTWeb,
					rutaV.getFecha().toString(),
					rutaV.getData().getCategorias(),
					rutaV.getEstado(),
					rutaV.getImagen(),
					rutaV.getVideo(), aero,
					rutaV.getVisitas());

			return 	rutaW;
		}
		else {
			throw new RutaDeVueloNoExisteException("La Ruta de Vuelo " + nombre + " no existe");
		}
	}
	
	public List<String> listarTodasLasRutasDeVuelo() {
		ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
		List<String> rutas = manejadorU.listarRutasDeVuelo();
		return rutas;
	}
	
	public String[] listarTodasLasRutasDeVueloWeb() {
		ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
		ArrayList<String> rutas = (ArrayList<String>)manejadorU.listarRutasDeVuelo();
		String[] rutasStr = new String[rutas.size()];
		for(int x = 0; x<rutas.size(); x++) {
			rutasStr[x] = rutas.get(x);
		}
		return rutasStr;
	}

	public String obtenerNicknameAerolineaDeRuta(String nombreRuta) {
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		ArrayList<User> usrs = (ArrayList<User>)manUsr.getUsuarios();
		for (User u : usrs) {
			if (u.isAirline()) {
				try {
					
					 ArrayList<String> rutas =  (ArrayList<String>)listarRutasDeVuelo(u.getNickname());
					 for (String r : rutas) {
						 if (r.equals(nombreRuta))
							 return u.getNickname();
					 }
					
				}catch (UsuarioNoEsAerolineaExcepcion e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return null;
	}
	
	public DTAerolinea obtenerAerolineaDeRutaDT(String nombreRuta) {
		ManejadorUsuario manUsr = ManejadorUsuario.getInstance();
		ArrayList<User> usrs = (ArrayList<User>)manUsr.getUsuarios();
		for (User u : usrs) {
			if (u.isAirline()) {
				try {
					
					 ArrayList<String> rutas =  (ArrayList<String>)listarRutasDeVuelo(u.getNickname());
					 for (String r : rutas) {
						 if (r.equals(nombreRuta))
							 return (DTAerolinea) u.getDTO();
					 }
					
				}catch (UsuarioNoEsAerolineaExcepcion e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return null;
	}
	
    public String obtenerRutaDeVuelo(String nomVuelo) throws VueloNoExisteException {
		ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
		Flight vueloR = manejadorU.obtenerVuelo(nomVuelo);
		String nomRuta = ""; 
		FlightRoute ruta = null; 
		
		if  (vueloR != null) {
			ruta = vueloR.getRoute(); 
			nomRuta = ruta.getNombre();
		} else
			throw new VueloNoExisteException("El vuelo " + nomVuelo + " no existe");
					
		return nomRuta; 
    }
    
    public void rechazarRuta(String nomRuta) {
    	ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
    	FlightRoute ruta = manejadorU.obtenerRutaDeVuelo(nomRuta);
    	ruta.setEstado(RouteState.Rechazada);
    	
    	manejadorU.updateRutaDeVuelo(ruta);
    }
    
    public void aceptarRuta(String nomRuta) {
    	ManejadorRutaDeVuelo manejadorU = ManejadorRutaDeVuelo.getInstance();
    	FlightRoute ruta = manejadorU.obtenerRutaDeVuelo(nomRuta);
    	ruta.setEstado(RouteState.Confirmada);
    	
    	manejadorU.updateRutaDeVuelo(ruta);
    }
    
    public List<String> listarRutasIngresadas(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		return aero.listPendingAprovalFlightRoutes();
    }
    
    public String[] listarRutasIngresadasWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		String[] rutasStr = new String[aero.listPendingAprovalFlightRoutes().size()];
		for(int x = 0; x<aero.listPendingAprovalFlightRoutes().size(); x++) {
			rutasStr[x] = aero.listPendingAprovalFlightRoutes().get(x);
		}
		return rutasStr;
    }
    
     public List<String> listarRutasConfirmadas(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		List<String> res = new ArrayList<String>();
		for(DTRutaDeVuelo r : aero.listAcceptedFlightRoutesDTO()) {
			res.add(r.getNombre());
		}
		return res;
    }

    
    public List<DTRutaDeVuelo> listarRutasConfirmadasDT(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		return aero.listAcceptedFlightRoutesDTO();
    }

    public DTRutaDeVueloWeb[] listarRutasConfirmadasDTWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		DTRutaDeVueloWeb[] rutasDTStr = new DTRutaDeVueloWeb[aero.listAcceptedFlightRoutesDTO().size()];
		DTCiudadWeb ciudadOrigenDTWeb = null;
		DTCiudadWeb ciudadDestinoDTWeb = null;
		DTAerolinea aerolineaDT = null;
		List<DTRutaDeVuelo> rutas = aero.listAcceptedFlightRoutesDTO();
		for(int x = 0; x<rutas.size(); x++) {
			ciudadOrigenDTWeb = new DTCiudadWeb(rutas.get(x).getCiudadOrigen().getPais(), 
					rutas.get(x).getCiudadOrigen().getNombre(), 
					rutas.get(x).getCiudadOrigen().getAeropuerto(), 
					rutas.get(x).getCiudadOrigen().getDescripcion(),
					rutas.get(x).getCiudadOrigen().getSitioWeb(),
					rutas.get(x).getCiudadOrigen().getFechaAlta().toString());
			
			ciudadDestinoDTWeb = new DTCiudadWeb(rutas.get(x).getCiudadDestino().getPais(), 
					rutas.get(x).getCiudadDestino().getNombre(), 
					rutas.get(x).getCiudadDestino().getAeropuerto(), 
					rutas.get(x).getCiudadDestino().getDescripcion(),
					rutas.get(x).getCiudadDestino().getSitioWeb(),
					rutas.get(x).getCiudadDestino().getFechaAlta().toString());
			aerolineaDT = new DTAerolinea(aero.getNickname(),
					aero.getNombre(),
					aero.getEmail(),
					aero.getPassword(),
					aero.getImageUrl(),
					aero.getDescription(),
					aero.getWebSite());

			rutasDTStr[x] = new DTRutaDeVueloWeb(rutas.get(x).getNombre(),
					rutas.get(x).getDescripcion(),
					rutas.get(x).getDescripcionCorta(),
					rutas.get(x).getHora().toString(),
					rutas.get(x).getCostoTurista(),
					rutas.get(x).getCostoEjecutivo(),
					rutas.get(x).getCostoEquipajeExtra(), 
					ciudadOrigenDTWeb, ciudadDestinoDTWeb,
					rutas.get(x).getFecha().toString(),
					rutas.get(x).getCategorias(),
					rutas.get(x).getEstado(), 
					rutas.get(x).getImagen(),
					rutas.get(x).getVideo(),
					aerolineaDT,
					rutas.get(x).getVisitas()
					);
		}
		return rutasDTStr;
    }
    
    public boolean esVueloDeAerolinea(String nickAerolinea, String nomVuelo) {
    	ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = manejadorU.obtenerAerolinea(nickAerolinea);
		if (aero==null)
			return false;
		List<FlightRoute> rutas = aero.getAllFlightRoutes();
		if (rutas ==null)
			return false;
		for (FlightRoute ruta : rutas) {
			List<Flight> vuelos = ruta.getVuelos();
			if (vuelos!=null) {
				for (Flight v : vuelos){
					if (v.getName().equals(nomVuelo)) {
						return true;
					}
				}
			}
		}
		
    	return false;
    }
    

    public void sumarVisita(String nomRuta) {
    	ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		FlightRoute rutaV = mrv.obtenerRutaDeVuelo(nomRuta);
		if (rutaV != null) {
			rutaV.setVisitas(rutaV.getVisitas()+1);
			mrv.updateRutaDeVuelo(rutaV);
		}
		
    }
    
    public List<DTRutaDeVuelo> obtener5MasVisitadas(){
    	List<DTRutaDeVuelo> nombresRutas = listarTodasRutasDeVueloConfirmadasDT();
    	nombresRutas.sort(new Comparator<DTRutaDeVuelo>() {
			    public int compare(DTRutaDeVuelo o1, DTRutaDeVuelo o2) {
			        return Integer.compare(o2.getVisitas(), o1.getVisitas());
			    }
			});
    	List<DTRutaDeVuelo> rutas = new ArrayList<DTRutaDeVuelo>();
    	if(nombresRutas.size() >=5) {
			for (int i = 0; i <5; i++) {
				
				rutas.add(nombresRutas.get(i));
			}
    	}
    	else return nombresRutas;
    	return rutas;
    }
    
    public void finalizarRuta(String nomRuta) {
    	ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		FlightRoute rutaV = mrv.obtenerRutaDeVuelo(nomRuta);
		if (rutaV != null) {
			rutaV.setEstado(RouteState.Finalizada);
			mrv.updateRutaDeVuelo(rutaV);
		}
    }

}