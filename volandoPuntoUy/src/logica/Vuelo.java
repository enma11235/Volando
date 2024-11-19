package logica;

import java.time.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vuelo {
	
	//links
	private List<Reserva> reservas; 
	private RutaDeVuelo rutaDeVuelo;

	//atributos
	private String nombre;
	private LocalDate fecha;
	private Duration duracion;
	private int cantAsientosTurista; 
	private int cantAsientosTuristaDisponible; 
	private int cantAsientosEjecutivo;
	private int cantAsientosEjecutivoDisponible; 
	private LocalDate fechaAlta; 
	private String imagen;
	private int nroAsiento = 1;
    
   //constructor	   
   public Vuelo(String nom, LocalDate fechaVuelo, Duration duracionV, int cantAT, int cantAE, LocalDate fechaA, String imagen) {
        this.nombre = nom;
        this.fecha = fechaVuelo;
        this.duracion = duracionV;
        this.cantAsientosTurista = cantAT; 
        this.cantAsientosTuristaDisponible = cantAT; 
        this.cantAsientosEjecutivo = cantAE; 
        this.cantAsientosEjecutivoDisponible = cantAE; 
        this.fechaAlta = fechaA; 
        this.imagen = imagen;
    }
   
   public Vuelo(String nom, LocalDate fechaVuelo, Duration duracionV, int cantAT, int cantAE, int cantATD, int cantAED, LocalDate fechaA, String imagen) {
        this.nombre = nom;
        this.fecha = fechaVuelo;
        this.duracion = duracionV;
        this.cantAsientosTurista = cantAT; 
        this.cantAsientosTuristaDisponible = cantATD; 
        this.cantAsientosEjecutivo = cantAE; 
        this.cantAsientosEjecutivoDisponible = cantAED; 
        this.fechaAlta = fechaA; 
        this.imagen = imagen;
    }
   
   
//---------------OPERACIONES-----------------------
   
public String getNombre() {
	return nombre;
}

public LocalDate getFechaVuelo() {
	return fecha;
}

public Duration getDuracion() {
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

public void setFechaAlta(LocalDate fechaAlta) {
	this.fechaAlta = fechaAlta;
}

public DTVuelo getData() {
	List<DTReserva> dtres = null;
	if (reservas!=null) {
		dtres = new ArrayList<DTReserva>();
		for (Reserva r : reservas) {
			dtres.add(r.getData());
		}
	}
	DTVuelo dtv = new DTVuelo(this.nombre, this.fecha, this.duracion, this.cantAsientosTurista, this.cantAsientosEjecutivo, this.cantAsientosTuristaDisponible, this.cantAsientosEjecutivoDisponible, this.fechaAlta, dtres, this.imagen);
	return dtv;
}
   
public void addReserva(Reserva reservaV) {
	if (reservas == null) {
        reservas = new ArrayList<Reserva>(); 
    }
			if (reservaV.getTipoAsiento() == TipoAsiento.TURISTA)
				this.cantAsientosTuristaDisponible -= reservaV.getCantPasajeros();
			else
				this.cantAsientosEjecutivoDisponible -= reservaV.getCantPasajeros();					
				
    reservas.add(reservaV); 
}

public RutaDeVuelo getRutaDeVuelo() {
	return this.rutaDeVuelo;
}

public void setRutaDeVuelo(RutaDeVuelo rutaV) {
	this.rutaDeVuelo = rutaV;
}

public List<Reserva> getReservas() {
	return this.reservas;
}

public String getImagen() {
	return this.imagen;
}

public void setImagen(String imagen) {
	this.imagen = imagen;
}

public int getNroAsiento() {
	return nroAsiento;
}

public void setNroAsiento(int nroAsiento) {
	this.nroAsiento = nroAsiento;
}

}
