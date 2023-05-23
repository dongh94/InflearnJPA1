package org.example.hellojpa;

import javax.persistence.*;

@Entity
public class MemberProduct {
    // Member와 Product가 다 : 다 관계로 이뤄질 때 애초에 설계를 이렇게 하면 안됬지만 혹시나 쓸려고 한다면
    // MemberProduct라는 하나의 조인 테이블을 만들어서 다: 1 관계를 놓고 유지보수 하는 방식을 채택하라.
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
