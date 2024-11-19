package datatype;
import java.util.List;

public class DTReservaWeb {
	private TipoAsiento tipoAsiento;
	private int cantEquipaje;
	private int cantPasajeros;
	private String fecha;
	private float costo;
	private String nomVuelo;
	private String nicknameCliente;
	private DTPasaje[] pasajes;
	private DTCheckinWeb embarque = null;
	
	public DTReservaWeb(TipoAsiento tipAsi, int cantEquip, int cantPas, float cost, String fecha, String vuelo, String nick, List<DTPasaje> pas, DTCheckin check) {	
		this.setTipoAsiento(tipAsi);
		this.setCantEquipaje(cantEquip);
		this.setCantPasajeros(cantPas);
		this.setFecha(fecha);
		this.setCosto(cost);
		this.setNomVuelo(vuelo);
		pasajes = new DTPasaje[pas.size()];
		if(pas!=null) pasajes = pas.toArray(this.getPasajes());
		this.nicknameCliente = nick;
		if(check != null) embarque = new DTCheckinWeb(check.getFechaEmbarque().toString(), check.getHoraEmbarque().toString());
	}

	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}

	public void setTipoAsiento(TipoAsiento tipoAsiento) {
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

	public DTPasaje[] getPasajes() {
		return pasajes;
	}

	public void setPasajes(DTPasaje[] pasajes) {
		this.pasajes = pasajes;
	}

	public DTCheckinWeb getEmbarque() {
		return embarque;
	}

	public void setEmbarque(DTCheckinWeb embarque) {
		this.embarque = embarque;
	}
	
	
	
	
	
}
