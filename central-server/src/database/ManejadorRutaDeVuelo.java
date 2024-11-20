package database;
import controller.*;
import service.*;
import excepciones.*;
import persistencia.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import persistencia.CategoriaJPA;
import persistencia.CiudadJPA;
import persistencia.JPAUtil;
import persistencia.RutaDeVueloJPA;
import persistencia.VueloJPA;

import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.util.ArrayList;

public class ManejadorRutaDeVuelo {

	// instancia
	private static ManejadorRutaDeVuelo instancia = null;

	// constructor
	private ManejadorRutaDeVuelo() {

	}

	// ------------------OPERACIONES---------------------

	public static ManejadorRutaDeVuelo getInstance() {
		if (instancia == null)
			instancia = new ManejadorRutaDeVuelo();
		return instancia;
	}

	public void agregarCategoria(Categoria cate) {
		CategoriaJPA cjpa = new CategoriaJPA(cate.getNombre());
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(cjpa);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public Categoria buscarCategoria(String nombre) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		CategoriaJPA c = em.find(CategoriaJPA.class, nombre);
		em.close();
		return c != null ? c.toClass() : null;
	}

	public List<String> listarCategorias() {
		List<String> lista = new ArrayList<>();
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

		try {
			List<CategoriaJPA> cs = em.createQuery("SELECT c FROM CategoriaJPA c", CategoriaJPA.class).getResultList();

			for (CategoriaJPA c : cs) {
				lista.add(c.toClass().getNombre());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return lista;
	}

	public Flight obtenerVuelo(String nombre) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		Flight vuelo = null;

		try {
			VueloJPA vueloJPA = em.createQuery("SELECT v FROM VueloJPA v WHERE v.nombre = :nombre", VueloJPA.class)
					.setParameter("nombre", nombre).getSingleResult();

			if (vueloJPA != null) {
				vuelo = new Flight(vueloJPA.getNombre(), vueloJPA.getFecha(), Duration.parse(vueloJPA.getDuracion()),
						vueloJPA.getCantAsientosTurista(), vueloJPA.getCantAsientosEjecutivo(), vueloJPA.getCantAsientosTuristaDisponible(), vueloJPA.getCantAsientosEjecutivoDisponible(), vueloJPA.getFechaAlta(),
						vueloJPA.getImagen());
				vuelo.setRoute(obtenerRutaDeVuelo(vueloJPA.getRutaDeVueloId()));
				if (vueloJPA.getReservasIds()!=null) {
					for(Long id : vueloJPA.getReservasIds())
						vuelo.addBooking(ManejadorUsuario.getInstance().obtenerReserva(id, vuelo, 0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return vuelo;
	}
	
	public Flight obtenerVueloConReservas(String nombre) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		Flight vuelo = null;

		try {
			VueloJPA vueloJPA = em.createQuery("SELECT v FROM VueloJPA v WHERE v.nombre = :nombre", VueloJPA.class)
					.setParameter("nombre", nombre).getSingleResult();

			if (vueloJPA != null) {
				vuelo = new Flight(vueloJPA.getNombre(), vueloJPA.getFecha(), Duration.parse(vueloJPA.getDuracion()),
						vueloJPA.getCantAsientosTurista(), vueloJPA.getCantAsientosEjecutivo(), vueloJPA.getCantAsientosTuristaDisponible(), vueloJPA.getCantAsientosEjecutivoDisponible(), vueloJPA.getFechaAlta(),
						vueloJPA.getImagen());
				for(Long id : vueloJPA.getReservasIds()) {
					vuelo.addBooking(ManejadorUsuario.getInstance().obtenerReserva(id,vuelo, 0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return vuelo;
	}

	public boolean existeVuelo(String nombre) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		boolean existe = false;
		try {
			String nombreLimpio = nombre.trim().toLowerCase();

			Long count = em
					.createQuery("SELECT COUNT(v) FROM VueloJPA v WHERE TRIM(LOWER(v.nombre)) = :nombreL", Long.class)
					.setParameter("nombreL", nombreLimpio).getSingleResult();
			existe = (count > 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return existe;
	}

	public boolean existeCategoria(String nombre) {
		return buscarCategoria(nombre) != null;
	}

	public boolean existeCiudad(String pais, String ciudad) {
		String clave = construirClaveCiudad(pais, ciudad);
		return obtenerCiudad(clave) != null;
	}

	public List<String> listarRutasDeVuelo() {
		List<String> lista = new ArrayList<>();
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
			List<RutaDeVueloJPA> rutasJPA = em.createQuery("SELECT r FROM RutaDeVueloJPA r", RutaDeVueloJPA.class)
					.getResultList();
			for (RutaDeVueloJPA r : rutasJPA) {
				lista.add(r.getNombre());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return lista;
	}

	public FlightRoute obtenerRutaDeVuelo(String nombreR) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		FlightRoute ruta = null;

		try {
			RutaDeVueloJPA rutaJPA = em.find(RutaDeVueloJPA.class, nombreR);
			if (rutaJPA != null) {

				Ciudad ciudadOrigen = obtenerCiudad(rutaJPA.getCiudadOrigenId());
				Ciudad ciudadDestino = obtenerCiudad(rutaJPA.getCiudadDestinoId());

				List<Categoria> categorias = new ArrayList<Categoria>();
				for (String c : rutaJPA.getCategorias()) {
					categorias.add(buscarCategoria(c));
				}

				

				ruta = new FlightRoute(rutaJPA.getNombre(), rutaJPA.getDescripcion(), rutaJPA.getDescripcionCorta(),
						rutaJPA.getHora(), rutaJPA.getCostoTurista(), rutaJPA.getCostoEjecutivo(),
						rutaJPA.getCostoEquipajeExtra(), ciudadOrigen, ciudadDestino, rutaJPA.getFechaAlta(),
						categorias, rutaJPA.getImagen(), rutaJPA.getVideo(), rutaJPA.getVisitas());
				
				List<Flight> vuelos = obtenerVuelosPorNombres(rutaJPA.getVuelos());
				for(Flight v: vuelos) {
					ruta.addVuelo(v);
				}
				
				ruta.setEstado(rutaJPA.getEstado());
			}
		} finally {
			em.close();
		}
		return ruta;
	}

	private List<Flight> obtenerVuelosPorNombres(List<String> nombresVuelos) {
	    if (nombresVuelos == null || nombresVuelos.isEmpty()) {
	        return new ArrayList<>(); 
	    }

	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    try {
	        TypedQuery<VueloJPA> query = em.createQuery(
	            "SELECT v FROM VueloJPA v WHERE v.nombre IN :nombres", VueloJPA.class);
	        query.setParameter("nombres", nombresVuelos);
	        return query.getResultList().stream()
	                .map(v -> new Flight(v.getNombre(), v.getFecha(), Duration.parse(v.getDuracion()),
	                        v.getCantAsientosTurista(), v.getCantAsientosEjecutivo(), v.getCantAsientosTuristaDisponible(), v.getCantAsientosEjecutivoDisponible(), v.getFechaAlta(), v.getImagen()))
	                .toList();
	    } finally {
	        em.close();
	    }
	}


	public boolean existeRuta(String nombre) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		boolean existe = false;
		try {
			String nombreLimpio = nombre.trim().toLowerCase();

			Long count = em.createQuery("SELECT COUNT(r) FROM RutaDeVueloJPA r WHERE TRIM(LOWER(r.nombre)) = :nombre",
					Long.class).setParameter("nombre", nombreLimpio).getSingleResult();
			existe = (count > 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return existe;
	}

	public void addRutaDeVuelo(FlightRoute rutaV) {
		RutaDeVueloJPA rutaJPA = new RutaDeVueloJPA(rutaV);
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(rutaJPA);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	public void addVuelo(Flight v) {
		VueloJPA JPA = new VueloJPA(v);
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(JPA);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	public void updateRutaDeVuelo(FlightRoute rutaV) {
	    RutaDeVueloJPA rutaJPA = new RutaDeVueloJPA(rutaV);
	    
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();     
	        rutaJPA = em.merge(rutaJPA); 
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}
	
	public void updateVuelo(Flight v) {
	    VueloJPA vJPA = new VueloJPA(v);
	    
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();     
	        em.merge(vJPA); 
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}


	private String construirClaveCiudad(String pais, String ciudad) {
		return pais.trim() + "-" + ciudad.trim();
	}

	public void agregarCiudad(Ciudad ciudadR) {
		CiudadJPA cjpa = new CiudadJPA(construirClaveCiudad(ciudadR.getPais(), ciudadR.getNombre()), ciudadR);
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(cjpa);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<Ciudad> listarCiudades() {
		List<Ciudad> listaDeCiudades = new ArrayList<>();
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

		try {
			List<CiudadJPA> ciudadesJPA = em.createQuery("SELECT c FROM CiudadJPA c", CiudadJPA.class).getResultList();

			for (CiudadJPA ciudadJPA : ciudadesJPA) {
				listaDeCiudades.add(ciudadJPA.toClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return listaDeCiudades;
	}

	public Ciudad obtenerCiudad(String clave) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		CiudadJPA ciudadJPA = em.find(CiudadJPA.class, clave);
		em.close();
		return ciudadJPA != null ? ciudadJPA.toClass() : null;
	}

}