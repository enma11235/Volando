package model;

import java.time.LocalDate;
import java.time.LocalTime;
import datatype.*
;public class Checkin {

	private Long id;
	private LocalDate fechaEmbarque;
	private LocalTime horaEmbarque;
	
	public Checkin(LocalDate fechaEm, LocalTime horaEm) {
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
	
	public DTCheckin getInfo() {
		return new DTCheckin(fechaEmbarque, horaEmbarque);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}