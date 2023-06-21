package com.finalProject.nisha.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private double unitPrice;
    private long categoryId;
    private String description;
    @OneToMany(mappedBy = "product")
    private List<Orderline> orderlines;
}
