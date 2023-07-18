package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Order;
import com.finalProject.nisha.models.Product;
import jakarta.transaction.Transactional;


public class OrderlineDto {
    public long id;
    public short quantity;
    public double subTotal;
    public Product product;
    public Order order;
}
