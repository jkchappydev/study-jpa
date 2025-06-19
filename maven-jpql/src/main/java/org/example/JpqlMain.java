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
            member1.changeTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.changeTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            // ==== 상태필드 (m.username 이후로 더 탐색할 수 없음) ====
            String query1 = "select m.username from Member m";

            // 실무에서 사용 금지
            // ==== 단일 값 연관 경로 (m.team 이후로 탐색 가능 (ex: m.team.name)) ====
            String query2 = "select m.team from Member m"; // 묵시적 내부 조인 발생 (Member에 연관된 Team을 가져와야 하기 때문에)
            List<Team> result2 = em.createQuery(query2, Team.class).getResultList();

            // ==== 컬렉션 값 연관 경로 ====
            String query3 = "select t.members.size from Team t"; // t.members = @OneToMany
            Integer result3 = em.createQuery(query3, Integer.class).getSingleResult();

            String query4 = "select m.username from Team t join t.members m"; // t.members의 username을 가져오고 싶으면, 명시적으로 조인
            List<String> result4 = em.createQuery(query4, String.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
