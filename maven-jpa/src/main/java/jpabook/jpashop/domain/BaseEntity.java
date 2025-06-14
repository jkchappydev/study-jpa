package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // 공통 속성으로 사용해야 할 때 헤딩 어노테이션 사용 (상속관계 매핑이 아니다!)
public abstract class BaseEntity { // 추상 클래스 사용 권장

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

}
