package logica;

public class DTUsuario {
	
	private String nickname;
	private String nombre;
	private String email;
	private String pass;
	private String imagen;

	public DTUsuario(String nickname, String nombre, String email, String pass, String imagen) {
		this.setNickname(nickname);
		this.setNombre(nombre);
		this.setEmail(email);
		this.setPass(pass);
		this.imagen = imagen;
	}
	public String getImagen() {
		if (imagen==null || imagen.equals(""))
			return "no_image.jpg";
		else
			return imagen;
	}
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
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setImagen(String img) {
		this.imagen = img;
	}
}
