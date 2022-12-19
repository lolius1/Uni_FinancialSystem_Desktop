package hibernate;

import model.Expenditure;
import model.Income;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class IncomeHibernate {

    EntityManagerFactory emf = null;
    public IncomeHibernate(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Income income) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(income);
            em.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Income income) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            income = em.merge(income);
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
            Income income;
            try {
                income = em.find(Income.class, id);
                income.getIncome_ID();

                em.merge(income);
            } catch (Exception e) {
                throw new Exception("The category with id" + id + "no longer exists",e);
            }
            em.remove(income);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
