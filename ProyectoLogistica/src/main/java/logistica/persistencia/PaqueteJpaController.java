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
import logistica.entidad.Paquete;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class PaqueteJpaController implements Serializable {

    public PaqueteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PaqueteJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paquete paquete) {
        if (paquete.getViajes() == null) {
            paquete.setViajes(new ArrayList<ViajePaquete>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ViajePaquete> attachedViajes = new ArrayList<ViajePaquete>();
            for (ViajePaquete viajesViajePaqueteToAttach : paquete.getViajes()) {
                viajesViajePaqueteToAttach = em.getReference(viajesViajePaqueteToAttach.getClass(), viajesViajePaqueteToAttach.getViajePaqueteID());
                attachedViajes.add(viajesViajePaqueteToAttach);
            }
            paquete.setViajes(attachedViajes);
            em.persist(paquete);
            for (ViajePaquete viajesViajePaquete : paquete.getViajes()) {
                Paquete oldPaqueteOfViajesViajePaquete = viajesViajePaquete.getPaquete();
                viajesViajePaquete.setPaquete(paquete);
                viajesViajePaquete = em.merge(viajesViajePaquete);
                if (oldPaqueteOfViajesViajePaquete != null) {
                    oldPaqueteOfViajesViajePaquete.getViajes().remove(viajesViajePaquete);
                    oldPaqueteOfViajesViajePaquete = em.merge(oldPaqueteOfViajesViajePaquete);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paquete paquete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paquete persistentPaquete = em.find(Paquete.class, paquete.getPaqueteID());
            List<ViajePaquete> viajesOld = persistentPaquete.getViajes();
            List<ViajePaquete> viajesNew = paquete.getViajes();
            List<ViajePaquete> attachedViajesNew = new ArrayList<ViajePaquete>();
            for (ViajePaquete viajesNewViajePaqueteToAttach : viajesNew) {
                viajesNewViajePaqueteToAttach = em.getReference(viajesNewViajePaqueteToAttach.getClass(), viajesNewViajePaqueteToAttach.getViajePaqueteID());
                attachedViajesNew.add(viajesNewViajePaqueteToAttach);
            }
            viajesNew = attachedViajesNew;
            paquete.setViajes(viajesNew);
            paquete = em.merge(paquete);
            for (ViajePaquete viajesOldViajePaquete : viajesOld) {
                if (!viajesNew.contains(viajesOldViajePaquete)) {
                    viajesOldViajePaquete.setPaquete(null);
                    viajesOldViajePaquete = em.merge(viajesOldViajePaquete);
                }
            }
            for (ViajePaquete viajesNewViajePaquete : viajesNew) {
                if (!viajesOld.contains(viajesNewViajePaquete)) {
                    Paquete oldPaqueteOfViajesNewViajePaquete = viajesNewViajePaquete.getPaquete();
                    viajesNewViajePaquete.setPaquete(paquete);
                    viajesNewViajePaquete = em.merge(viajesNewViajePaquete);
                    if (oldPaqueteOfViajesNewViajePaquete != null && !oldPaqueteOfViajesNewViajePaquete.equals(paquete)) {
                        oldPaqueteOfViajesNewViajePaquete.getViajes().remove(viajesNewViajePaquete);
                        oldPaqueteOfViajesNewViajePaquete = em.merge(oldPaqueteOfViajesNewViajePaquete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = paquete.getPaqueteID();
                if (findPaquete(id) == null) {
                    throw new NonexistentEntityException("The paquete with id " + id + " no longer exists.");
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
            Paquete paquete;
            try {
                paquete = em.getReference(Paquete.class, id);
                paquete.getPaqueteID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paquete with id " + id + " no longer exists.", enfe);
            }
            List<ViajePaquete> viajes = paquete.getViajes();
            for (ViajePaquete viajesViajePaquete : viajes) {
                viajesViajePaquete.setPaquete(null);
                viajesViajePaquete = em.merge(viajesViajePaquete);
            }
            em.remove(paquete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paquete> findPaqueteEntities() {
        return findPaqueteEntities(true, -1, -1);
    }

    public List<Paquete> findPaqueteEntities(int maxResults, int firstResult) {
        return findPaqueteEntities(false, maxResults, firstResult);
    }

    private List<Paquete> findPaqueteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paquete.class));
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

    public Paquete findPaquete(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paquete.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaqueteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paquete> rt = cq.from(Paquete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
