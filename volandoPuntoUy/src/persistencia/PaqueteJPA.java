package persistencia;

import jakarta.persistence.*;
import model.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PaqueteJPA {

    @Id
    private String nombre;
	  
	@Column(length = 2000)
    private String descripcion;
    private String periodoValidez;  
    private float descuento;
    private LocalDate fechaAlta;
    private String imagen;

    @ElementCollection
    private List<Long> rutaPaquete; 

    @ElementCollection
    private List<Long> compra; 

    public PaqueteJPA() {}

	public PaqueteJPA(Paquete paquete) {
		this.nombre = paquete.getNombre();
		this.descripcion = paquete.getDescripcion();
		this.periodoValidez = paquete.getPeriodoValidez().toString();
		this.descuento = paquete.getDescuento();
		this.fechaAlta = paquete.getFechaAlta();
		this.imagen = paquete.getImagen();
		if (paquete.getRutaPaquete().size() > 0)
			this.rutaPaquete = paquete.getRutaPaquete().stream().map(RutaPaquete::getId).toList();
		else
			this.rutaPaquete = new ArrayList<>();
		
		if (paquete.getCompra() != null && paquete.getCompra().size() > 0)
			this.compra = paquete.getCompra().stream().map(Compra::getId).toList();
		else
			this.compra = new ArrayList<>();
	}
    
    public void addRutaPaquete(Long id) {
    	rutaPaquete.add(id);
    }
    
    public void addCompra(Long id) {
    	compra.add(id);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPeriodoValidez() {
        return periodoValidez;
    }

    public float getDescuento() {
        return descuento;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public String getImagen() {
        return imagen;
    }

    public List<Long> getRutaPaquete() {
        return rutaPaquete;
    }

    public List<Long> getCompra() {
        return compra;
    }
}
