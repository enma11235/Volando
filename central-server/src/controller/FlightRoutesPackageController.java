package controller;
import controller.*;
import service.*;
import excepciones.*;
import persistencia.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;
import java.time.Duration;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.log.SysoCounter;

import excepciones.PaqueteYaCompradoException;
import excepciones.PaqueteYaExisteException;


public class FlightRoutesPackageController implements IPackageController {

	@Override
	public List<String> listarPaquetes() {
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		return manejadorP.listarPaquetes();
	}

	@Override
	public List<String> listarPaquetesSinComprar() {
		List<String> res = new ArrayList<String>();
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		List<Package> paquetes =  manejadorP.getPaquetes();
		
		for (Package p:paquetes) {
			if (!p.esPaqueteComprado()) res.add(p.getNombre());
		}
		return res;
	}

	@Override
	public List<String> listarPaquetesNoVacios() {
		List<String> res = new ArrayList<String>();
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		List<Package> paquetes =  manejadorP.getPaquetes();
		
		for (Package p:paquetes) {
			if (!p.esVacio()) res.add(p.getNombre());
		}
		return res;
	}

	@Override
	public FlightRoutesPackageDTO obtenerInfoPaquete(String nombre) {
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		Package obtener = manejadorP.obtenerPaquete(nombre);
		FlightRoutesPackageDTO res = obtener.getInfoPaquete();
		return res;
	}
	
	@Override
	public FlightRoutesPackageWebDTO obtenerInfoPaqueteWeb(String nombre) {
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		Package obtener = manejadorP.obtenerPaquete(nombre);
		FlightRoutesPackageWebDTO dtp = null;
		
		if (obtener != null) {
			FlightRoutesPackageDTO paquete = obtener.getInfoPaquete();
			dtp =  new FlightRoutesPackageWebDTO(paquete.getNombre(), paquete.getDescripcion(), paquete.getPeriodoValidez().toString(), paquete.getDescuento(), paquete.getFechaAlta().toString(), paquete.getImagen(), paquete.getRutas());
		}
		return dtp;
	}

	@Override
	public void crearPaqueteRutasDeVuelo(String nombre, String descripcion, Duration periodoValidez, float descuento, LocalDate fechaAlta, String imagen)  throws PaqueteYaExisteException{
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		if (manejadorP.existePaquete(nombre))
			throw new PaqueteYaExisteException("El paquete " + nombre + " ya esta registrado");
		else {
			Package nuevo = new Package(nombre, descripcion, periodoValidez, descuento, fechaAlta, imagen);
			manejadorP.addPaquete(nuevo);
		}
	}

	@Override
	public void agregarRutaAPaquete(String nomRuta, String nomPaquete, SeatType asiento, int cantidad) {
		
		System.out.println(nomRuta+nomPaquete+asiento+cantidad);
		
		FlightRoutePackageLink nuevo = new FlightRoutePackageLink(asiento, cantidad);
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		ManejadorRutaDeVuelo manejadorR = ManejadorRutaDeVuelo.getInstance();
		FlightRoute ruta = manejadorR.obtenerRutaDeVuelo(nomRuta);
		nuevo.setRutaDeVuelo(ruta);
		System.out.println("quiero obtener: "+nomPaquete);
		Package paquete= manejadorP.obtenerPaquete(nomPaquete);
		nuevo.setPaquete(paquete);
		Long id = manejadorP.addRutaPaquete(nuevo);
		nuevo.setId(id);
		System.out.println("generado: "+nuevo.toString());
		System.out.println("pak: "+paquete.getIdsRutas());
		paquete.addRutaPaquete(nuevo);
		System.out.println("generadopost: "+nuevo.toString());
		System.out.println("pakpost: "+paquete.getIdsRutas());
		manejadorP.updatePaquete(paquete);
	}
	
	public List<String> listarPaquetesRuta(String nomRuta){
		List<String> res = new ArrayList<String>();
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		List<Package> paquetes =  manejadorP.getPaquetes();
		if (paquetes != null) {
			for (Package p:paquetes) {
				if (p.contieneRuta(nomRuta)) res.add(p.getNombre());
			}
		}
		return res;
	}
	
	@Override
	public void comprarPaquete(String nombrePaquete, String nicknameCliente) throws PaqueteYaCompradoException {
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();
		Package paquete = manejadorP.obtenerPaquete(nombrePaquete);
		
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Client cliente = manejadorU.obtenerCliente(nicknameCliente);
		
		ArrayList<Purchase> compras = (ArrayList<Purchase>) cliente.getCompras();
		boolean  paqueteComprado = false;
		for (Purchase com : compras) {
			Package paq = com.getPaquete();
			if (paq.getNombre().equals(paquete.getNombre())) {
				paqueteComprado = true;
			} 
		}
		if (paqueteComprado) {
			throw new PaqueteYaCompradoException("El paquete ya fue comprado");
		} else {
			Purchase compra = new Purchase(LocalDate.now(), paquete.getPrecioPaquete(), null);
	        paquete.comprarPaquete(compra);
	        compra.setCliente(cliente);
	        compra.setPaquete(paquete);
	        
	        Long id = manejadorU.addCompra(compra);
	        compra.setId(id);
	        
	        cliente.addCompra(compra);
	        manejadorU.updateUsuario(cliente);
	        
	        
		}
        
	}

}
