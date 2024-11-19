package model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import datatype.*
;

public class Client extends User {
	
	//links
	private List<Booking> reservas = new ArrayList<>();
	private List<Purchase> compras = new ArrayList<>();
	
	
	//atributos
	private String apellido;
	private LocalDate nacimiento;
	private String nacionalidad;
	private DocumentType tipoDocumento;
	private String numDocumento;
	
	
	//constructor
	public Client(String nickname, String nombre, String email, String contrasena, String apellido, LocalDate nacimiento,
			String nacionalidad, DocumentType tipo_documento, String num_documento, String imagen) {
		super(nickname, nombre, email, contrasena, imagen);
		this.apellido = apellido;
		this.nacimiento = nacimiento;
		this.nacionalidad = nacionalidad;
		this.tipoDocumento = tipo_documento;
		this.numDocumento = num_documento;
	}

	//---------------OPERACIONES--------------------
	
	@Override
	public Boolean esAerolinea() {
		return false;
	}

	@Override
	public Boolean esCliente() {
		return true;
	}

	@Override
	public UserDTO getData() {
		return new ClientDTO(super.getNickname(), super.getNombre(), super.getEmail(), super.getContrasena(), this.apellido, this.nacimiento, this.nacionalidad, this.tipoDocumento, this.numDocumento, super.getImagen());
	}
	
	//public void asociarPaqueteACliente(Paquete p) {}
	
	public void addReserva(Booking reserva) {
		this.reservas.add(reserva);
	}
	
	public void removeReserva(Booking reserva) {
		this.reservas.remove(reserva);
	}
	
	public List<Booking> getReservas() {
		return this.reservas;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public DocumentType getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(DocumentType tipo_documento) {
		this.tipoDocumento = tipo_documento;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String num_documento) {
		this.numDocumento = num_documento;
	}

	public void addCompra(Purchase compra) {
		// TODO Auto-generated method stub
		compras.add(compra);

	}

	public List<Purchase> getCompras() {
		return compras;
	}



}
