package com.finalProject.nisha.repositories;

import com.finalProject.nisha.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {
}
