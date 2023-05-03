package com.example.motorcycleshop.repository;

import com.example.motorcycleshop.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    Optional<Motorcycle> findById(Long id);

    Optional<Motorcycle> findByName(String name);
}
