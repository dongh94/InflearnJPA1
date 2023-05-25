package org.example;

import org.example.hellojpa.ExtendsMapping.Item;
import org.example.hellojpa.ExtendsMapping.Movie;
import org.example.hellojpa.Member;
import org.example.hellojpa.Team;
import org.hibernate.Hibernate;

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
//            Movie movie = new Movie();
//            movie.setDirector("김감독");
//            movie.setActor("권배우");
//            movie.setName("6월8일의어느날");
//            movie.setPrice(10000);
//
//            em.persist(movie);
            // 프록시 특징
//            em.getReference()
            // 프록시 초기화할 떄만 DB에 요청 => 초기화 이전과 이후 요청시 DB 쿼리 안날림 (성능개선에 유리)
            // 그렇다고 프록시가 실제와 바뀌는것 아님 !!!
            // 타입 체크시 == 말고 instanceof 를 사용해야 함.
            // 영속성에서 이미 em.find()로 찾은 건 바로 다음 em.getReference()로 해도 둘다 실제다.
            // ? => JPA는 영속성 컨테이너 안에서 == 이 보장되어야 함.
//
//            Member member = new Member();
//            member.setUsername("member");
//            em.persist(member);

            // 지연로딩과 즉시로딩
            // FetchType.LAZY  : FK가 묶인 것끼리 찾는게 덜할 때 : 프록시로 지연로딩해서 초기화X 땐 쿼리 안날림.
            // FetchType.EAGER : FK가 묶인 것끼리 계속 찾을때 : 즉시 모두 로딩
            // 즉시로딩을 사용하면 예상하지 못한 SQL이 나갈 수 있다. 즉 find할 때 연관된거 다 로딩되니까
            // 즉, JPQL에서 1 + N 문제가 일어난다는 의미. createQuery할 때 즉시 로딩되니까
            // 즉 연관된거 MTO, OTO은 기본이 eager이기에 에 모두 FetchType.LAZY 해주고,

            // 그냥 일단 모든 연관관계는 LAZY로 해서 Proxy로 거쳐가라 => 쿼리 안날리니까/
            // 방법 3가지가 있다. 1. FetchJoin으로 필요한 것만 조인.

            // Casecade
            // 단순 영속성 전이 = 단일 소유자만 = 영속성에 껴준다는 의미.

            // 고아객체
            // 단일 소유자만
            // 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 제거.
//            orphanRemoval = ture // 고아 객체 제거하겠다는 의미로 Parent의 OneToMany()에 넣어준다.

            // Casecade 와 orphanRemoval = ture 를 모두 키면 부모 엔티티를 통해 자식 엔티티 생명주기를 결정할 수 있다.


            // SQL 문을 직접 보고 싶을 때 사용.
            em.flush(); // 영속성 컨텍스트 밀어넣기
            em.clear(); // 영속성 스태이징 비우기 => 빈영속 상태
            // 지연로딩과 즉시로딩
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
            // 즉시로딩할 때 연관된거 모두다 가져오게된다.

            // 프록시
//            Member reference = em.getReference(Member.class, member.getId()); // 초기화 X Proxy
//            System.out.println("reference = " + reference); //
//            System.out.println("emf.getPersistenceUnitUtil().isLoaded() = " + emf.getPersistenceUnitUtil().isLoaded(reference)); // 초기화여부
//            Hibernate.initialize(reference); // 강제초기화 방법
//            reference.getUsername() // JPA는 강제초기화가 없으므로 강제 호출

//            em.detach(reference); // 만약 영속성이 준영속이 되면? !!!
//            reference.setUsername("refer?"); // 초기화 할 때, 에러를 던진다. => Lazy 해야하는 이유

//            Member member1 = em.find(Member.class, member.getId());
//            System.out.println("member1 = " + member1); // 이미 영속성에 있으므로 Proxy

//            System.out.println("member1 == reference = " + (member1 == reference)); // 항상 true

            // 상속관계
//            Movie findMoive = em.find(Movie.class, movie.getId());
//            System.out.println("findMoive = " + findMoive);

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
            e.printStackTrace();
            // tx
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
