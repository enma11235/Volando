package excepciones;

/**
 * Excepción utilizada para indicar que la aerolinea no existe o el nickName es de un cliente
 * 
 * @author TProg2024
 *
 */
@SuppressWarnings("serial")
public class UsuarioNoEsAerolineaExcepcion extends Exception {

    public UsuarioNoEsAerolineaExcepcion(String string) {
        super(string);
    }
}