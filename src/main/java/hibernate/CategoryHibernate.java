package hibernate;

import model.Category;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.util.List;

public class CategoryHibernate {
  EntityManagerFactory emf = null;

  public CategoryHibernate(EntityManagerFactory entityManagerFactory) {
    this.emf = entityManagerFactory;
  }

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Category category) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      em.persist(category);
      em.getTransaction().commit();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Category category) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      em.flush();
      category = em.merge(category);
      em.getTransaction().commit();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void remove(int id) throws Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Category category;
      try {
        category = em.find(Category.class, id);
        category.getCategory_ID();
        for (User u : category.getResponsibleUsers()) {
          for (Category s : category.getSubCategories()) {
            u.removeCategory(s);
          }
          u.removeCategory(category);
        }

        em.merge(category);
      } catch (Exception e) {
        throw new Exception("The category with id" + id + "no longer exists", e);
      }
      em.remove(category);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void AddResponsibleUserToCategory(Category category, User user) throws Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      try {
        category.addUser(user);
        user.addCategory(category);
        em.merge(user);
        em.merge(category);
        em.flush();
        category.getResponsibleUsers().clear();
        user.getResponsibleCategories().clear();
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

  public void RemoveResponsibleUserToCategory(Category category, User user) throws Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      try {
        category.removeUser(user);
        user.removeCategory(category);
        em.merge(user);
        em.merge(category);
        em.flush();
        category.getResponsibleUsers().clear();
        user.getResponsibleCategories().clear();
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

  public List<Category> getCategoryList() {
    return getCategoryList(true, -1, -1);
  }

  public List<Category> getCategoryList(boolean all, int maxRes, int firstRes) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
      criteriaQuery.select(criteriaQuery.from(Category.class));
      Query query = em.createQuery(criteriaQuery);

      if (!all) {
        query.setMaxResults(maxRes);
        query.setFirstResult(firstRes);
      }
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

  public List<Category> getFilteredParentCategoryList(String parent) {
    EntityManager em = getEntityManager();
    try {
      Query query =
          em.createQuery("FROM Category WHERE parent = :parent").setParameter("parent", parent);

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
  public List<Category> getFilteredDateCategoryList(LocalDate date) {
    EntityManager em = getEntityManager();
    try {
      Query query =
          em.createQuery("FROM Category WHERE dateCreated >= :date").setParameter("date", date);

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

  public List<Category> getFilteredCategoryList(String parent, LocalDate date) {
    EntityManager em = getEntityManager();
    try {
      Query query =
          em.createQuery("FROM Category WHERE parent = :parent AND dateCreated >= :date")
              .setParameter("parent", parent)
              .setParameter("date", date);

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

  public Category findCategory(int id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Category.class, id);
    } finally {
      em.close();
    }
  }

}
