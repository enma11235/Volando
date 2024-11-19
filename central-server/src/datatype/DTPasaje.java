package datatype;

public class DTPasaje {
	private String nombre;
	private String apellido;
	private int asiento = 0;
	
	public DTPasaje() {
		
	}
	public DTPasaje(String nombreP, String apellidoP, int asiento) {
		this.setNombre(nombreP);
		this.setApellido(apellidoP);
		this.asiento = asiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getAsiento() {
		return asiento;
	}
	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}
}
