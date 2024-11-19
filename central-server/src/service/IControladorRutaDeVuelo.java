package service;

import java.time.Duration;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import excepciones.ReservaYaExisteException;
import excepciones.RutaDeVueloNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.VueloNoExisteException;
import excepciones.VueloRepetidoException;
import datatype.*;
public interface IControladorRutaDeVuelo {

	/**
     * Registra la ruta de vuelo en el sistema.
     * @param n Nombre de la Ruta de Vuelo.
     * @param d Descripcion de la Ruta de Vuelo.
     * @param h Hora de la Ruta de Vuelo.
     * @param cT Costo Turista de la Ruta de Vuelo.
     * @param cE Costo Ejecutivo de la Ruta de Vuelo.
     * @param cEqE Costo de Equipaje Extra de la Ruta de Vuelo.
     * @param f Fecha de Alta de la Ruta de Vuelo.
     * @throws RutaDeVueloRepetidoException Si el nombre de la Ruta de Vuelo se encuentra registrada en el sistema.
     */
	
	public abstract void agregarRutaDeVuelo(String nicknameAerolinea, String nombre, String descripcion, String descripcionCorta, LocalTime hora, Float costoTurista, Float costoEjecutivo,
			Float costoEquipajeExtra, String claveCiudadOrigen, String claveCiudadDestino, LocalDate fechaAlta, String[] categorias, String imagen, String video, int visitas) throws RutaDeVueloRepetidaException, UsuarioNoEsAerolineaExcepcion;
	
    public abstract List<String> listarRutasDeVuelo(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;
    public abstract String[] listarRutasDeVueloWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;

    public abstract List<DTRutaDeVuelo> listarTodasRutasDeVueloConfirmadasDT();
    public abstract DTRutaDeVueloWeb[] listarTodasRutasDeVueloConfirmadasDTWeb();
       
	public abstract List<String> listarVuelos(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException;
	public abstract String[] listarVuelosWeb(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException;

	public abstract List<DTVuelo> listarVuelosDT(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException;
	public abstract DTVueloWeb[] listarVuelosDTWeb(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException;
	
	public abstract void altaVuelo(String nomRuta, String nomVuelo, LocalDate fechaVuelo, Duration duracion, Integer cantAsTurista, Integer cantAsEjecutivo, LocalDate fechaAlta, String imagen)throws VueloRepetidoException;

    public abstract DTRutaDeVuelo obtenerInfoRutaDeVuelo(String nombre) throws RutaDeVueloNoExisteException;
    public abstract DTRutaDeVueloWeb obtenerInfoRutaDeVueloWeb(String nombre, DTAerolinea aero) throws RutaDeVueloNoExisteException;
    
    public abstract DTVuelo obtenerInfoVuelo(String nombreRuta, String nombreVuelo);
    public abstract DTVueloWeb obtenerInfoVueloWeb(String nombreRuta, String nombreVuelo);

    public abstract List<String> listarReservas(String nombreVuelo);
    public abstract String[] listarReservasWeb(String nombreVuelo);

    public abstract DTReserva obtenerInfoReserva(String nombreVuelo, String nickCliente);
    public abstract DTReservaWeb obtenerInfoReservaWeb(String nombreVuelo, String nickCliente);

    public abstract float reservarVuelo(String nickCliente, String nombreVuelo, TipoAsiento tipo, Integer cantPasajes, Integer cantEqExtra, List<DTPasaje> pasajerosExtra, LocalDate fechaReserva) throws ReservaYaExisteException;

    public abstract DTPasaje crearPasaje(String nombre, String apellido);
    
    public abstract float reservarVueloConPaquete(String nickCliente, String nombreVuelo, TipoAsiento tipo, Integer cantPasajes, Integer cantEqExtra, List<DTPasaje> pasajerosExtra, LocalDate fechaReserva, float descuento) throws ReservaYaExisteException;

    public abstract String obtenerNicknameAerolineaDeRuta(String nombreRuta);
    public abstract DTAerolinea obtenerAerolineaDeRutaDT(String nombre) throws RutaDeVueloNoExisteException;
    
    public abstract String obtenerRutaDeVuelo(String nomVuelo) throws VueloNoExisteException;
    
    public abstract void rechazarRuta(String nomRuta);
    
    public abstract void aceptarRuta(String nomRuta);
    
    public abstract List<String> listarRutasIngresadas(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;
    public abstract String[] listarRutasIngresadasWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;

    public abstract List<String> listarRutasConfirmadas(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;
    
    public abstract List<DTRutaDeVuelo> listarRutasConfirmadasDT(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;
    public abstract DTRutaDeVueloWeb[] listarRutasConfirmadasDTWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion;
    
    public abstract List<String> listarTodasLasRutasDeVuelo();
    public abstract String[] listarTodasLasRutasDeVueloWeb();

    public abstract boolean esVueloDeAerolinea(String nickAerolinea, String nomVuelo);
    
    public abstract void hacerCheckin(String nickCliente, String nomVuelo, boolean asignarAsientos);
    
    public abstract void sumarVisita(String nomRuta);
    
    public abstract void finalizarRuta(String nomRuta);
    
    public List<DTRutaDeVuelo> obtener5MasVisitadas();
}