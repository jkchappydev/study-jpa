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

            // ==== NamedQuery ====
            List<Member> result = em.createNamedQuery("Member.findByUsername", Member.class)
                            .setParameter("username", "회원1")
                            .getResultList();

            for (Member member : result) {
                System.out.println(member);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
