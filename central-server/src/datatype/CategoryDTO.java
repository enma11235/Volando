package datatype;

public class CategoryDTO {

	private String nombre;
	
	public CategoryDTO(String nom) {
		setNombre(nom);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
