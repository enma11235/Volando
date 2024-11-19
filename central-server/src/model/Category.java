package model;
import datatype.*;

public class Category {
	
	//links
	
	//atributos
	private String nombre;

	//constructor
	public Category(String nombre) {
		this.nombre = nombre;
	}

	//-----------OPERACIONES-------------------
	public String getNombre() {
		return nombre;
	}
	
	public CategoryDTO getDT() {
		return new CategoryDTO(nombre);
	}
}
