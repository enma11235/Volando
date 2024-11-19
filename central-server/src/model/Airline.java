package model;

import java.util.*;
import datatype.*;

public class Airline extends User {
	
	
	private List<FlightRoute> rutasDeVuelo;

	//atributos
	private String descripcion;
	private String sitioWeb;

	public Airline(String nickname, String nombre, String email, String contrasena, String descripcion, String sitio_web, List<FlightRoute> rvs, String imagen) {
		super(nickname, nombre, email, contrasena, imagen);
		this.descripcion = descripcion;
		this.sitioWeb = sitio_web;
		this.rutasDeVuelo = rvs;
	}
	
	//----------------OPERACIONES---------------------

	@Override
	public Boolean esAerolinea() {
		return true;
	}

	@Override
	public Boolean esCliente() {
		return false;
	}

	@Override
	public UserDTO getData() {
		return new AirlineDTO(super.getNickname(), super.getNombre(), super.getEmail(), super.getContrasena(), this.descripcion, this.sitioWeb, super.getImagen());
	}
	
	public void agregarRutaDeVuelo(FlightRoute ruta) {
		this.rutasDeVuelo.add(ruta);
	}

	
	public void removeRutaDeVuelo(FlightRoute ruta) {
		this.rutasDeVuelo.remove(ruta);
	}
	
	public List<String> listarRutasDeVuelo() {
		List<String> nombresrvs = new ArrayList<String>();
		for (int i = 0; i < rutasDeVuelo.size(); i++) {
			nombresrvs.add(rutasDeVuelo.get(i).getNombre());
		}
		return nombresrvs;
	}
	
	public List<FlightRoute> getRutasDeVuelo(){
		return rutasDeVuelo;
	}
	
	public FlightRoute getRutaDeVuelo(String nomRutaDeVuelo) {
	    if (rutasDeVuelo == null || rutasDeVuelo.isEmpty()) {
	        return null; 
	    } 
	    for (FlightRoute ruta : rutasDeVuelo) {
	        if (ruta.getNombre().equals(nomRutaDeVuelo)) {
	            return ruta; 
	        }
	    }
	    return null;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
	
	public List<String> listarRutasIngresadas(){
		List<String> nombresrvs = new ArrayList<String>();
		for (int i = 0; i < rutasDeVuelo.size(); i++) {
			if (rutasDeVuelo.get(i).getEstado() == FlightRouteState.Ingresada) nombresrvs.add(rutasDeVuelo.get(i).getNombre());			
		}
		return nombresrvs;
	}
	public List<FlightRouteDTO> listarRutasConfirmadasDT(){
		List<FlightRouteDTO> dtrvs = new ArrayList<FlightRouteDTO>();
		for (int i = 0; i < rutasDeVuelo.size(); i++) {
			if (rutasDeVuelo.get(i).getEstado() == FlightRouteState.Confirmada) dtrvs.add(rutasDeVuelo.get(i).getData());			
		}
		return dtrvs;
	}
}
