package model;
import datatype.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Booking {
	
	//links
	private Client cliente;
	private Flight vuelo;
	private List<Ticket> pasajes;
	private Checkin embarque = null;
	
	//atributos
	private SeatType tipoAsiento;
	private int cantEquipaje;
	private int cantPasajeros;
	private LocalDate fecha;
	private float costo;
	
	//JPA
	private Long id;
	private String nicknameCliente;
	
	
	//constructor
	public Booking(SeatType tipAsiento, int cantEquipaje, int cantPasajeros, LocalDate fechaR, float costoR) {	
		this.tipoAsiento = tipAsiento;
		this.cantEquipaje = cantEquipaje;
		this.cantPasajeros = cantPasajeros;
		this.fecha = fechaR;
		this.costo = costoR;
		pasajes = new ArrayList<Ticket>();
	}
	
	//---------------OPERACIONES-----------------------
	
	public BookingDTO getData() {
		List<TicketDTO> pas =  new ArrayList<TicketDTO>();
		for (Ticket p : pasajes) {
			pas.add(p.getData());
		}
		BookingDTO reser = new BookingDTO(this.tipoAsiento, this.cantEquipaje, this.cantPasajeros, this.costo, this.fecha, this.vuelo.getNombre(), pas, embarque!=null ? embarque.getInfo() : null);
		reser.setNicknameCliente(nicknameCliente);
		return reser;
	}
	
	public void setCliente(Client clienteR) {
		this.cliente = clienteR;
	}
	
	public void setVuelo(Flight vueloR) {
		this.vuelo = vueloR;
	}
	
	public String getNicknameCliente() {
		return nicknameCliente;
	}

	public void setNicknameCliente(String nicknameCliente) {
		this.nicknameCliente = nicknameCliente;
	}

	public Flight getVuelo() {
		return this.vuelo;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(long reservaId) {
		this.id = reservaId;
	}

	public Client getCliente() {
		return this.cliente;
	}
	
	public SeatType getTipoAsiento() {
		return tipoAsiento;
	}


	public int getCantPasajeros() {
		return cantPasajeros;
	}


	public void addPasaje(Ticket pasajeR) {
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
	
	public List<Ticket> getPasajes() {
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

	public void setPasajes(List<Ticket> pasajes) {
		this.pasajes = pasajes;
	}

	public void setTipoAsiento(SeatType tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

	public void setCantPasajeros(int cantPasajeros) {
		this.cantPasajeros = cantPasajeros;
	}
	
}
