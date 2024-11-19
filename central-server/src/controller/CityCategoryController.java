package controller;
import controller.*;
import service.*;
import excepciones.*;
import persistencia.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;



public class CityCategoryController implements ICityCategoryController{
	
	public CityCategoryController() {
		
	}

	public void crearNuevaCiudad(String pais, String ciudad, String aeropuerto, String descripcion, String sitio_web, LocalDate fecha_alta) throws CiudadRepetidaException {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		if (mrv.existeCiudad(pais, ciudad))
			throw new CiudadRepetidaException("La ciudad " + ciudad + " con país "+pais+" ya esta registrada");
		else {
			City ciudadO = new City(pais, ciudad, aeropuerto, descripcion, sitio_web, fecha_alta);
			mrv.agregarCiudad(ciudadO);
		}
	}
	
	public List<CityDTO> listarCiudades(){
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		List<City> ciudades = mrv.listarCiudades();
		List<CityDTO> dtciudades = new ArrayList<CityDTO>();
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
			Category cate = new Category(nombre);
			mrv.agregarCategoria(cate);
		}
	}
	
	public Category obtenerCategoria(String nombre) {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		return mrv.buscarCategoria(nombre);
	}
	
	public List<String> listarCategorias() {
		ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
		return mrv.listarCategorias();
	}

}