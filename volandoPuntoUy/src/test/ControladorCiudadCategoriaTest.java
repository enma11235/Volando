package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import logica.DTCiudad;
import logica.Fabrica;
import logica.IControladorCiudadCategoria;

public class ControladorCiudadCategoriaTest {
	private static IControladorCiudadCategoria controladorCC;

	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorCC = fabrica.getIControladorCiudadCategoria();

		String pais = "USA";
		String ciudad = "Alabama";
		String aeropuerto = "Birmingham Airport";
		String descripcion = "Birmingham Airport";
		String sitioWeb = "https://alabama.com";
		LocalDate fechaAlta = LocalDate.of(2001, 1, 1);

		try {
			controladorCC.crearNuevaCiudad(pais, ciudad, aeropuerto, descripcion, sitioWeb, fechaAlta);
		} catch (Exception e) {
		}
	}

	@Test
	void testCrearNuevaCiudad() {
		String pais = "Puerto Rico";
		String ciudad = "Torres de Sabana";
		String aeropuerto = "No hay";
		String descripcion = "Capital del trap";
		String sitioWeb = "https://2016.com";
		LocalDate fechaAlta = LocalDate.of(2016, 1, 1);

		try {
			controladorCC.crearNuevaCiudad(pais, ciudad, aeropuerto, descripcion, sitioWeb, fechaAlta);
			List<DTCiudad> ciudades = controladorCC.listarCiudades();
			boolean existe = false;
			for(int x=0; x<ciudades.size(); x++) {
				existe = ciudades.get(x).getPais() == pais;
				if (existe)
					break;
			}
			assertTrue(existe);
		} catch (CiudadRepetidaException e) {
			fail("Ciudad ya está registrada");
		}
	}

	@Test
	void testAgregarCategoria() {
		String categoria = "Ejemplo";

		try {
			controladorCC.agregarCategoria(categoria);
			assertThrows(CategoriaRepetidaException.class, () -> {
				controladorCC.agregarCategoria(categoria); // Debería fallar por duplicar la categoría
			});
		} catch (CategoriaRepetidaException e) {
			fail("Categoría repetida de primeras");
		}
	}

	@Test
	void testListarCiudades() {
		List<DTCiudad> ciudades = controladorCC.listarCiudades();
		assertNotNull(ciudades);
		assertTrue(ciudades.size() > 0);
	}

}
