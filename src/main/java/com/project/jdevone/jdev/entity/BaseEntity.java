package com.project.jdevone.jdev.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass   //테이블로 생성되지 않도록 함
@EntityListeners(value = {AuditingEntityListener.class})    //AuditingEntityListener: JPA내부에서 엔티티 객체가 생성,변경되는 것을 감지
@Getter
abstract class BaseEntity {
    /**
     * 날짜/시간 자동처리 추상 클래스
     */

    @CreatedDate
    @Column(name = "rgdt", updatable = false)
    private LocalDateTime rgdt;

    @LastModifiedDate
    @Column(name = "updt")
    private LocalDateTime updt;
}
