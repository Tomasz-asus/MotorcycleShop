package com.example.motorcycleshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Motorcycle> motorcycles = new ArrayList<>();

    private String firstAndLastName;
    private String street;
    private String city;

    private Integer phone;

    private LocalDateTime orderDate;

    private String username;

    private String basketName;

    public Order() {
    }

    public Order(Long id,
                 List<Motorcycle> motorcycles,
                 String firstAndLastName,
                 String street,
                 String city,
                 Integer phone,
                 LocalDateTime orderDate,
                 String username,
                 String basketName) {
        this.id = id;
        this.motorcycles = motorcycles;
        this.firstAndLastName = firstAndLastName;
        this.street = street;
        this.city = city;
        this.phone = phone;
        this.orderDate = orderDate;
        this.username = username;
        this.basketName = basketName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void setMotorcycles(List<Motorcycle> motorcycles) {
        this.motorcycles = motorcycles;
    }

    public String getFirstAndLastName() {
        return firstAndLastName;
    }

    public void setFirstAndLastName(String firstAndLastName) {
        this.firstAndLastName = firstAndLastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBasketName() {
        return basketName;
    }

    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }
}

