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
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // ==== 내부 조인 ====
            String query1 = "select m from Member m inner join m.team t"; // inner 생략 가능
            List<Member> result1 = em.createQuery(query1, Member.class).getResultList();

            // ==== 외부 조인 ====
            String query2 = "select m from Member m left outer join m.team t"; // outer 생략 가능
            List<Member> result2 = em.createQuery(query2, Member.class).getResultList();

            // ==== 세타 조인 ====
            String query3 = "select m from Member m, Team t where m.username = t.name"; // cross join
            List<Member> result3 = em.createQuery(query3, Member.class).getResultList();

            // ==== ON 절 (조인 대상 필터링) ====
            String query4 = "select m from Member m left join m.team t on t.name = 'teamA'";
            List<Member> result4 = em.createQuery(query4, Member.class).getResultList();

            // ==== ON 절 (연관관계 없는 엔티티 외부 조인) ====
            String query5 = "select m from Member m left join Team t on m.username = t.name"; // member의 username와 team의 name은 연관관계가 없음
            List<Member> result5 = em.createQuery(query5, Member.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
