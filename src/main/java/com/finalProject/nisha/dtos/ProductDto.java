package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Orderline;

import java.util.List;

public class ProductDto {
    public long id;
    public String productName;
    public double unitPrice;
    public long categoryId;
    public String description;
    public List<Orderline> orderlines;
}
