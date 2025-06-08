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

    @ManyToOne // 일대다 양방향 (이런 매핑은 공식적으로 존재 X, 사용하지 말자)
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

}
