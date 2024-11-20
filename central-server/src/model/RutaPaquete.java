package model;
import datatype.*;
public class RutaPaquete {
	
	//links
	private FlightRoute ruta;
	private Paquete paquete;
	
	//constructor
	
	//atributos
	private SeatType tipoAsiento;
	public RutaPaquete(SeatType tipoAsiento, int cantidad) {
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
	public DTRutaPaquete obtenerInfo() {
		DTRutaDeVuelo rutaV = ruta.getData();
		return new DTRutaPaquete(tipoAsiento, cantidad, costo, rutaV);
	}
	
	public void setPaquete(Paquete paqueteR) {	
		this.paquete = paqueteR;
		calcularCosto();
	}
	
	private void calcularCosto() {
		if (this.paquete!=null && this.ruta !=null) {
			switch (this.tipoAsiento) {
				case EXECUTIVE:
					this.costo = this.cantidad * (this.ruta.getCostoEjecutivo() * (1 - (this.paquete.getDescuento()) / 100) );
					break;
				case TOURIST:
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
	public Paquete getPaquete() {
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
