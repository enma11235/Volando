package datatype;

import java.time.LocalDate;

public class DTCiudad {
	
	private String nombre, pais, aeropuerto, descripcion, sitioWeb;
	private LocalDate fechaAlta;
	public DTCiudad(String pais, String nombre, String aeropuerto, String descripcion, String sitio_web,
			LocalDate fecha_alta) {
		this.nombre = nombre;
		this.pais = pais;
		this.aeropuerto = aeropuerto;
		this.descripcion = descripcion;
		this.sitioWeb = sitio_web;
		this.fechaAlta = fecha_alta;
	}
	public String getNombre() {
		return nombre;
	}
	public String getPais() {
		return pais;
	}
	public String getAeropuerto() {
		return aeropuerto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getSitioWeb() {
		return sitioWeb;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	

}