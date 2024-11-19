package model;

import java.time.LocalDate;
import datatype.*;

public class Compra {

	//links
	private Cliente cliente;
	private Paquete paquete;
	
	//atributos
	private LocalDate fecha;
	private float costo;
	private LocalDate vencimiento;
	
	private Long id;
	
	//constructor
	public Compra(LocalDate fecha, float costo, LocalDate vencimiento) {
		this.fecha = fecha;
		this.costo = costo;
		this.vencimiento = vencimiento;
	}
	
	//-----------------OEPRACIONES-------------------------
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
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
	
	public Paquete getPaquete() {
		return paquete;
	}
	
	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
