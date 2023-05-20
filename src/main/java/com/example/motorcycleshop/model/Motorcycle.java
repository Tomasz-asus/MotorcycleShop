package com.example.motorcycleshop.model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Motorcycle {

    @Id
    @SequenceGenerator(
            name = "motorcycle_sequence",
            sequenceName = "motorcycle_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "motorcycle_sequence")
    private Long id;

    private String motorcycleName;
    private String motorcycleDescription;
    private Double motorcyclePrice;
    private String imageURL;

    @Enumerated(EnumType.STRING)
    private MotorcycleCategory category;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Basket> baskets;


    public Motorcycle() {
    }

    public Motorcycle(String name, String description, Double price, String pictureURL, MotorcycleCategory category){
        this.motorcycleName = name;
        this.motorcycleDescription = description;
        this.motorcyclePrice = price;
        this.imageURL = pictureURL;
        this.category = category;
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

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "id=" + id +
                ", motorcycleName='" + motorcycleName + '\'' +
                ", motorcycleDescription='" + motorcycleDescription + '\'' +
                ", motorcyclePrice=" + motorcyclePrice +
                ", imageURL='" + imageURL + '\'' +
                ", category=" + category +
 //               ", baskets=" + baskets +
                '}';
    }
}
