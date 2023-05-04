package com.example.motorcycleshop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Basket {

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String basketName;
@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Motorcycle> motorcycles = new ArrayList<>();


    public Basket() {
    }

    public Basket(String basketName) {
        this.id = id;
        this.basketName = basketName;
        this.motorcycles = motorcycles;
    }

    public String getBasketName() {
        return basketName;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void removeMotorcycleFromBasket(Motorcycle motorcycle) {
        motorcycles.remove(motorcycle);
    }
}
