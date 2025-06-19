import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("maven-jpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // ==== TYPE ====
            String query = "select i from Item i where type(i) IN (Book, Movie)";
            List<Item> items = em.createQuery(query, Item.class).getResultList();

            // ==== TREAT ====
            String query2 = "select i from Item i where treat(i as Book) IN (Book, Movie)";
            List<Item> items2 = em.createQuery(query2, Item.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}