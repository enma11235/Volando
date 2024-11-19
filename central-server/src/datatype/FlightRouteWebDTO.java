package datatype;

import java.util.List;

public class FlightRouteWebDTO {
	private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private String hora;
    private Float costoTurista;
    private Float costoEjecutivo;
    private Float costoEquipajeExtra;
    private CityWebDTO ciudadOrigen;
    private CityWebDTO ciudadDestino;  
    private String fechaAlta;
    private CategoryDTO[] categorias; //Cambiar a puntero
    

	private FlightRouteState estado;
    private String imagen;
    private String video;
    private int visitas;
    private AirlineDTO aerolinea;
   
    
    public FlightRouteWebDTO(String nombreR, String desR, String desCortaR, String horaR, Float costoTurR, Float costoEjR, Float costoEqExR, CityWebDTO ciudadOR, CityWebDTO ciudadDR, String fechaR, List<CategoryDTO> categoriasR, FlightRouteState estadoR, String imagen, String video, AirlineDTO aero, int visitas) {
        setNombre(nombreR);
        setDescripcion(desR);
        setDescripcionCorta(desCortaR);
        setHora(horaR);
        setCostoTurista(costoTurR);
        setCostoEjecutivo(costoEjR);
        setCostoEquipajeExtra(costoEqExR);
        setCiudadOrigen(ciudadOR);
        setCiudadDestino(ciudadDR);
        setFechaAlta(fechaR);
        categorias = new CategoryDTO[categoriasR.size()];
        if(categoriasR != null) categorias = categoriasR.toArray(categorias);
        setEstado(estadoR);
        this.imagen = imagen;
        this.aerolinea = aero;
        this.video = video;
        this.visitas = visitas;
    }

	public String getImagen() {
		if (imagen==null || imagen.equals("") || imagen.equals("(Sin Imagen)"))
			return "no_image.jpg";
		else
			return imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public Float getCostoTurista() {
		return costoTurista;
	}

	public void setCostoTurista(Float costoTurista) {
		this.costoTurista = costoTurista;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Float getCostoEjecutivo() {
		return costoEjecutivo;
	}

	public void setCostoEjecutivo(Float costoEjecutivo) {
		this.costoEjecutivo = costoEjecutivo;
	}

	public Float getCostoEquipajeExtra() {
		return costoEquipajeExtra;
	}

	public void setCostoEquipajeExtra(Float costoEquipajeExtra) {
		this.costoEquipajeExtra = costoEquipajeExtra;
	}

	public CityWebDTO getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(CityWebDTO ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public CityWebDTO getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(CityWebDTO ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}
	
	public void setImagen(String i) {
		this.imagen = i;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FlightRouteState getEstado() {
		return estado;
	}

	public void setEstado(FlightRouteState estado) {
		this.estado = estado;
	}

	public AirlineDTO getAerolinea() {
		return aerolinea;
	}

	public void setAerolinea(AirlineDTO aerolinea) {
		this.aerolinea = aerolinea;
	}
	
	public CategoryDTO[] getCategorias() {
		return categorias;
	}

	public void setCategorias(CategoryDTO[] categorias) {
		this.categorias = categorias;
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