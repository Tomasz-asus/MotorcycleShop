package com.example.motorcycleshop.model;

import jakarta.persistence.*;

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


}
