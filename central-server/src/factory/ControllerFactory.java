package factory;

import service.*;
import controller.*;

/**
 * Fábrica para la construcción de un controlador de usuarios (uno distinto para cada invocación).
 * Se implementa en base al patrón Singleton.

 *
 */
public class ControllerFactory {

    private static ControllerFactory instancia;

    private ControllerFactory() {
    };

    public static ControllerFactory getInstance() {
        if (instancia == null) {
            instancia = new ControllerFactory();
        }
        return instancia;
    }
    
    public IFlightRouteController getIControladorRutaDeVuelo() {
        return new FlightRouteController();
    }
    
    public ICityCategoryController getIControladorCiudadCategoria() {
    	return new CityCategoryController();
    }
    
    public IUserController getIControladorUsuario() {
    	return new UserController();
    }
    

    public IPackageController getIControladorPaquete() {
    	return new FlightRoutesPackageController();
    }

}