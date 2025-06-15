import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("maven-jpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setName("John Doe");
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("kim");
            em.persist(member2);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            // 프록시 클래스 확인
            System.out.println("refMember: " + refMember.getClass()); // refMember: class jpabook.jpashop.domain.Member$HibernateProxy$7XXfGFhF

            // 프록시 인스턴스 초기화 여부 확인
            System.out.println("isLoaded=" + emf.getPersistenceUnitUtil().isLoaded(refMember)); // isLoaded=false
            // refMember.getName(); // 해당 방법으로도 프록시 초기화가 되긴 하지만 굉장히 보기 좋지 않음 방법
            Hibernate.initialize(refMember); // Hibernate에서 제공하는 강제 프록시 초기화
            System.out.println("isLoaded=" + emf.getPersistenceUnitUtil().isLoaded(refMember)); // isLoaded=true


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace(); // 예외 처리 확인
        } finally {
            em.close();
        }

        emf.close();
    }
}