package com.example.motorcycleshop.repository;

import com.example.motorcycleshop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);

    Optional<Client> findByVerificationCode(String code);
}
