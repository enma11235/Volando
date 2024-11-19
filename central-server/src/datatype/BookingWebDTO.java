package datatype;
import java.util.List;

public class BookingWebDTO {
	private SeatType tipoAsiento;
	private int cantEquipaje;
	private int cantPasajeros;
	private String fecha;
	private float costo;
	private String nomVuelo;
	private String nicknameCliente;
	private TicketDTO[] pasajes;
	private CheckinWebDTO embarque = null;
	
	public BookingWebDTO(SeatType tipAsi, int cantEquip, int cantPas, float cost, String fecha, String vuelo, String nick, List<TicketDTO> pas, CheckinDTO check) {	
		this.setTipoAsiento(tipAsi);
		this.setCantEquipaje(cantEquip);
		this.setCantPasajeros(cantPas);
		this.setFecha(fecha);
		this.setCosto(cost);
		this.setNomVuelo(vuelo);
		pasajes = new TicketDTO[pas.size()];
		if(pas!=null) pasajes = pas.toArray(this.getPasajes());
		this.nicknameCliente = nick;
		if(check != null) embarque = new CheckinWebDTO(check.getFechaEmbarque().toString(), check.getHoraEmbarque().toString());
	}

	public SeatType getTipoAsiento() {
		return tipoAsiento;
	}

	public void setTipoAsiento(SeatType tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

	public int getCantEquipaje() {
		return cantEquipaje;
	}

	public void setCantEquipaje(int cantEquipaje) {
		this.cantEquipaje = cantEquipaje;
	}

	public int getCantPasajeros() {
		return cantPasajeros;
	}

	public void setCantPasajeros(int cantPasajeros) {
		this.cantPasajeros = cantPasajeros;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getNomVuelo() {
		return nomVuelo;
	}

	public void setNomVuelo(String nomVuelo) {
		this.nomVuelo = nomVuelo;
	}

	public String getNicknameCliente() {
		return nicknameCliente;
	}

	public void setNicknameCliente(String nicknameCliente) {
		this.nicknameCliente = nicknameCliente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public TicketDTO[] getPasajes() {
		return pasajes;
	}

	public void setPasajes(TicketDTO[] pasajes) {
		this.pasajes = pasajes;
	}

	public CheckinWebDTO getEmbarque() {
		return embarque;
	}

	public void setEmbarque(CheckinWebDTO embarque) {
		this.embarque = embarque;
	}
	
	
	
	
	
}
