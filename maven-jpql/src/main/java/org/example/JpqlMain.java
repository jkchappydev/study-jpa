package org.example;

import org.example.jpql.Member;

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

            // 두 번째 파라미터는 응답 클래스에 대해서 타입 정보를 줄 수 있는데, 엔티티가 아닌것을 줄 수도 있지만(프로젝션) 기본적으로 엔티티
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class); // 반환 타입 정보가 명확할 때
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m"); // username은 String, age는 int라서 반환 타입이 명확하지 않음

            // 결과 조회 API
            TypedQuery<Member> query4 = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query4.getResultList(); // 결과가 없으면 빈 리스트 반환

            TypedQuery<Member> query5 = em.createQuery("select m from Member m where m.id = 10", Member.class);
            Member result = query5.getSingleResult(); // 결과가 정확하게 하나가 나와야함 (아니면 Exception)
            // + Spring Data JPA를 사용하면 -> 결과가 없으면 null 반환
            // 아니면 try ~ catch 사용

            // 파라미터 바인딩 - 이름 기준
            Member result2 = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("result2 = " + result2.getUsername());

            // 파라미터 바인딩 - 위치 기준 (사용X)
            Member result3 = em.createQuery("select m from Member m where m.username = ?1", Member.class)
                    .setParameter(1, "member1")
                    .getSingleResult();
            System.out.println("result3 = " + result3.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
