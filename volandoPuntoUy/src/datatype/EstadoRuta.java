package datatype;

public enum EstadoRuta {
	Ingresada,
	Rechazada,
	Confirmada,
	Finalizada;
	public static EstadoRuta fromString(String tipo) {
	    for (EstadoRuta doc : EstadoRuta.values()) {
	        if (doc.name().equalsIgnoreCase(tipo)) {
	            return doc;
	        }
	    }
	    throw new IllegalArgumentException("Tipo de estado no reconocido: " + tipo);
	}
}

