package persistencia;

import jakarta.persistence.*;
import logica.Cliente;
import logica.Compra;
import logica.Reserva;
import logica.TipoDocumento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Cliente")
public class ClienteJPA extends UsuarioJPA {

    private String apellido;
    private LocalDate nacimiento;
    private String nacionalidad;
    
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
    
    private String numDocumento;
    
    @ElementCollection
	private List<Long> reservasIds = new ArrayList<>();
    
    @ElementCollection
	private List<Long> comprasIds = new ArrayList<>();

    public ClienteJPA() {}

    public ClienteJPA(Cliente cliente) {
    	super(cliente.getNickname(), cliente.getNombre(), cliente.getEmail(), cliente.getContrasena(), cliente.getImagen()); 
    	this.apellido = cliente.getApellido();
        this.nacimiento = cliente.getNacimiento();
        this.nacionalidad = cliente.getNacionalidad();
        this.tipoDocumento = cliente.getTipoDocumento();
        this.numDocumento = cliente.getNumDocumento();
        if (cliente.getCompras().size()>0)
        	this.comprasIds = cliente.getCompras().stream().map(Compra::getId).toList();
        if (cliente.getReservas().size()>0)
        	this.reservasIds = cliente.getReservas().stream().map(Reserva::getId).toList();
    }

    @Override
    public Boolean esAerolinea() {
        return false;
    }

    @Override
    public Boolean esCliente() {
        return true;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

	public List<Long> getReservasIds() {
		return reservasIds;
	}

	public void setReservasIds(List<Long> reservasIds) {
		this.reservasIds = reservasIds;
	}

	public List<Long> getComprasIds() {
		return comprasIds;
	}

	public void setComprasIds(List<Long> comprasIds) {
		this.comprasIds = comprasIds;
	}
    
    
    
}
