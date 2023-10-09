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
import logistica.entidad.EmpleadoViaje;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class EmpleadoViajeJpaController implements Serializable {

    public EmpleadoViajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EmpleadoViajeJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpleadoViaje empleadoViaje) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(empleadoViaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpleadoViaje empleadoViaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            empleadoViaje = em.merge(empleadoViaje);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = empleadoViaje.getEmpleadoViajeID();
                if (findEmpleadoViaje(id) == null) {
                    throw new NonexistentEntityException("The empleadoViaje with id " + id + " no longer exists.");
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
            EmpleadoViaje empleadoViaje;
            try {
                empleadoViaje = em.getReference(EmpleadoViaje.class, id);
                empleadoViaje.getEmpleadoViajeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleadoViaje with id " + id + " no longer exists.", enfe);
            }
            em.remove(empleadoViaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpleadoViaje> findEmpleadoViajeEntities() {
        return findEmpleadoViajeEntities(true, -1, -1);
    }

    public List<EmpleadoViaje> findEmpleadoViajeEntities(int maxResults, int firstResult) {
        return findEmpleadoViajeEntities(false, maxResults, firstResult);
    }

    private List<EmpleadoViaje> findEmpleadoViajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpleadoViaje.class));
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

    public EmpleadoViaje findEmpleadoViaje(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpleadoViaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoViajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpleadoViaje> rt = cq.from(EmpleadoViaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
