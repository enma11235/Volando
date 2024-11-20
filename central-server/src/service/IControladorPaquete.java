package service;
import datatype.*;
import java.util.List;

import excepciones.PaqueteYaCompradoException;
import excepciones.PaqueteYaExisteException;

import java.time.Duration;
import java.time.LocalDate;


public interface IControladorPaquete {
	public List<String> listarPaquetes();
	public List<String> listarPaquetesSinComprar();
	public List<String> listarPaquetesNoVacios();
	public DTPaquete obtenerInfoPaquete(String nombre);
	public abstract DTPaqueteWeb obtenerInfoPaqueteWeb(String nombre);
	public void crearPaqueteRutasDeVuelo(String nombre, String descripcion, Duration periodoValidez, float descuento, LocalDate fechaAlta, String imagen) throws PaqueteYaExisteException;
	public void agregarRutaAPaquete(String nomRuta, String nomPaquete, SeatType asiento, int cantidad);
	public List<String> listarPaquetesRuta(String nomRuta);
	public void comprarPaquete(String nombrePaquete, String nicknameCliente) throws PaqueteYaCompradoException;;
}
