package hibernate;

import model.Category;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserHibernate {
    EntityManagerFactory emf = null;

    public UserHibernate(EntityManagerFactory entityManagerFactory)
    {
        this.emf = entityManagerFactory;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(User user){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally{
            if(em != null)
            {
                em.close();
            }
        }
    }

    public void edit(User user)
    {
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally{
            if(em != null)
            {
                em.close();
            }
        }
    }

    public void remove(int id)
    {
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            User user = null;

            try{
                user = em.getReference(User.class, id);
                user.getUser_ID();
                for(Category c: user.getResponsibleCategories())
                {
                    c.removeUser(user);
                }
                em.merge(user);
            }catch(Exception e){
                e.printStackTrace();
            }
            em.remove(user);
            em.getTransaction().commit();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally{
            if(em != null)
            {
                em.close();
            }
        }
    }

    public List<User> getUserList(){
        return getUserList(true,-1,-1);
    }

    public List<User> getUserList(boolean all,int maxRes,int firstRes){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
            Query query = em.createQuery(criteriaQuery);

            if(!all)
            {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            if(em != null)
            {
                em.close();
            }
        }
        return null;
    }

    public List<User> getFilteredTypeUserList(String type) {
        EntityManager em = getEntityManager();
        try {
            Query query =
                    em.createQuery("FROM User WHERE type = :type").setParameter("type", type);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public User findUser(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

}
