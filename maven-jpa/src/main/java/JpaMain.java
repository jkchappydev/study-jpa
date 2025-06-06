import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

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
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            // member.setTeamId(team.getId());
            member.setTeam(team); // 바로 가능
            em.persist(member);

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            // Team findTeam = em.find(Team.class, findMember.getTeamId());
            Team findTeam = findMember.getTeam(); // 바로 가능
            System.out.println(findTeam.getName());

            // 수정
            Team newTeam = em.find(Team.class, 100L);
            newTeam.setName("TeamA1");
            findMember.setTeam(newTeam);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}