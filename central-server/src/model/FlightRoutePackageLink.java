package model;
import datatype.*;
public class FlightRoutePackageLink {
	
	//links
	private FlightRoute ruta;
	private FlightRoutesPackage paquete;
	
	//constructor
	
	//atributos
	private SeatType tipoAsiento;
	public FlightRoutePackageLink(SeatType tipoAsiento, int cantidad) {
		this.tipoAsiento = tipoAsiento;
		this.cantidad = cantidad;
	}

	private int cantidad;
	private float costo;
	
	private Long id; //jpa
	
	//operaciones
	public void setRutaDeVuelo(FlightRoute rutaP) {
		this.ruta = rutaP;
		calcularCosto();
	}
	public FlightRoutePackageLinkDTO obtenerInfo() {
		FlightRouteDTO rutaV = ruta.getData();
		return new FlightRoutePackageLinkDTO(tipoAsiento, cantidad, costo, rutaV);
	}
	
	public void setPaquete(FlightRoutesPackage paqueteR) {	
		this.paquete = paqueteR;
		calcularCosto();
	}
	
	private void calcularCosto() {
		if (this.paquete!=null && this.ruta !=null) {
			switch (this.tipoAsiento) {
				case EJECUTIVO:
					this.costo = this.cantidad * (this.ruta.getCostoEjecutivo() * (1 - (this.paquete.getDescuento()) / 100) );
					break;
				case TURISTA:
					this.costo = this.cantidad * (this.ruta.getCostoTurista() * (1 - (this.paquete.getDescuento()) / 100) );
					break;
			}
		}
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getCosto() {
		return this.costo; 			
	}
	public FlightRoute getRuta() {
		return ruta;
	}
	public FlightRoutesPackage getPaquete() {
		return paquete;
	}
	public SeatType getTipoAsiento() {
		return tipoAsiento;
	}
	public int getCantidad() {
		return cantidad;
	}
	@Override
	public String toString() {
		return "RutaPaquete [ruta=" + ruta + ", paquete=" + paquete + ", tipoAsiento=" + tipoAsiento + ", cantidad="
				+ cantidad + ", costo=" + costo + ", id=" + id + "]";
	}
	
}
