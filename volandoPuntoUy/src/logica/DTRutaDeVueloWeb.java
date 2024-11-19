package logica;

import java.util.List;

public class DTRutaDeVueloWeb {
	private String nombre;
    private String descripcion;
    private String descripcionCorta;
    private String hora;
    private Float costoTurista;
    private Float costoEjecutivo;
    private Float costoEquipajeExtra;
    private DTCiudadWeb ciudadOrigen;
    private DTCiudadWeb ciudadDestino;  
    private String fechaAlta;
    private DTCategoria[] categorias; //Cambiar a puntero
    

	private EstadoRuta estado;
    private String imagen;
    private String video;
    private int visitas;
    private DTAerolinea aerolinea;
   
    
    public DTRutaDeVueloWeb(String nombreR, String desR, String desCortaR, String horaR, Float costoTurR, Float costoEjR, Float costoEqExR, DTCiudadWeb ciudadOR, DTCiudadWeb ciudadDR, String fechaR, List<DTCategoria> categoriasR, EstadoRuta estadoR, String imagen, String video, DTAerolinea aero, int visitas) {
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
        categorias = new DTCategoria[categoriasR.size()];
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

	public DTCiudadWeb getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(DTCiudadWeb ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public DTCiudadWeb getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(DTCiudadWeb ciudadDestino) {
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

	public EstadoRuta getEstado() {
		return estado;
	}

	public void setEstado(EstadoRuta estado) {
		this.estado = estado;
	}

	public DTAerolinea getAerolinea() {
		return aerolinea;
	}

	public void setAerolinea(DTAerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	
	public DTCategoria[] getCategorias() {
		return categorias;
	}

	public void setCategorias(DTCategoria[] categorias) {
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