package org.example.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
        EntityManager em = emf.createEntityManager();
        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        // tx
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // code
            // 1. 회원 등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);
            // 2. 회원 조회
//            Member member = em.find(Member.class, 1L);
//            System.out.println("memberId = " + member.getId());
//            System.out.println("memberName = " + member.getName());
            // 3. 회원 수정
//            Member member = em.find(Member.class, 1L);
//            member.setName("HelloJPA");

            // 4. 회원 조회 ( JPQL = Entity 객체를 대상으로 검색하는 객체 지향 쿼리 = SQL 추상화해서 의존 X )
//            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(2)
//                    .setMaxResults(3)
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member.getName());
//            }
            // 5. 영속성 컨텍스트 = git 스테이징과 같다, 1차 캐시와 같다 => 비영속이 "모두" 끝나고 영속성 코드가 랜더링된다.
            // entity , SQL 전부 스테이징 된다.
//            // 비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("HelloJPA");
//
//            // 영속 EntityManager
//            System.out.println(" == BEFORN == ");
//            em.persist(member);
//            System.out.println(" == AFTER == ");
//            Member member1 = em.find(Member.class, 100L);
//            System.out.println("member1.getId() = " + member1.getId());
//            System.out.println("member1.getName() = " + member1.getName());
            // 6. 영속성 2 => 1차캐시 => 쓰기지연, 지연로딩 => 동일성 보장
//            Member member1 = em.find(Member.class, 100L); // DB에서 1차 캐시로 꺼내오고 ( 비영속 먼저하고 )
//            Member member2 = em.find(Member.class, 100L); // 1차 캐시에서 바로 꺼낸다. 먼저 들른다는 얘기지.
//            System.out.println("member1 = " + member1);
//            System.out.println("member2 = " + member2);
//            System.out.println(member1 == member2); // 영속 엔티티의 동일성 보장 true
            // 7. 영속성 변경 감지
//            Member member = em.find(Member.class, 100L);
//            member.setName("HiJPA"); // 끝.
            // 8. 영속성 삭제
//            Member member1 = em.find(Member.class, 100L);
//            em.remove(member1);
            // code
            // tx
            tx.commit(); // (SQL 필요한거 싹다 보낸다.)
        } catch (Exception e) {
            // tx
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
