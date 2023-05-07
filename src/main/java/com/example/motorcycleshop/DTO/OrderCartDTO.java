package com.example.motorcycleshop.DTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderCartDTO {


    private String firstAndLastName;
    private String street;
    private String city;

    private Integer phone;

    private String userNameOfOrder;

    private String basketName;

    public OrderCartDTO(String firstAndLastName,
                        String street,
                        String city,
                        Integer phone,
                        String userNameOfOrder,
                        String basketName) {
        this.firstAndLastName = firstAndLastName;
        this.street = street;
        this.city = city;
        this.phone = phone;
        this.userNameOfOrder = userNameOfOrder;
        this.basketName = basketName;
    }

    public String getFirstAndLastName() {
        return firstAndLastName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getUserNameOfOrder() {
        return userNameOfOrder;
    }

    public String getBasketName() {
        return basketName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCartDTO orderCartDTO)) return false;
        OrderCartDTO that = (OrderCartDTO) o;
        return Objects.equals(userNameOfOrder, that.userNameOfOrder)
                && Objects.equals(firstAndLastName, that.firstAndLastName)
                && Objects.equals(basketName, that.basketName)
                && Objects.equals(street, that.street)
                && Objects.equals(city, that.city)
                && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNameOfOrder, firstAndLastName, basketName, street, city, phone);
    }
}
