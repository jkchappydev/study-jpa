package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
// Setter를 사용하지 않는다. (불변 객체로 만듬)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // 값 타입
public class Address {

    private String city;
    private String street;
    private String zipCode;

}
