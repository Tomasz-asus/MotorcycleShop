package com.example.motorcycleshop.model;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String motorcycleName;
    private String description;

    private Double price;

    private String yearOfManufacture;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MotorcycleTyp motorcycleTyp;

    public Motorcycle() {
    }

    public Motorcycle(String motorcycleName, String description, Double price, String yearOfManufacture, String imageUrl, MotorcycleTyp motorcycleTyp) {
        this.motorcycleName = motorcycleName;
        this.description = description;
        this.price = price;
        this.yearOfManufacture = yearOfManufacture;
        this.imageUrl = imageUrl;
        this.motorcycleTyp = motorcycleTyp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotorcycleName() {
        return motorcycleName;
    }

    public void setMotorcycleName(String motorcycleName) {
        this.motorcycleName = motorcycleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MotorcycleTyp getMotorcycleTyp() {
        return motorcycleTyp;
    }

    public void setMotorcycleTyp(MotorcycleTyp motorcycleTyp) {
        this.motorcycleTyp = motorcycleTyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motorcycle that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getMotorcycleName(), that.getMotorcycleName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getYearOfManufacture(), that.getYearOfManufacture()) && Objects.equals(getImageUrl(), that.getImageUrl()) && getMotorcycleTyp() == that.getMotorcycleTyp();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMotorcycleName(), getDescription(), getPrice(), getYearOfManufacture(), getImageUrl(), getMotorcycleTyp());
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "id=" + id +
                ", name='" + motorcycleName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", yearOfManufacture='" + yearOfManufacture + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", motorcycleTyp=" + motorcycleTyp +
                '}';
    }
}
