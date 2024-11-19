package datatype;

import java.util.List;

public class FlightWebDTO {

    private String nombre;
    private String fecha;
    private String duracion;
    private int maxTurista; 
    private int cantTuristaDisponible; 
    private int maxEjecutivo;
    private int cantEjecutivoDisponible; 
    private String fechaAlta;
    private BookingWebDTO[] reservas;
    private String imagen;

    public FlightWebDTO(String nomb, String fechaVuelo, String dura, int maxT, int maxE, int cantT, int cantE,  String fechaA, List<BookingWebDTO> res, String imagen) {
    	this.setNombre(nomb);
        this.setFecha(fechaVuelo);
        this.setDuracion(dura);
        this.setMaxTurista(maxT);
        this.setMaxEjecutivo(maxE);
    	this.setFechaAlta(fechaA);
    	this.reservas = new BookingWebDTO[res.size()];
    	int i = 0;
    	for(BookingWebDTO dr: res) {
    		this.reservas[i] = dr;//new DTReservaWeb(dr.getTipoAsiento(), dr.getCantEquipaje(), dr.getCantPasajeros(), dr.getCosto(), dr.getFecha().toString(), dr.getNomVuelo() , dr.getPasajes());
    		i++;
    	}
    	this.setCantEjecutivoDisponible(cantE); 
    	this.setCantTuristaDisponible(cantT);
    	this.imagen = imagen;
    }

    public BookingWebDTO[] getReservas() {
		return reservas;
	}

	public void setReservas(BookingWebDTO[] reservas) {
		this.reservas = reservas;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen() {
		if (imagen==null || imagen.equals("") || imagen.equals("(Sin Imagen)"))
			return "no_image.jpg";
		else
			return imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public int getMaxTurista() {
		return maxTurista;
	}

	public void setMaxTurista(int maxTurista) {
		this.maxTurista = maxTurista;
	}

	public int getCantTuristaDisponible() {
		return cantTuristaDisponible;
	}

	public void setCantTuristaDisponible(int cantTuristaDisponible) {
		this.cantTuristaDisponible = cantTuristaDisponible;
	}

	public int getMaxEjecutivo() {
		return maxEjecutivo;
	}

	public void setMaxEjecutivo(int maxEjecutivo) {
		this.maxEjecutivo = maxEjecutivo;
	}

	public int getCantEjecutivoDisponible() {
		return cantEjecutivoDisponible;
	}

	public void setCantEjecutivoDisponible(int cantEjecutivoDisponible) {
		this.cantEjecutivoDisponible = cantEjecutivoDisponible;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
}
