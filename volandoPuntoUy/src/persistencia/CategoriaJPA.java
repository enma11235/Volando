package persistencia;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Id;
import logica.Categoria;
import logica.Ciudad;

@Entity
public class CategoriaJPA {
	@Id
	private String nombre;

	public CategoriaJPA() {
		
	}
	public CategoriaJPA(String nombre) {
		this.nombre = nombre;
	}
	
	public Categoria toClass() {
        return new Categoria(nombre);
    }
}
