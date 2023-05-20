package com.example.motorcycleshop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Basket {

    @Id
    @SequenceGenerator(
            name = "basket_sequence",
            sequenceName = "basket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "basket_sequence")
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Motorcycle> motorcycles = new ArrayList<>();

    private String basketName;


    public Basket() {
    }

    public Basket(String basketName) {
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

    public String getBasketName() {
        return basketName;
    }

    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }

    public void removeMotorcycleFromBasket(Motorcycle motorcycle) {
        motorcycles.remove(motorcycle);
    }

    public void addMotorcycleToBasket(Motorcycle motorcycle) {
        motorcycles.add(motorcycle);
    }
}
