### JPA

1. 왜?

   - 객체 지향은 APIE (추다상캡) 등 시스템의 복잡성을 제어할 수 있는 다양한 장치를 제공한다.

   - SQL에 의존적인 개발을 피하고 싶다. => 현실적으로 관계형 DB 사용해서 => 객체 중심 개

   - 객체 vs 관계DB 페러다임이 다르다. => 해결! => 상속, 연관관계, 데이터타입, 데이터식별방법

     - 상속 : 객체는 **객체를 자바컬렉션에 저장하듯이** 테이블을 저장해 조회하기 편하다. DB는 그런거 없다.
     - 연관관계 : 객체는 **참조**를 이용 테이블은 **외래 키**를 이용
     - 타입 : 객체의 **클래스, 변수** => DB의 **테이블(엔티티), 칼럼**
     - 조회 : 객체는 자유롭게 그래프 탐색할 수 있다 (연관관계와 메서드를 통해서) DB는 매번 SQL 날린다. 조인도 있어 길게 날려..
     - 그럼.. 매번 SQL 날렸으니 모든 객체를 미리 로딩? 매번 모든 엔티티를 신뢰?

   - 생산성, 유지보수, 페러다임 불일치 해결, 성능, 접근 추상화, 벤더 독립성 UP

     - 생산성 : CRUD => jpa.persist(객체), jpa.find(객체), 객체.setName(), jpa.remove(객체)

     - 유지보수 : 객체 변수, 즉 필드를 변경한다는 것은 모든 SQL 까지도 수정해야함을 의미했지만, JPA는 필드만 추가하면 알아서 해준다.

     - 페러다임 불일치 해결 :

       - 상속 : 객체를 experts 해놓으면 JPA가 알아서 쿼리 날려준다.
       - 연관관계 : setter로 다른 객체를 저장하면 getter로 함께 불러올 수 있다.(그래프 탐)
       - 비교 : **동일한 트랜잭션에서 조회한 엔티티는 같음을 보장해 준다.**

     - 성능 : 동일성(identity) 보장, 1차캐시, 트랜잭션 지원하는 쓰기 지연, 지연 로딩을 지원해준다. 

       - 성능풀이 : 같은 Tx안에서 같은 Entity를 반환하고, DB Isolation level이 Read Commit 이어도 Repeatable Read를 보장해서 SQL을 한번만 실행(영속성 컨텍스트)하며 Tx를 Commit할 때까지 JDBC BATCH SQL로 모아서 한번에 처리 한다 예를 들어 Update, Delete로 인한 Row Lock이 걸리지 않아서 비즈니스 로직을 수행한 이후 Commit하는 순간 SQL을 처리하는 것이다. 마지막으로 객체의 연관관계를 get하게 될 때처럼 사용할 때 객체를 로딩(SQL 날림)을 실행해 준다. 이게 지연 로딩이다. 즉시로딩은 연관관계에 있는 모든 것을 그 즉시 모두 날려주는 것을 의미한다. 

       

2. 어디서?

   - Object Relational Mapping 역할
   - java App => **JPA** => JDBC API => DB
   - JPA의 Entity 영속성 컨텍스트 그 안에서 Entity분석, Insert/select/update/delete SQL, JDBC API사용, 페러다임의 불일치를 해결
   - 인터페이스의 모음으로 구현은 하이버네이트, EclipseLink, DataNucleus 가 한다.
   - 즉 ORM은 객체와 RDB 두 기둥 위에 있는 기술이다.

   

3. 어떻게?

   - **영속성 관리** 코드로 하자.
   - 주의할 점은 있다
     1. **엔티티 매니저 팩토리**는 하나만 생성해서 App 전체에서 공유한다.
     2. **엔티티 매니저**는 쓰레드간에 공유하지 않는다.
     3. JPA의 모든 데이터 변경은 **트랜잭션** 안에서 실행한다.
   - 추가로.
     - JPQL은 엔티티 객체를 대상으로 SQL을 추상화한 객체 지향 쿼리 언어다.
     - 모든 DB를 객체로 변환해서 검색하는 것은 불가능하기 때문이다.
     - 특정 DB SQL에 의존하지 않는다는 특징이 있다.



### 영속성 관리

### 엔티티 매핑

