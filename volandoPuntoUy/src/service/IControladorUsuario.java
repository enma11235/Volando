package service;
import datatype.*;
import java.time.LocalDate;

import java.util.List;

import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.ReservaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;
import model.Reserva;

public interface IControladorUsuario {
	  
	   //Operaciones de Usuarios
	   
	    /**
	     * Retorna la información de todos los usuarios registrados en el sistema.
	     * @throws UsuarioNoExisteException Si no existen usuarios registrados en el sistema.
	     */
	    public abstract DTUsuario[] listarUsuariosWeb() throws UsuarioNoExisteException;
	    
	    public abstract List<DTUsuario> listarUsuarios() throws UsuarioNoExisteException;
	    
	    public abstract DTAerolinea[] listarAerolineasDataWeb() throws UsuarioNoExisteException;

	    /**
	     * Retorna el nickname de todos los usuarios registrados en el sistema.
	     * @throws UsuarioNoExisteException Si no existen usuarios registrados en el sistema.
	     */
		public abstract List<String> listarUsuariosNick() throws UsuarioNoExisteException;
		
		public abstract String[] listarUsuariosNickWeb() throws UsuarioNoExisteException;

	    /**
	     * Retorna la información de un usuario con el nickname indicado.
	     * @throws UsuarioNoExisteException Si el nickname del usuario no está registrado en el sistema.
	     */
	    public abstract DTUsuario obtenerInfoUsuario(String nickName)throws UsuarioNoExisteException;

	    
	    
	    //Operaciones de Clientes
	    
	    /**
	     * Registra al usuario-cliente en el sistema.
	     * @throws UsuarioRepetidoException Si el nickname del usuario se encuentra registrado en el sistema.
	     */
	    public abstract void altaCliente(String nickName, String nombre, String email, String contrasena, String apellido, LocalDate nacimiento, String nacionalidad, TipoDocumento tipoDoc, String numDoc, String imagen) throws UsuarioRepetidoException;  

	    /**
	     * Cambia los datos del usuario registrado en el sistema.
	     */
	    public abstract void editarDatosCliente(String nickname, String nombre, String apellido, String contraseña, String Imagen, LocalDate nacimiento, String nacionalidad, TipoDocumento tipoDoc, String numDoc) throws UsuarioNoExisteException;

	    /**
	     * Retorna los nombres de todos los usuarios clientes registrados en el sistema.
	     * @throws UsuarioNoExisteException Si no existen usuarios-CLIENTES registrados en el sistema.
	     */    
	    public abstract List<String> listarClientes() throws UsuarioNoExisteException; 
	    
	    public abstract String[] listarClientesWeb() throws UsuarioNoExisteException;
	    /**
	     * Asocia el paquete de nombre "nombreaPaquete" al usuario cliente con nickname "nickName".
	     * @throws UsuarioNoExisteException Si el nickname del usuario no se encuentra registrado en el sistema.
	     * @throws PaqueteNoExisteException Si el nombre del paquete no se encuentra registrado en el sistema.
	     * @throws PaqueteYaCompradoException Si el paquete ya esta asociado al cliente en el sistema.
	     */
	    public abstract void clienteCompraPaquete(String nickName, String nombrePaquete) throws UsuarioNoExisteException, PaqueteNoExisteException, PaqueteYaCompradoException;   

		public abstract void compraPaquetes(String nickName, String nombrePaquete, LocalDate fechaCompra, LocalDate fechaVencimiento, float costo); 

	    /**
	     * Retorna info de las resrvas del cliente con nickname "nickName" registrado en el sistema.
	     * @throws UsuarioNoExisteException Si el nickname del usuario no se encuentra registrado en el sistema.
	     * @throws ReservaNoExisteException Si no existen reservas asociadas al cliente en el sistema.
	     */   
	    public abstract List<DTReserva> listarReservasCliente(String nickName) throws UsuarioNoExisteException, ReservaNoExisteException;
	    
	    public abstract DTReservaWeb[] listarReservasClienteWeb(String nickName) throws UsuarioNoExisteException, ReservaNoExisteException;

	    public abstract List<Reserva> listarReservasClienteObj(String nickName)throws UsuarioNoExisteException, ReservaNoExisteException;

	    /**
	     * Retorna info de los paquetes adquiridos por el cliente con nickname "nickName" registrado en el sistema.
	     * @throws UsuarioNoExisteException Si el nickname del usuario no se encuentra registrado en el sistema.
	     * @throws PaqueteNoExisteException Si no existen paquetes asociados al cliente en el sistema.
	     */   
	    public abstract List<DTPaquete> listarPaquetesCompradosCliente(String nickName) throws UsuarioNoExisteException, PaqueteNoExisteException;

	    public abstract DTPaqueteWeb[] listarPaquetesCompradosClienteWeb(String nickName) throws UsuarioNoExisteException, PaqueteNoExisteException;
	    //Operaciones de Aerolineas
	    
	    /**
	     * Registra al usuario-aerolinea en el sistema.
	     * @throws UsuarioRepetidoException Si el nickname del usuario se encuentra registrado en el sistema.
	     */
	    public abstract void altaAereolinea(String nickName, String nombre, String email, String contrasena, String descripcion, String sitioWeb, String imagen) throws UsuarioRepetidoException;

	    /**
	     * Cambia los datos del usuario registrado en el sistema.
	     */
	    public abstract void editarDatosAereolinea(String nickname, String nombre, String contraseña, String Imagen, String descripcion, String sitioWeb) throws UsuarioNoExisteException; 
	      
	    /**
	     * Retorna los nombres de todos los usuarios-aerolinea registrados en el sistema.
	     * @throws UsuarioNoExisteException Si no existen usuarios-AEROLINEA registrados en el sistema.
	     */  
	    public abstract List<String> listarAerolineas() throws UsuarioNoExisteException;

	}
