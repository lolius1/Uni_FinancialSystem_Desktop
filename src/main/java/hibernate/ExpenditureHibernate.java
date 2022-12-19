package hibernate;

import model.Category;
import model.Expenditure;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

public class ExpenditureHibernate {
    EntityManagerFactory emf = null;
    public ExpenditureHibernate(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Expenditure expenditure) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(expenditure);
            em.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Expenditure expenditure) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            expenditure = em.merge(expenditure);
            em.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(int id) throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Expenditure expenditure;
            try {
                expenditure = em.find(Expenditure.class, id);
                expenditure.getExpenditure_ID();

                em.merge(expenditure);
            } catch (Exception e) {
                throw new Exception("The category with id" + id + "no longer exists",e);
            }
            em.remove(expenditure);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void AddExpenditureToCategory(Category category, Expenditure expenditure) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                category.addExpenditure(expenditure);
                expenditure.addCategory(category);
                em.merge(expenditure);
                em.merge(category);
                em.flush();
                category.getExpenditure().clear();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("Error adding category", enfe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
