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
import logistica.entidad.Marca;
import logistica.entidad.Vehiculo;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class VehiculoJpaController implements Serializable {

    public VehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public VehiculoJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculo vehiculo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marca = vehiculo.getMarca();
            if (marca != null) {
                marca = em.getReference(marca.getClass(), marca.getMarcaID());
                vehiculo.setMarca(marca);
            }
            em.persist(vehiculo);
            if (marca != null) {
                marca.getVehiculos().add(vehiculo);
                marca = em.merge(marca);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehiculo vehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo persistentVehiculo = em.find(Vehiculo.class, vehiculo.getVehiculoID());
            Marca marcaOld = persistentVehiculo.getMarca();
            Marca marcaNew = vehiculo.getMarca();
            if (marcaNew != null) {
                marcaNew = em.getReference(marcaNew.getClass(), marcaNew.getMarcaID());
                vehiculo.setMarca(marcaNew);
            }
            vehiculo = em.merge(vehiculo);
            if (marcaOld != null && !marcaOld.equals(marcaNew)) {
                marcaOld.getVehiculos().remove(vehiculo);
                marcaOld = em.merge(marcaOld);
            }
            if (marcaNew != null && !marcaNew.equals(marcaOld)) {
                marcaNew.getVehiculos().add(vehiculo);
                marcaNew = em.merge(marcaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vehiculo.getVehiculoID();
                if (findVehiculo(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
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
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getVehiculoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            Marca marca = vehiculo.getMarca();
            if (marca != null) {
                marca.getVehiculos().remove(vehiculo);
                marca = em.merge(marca);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> findVehiculoEntities() {
        return findVehiculoEntities(true, -1, -1);
    }

    public List<Vehiculo> findVehiculoEntities(int maxResults, int firstResult) {
        return findVehiculoEntities(false, maxResults, firstResult);
    }

    private List<Vehiculo> findVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculo.class));
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

    public Vehiculo findVehiculo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculo> rt = cq.from(Vehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}