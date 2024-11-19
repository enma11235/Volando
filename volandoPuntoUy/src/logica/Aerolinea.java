package logica;

import java.util.ArrayList;
import java.util.List;

public class Aerolinea extends Usuario {
	
	
	private List<RutaDeVuelo> rutasDeVuelo;

	//atributos
	private String descripcion;
	private String sitioWeb;

	public Aerolinea(String nickname, String nombre, String email, String contrasena, String descripcion, String sitio_web, List<RutaDeVuelo> rvs, String imagen) {
		super(nickname, nombre, email, contrasena, imagen);
		this.descripcion = descripcion;
		this.sitioWeb = sitio_web;
		this.rutasDeVuelo = rvs;
	}
	
	//----------------OPERACIONES---------------------

	@Override
	public Boolean esAerolinea() {
		return true;
	}

	@Override
	public Boolean esCliente() {
		return false;
	}

	@Override
	public DTUsuario getData() {
		return new DTAerolinea(super.getNickname(), super.getNombre(), super.getEmail(), super.getContrasena(), this.descripcion, this.sitioWeb, super.getImagen());
	}
	
	public void agregarRutaDeVuelo(RutaDeVuelo ruta) {
		this.rutasDeVuelo.add(ruta);
	}

	
	public void removeRutaDeVuelo(RutaDeVuelo ruta) {
		this.rutasDeVuelo.remove(ruta);
	}
	
	public List<String> listarRutasDeVuelo() {
		List<String> nombresrvs = new ArrayList<String>();
		for (int i = 0; i < rutasDeVuelo.size(); i++) {
			nombresrvs.add(rutasDeVuelo.get(i).getNombre());
		}
		return nombresrvs;
	}
	
	public List<RutaDeVuelo> getRutasDeVuelo(){
		return rutasDeVuelo;
	}
	
	public RutaDeVuelo getRutaDeVuelo(String nomRutaDeVuelo) {
	    if (rutasDeVuelo == null || rutasDeVuelo.isEmpty()) {
	        return null; 
	    } 
	    for (RutaDeVuelo ruta : rutasDeVuelo) {
	        if (ruta.getNombre().equals(nomRutaDeVuelo)) {
	            return ruta; 
	        }
	    }
	    return null;
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
	
	public List<String> listarRutasIngresadas(){
		List<String> nombresrvs = new ArrayList<String>();
		for (int i = 0; i < rutasDeVuelo.size(); i++) {
			if (rutasDeVuelo.get(i).getEstado() == EstadoRuta.Ingresada) nombresrvs.add(rutasDeVuelo.get(i).getNombre());			
		}
		return nombresrvs;
	}
	public List<DTRutaDeVuelo> listarRutasConfirmadasDT(){
		List<DTRutaDeVuelo> dtrvs = new ArrayList<DTRutaDeVuelo>();
		for (int i = 0; i < rutasDeVuelo.size(); i++) {
			if (rutasDeVuelo.get(i).getEstado() == EstadoRuta.Confirmada) dtrvs.add(rutasDeVuelo.get(i).getData());			
		}
		return dtrvs;
	}
}
