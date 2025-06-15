package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정 (Team을 프록시 객체로 조회하게 된다. 즉, Member 클래스만 DB에서 조회)
    // @ManyToOne(fetch = FetchType.EAGER) // 즉시 로딩 설정 (Member와 Team을 함께 조회한다.)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "member") // 연관관계의 주인이 아님.
    private List<Order> orders = new ArrayList<>();

}
