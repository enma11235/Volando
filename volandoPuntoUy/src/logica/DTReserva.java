package logica;
import java.time.LocalDate;
import java.util.List;

public class DTReserva {
	private TipoAsiento tipoAsiento;
	private int cantEquipaje;
	private int cantPasajeros;
	private LocalDate fecha;
	private float costo;
	private String nomVuelo;
	private String nicknameCliente;
	private List<DTPasaje> pasajes;
	private DTCheckin embarque;
	
	public DTReserva(TipoAsiento tipAsi, int cantEquip, int cantPas, float cost, LocalDate fecha, String vuelo, List<DTPasaje> pas, DTCheckin embarque) {	
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



	public TipoAsiento getTipoAsiento() {
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



	public List<DTPasaje> getPasajes() {
		return pasajes;
	}



	public DTCheckin getEmbarque() {
		return embarque;
	}



	public void setEmbarque(DTCheckin embarque) {
		this.embarque = embarque;
	}
	
}
