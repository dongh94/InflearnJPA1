package org.example.hellojpa;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 반대편 기준 N : 1을 양방향 참조하기 위해 설정, mappedBy 반대편 변수이름
    private List<Member> members = new ArrayList<>(); // 읽기 전용 ( 주인이 아니므로 ) => add, remove 등 "주인과 같이" 해줘야 한다.

    // get, set, add 까지 만들어 줘야한다. => 주인쪽 add를 지정하거나 하나만 선택한다 !!!!
    // 편의 method
    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
