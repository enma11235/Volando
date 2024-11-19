package logica;


public class DTCiudadWeb {
	
	private String nombre, pais, aeropuerto, descripcion, sitioWeb;
	private String fechaAlta;
	public DTCiudadWeb(String pais, String nombre, String aeropuerto, String descripcion, String sitio_web,
			String fecha_alta) {
		this.setNombre(nombre);
		this.setPais(pais);
		this.setAeropuerto(aeropuerto);
		this.setDescripcion(descripcion);
		this.setSitioWeb(sitio_web);
		this.setFechaAlta(fecha_alta);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getAeropuerto() {
		return aeropuerto;
	}
	public void setAeropuerto(String aeropuerto) {
		this.aeropuerto = aeropuerto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSitioWeb() {
		return sitioWeb;
	}
	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
	

}