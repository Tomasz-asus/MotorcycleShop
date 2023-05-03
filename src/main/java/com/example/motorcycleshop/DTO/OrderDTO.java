package com.example.motorcycleshop.DTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDTO {


    private String firstAndLastName;
    private String street;
    private String city;

    private Integer phone;

    private LocalDateTime orderDate;

    private String username;

    private String basketName;

    public OrderDTO(String firstAndLastName,
                    String street,
                    String city,
                    Integer phone,
                    LocalDateTime orderDate,
                    String username,
                    String basketName) {
        this.firstAndLastName = firstAndLastName;
        this.street = street;
        this.city = city;
        this.phone = phone;
        this.orderDate = orderDate;
        this.username = username;
        this.basketName = basketName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO orderDTO)) return false;
        return Objects.equals(getFirstAndLastName(), orderDTO.getFirstAndLastName()) && Objects.equals(getStreet(), orderDTO.getStreet()) && Objects.equals(getCity(), orderDTO.getCity()) && Objects.equals(getPhone(), orderDTO.getPhone()) && Objects.equals(getOrderDate(), orderDTO.getOrderDate()) && Objects.equals(getUsername(), orderDTO.getUsername()) && Objects.equals(getBasketName(), orderDTO.getBasketName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstAndLastName(), getStreet(), getCity(), getPhone(), getOrderDate(), getUsername(), getBasketName());
    }
}
