package datatype;

import java.time.Duration;

import java.time.LocalDate;
import java.util.List;

public class DTPaquete {

	 private String nombre;
	  private String descripcion;
	  private Duration periodoValidez;
	  private float descuento; 
	  private LocalDate fechaAlta;
	  private String imagen;
	  private List<DTRutaPaquete> rutas;
	
	  
	public DTPaquete(String nombre, String descripcion, Duration periodoValidez, float descuento, LocalDate fechaAlta, String imagen,
			List<DTRutaPaquete> rutas) {

		this.nombre = nombre;
		this.descripcion = descripcion;
		this.periodoValidez = periodoValidez;
		this.descuento = descuento;
		this.fechaAlta = fechaAlta;
		this.rutas = rutas;
		this.imagen = imagen;
	}


	public List<DTRutaPaquete> getRutas() {
		return rutas;
	}

	public String getNombre() {
		return nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public float getPrecioPaquete() {
		float precio = 0; 
		if (rutas != null) {
			for (DTRutaPaquete RP : rutas)
				precio = precio + RP.getCosto(); 
		}	
	
		return precio; 
	}

	public Duration getPeriodoValidez() {
		return periodoValidez;
	}
	
	public boolean esValidoHoy() {
        LocalDate fechaExpiracion = fechaAlta.plusDays(periodoValidez.toDays()); 
        return !LocalDate.now().isAfter(fechaExpiracion);
    }


	public float getDescuento() {
		return descuento;
	}


	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	@Override
    public String toString() {
        return "Paquete{" +
                "Nombre='" + nombre + '\'' +
                ", Descripción='" + descripcion + '\'' +
                ", Periodo de Validez=" + periodoValidez.toDays() + " días" + // Ejemplo para mostrar los días
                ", Descuento=" + descuento + "%" +
                ", Fecha de Alta=" + fechaAlta +
                ", Rutas=" + (rutas != null ? rutas.size() + " rutas" : "No hay rutas") + 
                '}';
    }


	public String getImagen() {
		return imagen;
	}
	
}
