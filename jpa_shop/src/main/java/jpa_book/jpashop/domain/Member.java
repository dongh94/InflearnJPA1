package jpa_book.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity{
    @Id
    @GeneratedValue // 기본이 Auto => 데이터베이스 위임 autoincre
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "member") // Orders 의 다 : 1 의 읽기전용
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) { // 읽기 전용이므로
        order.setMember(this);
        orders.add(order);
    }
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
