package logica;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;



public class ControladorCiudadCategoria implements IControladorCiudadCategoria{
	
	public ControladorCiudadCategoria() {
		
	}

	public void crearNuevaCiudad(String pais, String ciudad, String aeropuerto, String descripcion, String sitio_web, LocalDate fecha_alta) throws CiudadRepetidaException {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		if (mrv.existeCiudad(pais, ciudad))
			throw new CiudadRepetidaException("La ciudad " + ciudad + " con país "+pais+" ya esta registrada");
		else {
			Ciudad ciudadO = new Ciudad(pais, ciudad, aeropuerto, descripcion, sitio_web, fecha_alta);
			mrv.agregarCiudad(ciudadO);
		}
	}
	
	public List<DTCiudad> listarCiudades(){
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		List<Ciudad> ciudades = mrv.listarCiudades();
		List<DTCiudad> dtciudades = new ArrayList<DTCiudad>();
		for (int x = 0; x<ciudades.size(); x++) {
			dtciudades.add(ciudades.get(x).toDataType());
		}
		return dtciudades;
	}
	
	public void agregarCategoria(String nombre) throws CategoriaRepetidaException{
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		if (mrv.existeCategoria(nombre))
			throw new CategoriaRepetidaException("La categoría ya esta registrada");
		else {
			Categoria cate = new Categoria(nombre);
			mrv.agregarCategoria(cate);
		}
	}
	
	public Categoria obtenerCategoria(String nombre) {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		return mrv.buscarCategoria(nombre);
	}
	
	public List<String> listarCategorias() {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		return mrv.listarCategorias();
	}

}