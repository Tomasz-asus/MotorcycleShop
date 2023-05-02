package com.example.motorcycleshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    private String username;

    private String password;

    @ManyToMany
    private Collection<Role> roles = new ArrayList<>();

    public Client() {
    }

    public Client(Long id, String name, String username, String password, Collection<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
