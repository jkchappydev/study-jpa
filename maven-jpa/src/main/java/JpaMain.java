import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;
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
            Team team1 = new Team();
            team1.setName("Team 1");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("Team 2");
            em.persist(team2);

            Member member = new Member();
            member.setName("John Doe");
            member.setTeam(team1);
            em.persist(member);

            Member member2 = new Member();
            member2.setName("John Doe");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            // fetct 조인 (현재 Team에 지연 로딩 설정 적용)
            em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();

            // 현재 Team에 지연 로딩 설정 적용
//            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList(); // 여기까진 쿼리 1번 (Member만 가져옴)
//
//            for (Member m : members) {
//                m.getTeam().getName(); // Team을 지연로딩 → 매번 쿼리 발생 (N번)
//            }
            // 총 쿼리 수: 1 + N

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