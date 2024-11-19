package persistencia;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import logica.Ciudad;

@Entity
public class CiudadJPA {
	@Id
	private String id;
    private String nombre;
    private String pais;
    private String aeropuerto;
    private String descripcion;
    private String sitioWeb;
    private LocalDate fechaAlta;
    
    public CiudadJPA() {}

    public CiudadJPA(String id, Ciudad ciudad) {
    	this.id = id;
        this.nombre = ciudad.getNombre();
        this.pais = ciudad.getPais();
        this.aeropuerto = ciudad.getAeropuerto();
        this.descripcion = ciudad.getDescripcion();
        this.sitioWeb = ciudad.getSitioWeb();
        this.fechaAlta = ciudad.getFechaAlta();
    }
    
    public CiudadJPA(Ciudad ciudad) {
    	this.id = construirClaveCiudad(ciudad.getPais(), ciudad.getNombre());
        this.nombre = ciudad.getNombre();
        this.pais = ciudad.getPais();
        this.aeropuerto = ciudad.getAeropuerto();
        this.descripcion = ciudad.getDescripcion();
        this.sitioWeb = ciudad.getSitioWeb();
        this.fechaAlta = ciudad.getFechaAlta();
    }
    
    public Ciudad toClass() {
        return new Ciudad(pais, nombre, aeropuerto, descripcion, sitioWeb, fechaAlta);
    }
    
    private String construirClaveCiudad(String pais, String ciudad) {
    	return pais.trim()+"-"+ciudad.trim();
    }
}
