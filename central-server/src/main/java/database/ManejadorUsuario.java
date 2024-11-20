package database;
import controller.*;
import service.*;
import excepciones.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;
import persistence.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class ManejadorUsuario {

	private Map<String, User> usuarios;
	private static ManejadorUsuario instancia = null; // Singleton

	private ManejadorUsuario() {
		usuarios = new HashMap<String, User>();
	}

	public static ManejadorUsuario getInstance() {
		if (instancia == null)
			instancia = new ManejadorUsuario();
		return instancia;
	}

	public void addUsuario(User usuario) {
		
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			if (usuario.isAirline()) {
	            Airline aerolinea = (Airline) usuario; 
	            AirlineEntity aerolineaJPA = new AirlineEntity(aerolinea); 
	            em.persist(aerolineaJPA);
	        } 
	        else {
	            Client cliente = (Client) usuario; 
	            ClientEntity clienteJPA = new ClientEntity(cliente);  
	            em.persist(clienteJPA); 
	        }
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
	
	public void updateUsuario(User usuario) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			if (usuario.isAirline()) {
	            Airline aerolinea = (Airline) usuario; 
	            AirlineEntity aerolineaJPA = new AirlineEntity(aerolinea); 
	            em.merge(aerolineaJPA);
	        } 
	        else {
	            Client cliente = (Client) usuario; 
	            ClientEntity clienteJPA = new ClientEntity(cliente);  
	            em.merge(clienteJPA); 
	        }
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

	public User obtenerUsuario(String nickName) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		UserEntity usuarioJ = null;
		try {
			usuarioJ = em.find(UserEntity.class, nickName);
			if(usuarioJ == null)
				return null;
			if(usuarioJ.esAerolinea()) {
				return obtenerAerolinea(nickName);
			}else {
				return obtenerCliente(nickName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return null;
		
	}

	public List<User> getUsuarios() {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		List<User> usuarios = new ArrayList<>();
		try {
			List<UserEntity> usuariosJPA = em.createQuery("SELECT u FROM UsuarioJPA u", UserEntity.class).getResultList();
			for(UserEntity ujpa : usuariosJPA) {
				usuarios.add(obtenerUsuario(ujpa.getNickname()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return usuarios;
	}
	
	public List<String> listarClientes() {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		List<String> clientes = new ArrayList<>();
		try {
			List<UserEntity> usuariosJPA = em.createQuery("SELECT u FROM UsuarioJPA u", UserEntity.class).getResultList();
			for(UserEntity ujpa : usuariosJPA) {
				if(ujpa.esCliente())
					clientes.add(ujpa.getNickname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return clientes;
	}

	public void editarDatosAereolinea(String nickname, String nombre, String contrasena, String imagen,
			String descripcion, String sitioWeb) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			AirlineEntity aerolinea = em.find(AirlineEntity.class, nickname);
			if (aerolinea != null) {
				if (descripcion != null && !descripcion.isEmpty()) {
					aerolinea.setDescripcion(descripcion);
				}
				if (sitioWeb != null && !sitioWeb.isEmpty()) {
					aerolinea.setSitioWeb(sitioWeb);
				}
				if (nombre != null && !nombre.isEmpty()) {
					aerolinea.setNombre(nombre);
				}
				if (contrasena != null && !contrasena.isEmpty()) {
					aerolinea.setContrasena(contrasena);
				}
				if (imagen != null && !imagen.isEmpty()) {
					aerolinea.setImagen(imagen);
				}
				em.merge(aerolinea);
			}

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

	public void editarDatosCliente(String nickname, String nombre, String apellido, String contrasena, String imagen,
			LocalDate nacimiento, String nacionalidad, DocumentType tipoDoc, String numDoc) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			ClientEntity cliente = em.find(ClientEntity.class, nickname);
			if (cliente != null) {
				if (apellido != null && !apellido.isEmpty()) {
					cliente.setApellido(apellido);
				}
				if (nacimiento != null) {
					cliente.setNacimiento(nacimiento);
				}
				if (nacionalidad != null && !nacionalidad.isEmpty()) {
					cliente.setNacionalidad(nacionalidad);
				}
				if (tipoDoc != null) {
					cliente.setTipoDocumento(tipoDoc);
				}
				if (numDoc != null && !numDoc.isEmpty()) {
					cliente.setNumDocumento(numDoc);
				}
				if (nombre != null && !nombre.isEmpty()) {
					cliente.setNombre(nombre);
				}
				if (contrasena != null && !contrasena.isEmpty()) {
					cliente.setContrasena(contrasena);
				}
				if (imagen != null && !imagen.isEmpty()) {
					cliente.setImagen(imagen);
				}
				em.merge(cliente);
			}

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

	public Airline obtenerAerolinea(String nickName) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		AirlineEntity ajpa = null;
		Airline a = null;
		try {
			ajpa = em.find(AirlineEntity.class, nickName);
			ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance(); 
			List<FlightRoute> lrv = new ArrayList<FlightRoute>();
			for(String r : ajpa.getRutasDeVuelo()) {
				lrv.add(mrv.obtenerRutaDeVuelo(r));
			}	
			a = new Airline(ajpa.getNickname(), ajpa.getNombre(), ajpa.getEmail(), ajpa.getContrasena(), ajpa.getDescripcion(), ajpa.getSitioWeb(), lrv, ajpa.getImagen());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return a;
	}

	public Client obtenerCliente(String nickName) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		ClientEntity cliente = null;
		Client c = null;
		try {
			cliente = em.find(ClientEntity.class, nickName); 
			c = new Client(cliente.getNickname(), cliente.getNombre(), cliente.getEmail(), cliente.getContrasena(), cliente.getApellido(), cliente.getNacimiento(), cliente.getNacionalidad(), cliente.getTipoDocumento(), cliente.getNumDocumento(), cliente.getImagen());
			for(Long id : cliente.getReservasIds()) {
				c.addBooking(obtenerReserva(id, c));
			}
			for(Long id : cliente.getComprasIds()) {
				c.addPurchase(obtenerCompra(id, c));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return c;
	}
	
	public Client obtenerClienteSinCompras(String nickName) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		ClientEntity cliente = null;
		Client c = null;
		try {
			cliente = em.find(ClientEntity.class, nickName); 
			c = new Client(cliente.getNickname(), cliente.getNombre(), cliente.getEmail(), cliente.getContrasena(), cliente.getApellido(), cliente.getNacimiento(), cliente.getNacionalidad(), cliente.getTipoDocumento(), cliente.getNumDocumento(), cliente.getImagen());
			for(Long id : cliente.getReservasIds()) {
				c.addBooking(obtenerReserva(id, c));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return c;
	}
	
	public Long addCheckin(Checkin checkin) {
	    CheckinJPA checkinJPA = new CheckinJPA(checkin);
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        em.persist(checkinJPA);
	        transaction.commit();
	        
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    return checkinJPA.getId();
	}

	public void updateCheckin(Checkin checkin) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        CheckinJPA checkinJPA = new CheckinJPA(checkin);
	        if (checkinJPA != null) {
	            em.merge(checkinJPA);
	            transaction.commit();
	        }
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	public Checkin obtenerCheckin(Long id) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Checkin checkin = null;
	    
	    try {
	        CheckinJPA checkinJPA = em.find(CheckinJPA.class, id);
	        if (checkinJPA != null) {
	            checkin = new Checkin(checkinJPA.getFechaEmbarque(), checkinJPA.getHoraEmbarque());
	            checkin.setId(id);
	        }
	    }catch(Exception e) {
	    } finally {
	        em.close();
	    }
	    
	    return checkin;
	}

	public Long addCompra(Compra compra) {
	    PurchaseEntity compraJPA = new PurchaseEntity(compra);
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        em.persist(compraJPA);
	        transaction.commit();
	        
	        
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    
	    return compraJPA.getId();
	}
	
	public void updateCompra(Compra compra) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        PurchaseEntity compraJPA = new PurchaseEntity(compra);
	        if (compraJPA != null) {
	            em.merge(compraJPA);
	            transaction.commit();
	        }
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	public Compra obtenerCompra(Long id) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Compra compra = null;
	    
	    try {
	        PurchaseEntity compraJPA = em.find(PurchaseEntity.class, id);
	        if (compraJPA != null) {
	            compra = new Compra(compraJPA.getFecha(), compraJPA.getCosto(), compraJPA.getVencimiento());
	            compra.setCliente(obtenerCliente(compraJPA.getClienteNickname()));
	            ManejadorPaquetes mpq = ManejadorPaquetes.getInstance(); 
	            compra.setPaquete(mpq.obtenerPaquete(compraJPA.getNombrePaquete()));
	        }
	    } finally {
	        em.close();
	    }
	    
	    return compra;
	}
	
	public Compra obtenerCompra(Long id, Paquete p, String aux) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Compra compra = null;
	    
	    try {
	        PurchaseEntity compraJPA = em.find(PurchaseEntity.class, id);
	        if (compraJPA != null) {
	            compra = new Compra(compraJPA.getFecha(), compraJPA.getCosto(), compraJPA.getVencimiento());
	            compra.setId(id);
	            compra.setCliente(obtenerClienteSinCompras(compraJPA.getClienteNickname()));
	            compra.setPaquete(p);
	        }
	    } finally {
	        em.close();
	    }
	    
	    return compra;
	}
	
	public Compra obtenerCompra(Long id, Client c) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Compra compra = null;
	    
	    try {
	        PurchaseEntity compraJPA = em.find(PurchaseEntity.class, id);
	        if (compraJPA != null) {
	            compra = new Compra(compraJPA.getFecha(), compraJPA.getCosto(), compraJPA.getVencimiento());
	            compra.setCliente(c);
	            compra.setId(id);
	            ManejadorPaquetes mpq = ManejadorPaquetes.getInstance(); 
	            compra.setPaquete(mpq.obtenerPaquete(compraJPA.getNombrePaquete()));
	        }
	    } finally {
	        em.close();
	    }
	    
	    return compra;
	}
	
	public Long addReserva(Booking reserva) {
	    BookingEntity reservaJPA = new BookingEntity(reserva);
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        em.persist(reservaJPA);
	        
	        transaction.commit();
	        System.out.println("Inserado id "+reservaJPA.getId());
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    return reservaJPA.getId();
	}
	
	public void updateReserva(Booking reserva) {
		System.out.println("Actualizo reserva id "+reserva.getId());
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        BookingEntity r = new BookingEntity(reserva);
	        if (r != null) {
	            em.merge(r);
	            transaction.commit();
	        }
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}
	
	public void updatePasaje(Pasaje p) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        PasajeJPA r = new PasajeJPA(p);
	        if (r != null) {
	            em.merge(r);
	            transaction.commit();
	        }
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	
	public Long addPasaje(Pasaje pasaje) {
	    PasajeJPA pasajeJPA = new PasajeJPA(pasaje);
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        em.persist(pasajeJPA);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    return pasajeJPA.getId();
	}

	
	public Booking obtenerReserva(Long id) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Booking reserva = null;
	    
	    try {
	        BookingEntity reservaJPA = em.find(BookingEntity.class, id);
	        if (reservaJPA != null) {
	            reserva = new Booking(reservaJPA.getTipoAsiento(), reservaJPA.getCantEquipaje(), reservaJPA.getCantPasajeros(), reservaJPA.getFecha(), reservaJPA.getCosto());
	            reserva.setId(reservaJPA.getId());
	            reserva.setNicknameCliente(reservaJPA.getClienteNickname());
	            ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
	            reserva.setVuelo(mrv.obtenerVuelo(reservaJPA.getVueloId()));
	            reserva.setEmbarque(obtenerCheckin(reservaJPA.getCheckInId()));
	            for(Long idP : reservaJPA.getPasajesIds()) {
	            	reserva.addPasaje(obtenerPasaje(idP));
	            }
	        }
	    } finally {
	        em.close();
	    }
	    
	    return reserva;
	}
	
	
	public Booking obtenerReserva(Long id, Flight a, int aux) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Booking reserva = null;
	    
	    try {
	        BookingEntity reservaJPA = em.find(BookingEntity.class, id);
	        if (reservaJPA != null) {
	            reserva = new Booking(reservaJPA.getTipoAsiento(), reservaJPA.getCantEquipaje(), reservaJPA.getCantPasajeros(), reservaJPA.getFecha(), reservaJPA.getCosto());
	            reserva.setId(reservaJPA.getId());
	            reserva.setNicknameCliente(reservaJPA.getClienteNickname());
	            reserva.setVuelo(a);
	            reserva.setEmbarque(obtenerCheckin(reservaJPA.getCheckInId()));
	            for(Long idP : reservaJPA.getPasajesIds()) {
	            	reserva.addPasaje(obtenerPasaje(idP));
	            }
	        }
	    } finally {
	        em.close();
	    }
	    
	    return reserva;
	}
	
	public Booking obtenerReserva(Long id, Client c) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Booking reserva = null;
	    
	    try {
	        BookingEntity reservaJPA = em.find(BookingEntity.class, id);
	        if (reservaJPA != null) {
	            reserva = new Booking(reservaJPA.getTipoAsiento(), reservaJPA.getCantEquipaje(), reservaJPA.getCantPasajeros(), reservaJPA.getFecha(), reservaJPA.getCosto());
	            reserva.setCliente(c);
	            reserva.setId(id);
	            ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance();
	            reserva.setVuelo(mrv.obtenerVuelo(reservaJPA.getVueloId()));
	            System.out.println("Obtengo reserva"+id+", tiene idceheckin: "+reservaJPA.getCheckInId());
	            reserva.setEmbarque(obtenerCheckin(reservaJPA.getCheckInId()));
	            
	            for(Long idP : reservaJPA.getPasajesIds()) {
	            	reserva.addPasaje(obtenerPasaje(idP));
	            }
	        }
	    } finally {
	        em.close();
	    }
	    
	    return reserva;
	}
	
	public Pasaje obtenerPasaje(Long id) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    Pasaje pasaje = null;
	    
	    try {
	        PasajeJPA pasajeJPA = em.find(PasajeJPA.class, id);
	        if (pasajeJPA != null) {
	            pasaje = new Pasaje(pasajeJPA.getNombre(), pasajeJPA.getApellido(), pasajeJPA.getAsiento());
	            pasaje.setId(id);
//	            pasaje.setReserva(obtenerReserva(pasajeJPA.getReservaId()));
	        }
	    } finally {
	        em.close();
	    }
	    
	    return pasaje;
	}



}