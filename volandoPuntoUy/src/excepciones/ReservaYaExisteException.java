package excepciones;

@SuppressWarnings("serial")
public class ReservaYaExisteException extends Exception {
    public ReservaYaExisteException(String string) {
	        super(string);
	    }
}
