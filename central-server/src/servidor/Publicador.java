
package servidor;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.PaqueteYaExisteException;
import excepciones.ReservaNoExisteException;
import excepciones.ReservaYaExisteException;
import excepciones.RutaDeVueloNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;
import excepciones.VueloNoExisteException;
import excepciones.VueloRepetidoException;
import factory.ControllerFactory;
import model.*;
import datatype.*;
import service.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
/**
 *
 * @author Pablo
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {
    private Endpoint endpoint = null;
    private ControllerFactory fab;
    private IUserController IU;
    private ICityCategoryController ICC;
    private IPackageController IP;
    private IFlightRouteController IRV;
    
    private String ip;
    private int port;
    
 // Constructor
    public Publicador() {
        // Ruta al archivo de propiedades
        //String configFilePath = "config.properties"; // Asegúrate de que el archivo esté en la misma ubicación que el JAR
        String configFilePath = new File(System.getProperty("./"), "config.properties").getPath(); //Lo busca en el mismo directorio donde se genero el jar

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);

            // Obtener los valores de las propiedades
            ip = properties.getProperty("publicador.ip", "localhost");  // Valor predeterminado si no está presente
            port = Integer.parseInt(properties.getProperty("publicador.port", "9129"));  // Valor predeterminado
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de configuración: " + e.getMessage());
            ip = "localhost";
            port = 9129;
        }
    }

    // Operaciones las cuales quiero publicar
    @WebMethod(exclude = true)
    public void publicar() {
    	
    	// Obtener el directorio home del usuario y construir la ruta a config.properties
    	String homeDir = System.getProperty("user.home");
		File configFile = new File(homeDir + File.separator + "volandoUy" + File.separator + "config.properties");
    	System.err.println("Buscando archivo: "+homeDir + File.separator + "volandoUy" + File.separator + "config.properties");
		// Valores predeterminados
		String defaultIp = "localhost";
		String defaultPort = "9129";
		String ipStr = defaultIp;
		String portStr = defaultPort;

		// Verificar si el archivo de configuración existe y cargar sus propiedades si está presente
		Properties config = new Properties();
		if (configFile.exists()) {
		    try (FileInputStream input = new FileInputStream(configFile)) {
		        config.load(input);
		        // Si la IP o el puerto están definidos en el archivo, utilizarlos
		        ipStr = config.getProperty("publicador.ip", defaultIp);
		        portStr = config.getProperty("publicador.port", defaultPort);
		    } catch (IOException e) {
		        System.err.println("Error al cargar el archivo de configuración. Usando valores predeterminados.");
		    }
		} else {
		    System.out.println("Archivo de configuración no encontrado. Usando valores predeterminados.");
		}		
        // Usando las propiedades cargadas para formar la URL del endpoint
        String endpointUrl = "http://" + ipStr + ":" + portStr + "/publicador";
        System.out.println("Servicio publicado en " + endpointUrl);

        fab = ControllerFactory.getInstance();
        IU = fab.getIControladorUsuario();
        ICC = fab.getIControladorCiudadCategoria();
        IP = fab.getIControladorPaquete();
        IRV = fab.getIControladorRutaDeVuelo();

        // Publicar el servicio en la URL dinámica
        endpoint = Endpoint.publish(endpointUrl, this);  // Usamos endpointUrl en lugar de valores fijos
    }


    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }
    @WebMethod
    public UserDTO obtenerInfoUsuario(String nick) throws UsuarioNoExisteException{
		UserDTO ret = null;
        ret = IU.obtenerInfoUsuario(nick);
        if(ret instanceof ClientDTO) return((UserDTO) new ClientWebDTO(ret.getNickname(), ret.getNombre(), ret.getEmail(), ret.getPass(), ((ClientDTO) ret).getApellido(), ((ClientDTO) ret).getNacimiento().toString(), ((ClientDTO) ret).getNacionalidad(), ((ClientDTO) ret).getTipoDocumento(), ((ClientDTO) ret).getNumDocumento(), ret.getImagen()));
        return ret;
    }
    
    @WebMethod
    public boolean esCliente(String nick) throws UsuarioNoExisteException {
		boolean ret = false;
        ret = IU.obtenerInfoUsuario(nick) instanceof ClientWebDTO;
        return ret;
    }
    
    @WebMethod
    public ClientWebDTO cargaClienteWeb(String nick) throws UsuarioNoExisteException {
        return null;
    }
    
    @WebMethod
    public boolean esAerolinea(String nick) throws UsuarioNoExisteException {
		boolean ret = false;
        ret = IU.obtenerInfoUsuario(nick) instanceof AirlineDTO;
        return ret;
    }
    
    @WebMethod
    public UserDTO[] listarUsuarios() throws UsuarioNoExisteException{
		UserDTO[] ret = null;
		ret =  IU.listarUsuariosWeb();
        return ret;
    }
    
    @WebMethod
    public AirlineDTO[] listarAerolineasDataWeb() throws UsuarioNoExisteException {
    	return IU.listarAerolineasDataWeb();
    }
    
     @WebMethod
     public String[] listarUsuariosNickWeb() throws UsuarioNoExisteException{
    	 return IU.listarUsuariosNickWeb();
     }
     
     @WebMethod
     public void altaCliente(String nickName, String nombre, String email, String contrasena, String apellido, String nacimiento, String nacionalidad, DocumentType tipoDoc, String numDoc, String imagen) throws UsuarioRepetidoException{
    	 IU.altaCliente(nickName, nombre, email, contrasena, apellido, LocalDate.parse(nacimiento), nacionalidad, tipoDoc, numDoc, imagen);
     }
     
     @WebMethod
     public void editarDatosCliente(String nickname, String nombre, String apellido, String contrasena, String Imagen, String nacimiento, String nacionalidad, DocumentType tipoDoc, String numDoc) throws UsuarioNoExisteException{
    	 IU.editarDatosCliente(nickname, nombre, apellido, contrasena, Imagen, LocalDate.parse(nacimiento), nacionalidad, tipoDoc, numDoc);
     }
     
     @WebMethod
     public String[] listarClientesWeb() throws UsuarioNoExisteException{
    	 return IU.listarClientesWeb();
     }
     
     @WebMethod
     public void clienteCompraPaquete(String nickName, String nombrePaquete) throws UsuarioNoExisteException, PaqueteNoExisteException, PaqueteYaCompradoException{
    	 IU.clienteCompraPaquete(nickName, nombrePaquete);
     }
     
     @WebMethod
     public void compraPaquetes(String nickName, String nombrePaquete, String fechaCompra, String fechaVencimiento, float costo) {
    	 IU.compraPaquetes(nickName, nombrePaquete, LocalDate.parse(fechaCompra), LocalDate.parse(fechaVencimiento), costo);
     }
     
     @WebMethod
     public BookingWebDTO[] listarReservasClienteWeb(String nickName) throws UsuarioNoExisteException, ReservaNoExisteException{
    	 return IU.listarReservasClienteWeb(nickName);
     }
     
     @WebMethod
     public FlightRoutesPackageWebDTO[] listarPaquetesCompradosClienteWeb(String nickName) throws UsuarioNoExisteException, PaqueteNoExisteException{
    	 return IU.listarPaquetesCompradosClienteWeb(nickName);
     }
     
     @WebMethod
     public void altaAereolinea(String nickName, String nombre, String email, String contrasena, String descripcion, String sitioWeb, String imagen) throws UsuarioRepetidoException{
    	 IU.altaAereolinea(nickName, nombre, email, contrasena, descripcion, sitioWeb, imagen);
     }
     
     @WebMethod
     public void editarDatosAereolinea(String nickname, String nombre, String contraseña, String Imagen, String descripcion, String sitioWeb) throws UsuarioNoExisteException{
    	 IU.editarDatosAereolinea(nickname, nombre, contraseña, Imagen, descripcion, sitioWeb);
     }
     
     @WebMethod
     public String[] listarCategorias() {
    	 ArrayList<String> cats = (ArrayList<String>) ICC.listarCategorias();
    	 String[] ret = new String[cats.size()];
    	 int i = 0;
    	 for(String c: cats) {
    		 ret[i] = c;
    		 i++;
    	 }
    	 return ret;
     }
     
     @WebMethod
     public CityWebDTO[] listarCiudades() {
    	 ArrayList<CityDTO> ciu = (ArrayList<CityDTO>) ICC.listarCiudades();
    	 CityWebDTO[] ret = new CityWebDTO[ciu.size()];
    	 int i = 0;
    	 for(CityDTO c: ciu) {
    		 ret[i] = new CityWebDTO(c.getPais(), c.getNombre(), c.getAeropuerto(), c.getDescripcion(), c.getSitioWeb(), c.getFechaAlta().toString());
    		 i++;
    	 }
    	 return ret;
     }
     
     @WebMethod
     public void agregarCategoria(String nombre) throws CategoriaRepetidaException {
    	 ICC.agregarCategoria(nombre);
     }
     
     @WebMethod
     public void crearNuevaCiudad(String pais, String ciudad, String aeropuerto, String descripcion, String sitio_web, String fecha_alta) throws CiudadRepetidaException {
    	 ICC.crearNuevaCiudad(pais, ciudad, aeropuerto, descripcion, sitio_web, LocalDate.parse(fecha_alta));
     }
     
     @WebMethod
	public String[] listarPaquetes(){
    	 ArrayList<String> paqs = (ArrayList<String>) IP.listarPaquetes();
    	 String[] ret = new String[paqs.size()];
    	 int i = 0;
    	 for(String p: paqs) {
    		 ret[i] = p;
    		 i++;
    	 }
    	 return ret;
     }
    
     @WebMethod
	public String[] listarPaquetesSinComprar() {
    	 ArrayList<String> paqs = (ArrayList<String>) IP.listarPaquetesSinComprar();
    	 String[] ret = new String[paqs.size()];
    	 int i = 0;
    	 for(String p: paqs) {
    		 ret[i] = p;
    		 i++;
    	 }
    	 return ret;
     }
     
     @WebMethod
     public String[] listarPaquetesNoVacios() {
    	 ArrayList<String> paqs = (ArrayList<String>) IP.listarPaquetesNoVacios();
    	 String[] ret = new String[paqs.size()];
    	 int i = 0;
    	 for(String p: paqs) {
    		 ret[i] = p;
    		 i++;
    	 }
    	 return ret;
     }
     
     @WebMethod
     public FlightRoutesPackageWebDTO obtenerInfoPaquete(String nombre) {
    	 return IP.obtenerInfoPaqueteWeb(nombre);
     }
     
     @WebMethod
     public void crearPaqueteRutasDeVuelo(String nombre, String descripcion, String periodoValidez, float descuento, String fechaAlta, String imagen)  throws PaqueteYaExisteException{
    	 IP.crearPaqueteRutasDeVuelo(nombre, descripcion, Duration.parse(periodoValidez), descuento, LocalDate.parse(fechaAlta), imagen);
     }
     
     @WebMethod
     public void agregarRutaAPaquete(String nomRuta, String nomPaquete, SeatType asiento, int cantidad) {
    	 IP.agregarRutaAPaquete(nomRuta, nomPaquete, asiento, cantidad);
     }
     
     @WebMethod
     public String[] listarPaquetesRuta(String nomRuta) {
    	 ArrayList<String> paqs = (ArrayList<String>) IP.listarPaquetesRuta(nomRuta);
    	 String[] ret = new String[paqs.size()];
    	 int i = 0;
    	 for(String p: paqs) {
    		 ret[i] = p;
    		 i++;
    	 }
    	 return ret;
     }
     	
     @WebMethod
     public void comprarPaquete(String nombrePaquete, String nicknameCliente) throws PaqueteYaCompradoException {
    	 IP.comprarPaquete(nombrePaquete, nicknameCliente);
     }
     
     //Controlador Ruta de Vuelo
     @WebMethod
     public void agregarRutaDeVuelo(String nicknameAerolinea, String nombre, String descripcion, String descripcionCorta, String hora, float costoTurista, float costoEjecutivo,
			float costoEquipajeExtra, String claveCiudadOrigen, String claveCiudadDestino, String fechaAlta, String[]categorias, String imagen, String video) throws RutaDeVueloRepetidaException, UsuarioNoEsAerolineaExcepcion{
    	 IRV.agregarRutaDeVuelo(nicknameAerolinea, nombre, descripcion, descripcionCorta, LocalTime.parse(hora), costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudadOrigen, claveCiudadDestino, LocalDate.parse(fechaAlta), categorias, imagen, video, 0);
     }
     
     @WebMethod
     public String[] listarRutasDeVueloWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion {	
    	 return IRV.listarRutasDeVueloWeb(nickAerolinea);
     }
     
     @WebMethod
     public FlightRouteWebDTO[] listarTodasRutasDeVueloConfirmadasDTWeb(){
    	 return IRV.listarTodasRutasDeVueloConfirmadasDTWeb();
     }
     
     @WebMethod
     public String[] listarVuelosWeb(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException {
    	 return IRV.listarVuelosWeb(nomAerolinea, nomRutaDeVuelo);
     }
     
     @WebMethod
     public FlightWebDTO[] listarVuelosDTWeb(String nomAerolinea, String nomRutaDeVuelo) throws VueloNoExisteException {
    	 return IRV.listarVuelosDTWeb(nomAerolinea, nomRutaDeVuelo);
     }
     @WebMethod
     public void altaVuelo(String nomRuta, String nomVuelo, String fechaVuelo, String duracion, Integer cantAsTurista, Integer cantAsEjecutivo, String fechaAlta, String imagen) throws VueloRepetidoException {
    	 IRV.altaVuelo(nomRuta, nomVuelo, LocalDate.parse(fechaVuelo), Duration.parse(duracion), cantAsTurista, cantAsEjecutivo, LocalDate.parse(fechaAlta), imagen);
     }
     
     @WebMethod
     public float reservarVuelo(String nickCliente, String nombreVuelo, String tipo, Integer cantPasajes, Integer cantEqExtra, TicketDTO[] pasajerosExtra, String fechaReserva) throws ReservaYaExisteException {
    	 SeatType tipoAsiento = SeatType.valueOf(tipo);
    	 List<TicketDTO> listPasajerosExtra = Arrays.asList(pasajerosExtra);
    	 return IRV.reservarVuelo(nickCliente, nombreVuelo, tipoAsiento, cantPasajes, cantEqExtra, listPasajerosExtra, LocalDate.parse(fechaReserva));
     }
     
     @WebMethod
     public TicketDTO crearPasaje(String nombre, String apellido) {
    	 return IRV.crearPasaje(nombre, apellido);
     }
     
     @WebMethod
     public float reservarVueloConPaquete(String nickCliente, String nombreVuelo, String tipo, Integer cantPasajes, Integer cantEqExtra, TicketDTO[] pasajerosExtra, String fechaReserva, float descuento) throws ReservaYaExisteException {
    	 SeatType tipoAsiento = SeatType.valueOf(tipo);
    	 List<TicketDTO> listPasajerosExtra = Arrays.asList(pasajerosExtra);
    	 return IRV.reservarVueloConPaquete(nickCliente, nombreVuelo, tipoAsiento, cantPasajes, cantEqExtra, listPasajerosExtra, LocalDate.parse(fechaReserva), descuento);
     }
     
     @WebMethod
     public BookingWebDTO obtenerInfoReservaWeb(String nombreVuelo, String nickCliente) {
    	 return IRV.obtenerInfoReservaWeb(nombreVuelo, nickCliente);
     }
     
     @WebMethod
     public String[] listarReservasWeb(String nombreVuelo){
    	 return IRV.listarReservasWeb(nombreVuelo);
     }
     
     @WebMethod
     public FlightWebDTO obtenerInfoVueloWeb(String nombreRuta, String nombreVuelo) {
    	 return IRV.obtenerInfoVueloWeb(nombreRuta, nombreVuelo);
     }
     
     @WebMethod
     public FlightRouteWebDTO obtenerInfoRutaDeVueloWeb(String nombre) throws RutaDeVueloNoExisteException {
    	 AirlineDTO aero = IRV.obtenerAerolineaDeRutaDT(nombre);
    	 return IRV.obtenerInfoRutaDeVueloWeb(nombre, aero);
     }
     
     @WebMethod
     public String[] listarTodasLasRutasDeVueloWeb() {
    	 return IRV.listarTodasLasRutasDeVueloWeb();
     }
     
     @WebMethod
     public String obtenerNicknameAerolineaDeRuta(String nombreRuta) {
    	 return IRV.obtenerNicknameAerolineaDeRuta(nombreRuta);
     }
     @WebMethod
 	 public AirlineDTO obtenerAerolineaDeRutaDT(String nombreRuta) throws RutaDeVueloNoExisteException {
    	 return IRV.obtenerAerolineaDeRutaDT(nombreRuta);
     }
 
     @WebMethod
     public String obtenerRutaDeVuelo(String nomVuelo) throws VueloNoExisteException {
    	 return IRV.obtenerRutaDeVuelo(nomVuelo);
     }
     @WebMethod
     public void rechazarRuta(String nomRuta) {
    	 IRV.rechazarRuta(nomRuta);
     }
     
     @WebMethod
     public void aceptarRuta(String nomRuta) {
    	 IRV.rechazarRuta(nomRuta);
     }
     
     @WebMethod
     public String[] listarRutasIngresadasWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	 return IRV.listarRutasIngresadasWeb(nickAerolinea);
     }
     
     @WebMethod
     public FlightRouteWebDTO[] listarRutasConfirmadasDTWeb(String nickAerolinea) throws UsuarioNoEsAerolineaExcepcion{
    	 return IRV.listarRutasConfirmadasDTWeb(nickAerolinea);
     }
     
     @WebMethod
     public boolean esVueloDeAerolinea(String nickAerolinea, String nomVuelo) {
    	 return IRV.esVueloDeAerolinea(nickAerolinea, nomVuelo);
     }
     
     @WebMethod
     public boolean verificarDisponibilidadNickname(String nickname) {
         try {
             String[] nicks = listarUsuariosNickWeb();
             for (String nick : nicks) {
                 if (nick.equalsIgnoreCase(nickname)) {
                     return false; 
                 }
             }
             return true;
         } catch (Exception e) {
             return false;
         }
     }

     @WebMethod
     public boolean verificarDisponibilidadEmail(String email) {
         try {
             UserDTO[] usuarios = listarUsuarios();
             for (UserDTO usuario : usuarios) {
                 if (usuario.getEmail().equalsIgnoreCase(email)) {
                     return false;
                 }
             }
             return true; 
         } catch (Exception e) {
             return false;
         }
     }
     
     @WebMethod
     public byte[] getFile(@WebParam(name = "fileName") String name) throws IOException {
         byte[] byteArray = null;

         // Obtener la ruta completa al archivo de la imagen usando el directorio base (home del usuario)
         String baseDir = System.getProperty("user.home") + File.separator + "volandoUy" + File.separator + "imgs";
         File file = new File(baseDir, name); // Usar el constructor con baseDir y nombre

         // Verificar si el archivo existe
         if (!file.exists()) {
             throw new FileNotFoundException("La imagen no existe en la ruta: " + file.getAbsolutePath());
         }

         // Leer el archivo y convertirlo a un arreglo de bytes
         try (FileInputStream fis = new FileInputStream(file)) {
             byteArray = new byte[(int) file.length()];
             fis.read(byteArray);
         } catch (IOException e) {
             // Manejar excepciones si ocurre un error al leer el archivo
             throw new IOException("Error al leer el archivo: " + file.getAbsolutePath(), e);
         }

         // Retornar el arreglo de bytes que contiene los datos binarios de la imagen
         return byteArray;
     }

     @WebMethod
     public String saveImage(byte[] imageData, String fileName) throws IOException {
         // Obtener el directorio home del usuario
         String homeDir = System.getProperty("user.home");
         String imagePath = homeDir + File.separator + "volandoUy" + File.separator + "imgs" + File.separator + fileName;
         
         // Crear directorio si no existe
         File imageDir = new File(homeDir + File.separator + "volandoUy" + File.separator + "imgs");
         if (!imageDir.exists()) {
             imageDir.mkdirs();
         }

         // Guardar la imagen
         try (FileOutputStream fos = new FileOutputStream(imagePath)) {
             fos.write(imageData);
         }

         return imagePath; // Retorna la ruta completa de la imagen guardada
     }

     
     @WebMethod
     public void realizarCheckin(String nick, String vuelo) {
    	 IRV.hacerCheckin(nick, vuelo, true);
     }
     
     @WebMethod
     public void sumarVisita(String nomRuta) {
    	 IRV.sumarVisita(nomRuta);
     }
     
     @WebMethod
     public void finalizarRuta(String nomRuta) {
    	 IRV.finalizarRuta(nomRuta);
     }
}