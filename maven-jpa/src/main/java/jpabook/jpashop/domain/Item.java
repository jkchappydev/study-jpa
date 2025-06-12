package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
// @Inheritance(strategy = InheritanceType.JOINED) // 부모 클래스에서 작성해야 한다. (조인 전략)
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
// 단일 테이블 전략은 @DiscriminatorColumn 어노테이션을 작성하지 않더라도 DTYPE 컬림이 반드시 생성된다.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 구현 클래스마다 테이블 전략
// @DiscriminatorColumn // DTYPE 컬럼 생성 (기본 컬럼명 DTYPE, 저장되는 값 default로 각각의 엔티티명이 들어감 -> 자식 클래스에서 지정 가능)
// @DiscriminatorColumn(name = "DIS_TYPE") // 이런식으로 컬럼명을 지정해 줄 수도 있다.
public abstract class Item { // abstract 클래스로 변경

    @Id // @GeneratedValue(strategy = GenerationType.IDENTITY) // <- 구현 클래스마다 테이블 전략 사용 불가 (특히 MySQL)
    private Long id;

    private String name;

    private int price;

}
