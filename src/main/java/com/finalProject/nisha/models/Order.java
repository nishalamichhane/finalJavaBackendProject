package com.finalProject.nisha.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double totalAmount;
    @OneToMany(mappedBy = "order")
    private List<Orderline> orderline;
    @OneToOne(mappedBy = "order")
    private Invoice  invoice;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (Orderline o : orderline){
            totalAmount += o.getSubTotal();
        }
        return totalAmount;
    }
}