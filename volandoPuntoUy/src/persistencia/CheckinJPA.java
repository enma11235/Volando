package persistencia;

import jakarta.persistence.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class CheckinJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaEmbarque;
    private LocalTime horaEmbarque;

    public CheckinJPA() {}

    public CheckinJPA(Checkin checkin) {
    	if (checkin.getId() != null) {  
	        this.id = checkin.getId();
	    }
        this.fechaEmbarque = checkin.getFechaEmbarque();
        this.horaEmbarque = checkin.getHoraEmbarque();
    }



	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public LocalDate getFechaEmbarque() {
		return fechaEmbarque;
	}

	public LocalTime getHoraEmbarque() {
		return horaEmbarque;
	}

    
}
