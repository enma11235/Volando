package model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import datatype.*
;

public class Cliente extends Usuario {
	
	//links
	private List<Reserva> reservas = new ArrayList<>();
	private List<Compra> compras = new ArrayList<>();
	
	
	//atributos
	private String apellido;
	private LocalDate nacimiento;
	private String nacionalidad;
	private TipoDocumento tipoDocumento;
	private String numDocumento;
	
	
	//constructor
	public Cliente(String nickname, String nombre, String email, String contrasena, String apellido, LocalDate nacimiento,
			String nacionalidad, TipoDocumento tipo_documento, String num_documento, String imagen) {
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
	public DTUsuario getData() {
		return new DTCliente(super.getNickname(), super.getNombre(), super.getEmail(), super.getContrasena(), this.apellido, this.nacimiento, this.nacionalidad, this.tipoDocumento, this.numDocumento, super.getImagen());
	}
	
	//public void asociarPaqueteACliente(Paquete p) {}
	
	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void removeReserva(Reserva reserva) {
		this.reservas.remove(reserva);
	}
	
	public List<Reserva> getReservas() {
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

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipo_documento) {
		this.tipoDocumento = tipo_documento;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String num_documento) {
		this.numDocumento = num_documento;
	}

	public void addCompra(Compra compra) {
		// TODO Auto-generated method stub
		compras.add(compra);

	}

	public List<Compra> getCompras() {
		return compras;
	}



}
