package com.example.motorcycleshop.service;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.exceptions.MotorcycleNotFoundException;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Motorcycle;

import java.util.List;

public interface MotorcycleShopService {

    MotorcycleDTO addMotorcycle(MotorcycleDTO motorcycleDTO) throws MotorcycleNotFoundException;

    List<MotorcycleDTO> getAllMotorcycles();

    List<Basket> getAllBaskets();

    void deleteMotorcycle(String name);

    void deleteBasket(String name);

    void deleteMotorcycleFromBasket(String basket, String productName);

    void clearMotorcyclesList();

    void addBasket(Basket basket);

    void addProductToBasket(String basketName, String productName);

    List<Motorcycle> getAllMotorcyclesFromBasket(String basketName);

    OrderCartDTO addOrder(OrderCartDTO orderDTO);
}
