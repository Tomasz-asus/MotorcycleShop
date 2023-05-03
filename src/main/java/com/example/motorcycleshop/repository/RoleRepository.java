package com.example.motorcycleshop.repository;

import com.example.motorcycleshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

Optional<Role> findById(Long id);
Optional<Role> findByRoleName(String roleName);
}