package datatype;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DTRutaDeVuelo {
	private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private LocalTime hora;
    private Float costoTurista;
    private Float costoEjecutivo;
    private Float costoEquipajeExtra;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;  
    private LocalDate fechaAlta;
    private List<DTCategoria> categorias; //Cambiar a puntero
    private EstadoRuta estado;
    private String imagen;
    private String video;
    private DTAerolinea aerolinea;
    private int visitas;
   
    
    public DTRutaDeVuelo(String nombreR, String desR, String desCortaR, LocalTime horaR, Float costoTurR, Float costoEjR, Float costoEqExR, DTCiudad ciudadOR, DTCiudad ciudadDR, LocalDate fechaR, List<DTCategoria> categoriasR, EstadoRuta estadoR, String imagen, String video, int visitas) {
        nombre = nombreR;
        descripcion =desR;
        descripcionCorta = desCortaR;
        hora = horaR;
        costoTurista =costoTurR;
        costoEjecutivo = costoEjR;
        costoEquipajeExtra = costoEqExR;
        ciudadOrigen = ciudadOR;
        ciudadDestino = ciudadDR;
        fechaAlta = fechaR;
        categorias = categoriasR;
        estado = estadoR;
        this.imagen = imagen;
        this.video = video;
        this.aerolinea = null;
        this.visitas = visitas;
    }

    //get
    public String getNombre() {
        return nombre;
    }
    
    public DTAerolinea getAerolinea() {
    	return this.aerolinea;
    }
    
    public void setAerolinea(DTAerolinea aero) {
    	this.aerolinea = aero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalTime getHora() {
        return hora;
    }
    
    public Float getCostoTurista() {
        return costoTurista;
    }
    
    public Float getCostoEjecutivo() {
        return costoEjecutivo;
    }
    
    public Float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }
    
    public DTCiudad getCiudadOrigen() {
        return ciudadOrigen;
    }
    
    public DTCiudad getCiudadDestino() {
        return ciudadDestino;
    }
    
    public LocalDate getFecha() {
        return fechaAlta;
    }

	public List<DTCategoria> getCategorias() {
		return categorias;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public EstadoRuta getEstado() {
		return estado;
	}

	public void setEstado(EstadoRuta estado) {
		this.estado = estado;
	}

	public String getImagen() {
		if (imagen==null || imagen.equals("") || imagen.equals("(Sin Imagen)"))
			return "no_image.jpg";
		else
			return imagen;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getVisitas() {
		return visitas;
	}

	public void setVisitas(int visitas) {
		this.visitas = visitas;
	}
    
}