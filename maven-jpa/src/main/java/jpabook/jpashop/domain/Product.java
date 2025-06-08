package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    // @ManyToMany(mappedBy = "products") // 다대다 양방향
    // private List<Member> members = new ArrayList<>();

    // 실무에서는 풀어써야 한다.
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();

}
