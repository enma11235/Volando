package persistence;

import jakarta.persistence.*;
import model.*;

import java.util.List;

@Entity
public class AirlineEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @OneToMany(mappedBy = "airline")
    private List<RouteEntity> routes;
      
	@Column(length = 2000)
    private String descripcion;

    private String sitioWeb;

}
