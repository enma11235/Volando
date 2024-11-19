package persistencia;

import jakarta.persistence.*;
import logica.Aerolinea;
import logica.Categoria;
import logica.RutaDeVuelo;

import java.util.List;

@Entity
@DiscriminatorValue("Aerolinea")
public class AerolineaJPA extends UsuarioJPA {

      
	@Column(length = 2000)
    private String descripcion;

    private String sitioWeb;

    @ElementCollection
    private List<String> rutasDeVueloIds;

    public AerolineaJPA() {}

    public AerolineaJPA(Aerolinea aerolinea) {
        super(aerolinea.getNickname(), aerolinea.getNombre(), aerolinea.getEmail(), aerolinea.getContrasena(), aerolinea.getImagen()); 
        this.descripcion = aerolinea.getDescripcion();
        this.sitioWeb = aerolinea.getSitioWeb();
        this.rutasDeVueloIds = aerolinea.getRutasDeVuelo().stream().map(RutaDeVuelo::getNombre).toList();
    }

    @Override
    public Boolean esAerolinea() {
        return true;
    }

    @Override
    public Boolean esCliente() {
        return false;
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

    public List<String> getRutasDeVuelo() {
        return rutasDeVueloIds;
    }

    public void setRutasDeVuel(List<String> rutasDeVueloNombres) {
        this.rutasDeVueloIds = rutasDeVueloNombres;
    }
}
