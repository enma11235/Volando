package database;

import controller.*;

import service.*;
import excepciones.*;
import datatype.*;
import database.*;
import factory.*;
import model.*;
import persistence.*;

import java.time.Duration;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ManejadorPaquetes {
	//instancia del singleton
	private static ManejadorPaquetes instancia = null;
	
	
	private ManejadorPaquetes() {
		
	}
	
	public static ManejadorPaquetes getInstance() {
		if (instancia == null)
			instancia = new ManejadorPaquetes();
		return instancia;
	}

	
	//operaciones	

	public List<String> listarPaquetes() {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    TypedQuery<String> query = em.createQuery("SELECT p.nombre FROM PaqueteJPA p", String.class);
	    return new ArrayList<>(query.getResultList());
	}



    public List<Paquete> getPaquetes() {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<PaqueteJPA> query = em.createQuery("SELECT p FROM PaqueteJPA p", PaqueteJPA.class);
        List<PaqueteJPA> paquetesJPA = query.getResultList();
        List<Paquete> paquetes = new ArrayList<>();
        
        for (PaqueteJPA p : paquetesJPA) {
            paquetes.add(obtenerPaquete(p.getNombre())); 
        }
        return paquetes;
    }


    public Paquete obtenerPaquete(String nombre) {

    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        String nombreLimpio = nombre.trim().toLowerCase();
        
        TypedQuery<PaqueteJPA> query = em.createQuery(
            "SELECT p FROM PaqueteJPA p WHERE TRIM(LOWER(p.nombre)) = :nombreL", PaqueteJPA.class)
            .setParameter("nombreL", nombreLimpio);
        
        PaqueteJPA paqueteJPA = query.getSingleResult();
        
        if(paqueteJPA == null)
        	return null;
        
        Paquete p = new Paquete(paqueteJPA.getNombre(), paqueteJPA.getDescripcion(), Duration.parse(paqueteJPA.getPeriodoValidez()), paqueteJPA.getDescuento(), paqueteJPA.getFechaAlta(), paqueteJPA.getImagen());
        ManejadorUsuario mu = ManejadorUsuario.getInstance(); 
        for(Long id : paqueteJPA.getCompra()) {
        	p.comprarPaquete(mu.obtenerCompra(id, p, ""));
        }
        for(Long id : paqueteJPA.getRutaPaquete()) {
        	if(id!=null) {
        	System.out.println("Busco: "+id);
        	p.addRutaPaquete(obtenerRutaPaquete(id,p));}
        }
        
        return p; 
    }



    public void addPaquete(Paquete nuevo) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    	EntityTransaction transaction = em.getTransaction();
    	 try {
             transaction.begin();
             PaqueteJPA paqueteJPA = new PaqueteJPA(nuevo); 
             em.persist(paqueteJPA);
             transaction.commit(); 
         } catch (RuntimeException e) {
             if (transaction.isActive()) {
                 transaction.rollback();
             }
             e.printStackTrace();
         }
    }
    
    public void updatePaquete(Paquete p) {
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    
	    try {
	        transaction.begin();
	        PaqueteJPA pJPA = new PaqueteJPA(p);
	        if (pJPA != null) {
	            em.merge(pJPA);
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

    public boolean existePaquete(String nombre) {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        String nombreLimpio = nombre.trim().toLowerCase();
        
        Long count = em.createQuery(
            "SELECT COUNT(p) FROM PaqueteJPA p WHERE TRIM(LOWER(p.nombre)) = :nombreL", Long.class)
            .setParameter("nombreL", nombreLimpio).getSingleResult();
        
        return count > 0;
    }
    
    public Long addRutaPaquete(RutaPaquete rutaPaquete) {
        RutaPaqueteJPA rutaPaqueteJPA = new RutaPaqueteJPA(rutaPaquete);
        
        System.out.println("persisto: "+rutaPaqueteJPA.toString());
        
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(rutaPaqueteJPA);
            transaction.commit();
    
            
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            
        }
        return rutaPaqueteJPA.getId();
    }

    
//    public void updateRutaPaquete(RutaPaquete rutaPaquete) {
//        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
//        EntityTransaction transaction = em.getTransaction();
//        
//        try {
//            transaction.begin();
//            RutaPaqueteJPA rutaPaqueteJPA = new RutaPaqueteJPA(rutaPaquete);
//            if (rutaPaqueteJPA != null) {
//                em.merge(rutaPaqueteJPA);
//                transaction.commit();
//            }
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//    }
    
    public RutaPaquete obtenerRutaPaquete(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        RutaPaquete rutaPaquete = null;
        System.out.println("Busco sim pauqete "+id);
        try {
            RutaPaqueteJPA rutaPaqueteJPA = em.find(RutaPaqueteJPA.class, id);
            if (rutaPaqueteJPA != null) {
                rutaPaquete = new RutaPaquete(rutaPaqueteJPA.getTipoAsiento(), rutaPaqueteJPA.getCantidad());
                ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance(); 
                rutaPaquete.setRutaDeVuelo(mrv.obtenerRutaDeVuelo(rutaPaqueteJPA.getNombreRuta()));
                rutaPaquete.setPaquete(obtenerPaquete(rutaPaqueteJPA.getNombrePaquete()));
            }
        } finally {
            em.close();
        }
        
        return rutaPaquete;
    }
    
    public RutaPaquete obtenerRutaPaquete(Long id, Paquete p) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        RutaPaquete rutaPaquete = null;
        
        System.out.println("Busco conpauqete "+id+" "+p.getNombre());
        
        try {
        	System.out.println("Id: "+id);
        	System.out.println("paq: "+p.getNombre());
            RutaPaqueteJPA rutaPaqueteJPA = em.find(RutaPaqueteJPA.class, id);
            if (rutaPaqueteJPA != null) {
                rutaPaquete = new RutaPaquete(rutaPaqueteJPA.getTipoAsiento(), rutaPaqueteJPA.getCantidad());
                ManejadorRutaDeVuelo mrv = ManejadorRutaDeVuelo.getInstance(); 
                rutaPaquete.setRutaDeVuelo(mrv.obtenerRutaDeVuelo(rutaPaqueteJPA.getNombreRuta()));
                rutaPaquete.setPaquete(p);
                rutaPaquete.setId(id);
            }
        } finally {
            em.close();
        }
        
        return rutaPaquete;
    }


}
