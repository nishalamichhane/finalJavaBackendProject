package com.finalProject.nisha.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orderlineId;
    private double total;
    private long userId;
    @OneToMany(mappedBy = "Order")
    private List<Orderline> orderlines;
}
