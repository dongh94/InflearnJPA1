package jpa_book.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS") // Order가 예약어가 걸려 있을 수 있음.
public class Order extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    // 단방향을 양방향을 위해 FK를 가졌기 때문에 주인으로 객체 자체를 가져온다.
//    @Column(name = "MEMBER_ID")
//    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID") // 반대 PK를 가지고 FK 설정
    private Member member;

    @OneToOne // Order를 1:1로 FK를 가진 주인
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @OneToMany(mappedBy = "order") // OrderItem의 다 : 1의 읽기 전용
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) { // 읽기 전용의 편의 메서드이자 영속성에서 동시성 오류 제거
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }


    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(Long memberId) {
//        this.memberId = memberId;
//    }
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
