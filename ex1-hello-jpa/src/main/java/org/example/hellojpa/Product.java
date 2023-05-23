package org.example.hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
//    @ManyToMany(mappedBy = "products") 다 : 다 도 1: 1 처럼 읽기전용을 하나 지정해 주면 된다. mappedBy로
    @OneToMany(mappedBy = "product") // 다: 다 이지만 조인 테이블에 1:다 를 해준다. 왜? ManyToMany는 너무 단순해. 기능 못넣어
    private List<MemberProduct> memberProducts = new ArrayList<>();

    private int orderAmount;

    private int orderPrice;

    private LocalDateTime orderDateTime; // 의미가 있으려면 뭐 아예 클래스 이름 바꿔서 사용도 가능. ex) Orders
}
