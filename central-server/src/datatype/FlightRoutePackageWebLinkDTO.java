package datatype;

public class FlightRoutePackageWebLinkDTO {

	private SeatType tipoAsiento;
	private int cantidad;
	private float costo;
	private FlightRouteWebDTO ruta;
	
	public FlightRoutePackageWebLinkDTO(SeatType asiento, int cant, float cost, FlightRouteDTO rut) {
		setTipoAsiento(asiento);
		setCantidad(cant);
		setCosto(cost);
		this.ruta = new FlightRouteWebDTO(rut.getNombre(), rut.getDescripcion(), rut.getDescripcionCorta(), rut.getHora().toString(), rut.getCostoTurista(), rut.getCostoEjecutivo(), rut.getCostoEquipajeExtra(), new CityWebDTO(rut.getCiudadOrigen().getPais(), rut.getCiudadOrigen().getNombre(), rut.getCiudadOrigen().getAeropuerto(), rut.getCiudadOrigen().getDescripcion(), rut.getCiudadOrigen().getSitioWeb(), rut.getCiudadOrigen().getFechaAlta().toString()), new CityWebDTO(rut.getCiudadDestino().getPais(), rut.getCiudadDestino().getNombre(), rut.getCiudadDestino().getAeropuerto(), rut.getCiudadDestino().getDescripcion(), rut.getCiudadDestino().getSitioWeb(), rut.getCiudadDestino().getFechaAlta().toString()), rut.getFecha().toString(), rut.getCategorias(), rut.getEstado(), rut.getImagen(), rut.getVideo(), rut.getAerolinea(), rut.getVisitas());
	}

	public SeatType getTipoAsiento() {
		return tipoAsiento;
	}

	public void setTipoAsiento(SeatType tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public FlightRouteWebDTO getRuta() {
		return ruta;
	}

	public void setRuta(FlightRouteWebDTO ruta) {
		this.ruta = ruta;
	}
	
	
		
		
}
