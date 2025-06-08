package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // Team이 기준이 될 경우
    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

}
