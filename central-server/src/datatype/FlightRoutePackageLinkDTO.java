package datatype;

public class FlightRoutePackageLinkDTO {

	private SeatType tipoAsiento;
	private int cantidad;
	private float costo;
	private FlightRouteDTO ruta;
	
	public FlightRoutePackageLinkDTO(SeatType asiento, int cant, float cost, FlightRouteDTO rut) {
		tipoAsiento = asiento;
		cantidad = cant;
		costo = cost;
		ruta = rut;
	}
	
	public SeatType getTipoAsiento() {
		return tipoAsiento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public float getCosto() {
		return costo;
	}

	public FlightRouteDTO getRuta() {
		return ruta;
	}
		
		
}
