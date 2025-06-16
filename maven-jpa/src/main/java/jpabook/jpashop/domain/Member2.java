package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded // 값 타입을 사용하는 곳임을 명시
    private Period wordPeriod;

    @Embedded
    private Address homeAddress;

    // 한 엔티티에서 같은 값 타입을 사용하면 컬럼명이 중복되서 에러남
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "work_zipCode")),
    })
    private Address workAddress;

}
