package com.example.motorcycleshop.service;

import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.model.OrderCart;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderCartMapper {

    private static BasketRepository basketRepository;

    private ClientRepository clientRepository;

    public OrderCartMapper(BasketRepository basketRepository, ClientRepository clientRepository) {
        this.basketRepository = basketRepository;
        this.clientRepository = clientRepository;
    }

    public static OrderCart fromDTO(OrderCartDTO orderCartDTO) {

        return new OrderCart(
                orderCartDTO.getBasketName(),
                orderCartDTO.getFirstAndLastName(),
                orderCartDTO.getStreet(),
                orderCartDTO.getCity(),
                orderCartDTO.getOrderDate(),
                orderCartDTO.getPhone(),
                orderCartDTO.getUserNameOfOrder(),
                mappingMotorcyclesFromBasket(orderCartDTO.getBasketName()));

    }

    private static List<Motorcycle> mappingMotorcyclesFromBasket(String basketName) {
        return new ArrayList<>(basketRepository.findByBasketName(basketName).get().getMotorcycles());
    }

    public static OrderCartDTO fromEntity(OrderCart orderCart) {
        return null;
    }
}
