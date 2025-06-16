package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipCode;

    // equals() 재정의 (기본으로 생성해주는거 사용하자)
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipCode, address.zipCode);
    }

    // 해시를 사용하는 HashMap 등을 사용할 때 효율적으로 사용되는 메서드이다. (보통 equals() 재정의 할 때 같이 재정의)
    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipCode);
    }

}
