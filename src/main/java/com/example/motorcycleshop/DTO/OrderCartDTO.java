package com.example.motorcycleshop.DTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderCartDTO {


    private String firstAndLastName;
    private String street;
    private String city;

    private Integer phone;

    private LocalDateTime orderDate;

    private String userNameOfOrder;

    private String basketName;

    public OrderCartDTO(String firstAndLastName,
                        String street,
                        String city,
                        Integer phone,
                        LocalDateTime orderDate,
                        String userNameOfOrder,
                        String basketName) {
        this.firstAndLastName = firstAndLastName;
        this.street = street;
        this.city = city;
        this.phone = phone;
        this.orderDate = orderDate;
        this.userNameOfOrder = userNameOfOrder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCartDTO orderCartDTO)) return false;
        return Objects.equals(getFirstAndLastName(), orderCartDTO.getFirstAndLastName()) && Objects.equals(getStreet(), orderCartDTO.getStreet()) && Objects.equals(getCity(), orderCartDTO.getCity()) && Objects.equals(getPhone(), orderCartDTO.getPhone()) && Objects.equals(getOrderDate(), orderCartDTO.getOrderDate()) && Objects.equals(getUserNameOfOrder(), orderCartDTO.getUserNameOfOrder()) && Objects.equals(getBasketName(), orderCartDTO.getBasketName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstAndLastName(), getStreet(), getCity(), getPhone(), getOrderDate(), getUserNameOfOrder(), getBasketName());
    }
}
