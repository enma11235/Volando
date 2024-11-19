package model;
import datatype.*;
import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import excepciones.RutaDeVueloNoExisteException;
import factory.ControllerFactory;
import service.IFlightRouteController;


public class FlightRoute {
	
	//links
	private List<Category> categorias;
	private List<Flight> vuelos;

	//atributos
    private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private LocalTime hora;
    private Float costoTurista;
    private Float costoEjecutivo;
    private Float costoEquipajeExtra;
    private City ciudadOrigen;
    private City ciudadDestino;
    private LocalDate fechaAlta;
    private FlightRouteState estado;
    private String imagen;
    private String video;
    private int visitas;
    
    
    
    //----------------OPERACIONES--------------------------

    public FlightRoute(String nombre, String descripcion, String desC, LocalTime hora, Float costoTurista, Float costoEjecutivo,
			Float costoEquipajeExtra, City ciudadOrigen, City ciudadDestino, LocalDate fechaAlta, List<Category> arrlistCategorias, String imagen, String video, int visitas) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.setDescripcionCorta(desC);
		this.hora = hora;
		this.costoTurista = costoTurista;
		this.costoEjecutivo = costoEjecutivo;
		this.costoEquipajeExtra = costoEquipajeExtra;
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		this.fechaAlta = fechaAlta;
		this.categorias=arrlistCategorias;
        this.vuelos = new ArrayList<Flight>();
        this.estado = FlightRouteState.Ingresada;
        this.imagen = imagen;
        this.video = video;
        this.visitas = visitas;
	}

	public String getNombre() {
        return nombre;
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
    
    public City getCiudadOrigen() {
        return ciudadOrigen;
    }
    
    public City getCiudadDestino() {
    	return ciudadDestino;
	}
    
    public LocalDate getFecha() {
        return fechaAlta;
    }
    
    
    
    public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setCategorias(List<Category> categorias) {
		this.categorias = categorias;
	}

	public void setVuelos(List<Flight> vuelos) {
		this.vuelos = vuelos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public void setCostoTurista(Float costoTurista) {
		this.costoTurista = costoTurista;
	}

	public void setCostoEjecutivo(Float costoEjecutivo) {
		this.costoEjecutivo = costoEjecutivo;
	}

	public void setCostoEquipajeExtra(Float costoEquipajeExtra) {
		this.costoEquipajeExtra = costoEquipajeExtra;
	}

	public void setCiudadOrigen(City ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public void setCiudadDestino(City ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public FlightRouteDTO getData() {
    	CityDTO ciudadO = ciudadOrigen.toDataType();
    	CityDTO ciudadD = ciudadDestino.toDataType();
    	List<CategoryDTO> cate = null;
    	if (!categorias.isEmpty()) {
    		cate= new ArrayList<CategoryDTO>();
			for (Category c : categorias) {
				if (c != null) {
					cate.add(c.getDT());
				} else {
				    throw new NullPointerException("La categoría es nula. Verifica la asignación de c.");
				}
			}
    	}
    	ControllerFactory fabrica = ControllerFactory.getInstance();
    	IFlightRouteController ICR = fabrica.getIControladorRutaDeVuelo();
    	FlightRouteDTO res = new FlightRouteDTO(nombre, descripcion, descripcionCorta, hora, costoTurista, costoEjecutivo, costoEquipajeExtra, ciudadO, ciudadD, fechaAlta, cate, estado, imagen, video, this.visitas);
    	try {
			res.setAerolinea(ICR.obtenerAerolineaDeRutaDT(nombre));
		} catch (RutaDeVueloNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return  res;
    }
   
    
    public void addCategoria(Category cat) {
		this.categorias.add(cat);
	}

	public List<Category> getCategorias() {
		return this.categorias;
	}
    
    
    public void addVuelo(Flight vuelo) {
    	vuelos.add(vuelo); 
	}
    
    public List<Flight> getVuelos() {
    	return this.vuelos; 
    }
    
    public Flight getVuelo(String nomVuelo) {
    	Flight vuelo = null;
    	for (Flight v : this.vuelos) {
    		if (v.getNombre().equals(nomVuelo)) {
    			vuelo = v;
    		}
    	}
    	return vuelo;
    }

	public FlightRouteState getEstado() {
		return estado;
	}

	public void setEstado(FlightRouteState estado) {
		this.estado = estado;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getImagen() {
		return imagen;
	}
	
	public String getVideo() {
		return video;
	}

	public int getVisitas() {
		return visitas;
	}

	public void setVisitas(int visitas) {
		this.visitas = visitas;
	}

//	@Override
//	public String toString() {
//		return "RutaDeVuelo [categorias=" + categorias + ", vuelos=" + vuelos + ", nombre=" + nombre + ", descripcion="
//				+ descripcion + ", descripcionCorta=" + descripcionCorta + ", hora=" + hora + ", costoTurista="
//				+ costoTurista + ", costoEjecutivo=" + costoEjecutivo + ", costoEquipajeExtra=" + costoEquipajeExtra
//				+ ", ciudadOrigen=" + ciudadOrigen + ", ciudadDestino=" + ciudadDestino + ", fechaAlta=" + fechaAlta
//				+ ", estado=" + estado + ", imagen=" + imagen + ", video=" + video + ", visitas=" + visitas + "]";
//	}
//    
    
}