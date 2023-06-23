package com.finalProject.nisha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "invoice")

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double totalAmount;
    private Date invoiceDate;
    private long userId;
    @OneToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;
}
