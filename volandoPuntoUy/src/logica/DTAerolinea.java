package logica;

public class DTAerolinea extends DTUsuario {
	
	private String descripcion;
	private String sitioWeb;
	
	public DTAerolinea(String nickname, String nombre, String email, String pass, String descripcion, String sitioWeb, String imagen) {
		super(nickname, nombre, email, pass, imagen);
		this.setDescripcion(descripcion);
		this.setSitioWeb(sitioWeb);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}


	
}
