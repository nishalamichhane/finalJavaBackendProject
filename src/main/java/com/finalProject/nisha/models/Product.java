package com.finalProject.nisha.models;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Product {

    private long id;
    private String productName;
    private double unitPrice;
    private long categoryId;
}
