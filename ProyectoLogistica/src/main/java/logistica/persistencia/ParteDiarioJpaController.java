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
import logistica.entidad.ParteDiario;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class ParteDiarioJpaController implements Serializable {

    public ParteDiarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ParteDiarioJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParteDiario parteDiario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(parteDiario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParteDiario parteDiario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            parteDiario = em.merge(parteDiario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = parteDiario.getParteDiarioID();
                if (findParteDiario(id) == null) {
                    throw new NonexistentEntityException("The parteDiario with id " + id + " no longer exists.");
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
            ParteDiario parteDiario;
            try {
                parteDiario = em.getReference(ParteDiario.class, id);
                parteDiario.getParteDiarioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parteDiario with id " + id + " no longer exists.", enfe);
            }
            em.remove(parteDiario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParteDiario> findParteDiarioEntities() {
        return findParteDiarioEntities(true, -1, -1);
    }

    public List<ParteDiario> findParteDiarioEntities(int maxResults, int firstResult) {
        return findParteDiarioEntities(false, maxResults, firstResult);
    }

    private List<ParteDiario> findParteDiarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParteDiario.class));
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

    public ParteDiario findParteDiario(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParteDiario.class, id);
        } finally {
            em.close();
        }
    }

    public int getParteDiarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParteDiario> rt = cq.from(ParteDiario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
