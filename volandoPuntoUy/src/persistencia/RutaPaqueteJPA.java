package persistencia;

import jakarta.persistence.*;
import logica.RutaPaquete;
import logica.TipoAsiento;

@Entity
public class RutaPaqueteJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreRuta;  
    private String nombrePaquete;  

    @Enumerated(EnumType.STRING)
    private TipoAsiento tipoAsiento;

    private int cantidad;

    public RutaPaqueteJPA() {}

    public RutaPaqueteJPA(RutaPaquete rutaPaquete) {
        this.nombreRuta = rutaPaquete.getRuta().getNombre();
        this.nombrePaquete = rutaPaquete.getPaquete().getNombre();
        this.tipoAsiento = rutaPaquete.getTipoAsiento();
        this.cantidad = rutaPaquete.getCantidad();

    }
    
    

    @Override
	public String toString() {
		return "RutaPaqueteJPA [id=" + id + ", nombreRuta=" + nombreRuta + ", nombrePaquete=" + nombrePaquete
				+ ", tipoAsiento=" + tipoAsiento + ", cantidad=" + cantidad + "]";
	}

	public String getNombreRuta() {
        return nombreRuta;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public int getCantidad() {
        return cantidad;
    }

	public Long getId() {
		return id;
	}
    
    


}
