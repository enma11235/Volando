package test;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.PaqueteYaExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import logica.Compra;
import logica.DTPaquete;
import logica.DTPaqueteWeb;
import logica.Fabrica;
import logica.IControladorPaquete;
import logica.IControladorRutaDeVuelo;
import logica.IControladorUsuario;
import logica.IControladorCiudadCategoria;
import logica.ManejadorPaquetes;
import logica.Paquete;
import logica.TipoAsiento;
import logica.TipoDocumento;

public class ControladorPaqueteTest {

	private static IControladorPaquete CP = Fabrica.getInstance().getIControladorPaquete();
	private static IControladorRutaDeVuelo CRDV = Fabrica.getInstance().getIControladorRutaDeVuelo();
	private static IControladorUsuario CU = Fabrica.getInstance().getIControladorUsuario();
	private static IControladorCiudadCategoria CCC = Fabrica.getInstance().getIControladorCiudadCategoria();
	
	@Test
	void testCrearPaquete() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre = "Invierno";
		try {
			CP.crearPaqueteRutasDeVuelo(nombre, "Vuelos de verano",d , 10, fA, "");
			DTPaquete paquete = CP.obtenerInfoPaquete(nombre);
			if (paquete.getNombre() != nombre) fail("No se agregó el paquete");
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");
		}
		
	}
	
	@Test
	void testInfoPaqueteWeb() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre = "Invsierno";
		try {
			CP.crearPaqueteRutasDeVuelo(nombre, "Vuelos sde verano",d , 10, fA, "");
			DTPaqueteWeb paquete = CP.obtenerInfoPaqueteWeb(nombre);
			if (paquete.getNombre() != nombre) fail("No se agregó el paquete");
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");
		}
		
	}
	
	@Test
	void testAgregarRutaAPaquete() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre = "Verano";
		String nomRuta = "FR-BR";
		String nomAero = "latam";
		Float costo = (float) 1;
		String pais = "Francia";
		String ciudad = "Paris";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Nacionales";
		cat[1]="Internacionales";
		try {
			CCC.agregarCategoria(cat[0]);
			CCC.agregarCategoria(cat[1]);
			CCC.crearNuevaCiudad(pais, ciudad, nombre, nomRuta, nomAero, fA);
			CU.altaAereolinea(nomAero, "", "greh", "", "", "", "");
			CRDV.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			CP.crearPaqueteRutasDeVuelo(nombre, "Vuelos de verano",d , 10, fA, "");
			CP.agregarRutaAPaquete(nomRuta, nombre, TipoAsiento.TURISTA, 1);
			DTPaquete paquete = CP.obtenerInfoPaquete(nombre);
			if (paquete.getNombre() != nombre) fail("No se agregó el paquete");
			if(paquete.getRutas().isEmpty()) fail("No se agregó la ruta");
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");	
		} catch (RutaDeVueloRepetidaException e) {
			fail("Ruta de vuelo ya existe, cuando no debería");
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			fail("Paquete ya existe, cuando no debería");
		} catch (UsuarioRepetidoException e) {
			fail("Aerolinea ya existe, cuando no debería");
		} catch (CiudadRepetidaException e) {
			fail("Ciudad ya existe, cuando no debería");
		} catch (CategoriaRepetidaException e) {
			fail("Categoria ya existe, cuando no debería");
		}
		
	}
	
	@Test
	void testListarPaquetes() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre1 = "Otono";
		String nombre2 = "Primavera";
		try {
			CP.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de otono",d , 10, fA, "");
			CP.crearPaqueteRutasDeVuelo(nombre2, "Vuelos de primavera",d , 10, fA, "");
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");
		}
		List<String> paquetes = CP.listarPaquetes();
		assert(paquetes.contains(nombre1));
		assert(paquetes.contains(nombre2));
		
	}
	
	@Test
	void testListarPaquetesNoVacios() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre1 = "Vacaciones";
		String nombre2 = "Trabajo";
		String nomRuta = "PT-CHN";
		String nomAero = "aero1";
		Float costo = (float) 1;
		String pais = "Francia";
		String ciudad = "Lyon";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Autos";
		cat[1]="Aviones";
		try {
			CCC.agregarCategoria(cat[0]);
			CCC.agregarCategoria(cat[1]);
			CCC.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			CU.altaAereolinea(nomAero, "", "gwer", "", "", "", "");
			CRDV.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			CP.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de verano",d , 10, fA, "");
			CP.crearPaqueteRutasDeVuelo(nombre2, "Vuelos de verano",d , 10, fA, "");
			CP.agregarRutaAPaquete(nomRuta, nombre1, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");	
		} catch (RutaDeVueloRepetidaException e) {
			fail("Ruta de vuelo ya existe, cuando no debería");
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			fail("Paquete ya existe, cuando no debería");
		} catch (UsuarioRepetidoException e) {
			fail("Aerolinea ya existe, cuando no debería");
		} catch (CiudadRepetidaException e) {
			fail("Ciudad ya existe, cuando no debería");
		} catch (CategoriaRepetidaException e) {
			fail("Categoria ya existe, cuando no debería");
		}
		List<String> paquetes = CP.listarPaquetesNoVacios();
		assert(paquetes.contains(nombre1));
		assert(!paquetes.contains(nombre2));
		
	}
	
	
	@Test
	void testListarPaquetesRuta() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre1 = "Vacaaaciones";
		String nombre2 = "Trabaajo";
		String nomRuta = "PT-aHN";
		String nomAero = "aerao1";
		Float costo = (float) 1;
		String pais = "Franacia";
		String ciudad = "Lyoan";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Autaos";
		cat[1]="Avioanes";
		try {
			CCC.agregarCategoria(cat[0]);
			CCC.agregarCategoria(cat[1]);
			CCC.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			CU.altaAereolinea(nomAero, "", "gawdwer", "", "", "", "");
			CRDV.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			CP.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de verano",d , 10, fA, "");
			CP.crearPaqueteRutasDeVuelo(nombre2, "Vuelos de verano",d , 10, fA, "");
			CP.agregarRutaAPaquete(nomRuta, nombre1, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");	
		} catch (RutaDeVueloRepetidaException e) {
			fail("Ruta de vuelo ya existe, cuando no debería");
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			fail("Paquete ya existe, cuando no debería");
		} catch (UsuarioRepetidoException e) {
			fail("Aerolinea ya existe, cuando no debería");
		} catch (CiudadRepetidaException e) {
			fail("Ciudad ya existe, cuando no debería");
		} catch (CategoriaRepetidaException e) {
			fail("Categoria ya existe, cuando no debería");
		}
		List<String> paquetes = CP.listarPaquetesRuta(nomRuta);
		assert(paquetes.contains(nombre1));
		assert(!paquetes.contains(nombre2));
		
	}
	
	@Test
	void testListarPaquetesSinComprar() {
		
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nomRuta = "BR-MX";
		String nomAero = "aero2";
		Float costo = (float) 1;
		String pais = "UK";
		String ciudad = "Londres";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "cat1";
		cat[1]="cat2";
		String nombre1 = "Paquete1";
		String nombre2 = "Paquete2";
		String nomCliente = "Unga";
		try {
			CCC.agregarCategoria(cat[0]);
			CCC.agregarCategoria(cat[1]);
			CCC.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			CU.altaAereolinea(nomAero, "", "fsdf", "", "", "", "");
			CRDV.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			CP.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de otono",d , 10, fA, "");
			CP.crearPaqueteRutasDeVuelo(nombre2, "Vuelos de primavera",d , 10, fA, "");
			CP.agregarRutaAPaquete(nomRuta, nombre2, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException | RutaDeVueloRepetidaException | UsuarioNoEsAerolineaExcepcion | UsuarioRepetidoException | CiudadRepetidaException | CategoriaRepetidaException e) {
			fail(e);
		}
		try {
			CU.altaCliente(nomCliente, "", "fsdfe", "", "", fA, "", TipoDocumento.CedulaIdentidad, "", "");
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		try {
			CU.clienteCompraPaquete(nomCliente, nombre2);
		} catch (UsuarioNoExisteException | PaqueteNoExisteException | PaqueteYaCompradoException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		List<String> paquetes = CP.listarPaquetesSinComprar();
		assert(paquetes.contains(nombre1));
		assert(!paquetes.contains(nombre2));
		
	}
	
	@Test
	void testDTPaquete() {
		DTPaquete prueba = new DTPaquete("a", "b", Duration.ofDays(50), 10, LocalDate.now(), "no", null);
		assert(prueba.getPrecioPaquete() == 0);
		assert(prueba.toString().contains("50")&&prueba.toString().contains("No hay rutas"));
	}
	
	
	@Test
	void testComprarPaquete() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre1 = "Vacadsadciones";
		String nombre2 = "Trabasdajo";
		String nomRuta = "PT-sdCHN";
		String nomAero = "aersado1";
		Float costo = (float) 1;
		String pais = "Frandsdcia";
		String ciudad = "Lysdon";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Autodsads";
		cat[1]="Avionasdes";
		
		String nickName = "anusel";
        String nombre = "Anusel";
        String apellido = "AsA";
        String email = "anuesl.aa@rhlm.com";
        LocalDate nacimiento = LocalDate.of(1990, 1, 1);
        String nacionalidad = "Uruguasya";
        TipoDocumento tipoDoc = TipoDocumento.CedulaIdentidad;
        String numDoc = "888828888";

        try {
            CU.altaCliente(nickName, nombre, email, "", apellido, nacimiento, nacionalidad, tipoDoc, numDoc, "");
            assertNotNull(CU.obtenerInfoUsuario(nickName));
        } catch (UsuarioRepetidoException e) {
            fail("Usuario ya registrado");
        } catch (UsuarioNoExisteException e) {
            fail("Usuario no registrado correctamente");
        }
		try {
			CCC.agregarCategoria(cat[0]);
			CCC.agregarCategoria(cat[1]);
			CCC.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			CU.altaAereolinea(nomAero, "", "gwdser", "", "", "", "");
			CRDV.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			CP.crearPaqueteRutasDeVuelo(nombre1, "Vuelosdsd de verano",d , 10, fA, "");
			CP.crearPaqueteRutasDeVuelo(nombre2, "Vuelosds de verano",d , 10, fA, "");
			CP.agregarRutaAPaquete(nomRuta, nombre1, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");	
		} catch (RutaDeVueloRepetidaException e) {
			fail("Ruta de vuelo ya existe, cuando no debería");
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			fail("Paquete ya existe, cuando no debería");
		} catch (UsuarioRepetidoException e) {
			fail("Aerolinea ya existe, cuando no debería");
		} catch (CiudadRepetidaException e) {
			fail("Ciudad ya existe, cuando no debería");
		} catch (CategoriaRepetidaException e) {
			fail("Categoria ya existe, cuando no debería");
		}
		try {
			CP.comprarPaquete(nombre1, nickName);
			CP.comprarPaquete(nombre2, nickName);
		} catch (PaqueteYaCompradoException e) {
			fail("no deberia estar comprado");
		}
		
	}
	
	
	@Test
	void testComprarPaquete2() {
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nombre1 = "Vacadsdadciones";
		String nombre2 = "Trabasddajo";
		String nomRuta = "PT-sdCdHN";
		String nomAero = "aersaddo1";
		Float costo = (float) 1;
		String pais = "Frandsddcia";
		String ciudad = "Lysddon";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "Autoddsads";
		cat[1]="Avionadsdes";
		
		String nickName = "anudel";
        String nombre = "Anusdel";
        String apellido = "AdsA";
        String email = "anuesld.aa@rhlm.com";
        LocalDate nacimiento = LocalDate.of(1990, 1, 1);
        String nacionalidad = "Uruguasya";
        TipoDocumento tipoDoc = TipoDocumento.CedulaIdentidad;
        String numDoc = "8888282888";

        try {
            CU.altaCliente(nickName, nombre, email, "", apellido, nacimiento, nacionalidad, tipoDoc, numDoc, "");
            assertNotNull(CU.obtenerInfoUsuario(nickName));
        } catch (UsuarioRepetidoException e) {
            fail("Usuario ya registrado");
        } catch (UsuarioNoExisteException e) {
            fail("Usuario no registrado correctamente");
        }
		try {
			CCC.agregarCategoria(cat[0]);
			CCC.agregarCategoria(cat[1]);
			CCC.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			CU.altaAereolinea(nomAero, "", "gwdsedr", "", "", "", "");
			CRDV.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			CP.crearPaqueteRutasDeVuelo(nombre1, "Vuelosdsd de verano",d , 10, fA, "");
			CP.crearPaqueteRutasDeVuelo(nombre2, "Vuelosds de verano",d , 10, fA, "");
			CP.agregarRutaAPaquete(nomRuta, nombre1, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException e) {
			fail("Paquete ya existe, cuando no debería");	
		} catch (RutaDeVueloRepetidaException e) {
			fail("Ruta de vuelo ya existe, cuando no debería");
		} catch (UsuarioNoEsAerolineaExcepcion e) {
			fail("Paquete ya existe, cuando no debería");
		} catch (UsuarioRepetidoException e) {
			fail("Aerolinea ya existe, cuando no debería");
		} catch (CiudadRepetidaException e) {
			fail("Ciudad ya existe, cuando no debería");
		} catch (CategoriaRepetidaException e) {
			fail("Categoria ya existe, cuando no debería");
		}
		try {
			CP.comprarPaquete(nombre1, nickName);
		} catch (PaqueteYaCompradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			CP.comprarPaquete(nombre1, nickName);
		} catch (PaqueteYaCompradoException e) {
			assertTrue(true);
		}
		
	}
}
