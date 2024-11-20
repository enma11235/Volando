package excepciones;

/**
 * Excepción utilizada para indicar la existencia de una ruta de vuelo repetida en el sistema.
 * 
 * @author TProg2024
 *
 */
@SuppressWarnings("serial")
public class RutaDeVueloRepetidaException extends Exception {

    public RutaDeVueloRepetidaException(String string) {
        super(string);
    }
}