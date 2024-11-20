package persistence;

import jakarta.persistence.*;
import model.*;

import java.time.LocalDate;
import java.util.List;

import datatype.SeatType;

@Entity
public class BookingEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

		private String clienteNickname;
		private String vueloId;
		
		@ElementCollection
		private List<Long> pasajesIds;
		
		private Long checkInId = null;
		
		@Enumerated(EnumType.STRING)
		private SeatType tipoAsiento;
		
		private int cantEquipaje;
		private int cantPasajeros;
		private LocalDate fecha;
		private float costo;
		
	public BookingEntity() {}
		
	public BookingEntity(Booking reserva) {
		if (reserva.getId() != null) {  
	        this.id = reserva.getId();
	    }
		this.tipoAsiento = reserva.getTipoAsiento();
		this.cantEquipaje = reserva.getCantEquipaje();
		this.cantPasajeros = reserva.getCantPasajeros();
		this.fecha = reserva.getFecha();
		this.costo = reserva.getCosto();
		this.vueloId = reserva.getVuelo().getName();
		if (reserva.getEmbarque() != null) {
			this.checkInId = reserva.getEmbarque().getId();
		}
		this.clienteNickname = reserva.getCliente().getNickname();
		this.pasajesIds = reserva.getPasajes().stream().map(Pasaje::getId).toList();
	}

	public void setPasajes(List<Long> ps) {
		pasajesIds = ps;
	}

	public Long getId() {
		return id;
	}


	public String getClienteNickname() {
		return clienteNickname;
	}


	public String getVueloId() {
		return vueloId;
	}


	public List<Long> getPasajesIds() {
		return pasajesIds;
	}


	public Long getCheckInId() {
		return checkInId;
	}


	public SeatType getTipoAsiento() {
		return tipoAsiento;
	}


	public int getCantEquipaje() {
		return cantEquipaje;
	}


	public int getCantPasajeros() {
		return cantPasajeros;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public float getCosto() {
		return costo;
	}
	
	

}
