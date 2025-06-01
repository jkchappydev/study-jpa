package com.study.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // JPA에서 관리할 엔티티 클래스임을 명시
@Table(name = "USER") // DB에 생성될 테이블 이름을 "USER"로 지정 (기본은 클래스 이름인 "Member")
public class Member {

    @Id // 해당 필드를 Primary Key로 지정 (필수)
    private Long id;

    @Column(name = "username") // DB 컬럼 이름을 "username"으로 설정 (기본은 변수명 "name")
    private String name;

}
