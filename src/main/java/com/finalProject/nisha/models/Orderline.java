package com.finalProject.nisha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="orderlines")
public class Orderline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private short quantity;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;
    public double calculateSubTotalAmount() {
        return this.quantity * product.getUnitPrice();
    }
}