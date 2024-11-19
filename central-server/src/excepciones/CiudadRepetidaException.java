package excepciones;


@SuppressWarnings("serial")
public class CiudadRepetidaException extends Exception {

    public CiudadRepetidaException(String string) {
        super(string);
    }
}