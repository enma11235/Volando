package excepciones;

@SuppressWarnings("serial")
public class VueloNoExisteException extends Exception {

    public VueloNoExisteException(String string) {
        super(string);
    }
}
