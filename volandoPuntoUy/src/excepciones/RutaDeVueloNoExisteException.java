package excepciones;

/**
 * Excepción utilizada para indicar la inexistencia de una ruta de vuelo en el sistema.
 * 
 * @author TProg2024
 *
 */
@SuppressWarnings("serial")
public class RutaDeVueloNoExisteException extends Exception {

    public RutaDeVueloNoExisteException(String string) {
        super(string);
    }
}