package com.example.motorcycleshop.DTO;

import com.example.motorcycleshop.model.MotorcycleTyp;

import java.util.Objects;

public class MotorcycleDTO {

    private String motorcycleName;
    private String description;

    private Double price;

    private String yearOfManufacture;

    private String imageUrl;

    private MotorcycleTyp motorcycleTyp;

    public MotorcycleDTO(String motorcycleName, String description, Double price, String yearOfManufacture, String imageUrl, MotorcycleTyp motorcycleTyp) {
        this.motorcycleName = motorcycleName;
        this.description = description;
        this.price = price;
        this.yearOfManufacture = yearOfManufacture;
        this.imageUrl = imageUrl;
        this.motorcycleTyp = motorcycleTyp;
    }

    public String getMotorcycleName() {
        return motorcycleName;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MotorcycleTyp getMotorcycleTyp() {
        return motorcycleTyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotorcycleDTO that)) return false;
        return Objects.equals(getMotorcycleName(), that.getMotorcycleName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getYearOfManufacture(), that.getYearOfManufacture()) && Objects.equals(getImageUrl(), that.getImageUrl()) && getMotorcycleTyp() == that.getMotorcycleTyp();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMotorcycleName(), getDescription(), getPrice(), getYearOfManufacture(), getImageUrl(), getMotorcycleTyp());
    }
}
