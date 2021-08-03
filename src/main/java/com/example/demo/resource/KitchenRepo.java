package com.example.demo.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepo extends JpaRepository<Kitchen, Long> {
	Kitchen findByEmail(String email);
}
