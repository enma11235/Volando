package persistence;

import jakarta.persistence.*;
import model.*;

import java.time.LocalDate;
import java.time.Duration;
import java.util.List;

@Entity
public class VueloJPA {

	@Id
    private String nombre;
    private LocalDate fecha;
    private String duracion;
    private int cantAsientosTurista;
    private int cantAsientosTuristaDisponible;
    private int cantAsientosEjecutivo;
    private int cantAsientosEjecutivoDisponible;
    private LocalDate fechaAlta;
    private String imagen;
    private int nroAsiento = 1;

    private String rutaDeVueloId;
    
    @ElementCollection
	private List<Long> reservasIds;

    public VueloJPA() {}

    public VueloJPA(Flight vuelo) {
        this.nombre = vuelo.getName();
        this.fecha = vuelo.getDate();
        this.duracion = vuelo.getDuration().toString();
        this.cantAsientosTurista = vuelo.getTouristSeats();
        this.cantAsientosTuristaDisponible = vuelo.getAvailableRouristSeats();
        this.cantAsientosEjecutivo = vuelo.getExecutiveSeats();
        this.cantAsientosEjecutivoDisponible = vuelo.getAvailableExecutiveSeats();
        this.fechaAlta = vuelo.getRegistrationDate();
        this.imagen = vuelo.getImage();
        this.rutaDeVueloId = vuelo.getRoute().getNombre();  
        this.nroAsiento = vuelo.getSeatNumber();
        if (vuelo.getBookings()!=null && vuelo.getBookings().size()>0)
        	this.reservasIds =vuelo.getBookings().stream().map(Booking::getId).toList();
    }

    
    
	public List<Long> getReservasIds() {
		return reservasIds;
	}

	public void setReservasIds(List<Long> reservasIds) {
		this.reservasIds = reservasIds;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public String getDuracion() {
		return duracion;
	}

	public int getCantAsientosTurista() {
		return cantAsientosTurista;
	}

	public int getCantAsientosTuristaDisponible() {
		return cantAsientosTuristaDisponible;
	}

	public int getCantAsientosEjecutivo() {
		return cantAsientosEjecutivo;
	}

	public int getCantAsientosEjecutivoDisponible() {
		return cantAsientosEjecutivoDisponible;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public String getImagen() {
		return imagen;
	}

	public String getRutaDeVueloId() {
		return rutaDeVueloId;
	}

    

}
