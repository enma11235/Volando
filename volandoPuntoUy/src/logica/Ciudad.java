package logica;

import java.time.LocalDate;

public class Ciudad {

	private String nombre, pais, aeropuerto, descripcion, sitioWeb;
	private LocalDate fechaAlta;
	
	public Ciudad(String pais, String nombre, String aeropuerto, String descripcion, String sitioWeb, LocalDate fechaAlta) {
		this.nombre = nombre;
		this.pais = pais;
		this.aeropuerto = aeropuerto;
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
		this.fechaAlta = fechaAlta;
	}

	public String getNombre() {
		return nombre;
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

	public String getPais() {
		return pais;
	}

	public DTCiudad toDataType() {
		DTCiudad dtc = new DTCiudad(this.pais, this.nombre, this.aeropuerto, this.descripcion, this.sitioWeb, this.fechaAlta);
		return dtc;
	}
	
	public String getId() {
		return construirClaveCiudad(pais, nombre);
	}

	private String construirClaveCiudad(String pais, String ciudad) {
    	return pais.trim()+"-"+ciudad.trim();
    }
	
}