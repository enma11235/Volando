package datatype;

import java.time.LocalDate;
import java.time.LocalTime;

public class DTCheckinWeb {

	private String fechaEmbarque;
	private String horaEmbarque;
	
	public DTCheckinWeb(String fechaEm, String horaEm) {
		fechaEmbarque = fechaEm;
		horaEmbarque = horaEm;
	}

	public String getHoraEmbarque() {
		return horaEmbarque;
	}

	public void setHoraEmbarque(String horaEmbarque) {
		this.horaEmbarque = horaEmbarque;
	}

	public String getFechaEmbarque() {
		return fechaEmbarque;
	}

	public void setFechaEmbarque(String fechaEmbarque) {
		this.fechaEmbarque = fechaEmbarque;
	}
	
}