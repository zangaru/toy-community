package com.toy.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //Entity 가 아닌 상속용 클래스
@EntityListeners(AuditingEntityListener.class) //자동으로 값 갱신
public class BaseEntity {

    @CreatedDate //자동으로 엔터티가 생성될 때의 시간으로 설정
    @Column(updatable = false) //최초 생성 이후 수정 금지
    private LocalDateTime createdAt;

    @LastModifiedDate //해당 필드의 값이 엔터티가 수정될 때의 시간으로 자동 갱신
    private LocalDateTime lastModifiedAt;
}
