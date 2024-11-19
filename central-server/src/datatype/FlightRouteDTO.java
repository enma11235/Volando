package datatype;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FlightRouteDTO {
	private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private LocalTime hora;
    private Float costoTurista;
    private Float costoEjecutivo;
    private Float costoEquipajeExtra;
    private CityDTO ciudadOrigen;
    private CityDTO ciudadDestino;  
    private LocalDate fechaAlta;
    private List<CategoryDTO> categorias; //Cambiar a puntero
    private FlightRouteState estado;
    private String imagen;
    private String video;
    private AirlineDTO aerolinea;
    private int visitas;
   
    
    public FlightRouteDTO(String nombreR, String desR, String desCortaR, LocalTime horaR, Float costoTurR, Float costoEjR, Float costoEqExR, CityDTO ciudadOR, CityDTO ciudadDR, LocalDate fechaR, List<CategoryDTO> categoriasR, FlightRouteState estadoR, String imagen, String video, int visitas) {
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
    
    public AirlineDTO getAerolinea() {
    	return this.aerolinea;
    }
    
    public void setAerolinea(AirlineDTO aero) {
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
    
    public CityDTO getCiudadOrigen() {
        return ciudadOrigen;
    }
    
    public CityDTO getCiudadDestino() {
        return ciudadDestino;
    }
    
    public LocalDate getFecha() {
        return fechaAlta;
    }

	public List<CategoryDTO> getCategorias() {
		return categorias;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public FlightRouteState getEstado() {
		return estado;
	}

	public void setEstado(FlightRouteState estado) {
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