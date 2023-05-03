package com.example.motorcycleshop.repository;

import com.example.motorcycleshop.model.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    Optional<OrderCart> findById(Long id);

    void deleteByUserNameOfOrder(String userNameOfOrder);

}
