package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Member3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(
            name = "FAVORITE_FOOD", // 테이블 명
            joinColumns = @JoinColumn(name = "MEMBER_ID") // 연관관계 설정
    )
    @Column(name = "FOOD_NAME") // String는 값이 하나이므로 명시적으로 컬럼명 지정 (Address는 city, street, zipCode 3개임)
    private Set<String> favoriteFoods = new HashSet<>();

    // 실무에서는 값 타입 컬렉션을 사용하면 안된다.
//    @ElementCollection
//    @CollectionTable(
//            name = "ADDRESS",
//            joinColumns = @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<Address> addressHistory = new ArrayList<>();

    // 값 타입 컬렉션 대안 (일대다에서 발생하는 update 쿼리는 어쩔수 없다: fk가 Address 테이블에 존재하기 때문에)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Cascade를 All로 한다. 고아 객체 제거 설정도 true
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

}
