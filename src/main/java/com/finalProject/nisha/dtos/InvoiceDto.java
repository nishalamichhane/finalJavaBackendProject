package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Order;
import com.finalProject.nisha.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class InvoiceDto {
    public long id;
    public Date invoiceDate;
    public User user;
    public Order order;
}