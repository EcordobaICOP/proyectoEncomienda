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
import logistica.entidad.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logistica.entidad.Marca;
import logistica.persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author ULTRA
 */
public class MarcaJpaController implements Serializable {

    public MarcaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public MarcaJpaController(){
        this.emf = Persistence.createEntityManagerFactory("proyectoLogisticaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        if (marca.getVehiculos() == null) {
            marca.setVehiculos(new ArrayList<Vehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vehiculo> attachedVehiculos = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculosVehiculoToAttach : marca.getVehiculos()) {
                vehiculosVehiculoToAttach = em.getReference(vehiculosVehiculoToAttach.getClass(), vehiculosVehiculoToAttach.getVehiculoID());
                attachedVehiculos.add(vehiculosVehiculoToAttach);
            }
            marca.setVehiculos(attachedVehiculos);
            em.persist(marca);
            for (Vehiculo vehiculosVehiculo : marca.getVehiculos()) {
                Marca oldMarcaOfVehiculosVehiculo = vehiculosVehiculo.getMarca();
                vehiculosVehiculo.setMarca(marca);
                vehiculosVehiculo = em.merge(vehiculosVehiculo);
                if (oldMarcaOfVehiculosVehiculo != null) {
                    oldMarcaOfVehiculosVehiculo.getVehiculos().remove(vehiculosVehiculo);
                    oldMarcaOfVehiculosVehiculo = em.merge(oldMarcaOfVehiculosVehiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getMarcaID());
            List<Vehiculo> vehiculosOld = persistentMarca.getVehiculos();
            List<Vehiculo> vehiculosNew = marca.getVehiculos();
            List<Vehiculo> attachedVehiculosNew = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculosNewVehiculoToAttach : vehiculosNew) {
                vehiculosNewVehiculoToAttach = em.getReference(vehiculosNewVehiculoToAttach.getClass(), vehiculosNewVehiculoToAttach.getVehiculoID());
                attachedVehiculosNew.add(vehiculosNewVehiculoToAttach);
            }
            vehiculosNew = attachedVehiculosNew;
            marca.setVehiculos(vehiculosNew);
            marca = em.merge(marca);
            for (Vehiculo vehiculosOldVehiculo : vehiculosOld) {
                if (!vehiculosNew.contains(vehiculosOldVehiculo)) {
                    vehiculosOldVehiculo.setMarca(null);
                    vehiculosOldVehiculo = em.merge(vehiculosOldVehiculo);
                }
            }
            for (Vehiculo vehiculosNewVehiculo : vehiculosNew) {
                if (!vehiculosOld.contains(vehiculosNewVehiculo)) {
                    Marca oldMarcaOfVehiculosNewVehiculo = vehiculosNewVehiculo.getMarca();
                    vehiculosNewVehiculo.setMarca(marca);
                    vehiculosNewVehiculo = em.merge(vehiculosNewVehiculo);
                    if (oldMarcaOfVehiculosNewVehiculo != null && !oldMarcaOfVehiculosNewVehiculo.equals(marca)) {
                        oldMarcaOfVehiculosNewVehiculo.getVehiculos().remove(vehiculosNewVehiculo);
                        oldMarcaOfVehiculosNewVehiculo = em.merge(oldMarcaOfVehiculosNewVehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = marca.getMarcaID();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getMarcaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<Vehiculo> vehiculos = marca.getVehiculos();
            for (Vehiculo vehiculosVehiculo : vehiculos) {
                vehiculosVehiculo.setMarca(null);
                vehiculosVehiculo = em.merge(vehiculosVehiculo);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
