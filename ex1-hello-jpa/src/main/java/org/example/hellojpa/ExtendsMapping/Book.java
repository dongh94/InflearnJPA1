package org.example.hellojpa.ExtendsMapping;

import javax.persistence.Entity;

@Entity
public class Book extends Item{ // Name, Price 등 상속해서 사용
    private String author;
    private String isbn;

}
