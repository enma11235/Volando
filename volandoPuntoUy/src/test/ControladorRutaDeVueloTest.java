package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import excepciones.ReservaNoExisteException;
import excepciones.ReservaYaExisteException;
import excepciones.RutaDeVueloNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;
import excepciones.VueloNoExisteException;
import logica.DTAerolinea;
import logica.DTPasaje;
import logica.DTReserva;
import logica.DTReservaWeb;
import logica.DTRutaDeVuelo;
import logica.DTRutaDeVueloWeb;
import logica.DTVuelo;
import logica.DTVueloWeb;
import logica.EstadoRuta;
import logica.Fabrica;
import logica.IControladorCiudadCategoria;
import logica.IControladorRutaDeVuelo;
import logica.IControladorUsuario;
import logica.TipoAsiento;

class ControladorRutaDeVueloTest {
	
	private static IControladorRutaDeVuelo controladorRutaDeVuelo;
	private static IControladorUsuario controladorUsuario;
	private static IControladorCiudadCategoria controladorCiudadCategoria;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorRutaDeVuelo = fabrica.getIControladorRutaDeVuelo();
		controladorUsuario = fabrica.getIControladorUsuario();
		controladorCiudadCategoria = Fabrica.getInstance().getIControladorCiudadCategoria();   
        
	}
	 
	@Test
	void testRegistrarRutaDeVueloOK() {
		//Datos de prueba
		String nick = "aireuropaP";
		String nombreAero = "Air Europa";
		String email = "resersvas@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nombre = "UX47";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Indiap";
		String ciudad = "Bangladeshf";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prueba3";
		cat[1]="Prueba5";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombre,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			DTRutaDeVuelo dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVuelo(nombre);
			
			assertEquals(dtrv.getNombre(), nombre);
			assertEquals(dtrv.getDescripcion(), descripcionRuta);
			assertEquals(dtrv.getHora(), hora);
			assertEquals(dtrv.getCostoTurista(), costoTurista);
			assertEquals(dtrv.getCostoEjecutivo(), costoEjecutivo);
			assertEquals(dtrv.getCostoEquipajeExtra(), costoEquipajeExtra);
			assertEquals(dtrv.getFecha(), fechaAlta);
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (RutaDeVueloNoExisteException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testListarRutaDeVueloOK() {
		String nickName = "us";
        String nombre = "S";
        String email = "s@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "UX40";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Espana";
		String ciudad = "Madrid";
		String claveCiudad = pais+"-"+ciudad;
		
		String[] cat = new String[2];
		cat[0] = "Test";
		cat[1]="Test1";
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickName, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickName,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        }catch(Exception e){}
		try {
			List<String> rutas = controladorRutaDeVuelo.listarRutasDeVuelo(nickName);
			int index = rutas.indexOf(nombreR);
		    
			assertEquals(nombreR,rutas.get(index));
		    
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	@Test
	void testListarRutaDeVueloWebOK() {
		String nickName = "us";
        String nombre = "S";
        String email = "s@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "UX40";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Espana";
		String ciudad = "Madrid";
		String claveCiudad = pais+"-"+ciudad;
		
		String[] cat = new String[2];
		cat[0] = "Test";
		cat[1]="Test1";
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickName, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickName,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        }catch(Exception e){}
		try {
			String[] rutas = controladorRutaDeVuelo.listarRutasDeVueloWeb(nickName); // Asume que este método ahora devuelve String[]
			int index = java.util.Arrays.asList(rutas).indexOf(nombreR); // Convertimos el arreglo a una lista temporalmente para buscar el índice

			assertEquals(nombreR, rutas[index]);
		    
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	@Test
	void testReservarVueloOK() {
		String nickNameU = "sur";
		String nickNameU2 = "sure";
		String nickNameA = "sura";
        String nombre = "S";
        String emailU = "sfewq@aa.com";
        String emailU2 = "su@aa.com";
        String emailA = "sa@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "UX39";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "China";
		String ciudad = "Shangai";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UX39874";
		String nomVuelo1 = "UX3dsa9874";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Global";
		cat[1]="Local";
		
		ArrayList<DTPasaje> pas = new ArrayList<DTPasaje>();
		pas.add(new DTPasaje("Jose", "Varela",0));
		pas.add(new DTPasaje("Pedro", "Picapiedra",0));
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, emailU, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaCliente(nickNameU2, nombre, emailU2, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, emailA, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo1, fechaAlta, dur, 20, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e);
        	}
        	try {
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo1, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU2, nomVuelo, TipoAsiento.TURISTA, 1, 1, pas, fechaAlta);
        	} catch(Exception e) {
        		fail(e);
        	}
        	List<DTReserva> res = null;
			try {
				res = controladorUsuario.listarReservasCliente(nickNameU);
			} catch (UsuarioNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReservaNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	assert(!res.isEmpty());
        	boolean excepcion = false;
        	try {
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
	    	} catch(Exception e) {
	    		excepcion = true;
	    	}
        	if(!excepcion) fail("No se lanzo excepcion reserva repetida");
	}
	
	@Test
	void testListarVueloOK() {
		String nickNameA = "latin";
        String nombre = "S";
        String email = "sfwefwe@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://f.s.com";
        
        String nombreR = "UX38";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Peru";
		String ciudad = "Lima";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UX38123";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Andina";
		cat[1]="Segura";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}
        List<String> vuelos = null;
        try {
			 vuelos = controladorRutaDeVuelo.listarVuelos(nickNameA, nombreR);
		} catch (VueloNoExisteException e) {
			fail("No se creo el vuelo");
		}
        assert(vuelos.contains(nomVuelo));
        }
	
	@Test
	void testListarVueloWebOK() {
		String nickNameA = "latin";
        String nombre = "S";
        String email = "sfwefwe@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://f.s.com";
        
        String nombreR = "UX38";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Peru";
		String ciudad = "Lima";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UX38123";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Andina";
		cat[1]="Segura";

        String[] vuelos = null;
        try {
            vuelos = controladorRutaDeVuelo.listarVuelosWeb(nickNameA, nombreR);
        } catch (VueloNoExisteException e) {
            fail("No se creó el vuelo");
        }

        // Verificamos si el arreglo contiene el vuelo
        assert(Arrays.asList(vuelos).contains(nomVuelo));
	}
	
	@Test
	void testListarVueloDTOK() {
		String nickNameA = "latsinw";
        String nombre = "Sw";
        String email = "sfwefwew@daa.com";
        String descripcion = "Viajesw";
        String sitioWeb = "https://f.s.com";
        
        String nombreR = "UX3d8w";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Pseruw";
		String ciudad = "Limaw";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UXd38123w";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Anddinaw";
		cat[1]="Segduraw";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}
        List<DTVuelo> vuelos = null;
        try {
			 vuelos = controladorRutaDeVuelo.listarVuelosDT(nickNameA, nombreR);
		} catch (VueloNoExisteException e) {
			fail("No se creo el vuelo");
		}
        assert(!vuelos.isEmpty());
        }
	
	@Test
	void testListarVueloDTWebOK() {
		String nickNameA = "latsin";
        String nombre = "S";
        String email = "sfwefwe@daa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://f.s.com";
        
        String nombreR = "UX3d8";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Pseru";
		String ciudad = "Lima";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UXd38123";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Anddina";
		cat[1]="Segdura";
		try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}

        
		DTVueloWeb[] vuelos = null;
		try {
		    vuelos = controladorRutaDeVuelo.listarVuelosDTWeb(nickNameA, nombreR);
		} catch (VueloNoExisteException e) {
		    fail("No se creó el vuelo");
		}

		// Verificamos si el arreglo no está vacío
		assert(vuelos != null && vuelos.length > 0);
	}
	
	@Test
	void testEsVueloDeAerolineaOK() {
		String nickNameA = "latn";
        String nombre = "S";
        String email = "sfwefwe@a.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://f.s.com";
        
        String nombreR = "U3d8";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Psedru";
		String ciudad = "Lidma";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UXd381s23";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Andfdina";
		cat[1]="Segdufra";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}
        boolean vuelos = false;
        vuelos = controladorRutaDeVuelo.esVueloDeAerolinea(nickNameA, nomVuelo);
        assert(vuelos);
        }
	
	
	@Test
	void testObtenerInfoVueloOK() {
		String nickNameA = "asazul";
        String nombre = "S";
        String email = "shrsth@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "AZ9das8";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Austrsaalia";
		String ciudad = "Sydadsney";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "AZsas98654";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Cangsdauros";
		cat[1]= "Tazmasdania";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}
        DTVuelo vuelo = null;
        vuelo = controladorRutaDeVuelo.obtenerInfoVuelo(nombreR, nomVuelo);
        assert(vuelo.getNombre() == nomVuelo);
	}
	@Test
	void testObtenerInfoVueloWebOK() {
		String nickNameA = "azul";
        String nombre = "S";
        String email = "shrth@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "AZ98";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Australia";
		String ciudad = "Sydney";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "AZ98654";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Canguros";
		cat[1]= "Tazmania";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}
        DTVueloWeb vuelo = null;
        vuelo = controladorRutaDeVuelo.obtenerInfoVueloWeb(nombreR, nomVuelo);
        assert(vuelo.getNombre() == nomVuelo);
	}
	
	@Test
	void testListarReservasOK() {
		String nickNameU = "juliodfrr";
		String nickNameA = "mojafverr";
        String nombre = "Srfr";
        String email = "sjytfbarr@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "MJf89r";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Canadadrr";
		String ciudad = "Monstrealr";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "MJ89f12rr";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Roteorr";
		cat[1]="Sanorfsr";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, email, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, "ntdsrw", "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e);
        	}
        	try {
				controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, null, fechaAlta);
			} catch (ReservaYaExisteException e) {
				fail(e);
			}
        	List<String> res = null;
			res = controladorRutaDeVuelo.listarReservas(nomVuelo);
        	assert(res.contains(nickNameU));
	}
	@Test
	void testListarReservasWebOK() {
		String nickNameU = "julio";
		String nickNameA = "mojave";
        String nombre = "S";
        String email = "sjytb@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "MJ89";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Canada";
		String ciudad = "Montreal";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "MJ8912";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Roto";
		cat[1]="Sano";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, email, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, "ntrw", "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e);
        	}
        	try {
				controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, null, fechaAlta);
			} catch (ReservaYaExisteException e) {
				fail(e);
			}
        	String[] res = null;
        	res = controladorRutaDeVuelo.listarReservasWeb(nomVuelo);

        	// Verificamos si el arreglo contiene `nickNameU`
        	assert(Arrays.asList(res).contains(nickNameU));

	}
	
	@Test
	void testObtenerInfoReservaOK() {
		String nickNameU = "jose";
		String nickNameA = "pailo";
        String nombre = "S";
        String email = "sfsde@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.htrws.com";
        
        String nombreR = "PO293";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Japon";
		String ciudad = "Osaka";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "PO293765";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Negocio";
		cat[1]="Relax";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, email, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, "sdawq", "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e.getMessage());
        	}
        	try {
				controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, null, fechaAlta);
			} catch (ReservaYaExisteException e) {
				fail(e);
			}
        	DTReserva res = null;
			res = controladorRutaDeVuelo.obtenerInfoReserva(nomVuelo, nickNameU);
        	assert(res != null);
	}
	@Test
	void testObtenerInfoReservaWebOK() {
		
		//Vine cargado del test listar reservas anterior
		String nickNameU = "julio";
		String nomVuelo = "MJ8912";
		
       	DTReservaWeb res = null;
		res = controladorRutaDeVuelo.obtenerInfoReservaWeb(nomVuelo, nickNameU);
        assert(res != null);
	}
	
	@Test
	void testHacerCheckin() {
		
		//Vine cargado del test listar reservas anterior
		String nickNameU = "julio";
		String nomVuelo = "MJ8912";
		controladorRutaDeVuelo.hacerCheckin(nickNameU, nomVuelo, true);
	}
	
	@Test
	void testObtenerNicknameAerolineaDeRutaOK() throws UsuarioRepetidoException {
		//Datos de prueba
		String nick = "afra";
		String nombreAero = "Air Europa";
		String email = "rshhs@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nombre = "FTR23";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "fsdf";
		String ciudad = "rwer";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "23";
		cat[1]="41";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombre,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
		String nomA = controladorRutaDeVuelo.obtenerNicknameAerolineaDeRuta(nombre);
		assert(nomA == nick);
	}
	
	
	@Test
	void testobtenerRutaDeVueloOK() {
		String nickNameA = "azwequl";
        String nombre = "S";
        String email = "shrdasdwth@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "AZ9dw8";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Austradwlia";
		String ciudad = "Sydney";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "AZ98dq654";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Cangufdros";
		cat[1]= "Tazmafsnia";
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaAereolinea(nickNameA, nombre, email, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	}catch(Exception e){
        		fail(e);
        	}
        String ruta = null;
        try {
			ruta = controladorRutaDeVuelo.obtenerRutaDeVuelo(nomVuelo);
		} catch (VueloNoExisteException e) {
			fail(e.getMessage());
		}
        assert(ruta == nombreR);
	}
	
	@Test
	void testListarTodasLasRutasDeVueloOK() {
		//Datos de prueba
		String nick = "arqwrireuropa";
		String nombreAero = "Air Europa";
		String email = "reserdwqqvas@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "airedasuropa";
		String email1 = "resedasdrvas@aireuropa.com.uy";

		
		String nombreR1 = "UX46f";
		String nombreR2 = "UX47f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Indfeia";
		String ciudad = "Banglafedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufweeba";
		cat[1]="Pruebfwefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			
			List<String> rutas = controladorRutaDeVuelo.listarTodasLasRutasDeVuelo();
			assert(!rutas.isEmpty());
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	@Test
	void testListarTodasLasRutasDeVueloWeb() {
		String[] rutas = controladorRutaDeVuelo.listarTodasLasRutasDeVueloWeb();
	}
	
	@Test
	void testListarTodasLasRutasDeVueloConfirmadasDTOK() {
		//Datos de prueba
		String nick = "arqwrireafwassduropaa";
		String nombreAero = "Air Europa";
		String email = "reserdwqawaeawfrasdfqvas@aireuropa.com.uya";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "aireddaasdawsusdropaa";
		String email1 = "reseddasasdgsdfgsdfdrvas@aireuropa.com.uya";

		
		String nombreR1 = "UX4ddad6f";
		String nombreR2 = "UX4add7f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Indfddeia";
		String ciudad = "Badngldasafedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufwesdaweba";
		cat[1]="Pruebfwwdaefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			
			List<DTRutaDeVuelo> rutas = controladorRutaDeVuelo.listarTodasRutasDeVueloConfirmadasDT();
			assert(!rutas.isEmpty());
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testListarTodasLasRutasDeVueloConfirmadasDTWebOK() {
		//Datos de prueba
		String nick = "arqwrireuropaa";
		String nombreAero = "Air Europa";
		String email = "reserdwqqvas@aireuropa.com.uya";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "aireddasuropaa";
		String email1 = "reseddasdrvas@aireuropa.com.uya";

		
		String nombreR1 = "UX4d6f";
		String nombreR2 = "UX4d7f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Indfdeia";
		String ciudad = "Badnglafedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufweweba";
		cat[1]="Pruebfwwefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
						
			// Supongamos que ya tienes el arreglo
			DTRutaDeVueloWeb[] rutas = controladorRutaDeVuelo.listarTodasRutasDeVueloConfirmadasDTWeb();

			// Convertimos el arreglo a una lista
			List<DTRutaDeVueloWeb> listaRutas = Arrays.asList(rutas);

			// Verificamos que la lista no esté vacía
			assert(!listaRutas.isEmpty());

			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testAceptarRechazarRuta() {
		//Datos de prueba
		String nick = "aireufsdropa";
		String nombreAero = "Air Europa";
		String email = "reservas@airdfseuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nombre = "UX46fd";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Inddia";
		String ciudad = "Banglsadesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prfdsueba";
		cat[1]="Pruesba2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombre,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			DTRutaDeVuelo dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVuelo(nombre);
			
			assertEquals(dtrv.getEstado(), EstadoRuta.Ingresada);
			controladorRutaDeVuelo.rechazarRuta(nombre);
			dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVuelo(nombre);
			assertEquals(dtrv.getEstado(), EstadoRuta.Rechazada);
			controladorRutaDeVuelo.aceptarRuta(nombre);
			dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVuelo(nombre);
			assertEquals(dtrv.getEstado(), EstadoRuta.Confirmada);
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (RutaDeVueloNoExisteException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testAceptarRechazarRutaWeb() {
		//Datos de prueba
		String nick = "aireuropa";
		String nombreAero = "Air Europa";
		String email = "reservas@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nombre = "UX46";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "India";
		String ciudad = "Bangladesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prueba";
		cat[1]="Prueba2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombre,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			DTAerolinea aero = (DTAerolinea) controladorUsuario.obtenerInfoUsuario(nick);
			DTRutaDeVueloWeb dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVueloWeb(nombre, aero);
			
			assertEquals(dtrv.getEstado(), EstadoRuta.Ingresada);
			controladorRutaDeVuelo.rechazarRuta(nombre);
			dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVueloWeb(nombre, aero);
			assertEquals(dtrv.getEstado(), EstadoRuta.Rechazada);
			controladorRutaDeVuelo.aceptarRuta(nombre);
			dtrv = controladorRutaDeVuelo.obtenerInfoRutaDeVueloWeb(nombre, aero);
			assertEquals(dtrv.getEstado(), EstadoRuta.Confirmada);
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (RutaDeVueloNoExisteException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	@Test
	void testListarRutasIngresadas() {
		//Datos de prueba
		String nick = "arqwridsfsdfsdfsdfdsreuropa";
		String nombreAero = "Aidsar Europa";
		String email = "reserdwqqvdsadfsdfsdfsasss@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "airedsdfsadssuropa";
		String email1 = "reseddsdasdfsdfsdfsdfsdfsdrvas@aireuropa.com.uy";

		
		String nombreR1 = "dsds";
		String nombreR2 = "UXddssd47f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Inddsdfeia";
		String ciudad = "Bandgladafedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufdaaweeba";
		cat[1]="Pruebdasfswdefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			
			List<String> rutas = controladorRutaDeVuelo.listarRutasIngresadas(nick);
			assert(!rutas.isEmpty());
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	@Test
	void testListarRutasIngresadasWeb() {
		//Datos de prueba
		String nick = "arqwridsreuropa";
		String nombreAero = "Air Europa";
		String email = "reserdwqqvdsas@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "airedadssuropa";
		String email1 = "reseddsdasdrvas@aireuropa.com.uy";

		
		String nombreR1 = "ds";
		String nombreR2 = "UXdsd47f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Inddfeia";
		String ciudad = "Bandglafedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufaweeba";
		cat[1]="Pruebfswdefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			
			String[] rutas = controladorRutaDeVuelo.listarRutasIngresadasWeb(nick);

			// Verificamos si el arreglo no está vacío
			assert(rutas.length > 0);

			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testListarRutasConfirmadasDT() {
		//Datos de prueba
		String nick = "arqwriddadsdsreuropa";
		String nombreAero = "Airdasds Europa";
		String email = "reserddswdqqvdsas@aireuropgfda.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "airedadssdsurdopa";
		String email1 = "reseddsdsddasdrvas@airfdseuropa.com.uy";

		
		String nombreR1 = "dsdaddassd";
		String nombreR2 = "UXdsddaadasd47f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Inddfasdseia";
		String ciudad = "Bdasdandgladsfedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufaweeddssasdba";
		cat[1]="Pruebfswasddsdefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.aceptarRuta(nombreR1);
			List<DTRutaDeVuelo> rutas = controladorRutaDeVuelo.listarRutasConfirmadasDT(nick);
			assert(!rutas.isEmpty());
			
			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testListarRutasConfirmadasDTWeb() {
		//Datos de prueba
		String nick = "arqwriddsreuropa";
		String nombreAero = "Airds Europa";
		String email = "reserddswqqvdsas@aireuropa.com.uy";
		String descripcionAero = "Aerolı́nea española que ofrece vuelos varios destinos en Europa y América.";
		String sitioWeb = "https://www.aireuropa.com";
		
		String nick1 = "airedadssdsuropa";
		String email1 = "reseddsdsddasdrvas@aireuropa.com.uy";

		
		String nombreR1 = "dsdasd";
		String nombreR2 = "UXdsdasd47f";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Inddfaseia";
		String ciudad = "Bdasdandglafedesh";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Prufaweedasdba";
		cat[1]="Pruebfswasddefa2";

		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorUsuario.altaAereolinea(nick,nombreAero , email, "",descripcionAero,sitioWeb, "");
			controladorUsuario.altaAereolinea(nick1,nombreAero , email1, "",descripcionAero,sitioWeb, "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick,nombreR1,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nick1,nombreR2,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.aceptarRuta(nombreR1);
			DTRutaDeVueloWeb[] rutas = controladorRutaDeVuelo.listarRutasConfirmadasDTWeb(nick);

			// Verificamos si el arreglo no está vacío
			assert(rutas.length > 0);

			
		} catch (RutaDeVueloRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	void testReservarVueloConPaqueteOK() {
		String nickNameU = "juldido";
		String nickNameA = "mojddave";
        String nombre = "S";
        String email = "sjddyddstb@aa.com";
        String descripcion = "Vidajes";
        String sitioWeb = "https://wdww.s.com";
        
        String nombreR = "MJdd89";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Cdanadda";
		String ciudad = "Modndtreal";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "MdJd8912";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Rdodto";
		cat[1]="Saddno";
		List<DTPasaje> pas = new ArrayList<DTPasaje>();
		pas.add(new DTPasaje("asdasd","sdas",0));
		pas.add(new DTPasaje("asdasddasd","saddsasdas",0));
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, email, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, "ndsdtdsrw", "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e);
        	}
        	try {
				controladorRutaDeVuelo.reservarVueloConPaquete(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta, 30);
			} catch (ReservaYaExisteException e) {
				fail(e);
			}
        	List<String> res = null;
			res = controladorRutaDeVuelo.listarReservas(nomVuelo);
        	assert(res.contains(nickNameU));
	}
	@Test
	void testsumarVisitas() {
		String nickNameU = "julido";
		String nickNameA = "mojdave";
        String nombre = "S";
        String email = "sjdyddstb@aa.com";
        String descripcion = "Vidajes";
        String sitioWeb = "https://wdww.s.com";
        
        String nombreR = "MJd89";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Cdanada";
		String ciudad = "Modntreal";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "MdJ8912";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Rodto";
		cat[1]="Sadno";
		List<DTPasaje> pas = new ArrayList<DTPasaje>();
		pas.add(new DTPasaje("asdasd","sdas",0));
		pas.add(new DTPasaje("asdasddasd","saddsasdas",0));
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, email, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, "ndsdtrw", "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
			controladorRutaDeVuelo.sumarVisita(nombreR);
        }catch(Exception e){
    		fail(e);
    	}
	}
	
	@Test
	void testobtener5MasVisitadas() {
		controladorRutaDeVuelo.obtener5MasVisitadas();        

	}

}