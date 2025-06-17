package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Address address; // Entity 클래스를 생성해서 한번 래핑하는 방식으로 접근해서 사용해야 한다.

    public AddressEntity(String city, String street, String zipCode) { // 생성자를 통한 값 변경 (복사)
        this.address = new Address(city, street, zipCode);
    }

}
