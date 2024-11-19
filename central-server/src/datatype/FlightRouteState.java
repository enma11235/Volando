package datatype;

public enum FlightRouteState {
	Ingresada,
	Rechazada,
	Confirmada,
	Finalizada;
	public static FlightRouteState fromString(String tipo) {
	    for (FlightRouteState doc : FlightRouteState.values()) {
	        if (doc.name().equalsIgnoreCase(tipo)) {
	            return doc;
	        }
	    }
	    throw new IllegalArgumentException("Tipo de estado no reconocido: " + tipo);
	}
}

