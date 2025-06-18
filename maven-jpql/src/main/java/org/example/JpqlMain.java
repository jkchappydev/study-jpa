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

            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            // ==== JPQL 기본 함수 ====
            String query1 = "select concat('a', 'b') from Member m";
            // "select 'a' || 'b' from Member m"; <- 하이버네이트 구현체
            List<String> result1 = em.createQuery(query1, String.class).getResultList();

            String query2 = "select substring(m.username, 2, 3) from Member m";
            List<String> result2 = em.createQuery(query2, String.class).getResultList();

            String query3 = "select locate('de', 'abcdefg') from Member m"; // 4
            List<Integer> result3 = em.createQuery(query3, Integer.class).getResultList();

            String query4 = "select size(t.members) from Team t"; // 컬렉션의 크기를 돌려줌
            List<Integer> result4 = em.createQuery(query4, Integer.class).getResultList();

            // @OrderColumn에 대해서
            // @OneToMany(mappedBy = "team")
            // @OrderColumn(name = "member_order")
            // private List<Member> members = new ArrayList<>();
            // String query5 = "select index(t.members) from Team t";
            // List<Integer> result5 = em.createQuery(query5, Integer.class).getResultList();

            // ==== 사용자 정의 함수 ====
            String query6 = "select function('group_concat', m.username) from Member m";
            // "select group_concat(m.username) from Member m"; <- 하이버네이트 구현체
            List<String> result6 = em.createQuery(query6, String.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
