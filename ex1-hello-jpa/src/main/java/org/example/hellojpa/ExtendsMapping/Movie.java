package org.example.hellojpa.ExtendsMapping;

import javax.persistence.Entity;

@Entity
public class Movie extends Item{ // Name, Price 등 상속해서 사용
    private String director;
    private String actor;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
