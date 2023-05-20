package com.example.motorcycleshop.DTO;

import com.example.motorcycleshop.model.MotorcycleCategory;

import java.util.Objects;

public class MotorcycleDTO {

    private String motorcycleName;
    private String motorcycleDescription;
    private Double motorcyclePrice;
    private String imageURL;
    private MotorcycleCategory category;

    public MotorcycleDTO(String motorcycleName, String motorcycleDescription, Double motorcyclePrice, String imageURL, MotorcycleCategory category) {
        this.motorcycleName = motorcycleName;
        this.motorcycleDescription = motorcycleDescription;
        this.motorcyclePrice = motorcyclePrice;
        this.imageURL = imageURL;
        this.category = category;
    }

    public String getMotorcycleName() {
        return motorcycleName;
    }

    public void setMotorcycleName(String motorcycleName) {
        this.motorcycleName = motorcycleName;
    }

    public String getMotorcycleDescription() {
        return motorcycleDescription;
    }

    public void setMotorcycleDescription(String motorcycleDescription) {
        this.motorcycleDescription = motorcycleDescription;
    }

    public Double getMotorcyclePrice() {
        return motorcyclePrice;
    }

    public void setMotorcyclePrice(Double motorcyclePrice) {
        this.motorcyclePrice = motorcyclePrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public MotorcycleCategory getCategory() {
        return category;
    }

    public void setCategory(MotorcycleCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorcycleDTO that = (MotorcycleDTO) o;
        return Objects.equals(motorcycleName, that.motorcycleName) && Objects.equals(motorcycleDescription, that.motorcycleDescription) && Objects.equals(motorcyclePrice, that.motorcyclePrice) && Objects.equals(imageURL, that.imageURL) && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(motorcycleName, motorcycleDescription, motorcyclePrice, imageURL, category);
    }
}
