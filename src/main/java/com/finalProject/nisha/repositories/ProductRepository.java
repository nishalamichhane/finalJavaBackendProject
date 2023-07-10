package com.finalProject.nisha.repositories;

import com.finalProject.nisha.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByName(String fileName);
}

