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

            // ==== 엔티티 패치 조인 ====
            // ==== fetch 사용 X ====
            String query = "select m from Member m";
            List<Member> result = em.createQuery(query, Member.class).getResultList();
            for (Member member : result) {
                // 현재, Team team이 FetchType.LAZY 설정되어 있어서 (지연로딩) Team객체는 Proxy로 들어오고, Team을 실제 사용하는 시점(member.getTeam().getName())마다 Team 조회 쿼리가 호출된다.
                // 회원1, 팀A(SQL 조회) <- '팀A'는 영속성 컨텍스트에 없음
                // 회원2, 팀A(영속성 컨텍스트)
                // =======
                // 회원3, 팀B(SQL 조회) <- '팀B'는 영속성 컨텍스트에 없음
                // N+1 문제 발생 (Member 조회 쿼리 1번 + Team 조회 쿼리 (Member 수만큼 Team 조회 쿼리) -> fetch 조인으로 해결
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }

            // ==== fetch 사용 O ====
            String fetchQuery = "select m from Member m inner join fetch m.team";
            List<Member> fetchResult = em.createQuery(fetchQuery, Member.class).getResultList();
            for (Member member : fetchResult) {
                // Member와 Team을 한 번에 가져온다 (inner join으로 가져옴)
                // Team은 FetchType.LAZY로 설정되어 있어도, Proxy를 가져온것이 아닌 진짜 데이터를 가져온다.
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }

            // ==== 컬렉션 패치 조인 ====
            String fetchQuery2 = "select t from Team t inner join fetch t.members";
            List<Team> fetchResult2 = em.createQuery(fetchQuery2, Team.class).getResultList();
            for (Team team : fetchResult2) {
                System.out.println("team = " + team.getName() + ", " + team.getMembers().size());
            }

            // ==== Fetch 조인과 DISTINCT ====
            String fetchQuery3 = "select distinct t from Team t inner join fetch t.members";
            List<Team> fetchResult3 = em.createQuery(fetchQuery3, Team.class).getResultList();
            for (Team team : fetchResult3) {
                System.out.println("team = " + team.getName() + ", " + team.getMembers().size());
            }

            // ==== Fetch 조인과 일반 조인 차이 ====
            // 일반 조인
            String query2 = "select t from Team t join t.members";
            List<Team> result2 = em.createQuery(query2, Team.class).getResultList();

            // Fetch 조인
            String fetchQuery4 = "select t from Team t join fetch t.members";
            List<Team> fetchResult4 = em.createQuery(fetchQuery4, Team.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
