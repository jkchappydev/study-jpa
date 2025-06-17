package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Address {
    
    @Column(length = 10) // 값 타입의 장점: 공통 설정 적용 가능
    private String city;

    @Column(length = 20)
    private String street;

    @Column(length = 5)
    private String zipCode;

    // 값 타입의 장점: 의미있는 메서드 생성 가능
    public String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(
                getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet()) &&
                Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipCode());
    }

}
