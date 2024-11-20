package excepciones;

@SuppressWarnings("serial")
public class PaqueteNoExisteException extends Exception {
	public PaqueteNoExisteException(String string) {
        super(string);
    }
}
