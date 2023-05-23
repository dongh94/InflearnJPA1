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

    @ManyToOne // N : 1 관계 => 보통 N이 주인이다. "FK를 들고있으면 주인" , 상대는 읽기만 가능하도록 양방향 설정해준다.(mappedby)
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
