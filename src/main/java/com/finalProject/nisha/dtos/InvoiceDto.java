package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Order;
import com.finalProject.nisha.models.User;

import java.util.Date;

public class InvoiceDto {
    public long id;
    public double totalAmount;
    public Date invoiceDate;
    public User user;
    public Order order;
}
