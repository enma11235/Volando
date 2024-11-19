package persistencia;

import jakarta.persistence.*;
import logica.Compra;

import java.time.LocalDate;

@Entity
public class CompraJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clienteNickname;  
    private String nombrePaquete; 

    private LocalDate fecha;
    private float costo;
    private LocalDate vencimiento;

    public CompraJPA() {}

    public CompraJPA(Compra compra) {
        this.clienteNickname = compra.getCliente().getNickname();
        this.nombrePaquete = compra.getPaquete().getNombre();
        this.fecha = compra.getFecha();
        this.costo = compra.getCosto();
        this.vencimiento = compra.getVencimiento();
    }

    public String getClienteNickname() {
        return clienteNickname;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public float getCosto() {
        return costo;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
}
