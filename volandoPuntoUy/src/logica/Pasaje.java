package logica;


public class Pasaje {
	private Reserva reserva;
	private String nombre;
	private String apellido;
	private int asiento = 0;
	
	private long id;
	
	public Pasaje(String nom, String ape, int asiento) {
		this.nombre = nom;
		this.apellido = ape;
		this.asiento = asiento;
	}
	
	public DTPasaje getData() {
		return new DTPasaje(nombre, apellido, asiento);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}
	
	public void setReserva(Reserva reser) {
		this.reserva = reser;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(long pasajeId) {
		this.id = pasajeId;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Reserva getReserva() {
		return this.reserva;
	}

	public int getAsiento() {
		return asiento;
	}

	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}
}
