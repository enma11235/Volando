package datatype;

public enum RouteState {
	Ingresada,
	Rechazada,
	Confirmada,
	Finalizada;
	public static RouteState fromString(String tipo) {
	    for (RouteState doc : RouteState.values()) {
	        if (doc.name().equalsIgnoreCase(tipo)) {
	            return doc;
	        }
	    }
	    throw new IllegalArgumentException("Tipo de estado no reconocido: " + tipo);
	}
}

