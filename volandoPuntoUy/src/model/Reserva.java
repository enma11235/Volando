package model;
import datatype.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Reserva {
	
	//links
	private Cliente cliente;
	private Vuelo vuelo;
	private List<Pasaje> pasajes;
	private Checkin embarque = null;
	
	//atributos
	private TipoAsiento tipoAsiento;
	private int cantEquipaje;
	private int cantPasajeros;
	private LocalDate fecha;
	private float costo;
	
	//JPA
	private Long id;
	private String nicknameCliente;
	
	
	//constructor
	public Reserva(TipoAsiento tipAsiento, int cantEquipaje, int cantPasajeros, LocalDate fechaR, float costoR) {	
		this.tipoAsiento = tipAsiento;
		this.cantEquipaje = cantEquipaje;
		this.cantPasajeros = cantPasajeros;
		this.fecha = fechaR;
		this.costo = costoR;
		pasajes = new ArrayList<Pasaje>();
	}
	
	//---------------OPERACIONES-----------------------
	
	public DTReserva getData() {
		List<DTPasaje> pas =  new ArrayList<DTPasaje>();
		for (Pasaje p : pasajes) {
			pas.add(p.getData());
		}
		DTReserva reser = new DTReserva(this.tipoAsiento, this.cantEquipaje, this.cantPasajeros, this.costo, this.fecha, this.vuelo.getNombre(), pas, embarque!=null ? embarque.getInfo() : null);
		reser.setNicknameCliente(nicknameCliente);
		return reser;
	}
	
	public void setCliente(Cliente clienteR) {
		this.cliente = clienteR;
	}
	
	public void setVuelo(Vuelo vueloR) {
		this.vuelo = vueloR;
	}
	
	public String getNicknameCliente() {
		return nicknameCliente;
	}

	public void setNicknameCliente(String nicknameCliente) {
		this.nicknameCliente = nicknameCliente;
	}

	public Vuelo getVuelo() {
		return this.vuelo;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(long reservaId) {
		this.id = reservaId;
	}

	public Cliente getCliente() {
		return this.cliente;
	}
	
	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}


	public int getCantPasajeros() {
		return cantPasajeros;
	}


	public void addPasaje(Pasaje pasajeR) {
		this.pasajes.add(pasajeR);
	}
	
	 @Override
	    public String toString() {
	        return "Reserva{" +
	                "vuelo=" + (this.vuelo != null ? this.vuelo.getNombre() : "null") +
	                ", fecha=" + (this.fecha != null ? this.fecha.toString() : "null") +
	                '}';
	    }

	public Checkin getEmbarque() {
		return embarque;
	}

	public void setEmbarque(Checkin embarque) {
		this.embarque = embarque;
	}
	
	public List<Pasaje> getPasajes() {
		return pasajes;
	}

	public int getCantEquipaje() {
		return cantEquipaje;
	}

	public void setCantEquipaje(int cantEquipaje) {
		this.cantEquipaje = cantEquipaje;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public void setPasajes(List<Pasaje> pasajes) {
		this.pasajes = pasajes;
	}

	public void setTipoAsiento(TipoAsiento tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

	public void setCantPasajeros(int cantPasajeros) {
		this.cantPasajeros = cantPasajeros;
	}
	
}
