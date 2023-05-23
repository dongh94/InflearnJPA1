package org.example.hellojpa;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Member, Team에 상속해서 공통으로 쓸려고 만듦

@MappedSuperclass // 자식 클래스에 매핑 정보만 제공, 매핑 정보를 모으는 역할 !
public abstract class BaseEntity { // 엔티티 X, 테이블, 상속 메핑 X, 조회 X, 검색 X, 그저 추상 O
    private String createdBy;
    private LocalDateTime createdTime;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedTime;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
