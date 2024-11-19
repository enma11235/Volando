package logica;

public abstract class Usuario {
	private String nickname;
	private String nombre;
	private String email;
	private String contrasena;
	private String imagen;
	
	public Usuario(String nickname, String nombre, String email, String contrasena, String imagen) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.imagen = imagen;
	}

	public abstract Boolean esAerolinea();
	
	public abstract Boolean esCliente();
	
	public abstract DTUsuario getData();

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	@Override
    public String toString() {
        return "Usuario{" +
                "nickname='" + nickname + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
