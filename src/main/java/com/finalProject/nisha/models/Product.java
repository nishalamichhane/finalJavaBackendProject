package com.finalProject.nisha.models;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private double unitPrice;
    private String description;
    @OneToMany(mappedBy = "product")
    private List<Orderline> orderlines;

    private String name;
    private String type;
    @Lob
    private byte[] imageData;

    @Enumerated(EnumType.STRING)
    private Category category;

}
