package datatype;

public class DTRutaPaquete {

	private SeatType tipoAsiento;
	private int cantidad;
	private float costo;
	private DTRutaDeVuelo ruta;
	
	public DTRutaPaquete(SeatType asiento, int cant, float cost, DTRutaDeVuelo rut) {
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

	public DTRutaDeVuelo getRuta() {
		return ruta;
	}
		
		
}
