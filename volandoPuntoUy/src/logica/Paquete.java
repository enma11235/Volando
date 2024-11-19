package logica;

import java.time.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;

public class Paquete {
	
	  private String nombre;

	  private String descripcion;
	  private Duration periodoValidez;
	  private float descuento; 
	  private LocalDate fechaAlta;
	  private String imagen;
	  
	  private List<RutaPaquete> rutaPaquete = new ArrayList<RutaPaquete>();

	  private List<Compra> compra = null;
	  

	  public Paquete(String nom, String desc, Duration perValidez, float descuento, LocalDate fAlta, String imagen) {
		  this.nombre = nom;
		  this.descripcion = desc; 
		  this.periodoValidez = perValidez;
		  this.descuento = descuento;
		  this.fechaAlta = fAlta;
		  this.setImagen(imagen);
	  } 
	  

		
		public String getIdsRutas() {
			String d ="";
			for(RutaPaquete rp : rutaPaquete) {
				d+=rp.getId().toString()+',';
			}
			return d;
		}

		public String getNombre() {
			return nombre;
		}


		public List<RutaPaquete> getRutaPaquete() {
			return rutaPaquete;
		}

		public List<Compra> getCompra() {
			return compra;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}


		public Duration getPeriodoValidez() {
			return periodoValidez;
		}


		public void setPeriodoValidez(Duration periodoValidez) {
			this.periodoValidez = periodoValidez;
		}


		public float getDescuento() {
			return descuento;
		}


		public void setDescuento(float descuento) {
			this.descuento = descuento;
		}


		public LocalDate getFechaAlta() {
			return fechaAlta;
		}


		public void setFechaAlta(LocalDate fechaAlta) {
			this.fechaAlta = fechaAlta;
		}

		
		public boolean esPaqueteComprado() {
			return compra != null;
		}
		
		public boolean esPaqueteCompradoPorCliente(String nickName) {
			boolean res = false; 
			if (compra != null) {
				for (Compra c: compra) {
					if ((c.getCliente().getNickname()).equals(nickName))
						res = true; 
				}
			}
			return res; 
		}
		public boolean esVacio() {
			return rutaPaquete.isEmpty();
		}
		
		public boolean esValidoHoy() {
	        LocalDate fechaExpiracion = fechaAlta.plusDays(periodoValidez.toDays()); 
	        return !LocalDate.now().isAfter(fechaExpiracion);
	    }
		
		
		public void addRutaPaquete(RutaPaquete rutaP) {
			System.out.println("Inserto: "+rutaP.getCantidad()+rutaP.getId());
			rutaPaquete.add(rutaP);
		}
		
		
		public void removeRutaPaquete(RutaPaquete rutaP) {
			rutaPaquete.remove(rutaP);
		}
		
		public DTPaquete getInfoPaquete() {
			List<DTRutaPaquete> rutaP = new ArrayList<DTRutaPaquete>();
			for (RutaPaquete r:rutaPaquete) {
				rutaP.add(r.obtenerInfo());
			}
			return new DTPaquete(nombre, descripcion, periodoValidez, descuento, fechaAlta, imagen, rutaP);
		}
		
		public void comprarPaquete(Compra compraP) {
			if (compra == null)
				compra = new ArrayList<Compra>(); 
			
			compra.add(compraP);
		}
		
		public float getPrecioPaquete() {
			float precio = 0; 
			for (RutaPaquete RP : rutaPaquete)
				precio = precio + RP.getCosto(); 
			
			return precio; 
		}

		public String getImagen() {
			
			return imagen;
		}

		public void setImagen(String imagen) {
			this.imagen = imagen;
		}
		
		public Boolean contieneRuta(String nomRuta) {
			Boolean res = false;
			for (RutaPaquete rt : rutaPaquete) {
				if (rt.obtenerInfo().getRuta().getNombre().equals(nomRuta)) res = true;
			}
			return res;
		}
		
	    
}
