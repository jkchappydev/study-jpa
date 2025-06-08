package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // 양방향 연관관계 설정 (외래키를 Order에서 관리하기 때문에 주인으로 설정하지 않음)
    // Order가 가지고있는 member 외래키와 매핑
    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();

}
