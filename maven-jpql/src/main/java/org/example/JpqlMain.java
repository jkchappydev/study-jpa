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

            // ==== 서브 쿼리 ====
            String query1 = "select m from Member m where m.age > (select avg(m2.age) from Member m2)"; // 나이가 평균보다 많은 회원
            List<Member> result1 = em.createQuery(query1, Member.class).getResultList();

            String query2 = "select m from Member m where exists (select t from m.team t where t.name = 'teamA')"; // 팀 A 소속인 회원
            List<Member> result2 = em.createQuery(query2, Member.class).getResultList();
            System.out.println(result2);

            String query3 = "select o from Order o where o.orderAmount > all (select p.stockAmount from Product p)"; // 전체 상품 각각의 재고보다 주문량이 많은 주문들
            List<Order> result3 = em.createQuery(query3, Order.class).getResultList();

            String query4 = "select m from Member m where m.team = any (select t from Team t)"; // 어떤 팀이든 팀에 소속된 회원
            List<Member> result4 = em.createQuery(query4, Member.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
