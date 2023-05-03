package com.example.motorcycleshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity

public class Client {

    @OneToOne(fetch = FetchType.EAGER)
    private Basket basket = new Basket();

    @OneToMany
    private List<OrderCart> orderCarts = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String clientName;

    private String userName;

    private String password;

    private String verificationCode;

    @ManyToMany
    private Collection<Role> roles = new ArrayList<>();

    public Client() {
    }

    public Client(Long id, String clientName, String userName, String password, Collection<Role> roles) {
        this.id = id;
        this.clientName = clientName;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<OrderCart> getOrders() {
        return orderCarts;
    }

    public void setOrders(List<OrderCart> orderCarts) {
        this.orderCarts = orderCarts;
    }
}
