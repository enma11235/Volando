package persistencia;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import datatype.EstadoRuta;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import model.*;

@Entity
public class RutaDeVueloJPA {
    @Id
    private String nombre;
      
	@Column(length = 2000)
    private String descripcion;
    private String descripcionCorta;
    private LocalTime hora;
    private Float costoTurista;
    private Float costoEjecutivo;
    private Float costoEquipajeExtra;

    private String ciudadOrigenId;
    private String ciudadDestinoId;

    private LocalDate fechaAlta;
    private String imagen;
    private String video;
    private int visitas;

    @ElementCollection
    private List<String> categorias; 
    @ElementCollection
    private List<String> vuelos;

    @Enumerated(EnumType.STRING)
    private EstadoRuta estado;

    public RutaDeVueloJPA() {}

    public RutaDeVueloJPA(RutaDeVuelo ruta) {
        this.nombre = ruta.getNombre();
        this.descripcion = ruta.getDescripcion();
        this.descripcionCorta = ruta.getDescripcionCorta();
        this.hora = ruta.getHora();
        this.costoTurista = ruta.getCostoTurista();
        this.costoEjecutivo = ruta.getCostoEjecutivo();
        this.costoEquipajeExtra = ruta.getCostoEquipajeExtra();
        this.ciudadOrigenId = ruta.getCiudadOrigen().getId();
        this.ciudadDestinoId = ruta.getCiudadDestino().getId();
        this.fechaAlta = ruta.getFechaAlta();
        this.imagen = ruta.getImagen();
        this.video = ruta.getVideo();
        this.visitas = ruta.getVisitas();
        this.estado = ruta.getEstado();
        this.categorias = ruta.getCategorias().stream().map(Categoria::getNombre).toList();
        this.vuelos = ruta.getVuelos().stream().map(Vuelo::getNombre).toList();
    }

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
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

	public String getCiudadOrigenId() {
		return ciudadOrigenId;
	}

	public String getCiudadDestinoId() {
		return ciudadDestinoId;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
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

	public List<String> getCategorias() {
		return categorias;
	}

	public List<String> getVuelos() {
		return vuelos;
	}

	public EstadoRuta getEstado() {
		return estado;
	}

    
}
