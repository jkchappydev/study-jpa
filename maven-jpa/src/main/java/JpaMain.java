import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("maven-jpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "10000");

            Member2 member = new Member2();
            member.setName("John Doe");
            member.setHomeAddress(address);
            em.persist(member);

            // Address를 복사한다.
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipCode());

            Member2 member2 = new Member2();
            member2.setName("Kim");
            member2.setHomeAddress(copyAddress); // member2에 복사한 copyAddress를 사용한다.
            em.persist(member2);

            // member의 city만 변경된다.
            // member.getHomeAddress().setCity("newCity");

            // setter를 사용하지 않고 생성자를 통해서 변경한다.
            Address newAddress = new Address("newCity", address.getStreet(), address.getZipCode());
            member.setHomeAddress(newAddress);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}