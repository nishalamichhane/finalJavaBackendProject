package com.finalProject.nisha.repositories;

import com.finalProject.nisha.models.Orderline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderlineRepository extends JpaRepository<Orderline, Long> {
}
