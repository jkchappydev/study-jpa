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
            Team team1 = new Team();
            team1.setName("팀A");
            em.persist(team1);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.changeTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.changeTeam(team1);
            em.persist(member2);

            Team team2 = new Team();
            team2.setName("팀B");
            em.persist(team2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.changeTeam(team2);
            em.persist(member3);

            Team team3 = new Team();
            team3.setName("팀C");
            em.persist(team3);

            Member member4 = new Member();
            member4.setUsername("회원4");
            em.persist(member4);

            em.flush();
            em.clear();

            // ==== 엔티티 직접 사용 - 기본키 값 ====
            // 1. 엔티티를 파라미터로 전달
            String query = "select m from Member m where m = :member";
            Member findMember = em.createQuery(query, Member.class)
                    .setParameter("member", member1)
                    .getSingleResult();

            // 2. 식별자를 직접 전달
            String query2 = "select m from Member m where m.id = :memberId";
            Long memberId = member1.getId();
            Member findMember2 = em.createQuery(query2, Member.class)
                    .setParameter("memberId", memberId)
                    .getSingleResult();

            // ==== 엔티티 직접 사용 - 외래키 값 ====
            // 1. 엔티티를 파라미터로 전달
            String query3 = "select m from Member m where m.team = :team";
            List<Member> findMember3 = em.createQuery(query3, Member.class)
                    .setParameter("team", team1)
                    .getResultList();

            // 2. 식별자를 직접 전달
            String query4 = "select m from Member m where m.team.id = :teamId";
            Long teamId = team1.getId();
            List<Member> findMember4 = em.createQuery(query4, Member.class)
                    .setParameter("teamId", teamId)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
