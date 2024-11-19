package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import excepciones.PaqueteYaExisteException;
import excepciones.ReservaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;

import java.time.Duration;
import java.time.LocalDate;


import logica.Fabrica;
import logica.IControladorPaquete;
import logica.IControladorRutaDeVuelo;
import logica.IControladorUsuario;
import logica.IControladorCiudadCategoria;

class ExcepcionesTest {

	private static IControladorPaquete CP = Fabrica.getInstance().getIControladorPaquete();
	private static IControladorRutaDeVuelo CRDV = Fabrica.getInstance().getIControladorRutaDeVuelo();
	private static IControladorUsuario CU = Fabrica.getInstance().getIControladorUsuario();
	private static IControladorCiudadCategoria CCC = Fabrica.getInstance().getIControladorCiudadCategoria();
	
	@Test
	void testNickRepetido() {
		boolean ex = false;
		try {
			CU.altaAereolinea("a", "b", "c", "", "d", "e", "");
			CU.altaAereolinea("a", "b", "c1", "", "d", "e", "");
		} catch (UsuarioRepetidoException e) {
			ex = true;
		}
		if(!ex) fail("No hay excepcion nick repetido");
		
	}
	
	@Test
	void testEmailRepetido() {
		boolean ex = false;
		try {
			CU.altaAereolinea("a1", "b", "c2", "", "d", "e", "");
			CU.altaAereolinea("a2", "b", "c2", "", "d", "e", "");
		} catch (UsuarioRepetidoException e) {
			ex = true;
		}
		if(!ex) fail("No hay excepcion email repetido");
		
	}
	
	@Test
	void testCategoriaRepetida() {
		boolean ex = false;
		try {
			CCC.agregarCategoria("a1");
			CCC.agregarCategoria("a1");
		} catch (CategoriaRepetidaException e) {
			// TODO Auto-generated catch block
			ex = true;
		}
		if(!ex) fail("No hay excepcion categoria repetido");
		
	}
	
	@Test
	void testCiudadRepetida() {
		boolean ex = false;
		try {
			CCC.crearNuevaCiudad("a1","b1","sd","sd", "dsad", LocalDate.now());
			CCC.crearNuevaCiudad("a1","b1","sd","sd", "dsad", LocalDate.now());
		} catch (CiudadRepetidaException e) {
			// TODO Auto-generated catch block
			ex = true;
		}
		if(!ex) fail("No hay excepcion ciudad repetida");
		
	}
	
	@Test
	void testPaqueteRepetido() {
		boolean ex = false;
		try {
			CP.crearPaqueteRutasDeVuelo("a", "b", Duration.ZERO, 0.1f, LocalDate.now(), "");
			CP.crearPaqueteRutasDeVuelo("a", "b", Duration.ZERO, 0.1f, LocalDate.now(), "");
		} catch (PaqueteYaExisteException e) {
			// TODO Auto-generated catch block
			ex = true;
		}
		if(!ex) fail("No hay excepcion paquete repetido");
		
	}
	
	@Test
	void testUsuarioNoExiste() {
		boolean ex = false;
		try {
			CU.listarReservasCliente("noexiste");
		} catch (UsuarioNoExisteException | ReservaNoExisteException e) {
			ex = true;
		}
		if(!ex) fail("No hay excepcion usuario inexistente");
		
	}
	
}

