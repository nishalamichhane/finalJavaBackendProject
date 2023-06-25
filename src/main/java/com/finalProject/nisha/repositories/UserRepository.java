package com.finalProject.nisha.repositories;

import com.finalProject.nisha.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
