package org.example.jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    private int price;

    private int stockAmount;

    @OneToMany(mappedBy = "product")
    private List<Order> orderList = new ArrayList<Order>();

}
