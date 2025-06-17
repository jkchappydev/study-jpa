import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("maven-jpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 1. ==== JPQL ====
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

            // 2. ==== Criteria ====
            // Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            // 루트 클래스 (조회를 시작할 클래스)
            Root<Member> m = query.from(Member.class);

            // 쿼리 생성
            CriteriaQuery<Member> cq = query.select(m);

            String username = "asdf";
            if(username != null) { // 동적 쿼리 작성에 유리
                cq = cq.where(cb.equal(m.get("name"), username));
            }

            em.createQuery(cq).getResultList();

            // 3. ==== QueryDSL ====
            // 사용을 위해서는 QMember 생성 및 설정 필요 (예: JPAQueryFactory 사용)
            // 예시:
            // JPAQueryFactory query = new JPAQueryFactory(em);
            // QMember member = QMember.member;
            // List<Member> list = query
            //     .selectFrom(member)
            //     .where(member.username.eq(username))
            //     .fetch();

            // 4. ==== 네이티브 SQL ====
            em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER")
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