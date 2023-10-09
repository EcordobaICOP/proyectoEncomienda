/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logistica.entidad.ViajePaquete;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logistica.entidad.Viaje;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class ViajeJpaController implements Serializable {

    public ViajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ViajeJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Viaje viaje) {
        if (viaje.getViajePaquetes() == null) {
            viaje.setViajePaquetes(new ArrayList<ViajePaquete>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ViajePaquete> attachedViajePaquetes = new ArrayList<ViajePaquete>();
            for (ViajePaquete viajePaquetesViajePaqueteToAttach : viaje.getViajePaquetes()) {
                viajePaquetesViajePaqueteToAttach = em.getReference(viajePaquetesViajePaqueteToAttach.getClass(), viajePaquetesViajePaqueteToAttach.getViajePaqueteID());
                attachedViajePaquetes.add(viajePaquetesViajePaqueteToAttach);
            }
            viaje.setViajePaquetes(attachedViajePaquetes);
            em.persist(viaje);
            for (ViajePaquete viajePaquetesViajePaquete : viaje.getViajePaquetes()) {
                Viaje oldViajeOfViajePaquetesViajePaquete = viajePaquetesViajePaquete.getViaje();
                viajePaquetesViajePaquete.setViaje(viaje);
                viajePaquetesViajePaquete = em.merge(viajePaquetesViajePaquete);
                if (oldViajeOfViajePaquetesViajePaquete != null) {
                    oldViajeOfViajePaquetesViajePaquete.getViajePaquetes().remove(viajePaquetesViajePaquete);
                    oldViajeOfViajePaquetesViajePaquete = em.merge(oldViajeOfViajePaquetesViajePaquete);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Viaje viaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Viaje persistentViaje = em.find(Viaje.class, viaje.getViajeID());
            List<ViajePaquete> viajePaquetesOld = persistentViaje.getViajePaquetes();
            List<ViajePaquete> viajePaquetesNew = viaje.getViajePaquetes();
            List<ViajePaquete> attachedViajePaquetesNew = new ArrayList<ViajePaquete>();
            for (ViajePaquete viajePaquetesNewViajePaqueteToAttach : viajePaquetesNew) {
                viajePaquetesNewViajePaqueteToAttach = em.getReference(viajePaquetesNewViajePaqueteToAttach.getClass(), viajePaquetesNewViajePaqueteToAttach.getViajePaqueteID());
                attachedViajePaquetesNew.add(viajePaquetesNewViajePaqueteToAttach);
            }
            viajePaquetesNew = attachedViajePaquetesNew;
            viaje.setViajePaquetes(viajePaquetesNew);
            viaje = em.merge(viaje);
            for (ViajePaquete viajePaquetesOldViajePaquete : viajePaquetesOld) {
                if (!viajePaquetesNew.contains(viajePaquetesOldViajePaquete)) {
                    viajePaquetesOldViajePaquete.setViaje(null);
                    viajePaquetesOldViajePaquete = em.merge(viajePaquetesOldViajePaquete);
                }
            }
            for (ViajePaquete viajePaquetesNewViajePaquete : viajePaquetesNew) {
                if (!viajePaquetesOld.contains(viajePaquetesNewViajePaquete)) {
                    Viaje oldViajeOfViajePaquetesNewViajePaquete = viajePaquetesNewViajePaquete.getViaje();
                    viajePaquetesNewViajePaquete.setViaje(viaje);
                    viajePaquetesNewViajePaquete = em.merge(viajePaquetesNewViajePaquete);
                    if (oldViajeOfViajePaquetesNewViajePaquete != null && !oldViajeOfViajePaquetesNewViajePaquete.equals(viaje)) {
                        oldViajeOfViajePaquetesNewViajePaquete.getViajePaquetes().remove(viajePaquetesNewViajePaquete);
                        oldViajeOfViajePaquetesNewViajePaquete = em.merge(oldViajeOfViajePaquetesNewViajePaquete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = viaje.getViajeID();
                if (findViaje(id) == null) {
                    throw new NonexistentEntityException("The viaje with id " + id + " no longer exists.");
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
            Viaje viaje;
            try {
                viaje = em.getReference(Viaje.class, id);
                viaje.getViajeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The viaje with id " + id + " no longer exists.", enfe);
            }
            List<ViajePaquete> viajePaquetes = viaje.getViajePaquetes();
            for (ViajePaquete viajePaquetesViajePaquete : viajePaquetes) {
                viajePaquetesViajePaquete.setViaje(null);
                viajePaquetesViajePaquete = em.merge(viajePaquetesViajePaquete);
            }
            em.remove(viaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Viaje> findViajeEntities() {
        return findViajeEntities(true, -1, -1);
    }

    public List<Viaje> findViajeEntities(int maxResults, int firstResult) {
        return findViajeEntities(false, maxResults, firstResult);
    }

    private List<Viaje> findViajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Viaje.class));
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

    public Viaje findViaje(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Viaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getViajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Viaje> rt = cq.from(Viaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
