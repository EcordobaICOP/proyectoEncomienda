/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logistica.entidad.Viaje;
import logistica.entidad.Paquete;
import logistica.entidad.ViajePaquete;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class ViajePaqueteJpaController implements Serializable {

    public ViajePaqueteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ViajePaqueteJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ViajePaquete viajePaquete) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Viaje viaje = viajePaquete.getViaje();
            if (viaje != null) {
                viaje = em.getReference(viaje.getClass(), viaje.getViajeID());
                viajePaquete.setViaje(viaje);
            }
            Paquete paquete = viajePaquete.getPaquete();
            if (paquete != null) {
                paquete = em.getReference(paquete.getClass(), paquete.getPaqueteID());
                viajePaquete.setPaquete(paquete);
            }
            em.persist(viajePaquete);
            if (viaje != null) {
                viaje.getViajePaquetes().add(viajePaquete);
                viaje = em.merge(viaje);
            }
            if (paquete != null) {
                paquete.getViajes().add(viajePaquete);
                paquete = em.merge(paquete);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ViajePaquete viajePaquete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ViajePaquete persistentViajePaquete = em.find(ViajePaquete.class, viajePaquete.getViajePaqueteID());
            Viaje viajeOld = persistentViajePaquete.getViaje();
            Viaje viajeNew = viajePaquete.getViaje();
            Paquete paqueteOld = persistentViajePaquete.getPaquete();
            Paquete paqueteNew = viajePaquete.getPaquete();
            if (viajeNew != null) {
                viajeNew = em.getReference(viajeNew.getClass(), viajeNew.getViajeID());
                viajePaquete.setViaje(viajeNew);
            }
            if (paqueteNew != null) {
                paqueteNew = em.getReference(paqueteNew.getClass(), paqueteNew.getPaqueteID());
                viajePaquete.setPaquete(paqueteNew);
            }
            viajePaquete = em.merge(viajePaquete);
            if (viajeOld != null && !viajeOld.equals(viajeNew)) {
                viajeOld.getViajePaquetes().remove(viajePaquete);
                viajeOld = em.merge(viajeOld);
            }
            if (viajeNew != null && !viajeNew.equals(viajeOld)) {
                viajeNew.getViajePaquetes().add(viajePaquete);
                viajeNew = em.merge(viajeNew);
            }
            if (paqueteOld != null && !paqueteOld.equals(paqueteNew)) {
                paqueteOld.getViajes().remove(viajePaquete);
                paqueteOld = em.merge(paqueteOld);
            }
            if (paqueteNew != null && !paqueteNew.equals(paqueteOld)) {
                paqueteNew.getViajes().add(viajePaquete);
                paqueteNew = em.merge(paqueteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = viajePaquete.getViajePaqueteID();
                if (findViajePaquete(id) == null) {
                    throw new NonexistentEntityException("The viajePaquete with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ViajePaquete viajePaquete;
            try {
                viajePaquete = em.getReference(ViajePaquete.class, id);
                viajePaquete.getViajePaqueteID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The viajePaquete with id " + id + " no longer exists.", enfe);
            }
            Viaje viaje = viajePaquete.getViaje();
            if (viaje != null) {
                viaje.getViajePaquetes().remove(viajePaquete);
                viaje = em.merge(viaje);
            }
            Paquete paquete = viajePaquete.getPaquete();
            if (paquete != null) {
                paquete.getViajes().remove(viajePaquete);
                paquete = em.merge(paquete);
            }
            em.remove(viajePaquete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ViajePaquete> findViajePaqueteEntities() {
        return findViajePaqueteEntities(true, -1, -1);
    }

    public List<ViajePaquete> findViajePaqueteEntities(int maxResults, int firstResult) {
        return findViajePaqueteEntities(false, maxResults, firstResult);
    }

    private List<ViajePaquete> findViajePaqueteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ViajePaquete.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ViajePaquete findViajePaquete(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ViajePaquete.class, id);
        } finally {
            em.close();
        }
    }

    public int getViajePaqueteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ViajePaquete> rt = cq.from(ViajePaquete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
