package datatype;

public enum DocumentType {
	Pasaporte,
	CedulaIdentidad,
	Extranjero;
	public static DocumentType fromString(String tipo) {
	    for (DocumentType doc : DocumentType.values()) {
	        if (doc.name().equalsIgnoreCase(tipo)) {
	            return doc;
	        }
	    }
	    throw new IllegalArgumentException("Tipo de documento no reconocido: " + tipo);
	}
}
