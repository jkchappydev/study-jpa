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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // ==== 엔티티 프로젝션 ====
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            Member findMember = result.get(0);
            findMember.setAge(20); // 해당 변경 사항이 반영됨 (엔티티 프로젝션은 영속성 컨텍스트에서 관리한다.)

            List<Team> result2 = em.createQuery("select m.team from Member m", Team.class).getResultList(); // 해당 방식보다
            List<Team> result3 = em.createQuery("select t from Member m join m.team t", Team.class).getResultList(); // 이 방식이 더 보기 좋다.

            // ==== 임베디드 타입 프로젝션 ====
            List<Address> result4 = em.createQuery("select o.address from Order o", Address.class).getResultList();

            // ==== 스칼라 타입 프로젝션 ====
            em.createQuery("select m.username, m.age from Member m").getResultList();
            // 1. Query 타입으로 조회
            List result5 = em.createQuery("select m.username, m.age from Member m").getResultList();
            Object o = result5.get(0);
            Object[] res1 = (Object[]) o;
            System.out.println("username: " + res1[0]);
            System.out.println("age: " + res1[1]);
            // 2. Object[] 타입으로 조회
            List<Object[]> result6 = em.createQuery("select m.username, m.age from Member m").getResultList();
            Object[] res2 = (Object[]) result6.get(0);
            System.out.println("username: " + res2[0]);
            System.out.println("age: " + res2[1]);
            // 3. new 명령어로 조회 (제일 권장)
            List<MemberDto> result7 = em.createQuery("select new org.example.jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class).getResultList(); // 패키지 경로는 QueryDSL로 극복 가능
            System.out.println("username: " + result7.get(0).getUsername());
            System.out.println("age: " + result7.get(0).getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
