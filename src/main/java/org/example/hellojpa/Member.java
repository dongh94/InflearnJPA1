package org.example.hellojpa;

import javax.persistence.*;
import java.util.Date;

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
public class Member {
    // TABLE strategy는 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
    @Id // 만 사용하면 PRIMARY KEY 직접 할당 의미. // IDENTITY 로 설정시 autoinc 지만 영속성 컨텍스트에서 동시성 이슈
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR") // IDENTITY: 데이터베이스 위임
    private Long id; // Long으로 간다. 10억 이상으로 그냥 해, Long 아니면 데이터베이스 연동도 안됨 어차피.

    @Column(name = "name", nullable = false, length = 100) // not null, String에서만 length 사용
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


    public Member() {
    }
}
