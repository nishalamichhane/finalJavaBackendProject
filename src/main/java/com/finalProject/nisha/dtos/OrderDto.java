package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderDto {
    public long id;
    public double totalAmount;
    public User user;
}
