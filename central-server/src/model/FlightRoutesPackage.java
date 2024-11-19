package model;

import java.time.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import datatype.*;
public class FlightRoutesPackage {
	
	  private String nombre;

	  private String descripcion;
	  private Duration periodoValidez;
	  private float descuento; 
	  private LocalDate fechaAlta;
	  private String imagen;
	  
	  private List<FlightRoutePackageLink> rutaPaquete = new ArrayList<FlightRoutePackageLink>();

	  private List<Purchase> compra = null;
	  

	  public FlightRoutesPackage(String nom, String desc, Duration perValidez, float descuento, LocalDate fAlta, String imagen) {
		  this.nombre = nom;
		  this.descripcion = desc; 
		  this.periodoValidez = perValidez;
		  this.descuento = descuento;
		  this.fechaAlta = fAlta;
		  this.setImagen(imagen);
	  } 
	  

		
		public String getIdsRutas() {
			String d ="";
			for(FlightRoutePackageLink rp : rutaPaquete) {
				d+=rp.getId().toString()+',';
			}
			return d;
		}

		public String getNombre() {
			return nombre;
		}


		public List<FlightRoutePackageLink> getRutaPaquete() {
			return rutaPaquete;
		}

		public List<Purchase> getCompra() {
			return compra;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}


		public Duration getPeriodoValidez() {
			return periodoValidez;
		}


		public void setPeriodoValidez(Duration periodoValidez) {
			this.periodoValidez = periodoValidez;
		}


		public float getDescuento() {
			return descuento;
		}


		public void setDescuento(float descuento) {
			this.descuento = descuento;
		}


		public LocalDate getFechaAlta() {
			return fechaAlta;
		}


		public void setFechaAlta(LocalDate fechaAlta) {
			this.fechaAlta = fechaAlta;
		}

		
		public boolean esPaqueteComprado() {
			return compra != null;
		}
		
		public boolean esPaqueteCompradoPorCliente(String nickName) {
			boolean res = false; 
			if (compra != null) {
				for (Purchase c: compra) {
					if ((c.getCliente().getNickname()).equals(nickName))
						res = true; 
				}
			}
			return res; 
		}
		public boolean esVacio() {
			return rutaPaquete.isEmpty();
		}
		
		public boolean esValidoHoy() {
	        LocalDate fechaExpiracion = fechaAlta.plusDays(periodoValidez.toDays()); 
	        return !LocalDate.now().isAfter(fechaExpiracion);
	    }
		
		
		public void addRutaPaquete(FlightRoutePackageLink rutaP) {
			System.out.println("Inserto: "+rutaP.getCantidad()+rutaP.getId());
			rutaPaquete.add(rutaP);
		}
		
		
		public void removeRutaPaquete(FlightRoutePackageLink rutaP) {
			rutaPaquete.remove(rutaP);
		}
		
		public FlightRoutesPackageDTO getInfoPaquete() {
			List<FlightRoutePackageLinkDTO> rutaP = new ArrayList<FlightRoutePackageLinkDTO>();
			for (FlightRoutePackageLink r:rutaPaquete) {
				rutaP.add(r.obtenerInfo());
			}
			return new FlightRoutesPackageDTO(nombre, descripcion, periodoValidez, descuento, fechaAlta, imagen, rutaP);
		}
		
		public void comprarPaquete(Purchase compraP) {
			if (compra == null)
				compra = new ArrayList<Purchase>(); 
			
			compra.add(compraP);
		}
		
		public float getPrecioPaquete() {
			float precio = 0; 
			for (FlightRoutePackageLink RP : rutaPaquete)
				precio = precio + RP.getCosto(); 
			
			return precio; 
		}

		public String getImagen() {
			
			return imagen;
		}

		public void setImagen(String imagen) {
			this.imagen = imagen;
		}
		
		public Boolean contieneRuta(String nomRuta) {
			Boolean res = false;
			for (FlightRoutePackageLink rt : rutaPaquete) {
				if (rt.obtenerInfo().getRuta().getNombre().equals(nomRuta)) res = true;
			}
			return res;
		}
		
	    
}
