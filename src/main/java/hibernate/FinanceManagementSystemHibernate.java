package hibernate;

import model.FinanceManagementSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

public class FinanceManagementSystemHibernate {

    public FinanceManagementSystemHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FinanceManagementSystem fmis) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fmis);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFinanceManagementSystem(fmis.getName()) != null) {
                throw new Exception("Finance management system " + fmis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FinanceManagementSystem fmis) throws  Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.flush();
            fmis = em.merge(fmis);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String name = fmis.getName();
                if (findFinanceManagementSystem(name) == null) {
                    throw new Exception("The finance management system with name " + fmis + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String name) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FinanceManagementSystem fmis;
            try {
                fmis = em.getReference(FinanceManagementSystem.class, name);
                fmis.getName();
//                library.getSections().clear();
//                library.getAllEmployees().clear();
//                library.getAllReaders().clear();
                em.merge(fmis);
            } catch (EntityNotFoundException enfe) {
                throw new Exception("The library with name " + name + " no longer exists.", enfe);
            }
            em.remove(fmis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public FinanceManagementSystem findFinanceManagementSystem(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FinanceManagementSystem.class, name);
        } finally {
            em.close();
        }
    }
}
