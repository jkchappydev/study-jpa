package com.study.jpa.jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    // @GeneratedValue의 strategy를 생략하면 기본은 AUTO 지만
    // Hibernate가 MySQL을 사용할 때도 종종 SEQUENCE 전략을 기본으로 택하려고 하며,
    // 이때 MySQL은 시퀀스를 지원하지 않으므로, Hibernate가 시퀀스를 모방한 테이블 (item_seq)을 자동으로 생성한다.
    // 따라서, MySQL에서는 항상 명시적으로 GenerationType.IDENTITY 설정 사용
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID") // 대 소문자 구분함
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

}
