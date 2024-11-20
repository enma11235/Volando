package persistencia;

import jakarta.persistence.*;
import model.*;

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

    public AerolineaJPA(Airline aerolinea) {
        super(aerolinea.getNickname(), aerolinea.getNombre(), aerolinea.getEmail(), aerolinea.getPassword(), aerolinea.getImageUrl()); 
        this.descripcion = aerolinea.getDescription();
        this.sitioWeb = aerolinea.getWebSite();
        this.rutasDeVueloIds = aerolinea.getAllFlightRoutes().stream().map(FlightRoute::getNombre).toList();
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
