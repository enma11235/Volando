package logica;

public enum TipoDocumento {
	Pasaporte,
	CedulaIdentidad,
	Extranjero;
	public static TipoDocumento fromString(String tipo) {
	    for (TipoDocumento doc : TipoDocumento.values()) {
	        if (doc.name().equalsIgnoreCase(tipo)) {
	            return doc;
	        }
	    }
	    throw new IllegalArgumentException("Tipo de documento no reconocido: " + tipo);
	}
}
