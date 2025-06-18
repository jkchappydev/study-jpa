package org.example;

import org.example.jpql.*;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("maven-jpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA"); // 일부러 맞춤 (연관관계 없는 엔티티 외부 조인)
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // ==== Enum 조심 ====
            String query1 = "select m.username, 'HELLO', TRUE from Member m where m.type = org.example.jpql.MemberType.ADMIN";
            List<Object[]> result1 = em.createQuery(query1).getResultList();

            String query2 = "select m.username, 'HELLO', TRUE from Member m where m.type = :userType"; // 파라미터 바인딩
            List<Object[]> result2 = em.createQuery(query2)
                            .setParameter("userType", MemberType.ADMIN)
                            .getResultList();

            // ITEM - BOOK 상속관계
            /*String query3 = "select i from Item i where type(i) = Book";
            em.createQuery(query3, Item.class).getResultList();*/

            String query4 = "select m.username, 'HELLO', TRUE from Member m where m.username is not null";
            List<Object[]> result4 = em.createQuery(query4).getResultList();

            String query5 = "select m.username, 'HELLO', TRUE from Member m where m.age between 0 and 10";
            List<Object[]> result5 = em.createQuery(query5).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
