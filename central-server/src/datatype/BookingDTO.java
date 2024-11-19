package datatype;
import java.time.LocalDate;
import java.util.List;

public class BookingDTO {
	private SeatType tipoAsiento;
	private int cantEquipaje;
	private int cantPasajeros;
	private LocalDate fecha;
	private float costo;
	private String nomVuelo;
	private String nicknameCliente;
	private List<TicketDTO> pasajes;
	private CheckinDTO embarque;
	
	public BookingDTO(SeatType tipAsi, int cantEquip, int cantPas, float cost, LocalDate fecha, String vuelo, List<TicketDTO> pas, CheckinDTO embarque) {	
		this.tipoAsiento = tipAsi;
		this.cantEquipaje = cantEquip;
		this.cantPasajeros = cantPas;
		this.fecha = fecha;
		this.costo = cost;
		this.nomVuelo = vuelo;
		this.pasajes = pas;
		this.embarque = embarque;
	}
	
	
	
	public String getNicknameCliente() {
		return nicknameCliente;
	}



	public void setNicknameCliente(String nicknameCliente) {
		this.nicknameCliente = nicknameCliente;
	}



	public SeatType getTipoAsiento() {
		return this.tipoAsiento;
	}
	public int getCantEquipaje() {
		return this.cantEquipaje;
	}
	public int getCantPasajeros() {
		return this.cantPasajeros;
	}
	public LocalDate getFecha() {
		return this.fecha;
	}
	public float getCosto() {
		return this.costo;
	}

	public String getNomVuelo() {
		return nomVuelo;
	}



	public List<TicketDTO> getPasajes() {
		return pasajes;
	}



	public CheckinDTO getEmbarque() {
		return embarque;
	}



	public void setEmbarque(CheckinDTO embarque) {
		this.embarque = embarque;
	}
	
}
