package service;
import datatype.*;
import java.time.LocalDate;


import java.util.List;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import model.Category;

public interface ICityCategoryController {


    public abstract void crearNuevaCiudad(String pais, String ciudad, String aeropuerto, String descripcion, String sitio_web, LocalDate fecha_alta) throws CiudadRepetidaException;

    public abstract List<CityDTO> listarCiudades();

    public abstract void agregarCategoria(String nombre) throws CategoriaRepetidaException;
    
    public abstract Category obtenerCategoria(String nombre);
    public List<String> listarCategorias();

}
