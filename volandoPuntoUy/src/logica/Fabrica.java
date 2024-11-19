package logica;
/**
 * Fábrica para la construcción de un controlador de usuarios (uno distinto para cada invocación).
 * Se implementa en base al patrón Singleton.

 *
 */
public class Fabrica {

    private static Fabrica instancia;

    private Fabrica() {
    };

    public static Fabrica getInstance() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }
    
    public IControladorRutaDeVuelo getIControladorRutaDeVuelo() {
        return new ControladorRutaDeVuelo();
    }
    
    public IControladorCiudadCategoria getIControladorCiudadCategoria() {
    	return new ControladorCiudadCategoria();
    }
    
    public IControladorUsuario getIControladorUsuario() {
    	return new ControladorUsuario();
    }
    

    public IControladorPaquete getIControladorPaquete() {
    	return new ControladorPaquete();
    }

}