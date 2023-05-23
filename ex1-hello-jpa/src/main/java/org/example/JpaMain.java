package org.example;

import org.example.hellojpa.ExtendsMapping.Item;
import org.example.hellojpa.ExtendsMapping.Movie;
import org.example.hellojpa.Member;
import org.example.hellojpa.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            // 9. 연관관계

//            Team team1 = new Team();
//            team1.setName("TeamA");
//            em.persist(team1);
//
//
//            Team team2 = new Team();
//            team2.setName("TeamB");
//            em.persist(team2);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeam(team1); // 이 때 !! team_id 까지 FK로 지정한다 JPA가.
//            em.persist(member);
//
//            // 양방향일 때 반드시 team에도 넣어줘야 한다.
//            team1.addMember(member);

            // 10. 상속관계
            Movie movie = new Movie();
            movie.setDirector("김감독");
            movie.setActor("권배우");
            movie.setName("6월8일의어느날");
            movie.setPrice(10000);

            em.persist(movie);

            // SQL 문을 직접 보고 싶을 때 사용.
            em.flush(); // 영속성 컨텍스트 밀어넣기
            em.clear(); // 영속성 스태이징 비우기 => 빈영속 상태


            // 상속관계
            Movie findMoive = em.find(Movie.class, movie.getId());
            System.out.println("findMoive = " + findMoive);

            // 연관관계 등록(단방향) 및 수정
//            Member member1 = em.find(Member.class, member.getId()); // 1차 캐시 상태란 말이다. => 양쪽 세팅해야 보임 add
//            member1.setTeam(team2); // 연관관계 수정
//            Team findteam = member1.getTeam();
//            System.out.println("findteam = " + findteam.getName());

            // 연관관계 주인 양방향 연결
//            Member member1 = em.find(Member.class, member.getId()); // 1차 캐시 상태란 말이다. => 양쪽 세팅햐야 보임 add
//            List<Member> members = member1.getTeam().getMembers();
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }

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
