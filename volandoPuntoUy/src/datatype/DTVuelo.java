package datatype;

import java.time.Duration;

import java.time.LocalDate;
import java.util.List;

public class DTVuelo {

    private String nombre;
    private LocalDate fecha;
    private Duration duracion;
    private int maxTurista; 
    private int cantTuristaDisponible; 
    private int maxEjecutivo;
    private int cantEjecutivoDisponible; 
    private LocalDate fechaAlta;
    private List<DTReserva> reservas;
    private String imagen;

    public DTVuelo(String nomb, LocalDate fechaVuelo, Duration dura, int maxT, int maxE, int cantT, int cantE,  LocalDate fechaA, List<DTReserva> res, String imagen) {
    	this.nombre = nomb;
        this.fecha = fechaVuelo;
        this.duracion = dura;
        this.maxTurista = maxT;
        this.maxEjecutivo = maxE;
    	this.fechaAlta = fechaA;
    	this.reservas = res;
    	this.cantEjecutivoDisponible = cantE; 
    	this.cantTuristaDisponible = cantT;
    	this.imagen = imagen;
    }


    public String getNombre() {
    	return nombre;
    }

    public LocalDate getFechaVuelo() {
		return fecha;
	}


	public Duration getDuracion() {
		return duracion;
	}


	public int getMaxTurista() {
		return maxTurista;
	}


	public int getMaxEjecutivo() {
		return maxEjecutivo;
	}


	public LocalDate getFechaAlta() {
		return fechaAlta;
	}


    /* Sirve para mostrar textualmente la información del vuelo, por ejemplo en un ComboBox. Sin terminar....
     */
    public String toString() {
        return  null; // getNombre() + " " + getFecha() ;
    }
    
    public List<DTReserva> getReservas() {
		return reservas;
	}
    public int getEjecutivoDisponible() {
    	return cantEjecutivoDisponible; 
    }
    public int getTuristaDisponible() {
    	return cantTuristaDisponible; 
    }


    public String getImagen() {
		if (imagen==null || imagen.equals("") || imagen.equals("(Sin Imagen)"))
			return "no_image.jpg";
		else
			return imagen;
	}
}
