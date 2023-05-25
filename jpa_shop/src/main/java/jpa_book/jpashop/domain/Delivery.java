package jpa_book.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 양방향 읽기전용 처리
    private Order order;

}
