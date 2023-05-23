package org.example.hellojpa.ExtendsMapping;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 조인 , SINGLE_TABLE (단일 테이블) // PK로 연동
@DiscriminatorColumn(name = "D_TYPE") // 하위 상속 받은 컬럼의 타입을 생성해서 칼럼으로 보여줌
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
