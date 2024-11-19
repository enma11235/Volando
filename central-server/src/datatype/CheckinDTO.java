package datatype;

import java.time.LocalDate;
import java.time.LocalTime;

public class CheckinDTO {

	private LocalDate fechaEmbarque;
	private LocalTime horaEmbarque;
	
	public CheckinDTO(LocalDate fechaEm, LocalTime horaEm) {
		fechaEmbarque = fechaEm;
		horaEmbarque = horaEm;
	}

	public LocalTime getHoraEmbarque() {
		return horaEmbarque;
	}

	public void setHoraEmbarque(LocalTime horaEmbarque) {
		this.horaEmbarque = horaEmbarque;
	}

	public LocalDate getFechaEmbarque() {
		return fechaEmbarque;
	}

	public void setFechaEmbarque(LocalDate fechaEmbarque) {
		this.fechaEmbarque = fechaEmbarque;
	}
	
}