```java
Member 객체


package org.example.hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Entity(JPA 관리)
// 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
//• final 클래스, enum, interface, inner 클래스 사용X
//• 저장할 필드에 final 사용 X
@Entity
@SequenceGenerator( // 왜? 기본키 속성 : null 아님, 유일성, 변하면 안된다. =>  Long형 + 대체키 + 키 생성전략 사용
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 50
)
public class Member extends BaseEntity{
    // TABLE strategy는 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
    @Id // 만 사용하면 PRIMARY KEY 직접 할당 의미. // IDENTITY 로 설정시 autoinc 지만 영속성 컨텍스트에서 동시성 이슈
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR") // IDENTITY: 데이터베이스 위임
    @Column(name = "MEMBER_ID")
    private Long id; // Long으로 간다. 10억 이상으로 그냥 해, Long 아니면 데이터베이스 연동도 안됨 어차피.


    @Column(name = "USERNAME", nullable = false, length = 100) // not null, String에서만 length 사용
    private String username;

    private Integer age;
    @Enumerated(EnumType.STRING) // 숫자로 놓으면 순서대로라 추후에 바뀔 가능성이 있기에 ORIGINAL 사용 X, STRING 사용 O
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP) // TIMESTAMP : 날짜와 시간, 예: 2013–10–11 11:11:11 / DATE : 날짜, TIME : 시간
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob // CLOB: String, char[], java.sql.CLOB, BLOB: byte[], java.sql. BLOB , 속성 X
    private String description;
    @Transient   // 필드 매핑X, 데이터베이스에 저장X, 조회X
    private String ignore;

    // ManyToOne은 항상! LAZY 설정
    @ManyToOne(fetch = FetchType.LAZY) // N : 1 관계 => 보통 N이 주인이다. "FK를 들고있으면 주인" , 상대는 읽기만 가능하도록 양방향 설정해준다.(mappedby)
    @JoinColumn(name = "TEAM_ID") // 반대의 PK
    private Team team;

    @OneToOne // 1 : 1 관계 => 반대에 mappedby 해주면 된다. 그럼 내가 FK로 가진 것.
    @JoinColumn(name = "LOCKER_ID") // 반대의 PK
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT") 다 : 다는 조인 테이블을 넣어준다. 근데 너무 단순해
    @OneToMany(mappedBy = "member") // 1 : 다의 연관관계 조인테이블에 해당하는 클래스를 만들어서 기능까지 추가하면서 처리해준다.
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
}

```

연관관계 매핑

고급 매핑

프록시와 연관관계 관리

```java
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
            // 3. 회원 수정 (변경감지 / 1차 캐시의 스냅샷)
//            Member member = em.find(Member.class, 1L);
//            member.setName("HelloJPA");

            // 4. 회원 조회 ( JPQL = Entity 객체를 대상으로 검색하는 객체 지향 쿼리 = SQL 추상화해서 의존 X, 즉시 flush() )
//            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(2)
//                    .setMaxResults(3)
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member.getName());
//            }
            // 5. 영속성 컨텍스트 = git 스테이징과 같다, 1차 캐시 => 비영속이 "모두" 끝나고 영속성 코드가 랜더링된다.
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
            // 즉 연관된거 MTO, OTO은 기본이 eager이기에 에 모두 FetchType.LAZY 해줘야 한다.

            // 그냥 일단 모든 연관관계는 LAZY로 해서 Proxy로 거쳐가라 => 쿼리 안날리니까/
            // 방법 3가지가 있다. 1. FetchJoin으로 필요한 것만 조인.

            // Casecade
            // 단순 영속성 전이 = 단일 소유자만 사용하는게 좋다 = 영속성에 껴준다는 의미.

            // 고아객체
            // 단일 소유자만
            // 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 제거.
//            orphanRemoval = ture // 고아 객체 제거하겠다는 의미로 Parent의 OneToMany()에 넣어준다.

            // Casecade 와 orphanRemoval = ture 를 모두 키면 부모 엔티티를 통해 자식 엔티티 생명주기를 결정할 수 있다.


            // SQL 문을 직접 보고 싶을 때 사용.
            em.flush(); // 영속성 컨텍스트 DB와 동기화
            em.clear(); // 영속성 스태이징 비우기 => 빈영속 상태
            // 지연로딩과 즉시로딩
            // 즉시로딩할 때 연관된거 모두다 가져오게된다. (JPQL)
//            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
            

            // 프록시
//            Member reference = em.getReference(Member.class, member.getId()); // 초기화 X Proxy
//            System.out.println("reference = " + reference);
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

```



값 타입

### 객체지향 쿼리 언어

1. 기본 문법과 쿼리 예

```java
            // code
            // JPQL
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);
            // 타입이 명확할 때
            TypedQuery<Member> query = em.createQuery("select m from Member as m ", Member.class);
            TypedQuery<String> query1 = em.createQuery("select m.username from Member as m", String.class);
            // 타입이 명확하지 않을 때,
            Query query2 = em.createQuery("select m.username, m.age from Member as m");

            // 결과값이 딱 하나일 때만 (: 이것은 바인딩)
            TypedQuery<Member> query3 = em.createQuery("select m from Member as m where m.username = :username", Member.class);
            query3.setParameter("username", "member1");
            Member singleResult = query3.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());
            
            // 체인 버젼
            Member singleResult1 = em.createQuery("select m from Member as m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult1 = " + singleResult1.getUsername());

            // code
```

