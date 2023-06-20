package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Product;

public class OrderlineDto {
    public long id;
    public long productId;
    public String productName;
    public short quantity;
    public double unitPrice;
    public double subTotal;
}
