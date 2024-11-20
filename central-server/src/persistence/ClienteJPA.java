package persistence;

import jakarta.persistence.*;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import datatype.DocumentType;

@Entity
@DiscriminatorValue("Cliente")
public class ClienteJPA extends UserEntity {

    private String apellido;
    private LocalDate nacimiento;
    private String nacionalidad;
    
    @Enumerated(EnumType.STRING)
    private DocumentType tipoDocumento;
    
    private String numDocumento;
    
    @ElementCollection
	private List<Long> reservasIds = new ArrayList<>();
    
    @ElementCollection
	private List<Long> comprasIds = new ArrayList<>();

    public ClienteJPA() {}

    public ClienteJPA(Cliente cliente) {
    	super(cliente.getNickname(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(), cliente.getImageUrl()); 
    	this.apellido = cliente.getLastName();
        this.nacimiento = cliente.getBirthday();
        this.nacionalidad = cliente.getNationality();
        this.tipoDocumento = cliente.getDocumentType();
        this.numDocumento = cliente.getDocumentNumber();
        if (cliente.getAllPurchases().size()>0)
        	this.comprasIds = cliente.getAllPurchases().stream().map(Compra::getId).toList();
        if (cliente.getAllBookings().size()>0)
        	this.reservasIds = cliente.getAllBookings().stream().map(Booking::getId).toList();
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

    public DocumentType getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(DocumentType tipoDocumento) {
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
