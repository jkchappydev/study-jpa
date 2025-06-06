package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne // 하나의 팀에 여러명의 멤버가 속함 (멤버 입장에서 Many, 팀이 One)
    @JoinColumn(name = "TEAM_ID") // 조인 해야 되는 컬림
    private Team team; // 이렇게 변경

}
