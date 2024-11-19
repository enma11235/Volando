package persistencia;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Id;
import model.*;

@Entity
public class CategoriaJPA {
	@Id
	private String nombre;

	public CategoriaJPA() {
		
	}
	public CategoriaJPA(String nombre) {
		this.nombre = nombre;
	}
	
	public Category toClass() {
        return new Category(nombre);
    }
}
