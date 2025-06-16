package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 필수
@AllArgsConstructor
@Embeddable // 값 타입임을 정의
public class Period {

    public boolean isWork() {
        // startDate와 endDate 사이에 있는가? 등의 메서드 작성 가능
        return true;
    }

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
