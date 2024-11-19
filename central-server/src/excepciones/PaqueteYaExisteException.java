package excepciones;

@SuppressWarnings("serial")
public class PaqueteYaExisteException extends Exception {
	public PaqueteYaExisteException(String string) {
        super(string);
    }
}