package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Orderline;

import java.util.List;

public class OrderDto {
    public long id;
    public double totalAmount;
    public long userId;
    //public Orderline orderline;
    public List<Orderline> orderline;
}
