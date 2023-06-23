package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Invoice;
import com.finalProject.nisha.models.Orderline;

import java.util.List;

public class OrderDto {
    public long id;
    public double totalAmount;
    public long userId;
    public List<Orderline> orderline;
    public Invoice invoice;
}
