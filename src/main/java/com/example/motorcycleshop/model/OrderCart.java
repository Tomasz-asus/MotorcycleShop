package com.example.motorcycleshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderCart {

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

    private String userNameOfOrder;

    private String basketName;

    public OrderCart() {
    }

    public OrderCart(Long id,
                     List<Motorcycle> motorcycles,
                     String firstAndLastName,
                     String street,
                     String city,
                     Integer phone,
                     LocalDateTime orderDate,
                     String userNameOfOrder,
                     String basketName) {
        this.id = id;
        this.motorcycles = motorcycles;
        this.firstAndLastName = firstAndLastName;
        this.street = street;
        this.city = city;
        this.phone = phone;
        this.orderDate = orderDate;
        this.userNameOfOrder = userNameOfOrder;
        this.basketName = basketName;
    }

    public OrderCart(String basketName, String firstAndLastName, String street, String city, LocalDateTime orderDate, Integer phone, String userNameOfOrder, List<Motorcycle> motorcycles) {
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

    public String getUserNameOfOrder() {
        return userNameOfOrder;
    }

    public void setUserNameOfOrder(String userNameOfOrder) {
        this.userNameOfOrder = userNameOfOrder;
    }

    public String getBasketName() {
        return basketName;
    }

    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }
}

