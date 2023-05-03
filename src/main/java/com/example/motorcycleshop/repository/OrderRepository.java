package com.example.motorcycleshop.repository;

import com.example.motorcycleshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    Optional<Order> findByfirstAndLastName (String firstAndLastName);
}
