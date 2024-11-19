package datatype;
import java.util.List;

public class DTPaqueteWeb {

	 private String nombre;
	  private String descripcion;
	  private String periodoValidez;
	  private float descuento; 
	  private String fechaAlta;
	  private String imagen;
	  private DTRutaPaqueteWeb[] rutas;
	
	  
	public DTPaqueteWeb(String nombre, String descripcion, String periodoValidez, float descuento, String fechaAlta, String imagen,
			List<DTRutaPaquete> rut) {

		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setPeriodoValidez(periodoValidez);
		this.setDescuento(descuento);
		this.setFechaAlta(fechaAlta);
		int i = 0;
		if(rut != null) {
			this.rutas = new DTRutaPaqueteWeb[rut.size()];
			for(DTRutaPaquete dr: rut) {
				this.rutas[i] = new DTRutaPaqueteWeb(dr.getTipoAsiento(), dr.getCantidad(), dr.getCosto(), dr.getRuta());
				i++;
			}
		}
		else this.rutas = null;
		this.setImagen(imagen);
	}

	public float getPrecioPaquete() {
		float precio = 0; 
		if (rutas != null) {
			for (DTRutaPaqueteWeb RP : rutas)
				precio = precio + RP.getCosto(); 
		}	
	
		return precio; 
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public DTRutaPaqueteWeb[] getRutas() {
		return rutas;
	}



	public void setRutas(DTRutaPaqueteWeb[] rutas) {
		this.rutas = rutas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPeriodoValidez() {
		return periodoValidez;
	}

	public void setPeriodoValidez(String periodoValidez) {
		this.periodoValidez = periodoValidez;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}
