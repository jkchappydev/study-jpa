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

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // @ManyToMany // 다대다 단방향
    // @JoinTable(name = "MEMBER_PRODUCT")
    // private List<Product> products = new ArrayList<>();

    // 실무에서는 풀어써야 한다.
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

}