package model;

import java.time.LocalDate;
import datatype.*;

public class Purchase {

	//links
	private Client cliente;
	private FlightRoutesPackage paquete;
	
	//atributos
	private LocalDate fecha;
	private float costo;
	private LocalDate vencimiento;
	
	private Long id;
	
	//constructor
	public Purchase(LocalDate fecha, float costo, LocalDate vencimiento) {
		this.fecha = fecha;
		this.costo = costo;
		this.vencimiento = vencimiento;
	}
	
	//-----------------OEPRACIONES-------------------------
	
	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public float getCosto() {
		return costo;
	}
	
	public LocalDate getVencimiento() {
		return vencimiento;
	}
	
	public FlightRoutesPackage getPaquete() {
		return paquete;
	}
	
	public void setPaquete(FlightRoutesPackage paquete) {
		this.paquete = paquete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
