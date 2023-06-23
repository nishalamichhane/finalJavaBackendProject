package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Order;

import java.util.Date;

public class InvoiceDto {
    public long id;
    public double totalAmount;
    public Date invoiceDate;
    public long userId;
    public Order order;
}
