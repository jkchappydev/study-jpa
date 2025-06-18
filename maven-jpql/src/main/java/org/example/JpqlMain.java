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
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // ==== 기본 CASE 식 ====
            String query1 = "select " +
                    "case when m.age <= 10 then '학생요금' " +
                    "when m.age >= 60 then '경로요금' " +
                    "else '일반요금' end " +
                    "from Member m";
            List<String> result1 = em.createQuery(query1).getResultList();

            // ==== 단순 CASE 식 ====
            String query2 = "select " +
                    "case t.name " +
                    "when '팀A' then '인센티브110%' " +
                    "when '팀B' then '인센티브120%' " +
                    "else '인센티브105%' end " +
                    "from Team t";
            List<String> result2 = em.createQuery(query2).getResultList();

            // ==== COALESCE ====
            String query3 = "select coalesce(m.username, '이름 없는 회원') from Member m"; // 사용자 이름이 없으면 '이름 없는 회원'을 반환
            List<String> result3 = em.createQuery(query3).getResultList();

            // ==== NULLIF ====
            String query4 = "select nullif(m.username, '관리자') from Member m"; // 사용자 이름이 ‘관리자’면 null을 반환하고 나머지는 본인의 이름을 반환
            List<String> result4 = em.createQuery(query4).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
