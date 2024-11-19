package model;
import datatype.*;

public class Categoria {
	
	//links
	
	//atributos
	private String nombre;

	//constructor
	public Categoria(String nombre) {
		this.nombre = nombre;
	}

	//-----------OPERACIONES-------------------
	public String getNombre() {
		return nombre;
	}
	
	public DTCategoria getDT() {
		return new DTCategoria(nombre);
	}
}
