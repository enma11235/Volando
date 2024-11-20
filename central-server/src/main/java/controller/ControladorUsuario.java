package controller;
import controller.*;
import service.*;
import excepciones.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;
import persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.ReservaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;

public class ControladorUsuario implements IControladorUsuario {

	// constructor
	public ControladorUsuario() {
	}

	// -----------------OPERACIONES------------------------------

	public DTUsuario[] listarUsuariosWeb() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<User> usuarios = manejadorU.getUsuarios();
		DTUsuario[] dtusuarios = new DTUsuario[usuarios.size()];
		for (int x = 0; x < usuarios.size(); x++) {
			dtusuarios[x] = usuarios.get(x).getDTO();
		}
		return dtusuarios;
	}

	public List<DTUsuario> listarUsuarios() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<User> usuarios = manejadorU.getUsuarios();
		List<DTUsuario> dtusuarios = new ArrayList<DTUsuario>();
		for (int x = 0; x < usuarios.size(); x++) {
			dtusuarios.add(usuarios.get(x).getDTO());
		}
		return dtusuarios;
	}

	public DTAerolinea[] listarAerolineasDataWeb() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<User> usuarios = manejadorU.getUsuarios();
		DTAerolinea[] dtaerolineasp = new DTAerolinea[usuarios.size()];
		int i = 0;
		for (int x = 0; x < usuarios.size(); x++) {
			if (usuarios.get(x).isAirline()) {
				dtaerolineasp[i] = (DTAerolinea) usuarios.get(x).getDTO();
				i++;
			}
		}
		DTAerolinea[] dtaerolineas = new DTAerolinea[i];
		System.arraycopy(dtaerolineasp, 0, dtaerolineas, 0, i);
		if (i == 0) {
			throw new UsuarioNoExisteException("No existen aerolíneas");
		} else {
			return dtaerolineas;
		}
	}

	public List<String> listarUsuariosNick() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<User> usuarios = manejadorU.getUsuarios();
		if (usuarios == null)
			throw new UsuarioNoExisteException("No existen usuarios registrados en el sistema");
		List<String> nickUsers = new ArrayList<String>();
		for (int x = 0; x < usuarios.size(); x++) {
			nickUsers.add(usuarios.get(x).getNickname());
		}
		return nickUsers;
	}

	public String[] listarUsuariosNickWeb() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<User> usuarios = manejadorU.getUsuarios();
		if (usuarios == null)
			throw new UsuarioNoExisteException("No existen usuarios registrados en el sistema");
		String[] nickUsers = new String[usuarios.size()];
		for (int x = 0; x < usuarios.size(); x++) {
			nickUsers[x] = usuarios.get(x).getNickname();
		}
		return nickUsers;
	}

	public DTUsuario obtenerInfoUsuario(String nickName) throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		User usuarioD = manejadorU.obtenerUsuario(nickName);
		if (usuarioD != null) {
			return usuarioD.getDTO();
		} else {
			throw new UsuarioNoExisteException(nickName + " no existe");
		}
	}

	public void altaCliente(String nickName, String nombre, String email, String contrasena, String apellido,
			LocalDate nacimiento, String nacionalidad, DocumentType tipoDoc, String numDoc, String imagen)
			throws UsuarioRepetidoException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		if (manejadorU.obtenerUsuario(nickName) == null) {
			boolean emailRepetido = false;
			List<User> usuarios = manejadorU.getUsuarios();
			for (int i = 0; i < usuarios.size(); i++) {
				if (usuarios.get(i).getEmail().equals(email)) {
					emailRepetido = true;
					break;
				}
			}
			if (emailRepetido) {
				throw new UsuarioRepetidoException("Email no disponible");
			} else {
				Client clienteN = new Client(nickName, nombre, email, contrasena, apellido, nacimiento, nacionalidad,
						tipoDoc, numDoc, imagen);
				manejadorU.addUsuario(clienteN);
			}
		} else {
			throw new UsuarioRepetidoException("Nickname no disponible");
		}
	}

	public void editarDatosCliente(String nickname, String nombre, String apellido, String contraseña, String Imagen,
			LocalDate nacimiento, String nacionalidad, DocumentType tipoDoc, String numDoc)
			throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Client clienteE = (Client) manejadorU.obtenerUsuario(nickname);
		if (clienteE == null) {
			throw new UsuarioNoExisteException(nickname + " no existe");
		} else {
			manejadorU.editarDatosCliente(nickname, nombre, apellido, contraseña, Imagen, nacimiento, nacionalidad,
					tipoDoc, numDoc);
		}
	}

	public List<String> listarClientes() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<String> usuarios = manejadorU.listarClientes();
		if (usuarios.isEmpty())
			throw new UsuarioNoExisteException("No hay clientes");
		return usuarios;
	}

	public String[] listarClientesWeb() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<String> usuarios = manejadorU.listarClientes();
		String[] ret = new String[usuarios.size()];
		for (int x = 0; x < usuarios.size(); x++) {
			ret[x] = usuarios.get(x);
		}
		if (usuarios.isEmpty())
			throw new UsuarioNoExisteException("No hay clientes");
		return ret;
	}

	public void clienteCompraPaquete(String nickName, String nombrePaquete)
			throws UsuarioNoExisteException, PaqueteNoExisteException, PaqueteYaCompradoException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();

		Client clienteC = manejadorU.obtenerCliente(nickName);
		Paquete paqueteC = manejadorP.obtenerPaquete(nombrePaquete);
		if (paqueteC.esPaqueteCompradoPorCliente(nickName)) {
			throw new PaqueteYaCompradoException("El paquete ya fue adquirido por el cliente " + nickName);
		}

		LocalDate fechaCompra = LocalDate.now();
		LocalDate fechaVencimiento = fechaCompra.plusDays(paqueteC.getPeriodoValidez().toDays());

		float costo = paqueteC.getPrecioPaquete();

		Compra com = new Compra(fechaCompra, costo, fechaVencimiento);
		
		com.setPaquete(paqueteC);
		com.setCliente(clienteC);
		Long idCompra = manejadorU.addCompra(com);
		com.setId(idCompra);
		clienteC.addPurchase(com);
		paqueteC.comprarPaquete(com);
		
		manejadorU.updateUsuario(clienteC);
		manejadorP.updatePaquete(paqueteC);

	}

	// Cargar paquetes comprados
	public void compraPaquetes(String nickName, String nombrePaquete, LocalDate fechaCompra, LocalDate fechaVencimiento,
			float costo) {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		ManejadorPaquetes manejadorP = ManejadorPaquetes.getInstance();

		Client clienteC = manejadorU.obtenerCliente(nickName);
		Paquete paqueteC = manejadorP.obtenerPaquete(nombrePaquete);

		Compra com = new Compra(fechaCompra, costo, fechaVencimiento);
		
		com.setPaquete(paqueteC);
		com.setCliente(clienteC);
		
		Long idCompra = manejadorU.addCompra(com);
		com.setId(idCompra);
		
		if(clienteC.getAllPurchases() != null) {
		for(Compra c : clienteC.getAllPurchases()) {
			System.out.println("Comprapre"+c.getId());
		}
		}
		paqueteC.comprarPaquete(com);
		clienteC.addPurchase(com);
		
		if(clienteC.getAllPurchases() != null) {
			for(Compra c : clienteC.getAllPurchases()) {
				System.out.println("Comprapost"+c.getId());
			}
			}

		System.out.println("Agrego compra "+idCompra+" a "+clienteC.getNickname());
		
		manejadorU.updateUsuario(clienteC);
		manejadorP.updatePaquete(paqueteC);
	}

	public List<DTReserva> listarReservasCliente(String nickName)
			throws UsuarioNoExisteException, ReservaNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<DTReserva> res = new ArrayList<DTReserva>();
		Client clienteC = manejadorU.obtenerCliente(nickName);
		if (clienteC != null) {
			for (Booking r : clienteC.getAllBookings()) {
				res.add(r.getData());
			}
		} else
			throw new UsuarioNoExisteException("Cliente no existe");
		return res;
	}

	public DTReservaWeb[] listarReservasClienteWeb(String nickName)
			throws UsuarioNoExisteException, ReservaNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<DTReserva> res = new ArrayList<DTReserva>();
		Client clienteC = manejadorU.obtenerCliente(nickName);
		if (clienteC != null) {
			for (Booking r : clienteC.getAllBookings()) {
				res.add(r.getData());
			}
		} else
			throw new UsuarioNoExisteException("Cliente no existe");

		DTReservaWeb[] resW = new DTReservaWeb[res.size()];
		for (int i = 0; i < res.size(); i++) {
			resW[i] = new DTReservaWeb(res.get(i).getTipoAsiento(), res.get(i).getCantEquipaje(),
					res.get(i).getCantPasajeros(), res.get(i).getCosto(), res.get(i).getFecha().toString(),
					res.get(i).getNomVuelo(), clienteC.getNickname(), res.get(i).getPasajes(),
					res.get(i).getEmbarque());
		}
		return resW;
	}

	public List<Booking> listarReservasClienteObj(String nickName)
			throws UsuarioNoExisteException, ReservaNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<Booking> res = new ArrayList<Booking>();
		Client clienteC = manejadorU.obtenerCliente(nickName);
		if (clienteC != null) {
			for (Booking r : clienteC.getAllBookings()) {
				res.add(r);
			}
		} else
			throw new UsuarioNoExisteException("Cliente no existe");
		return res;
	}

	public List<DTPaquete> listarPaquetesCompradosCliente(String nickName)
			throws UsuarioNoExisteException, PaqueteNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<Compra> com = manejadorU.obtenerCliente(nickName).getAllPurchases();
		List<DTPaquete> paquetesC = new ArrayList<>();

		for (Compra c : com) {
			paquetesC.add(c.getPaquete().getInfoPaquete());
		}
		// System.out.println("Cliente: " + nickName);
		// System.out.println("Paquetes: " + p);
		return paquetesC;
	}

	public DTPaqueteWeb[] listarPaquetesCompradosClienteWeb(String nickName)
			throws UsuarioNoExisteException, PaqueteNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<Compra> com = manejadorU.obtenerCliente(nickName).getAllPurchases();
		DTPaqueteWeb[] paquetesC = new DTPaqueteWeb[com.size()];
		int i = 0;
		for (Compra c : com) {
			paquetesC[i] = new DTPaqueteWeb(c.getPaquete().getNombre(), c.getPaquete().getDescripcion(),
					c.getPaquete().getPeriodoValidez().toString(), c.getPaquete().getDescuento(),
					c.getPaquete().getFechaAlta().toString(), c.getPaquete().getImagen(),
					c.getPaquete().getInfoPaquete().getRutas());
			i++;
		}

		// System.out.println("Cliente: " + nickName);
		// System.out.println("Paquetes: " + p);
		return paquetesC;
	}

	public void altaAereolinea(String nickName, String nombre, String email, String contrasena, String descripcion,
			String sitioWeb, String imagen) throws UsuarioRepetidoException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		if (manejadorU.obtenerUsuario(nickName) == null) {
			boolean emailRepetido = false;
			List<User> usuarios = manejadorU.getUsuarios();
			for (int i = 0; i < usuarios.size(); i++) {
				if (usuarios.get(i).getEmail().equals(email)) {
					emailRepetido = true;
					break;
				}
			}
			if (emailRepetido) {
				throw new UsuarioRepetidoException("Email no disponible");
			} else {
				Airline aero = new Airline(nickName, nombre, email, contrasena, descripcion, sitioWeb,
						new ArrayList<FlightRoute>(), imagen);
				manejadorU.addUsuario(aero);
			}
		} else {
			throw new UsuarioRepetidoException("Nickname no disponible");
		}
	}

	public void editarDatosAereolinea(String nickname, String nombre, String contraseña, String Imagen,
			String descripcion, String sitioWeb) throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		Airline aero = (Airline) manejadorU.obtenerUsuario(nickname);
		if (aero == null) {
			throw new UsuarioNoExisteException(nickname + " no existe");
		} else {
			manejadorU.editarDatosAereolinea(nickname, nombre, contraseña, Imagen, descripcion, sitioWeb);
		}

	}

	public List<String> listarAerolineas() throws UsuarioNoExisteException {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		List<User> usuarios = manejadorU.getUsuarios();
		List<String> arrayAerolineas = new ArrayList<String>();
		for (int x = 0; x < usuarios.size(); x++) {
			if (usuarios.get(x).isAirline()) {
				arrayAerolineas.add(usuarios.get(x).getNickname());
			}
		}
		if (arrayAerolineas.isEmpty()) {
			throw new UsuarioNoExisteException("No existen aerolíneas");
		} else {
			return arrayAerolineas;
		}
	}
}
