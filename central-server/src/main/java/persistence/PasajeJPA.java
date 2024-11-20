package persistence;

import jakarta.persistence.*;
import model.*;

@Entity
public class PasajeJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservaId;
    private String nombre;
    private String apellido;
    private int asiento;

    public PasajeJPA() {}

    public PasajeJPA(Pasaje pasaje) {
    	if (pasaje.getId() != null) {  
	        this.id = pasaje.getId();
	    }
    	if (pasaje.getReserva() != null) {  
    		this.reservaId = pasaje.getReserva().getId();
    	}
        this.nombre = pasaje.getNombre();
        this.apellido = pasaje.getApellido();
        this.asiento = pasaje.getAsiento();
    }

	public Long getId() {
		return id;
	}

	public Long getReservaId() {
		return reservaId;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public int getAsiento() {
		return asiento;
	}
    
    

}
