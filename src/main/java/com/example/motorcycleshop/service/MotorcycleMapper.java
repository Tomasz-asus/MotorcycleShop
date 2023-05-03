package com.example.motorcycleshop.service;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.model.OrderCart;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MotorcycleMapper {

    private static ClientRepository clientRepository;

    private static BasketRepository basketRepository;

    public MotorcycleMapper(ClientRepository clientRepository, BasketRepository basketRepository) {
        this.clientRepository = clientRepository;
        this.basketRepository = basketRepository;
    }

    public static OrderCart fromDTOToOrderCart(OrderCartDTO orderCartDTO){

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

    public static OrderCartDTO fromEntityToOrderCartDTO(OrderCart orderCart) {
        return new OrderCartDTO(
                orderCart.getBasketName(),
                orderCart.getFirstAndLastName(),
                orderCart.getStreet(),
                orderCart.getPhone(),
                orderCart.getOrderDate(),
                orderCart.getCity(),
                orderCart.getUserNameOfOrder()
        );
    }

    private static List<Motorcycle> mappingMotorcyclesFromBasket(String basketName) {
        return new ArrayList<>(basketRepository
                .findByBasketName(basketName)
                .get()
                .getMotorcycles());
    }


    public static Motorcycle fromDTOToMotorcycle(MotorcycleDTO motorcycleDTO){
        return new Motorcycle(
                motorcycleDTO.getMotorcycleName(),
                motorcycleDTO.getDescription(),
                motorcycleDTO.getPrice(),
                motorcycleDTO.getYearOfManufacture(),
                motorcycleDTO.getImageUrl(),
                motorcycleDTO.getMotorcycleTyp());
    }

    public static MotorcycleDTO fromEntityToMotorcycleDTO(Motorcycle motorcycle) {
        return new MotorcycleDTO(
                motorcycle.getMotorcycleName(),
                motorcycle.getDescription(),
                motorcycle.getPrice(),
                motorcycle.getYearOfManufacture(),
                motorcycle.getImageUrl(),
                motorcycle.getMotorcycleTyp()
        );

    }
}
