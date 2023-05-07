package com.example.motorcycleshop.service;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.exceptions.BasketNotFoundException;
import com.example.motorcycleshop.exceptions.MotorcycleNotFoundException;
import com.example.motorcycleshop.exceptions.ClientNotFoundException;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.model.OrderCart;
import com.example.motorcycleshop.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class MotorcycleService {
    private final MotorcycleRepository motorcycleRepository;
    private final BasketRepository basketRepository;
    private final ClientRepository clientRepository;
    private final OrderCartRepository orderCartRepository;
    private final RoleRepository roleRepository;

    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public MotorcycleService(MotorcycleRepository motorcycleRepository,
                             BasketRepository basketRepository,
                             ClientRepository clientRepository,
                             OrderCartRepository orderCartRepository,
                             RoleRepository roleRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.basketRepository = basketRepository;
        this.clientRepository = clientRepository;
        this.orderCartRepository = orderCartRepository;
        this.roleRepository = roleRepository;
    }

    //TODO security.app add
    public MotorcycleDTO addMotorcycle(MotorcycleDTO motorcycleDTO) {
        if(motorcycleRepository.findByMotorcycleName(motorcycleDTO.getMotorcycleName()).isPresent()){
            throw new RuntimeException("Motorcycle already exist");
        } else{
            Motorcycle save = motorcycleRepository.save(MotorcycleMapper.fromDTOToMotorcycle(motorcycleDTO));
            return MotorcycleMapper.fromEntityToMotorcycleDTO(save);
        }
    }
    public List<MotorcycleDTO> getAllMotorcycles() {
        return motorcycleRepository.findAll()
                .stream()
                .map(MotorcycleMapper::fromEntityToMotorcycleDTO)
                .collect(Collectors.toList());
    }
    public void deleteMotorcycle(String motorcycleName){
        motorcycleRepository.findByMotorcycleName(motorcycleName)
                .orElseThrow(()->new MotorcycleNotFoundException("Motorcycle not found "));
        motorcycleRepository.deleteByMotorcycleName(motorcycleName);
    }
    public void addBasket(Basket basket){
        basketRepository.save(basket);
    }
    public List<Basket> getAllBaskets(){
        return basketRepository.findAll();
    }
    public void deleteBasket(String basketName){
        basketRepository.deleteByBasketName(basketName);
    }
    public OrderCartDTO addOrderCart(OrderCartDTO orderCartDTO){

        OrderCart save = orderCartRepository.save(MotorcycleMapper.fromDTOToOrderCart(orderCartDTO));
        Client client = clientRepository.findByClientName(orderCartDTO.getUserNameOfOrder())
                .orElseThrow(()-> new ClientNotFoundException("User" +orderCartDTO.getUserNameOfOrder() + "not found."));

        client.getOrders().add(save);
        String customBasket = generate(20);
        Basket basket = new Basket(customBasket);
        basketRepository.save(basket);
        client.setBasket(basketRepository.findByBasketName(customBasket)
                .orElseThrow(()->new BasketNotFoundException("Basket" + customBasket+"was not found.")));
        clientRepository.save(client);
        return MotorcycleMapper.fromEntityToOrderCartDTO(save);
    }
    public void deleteOrderCard(String userNameOfOrder){
        orderCartRepository.deleteByUserNameOfOrder(userNameOfOrder);
    }

    public static String generate(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();

}

    public void deleteMotorcycleFromBasket(String basketName, String motorcycleName){
        Basket byBasketName =basketRepository.findByBasketName(basketName)
                .orElseThrow(()->new BasketNotFoundException("Basket" + basketName+ "was not found"));
        Motorcycle byMotorcycleName = motorcycleRepository.findByMotorcycleName(motorcycleName)
                .orElseThrow(()->new MotorcycleNotFoundException("Motorcycle" + motorcycleName+"was not found"));
        if(!byBasketName.getMotorcycles().contains(byMotorcycleName)){
            throw new MotorcycleNotFoundException("Motorcycle" + motorcycleName+"was not found");
        }
        byBasketName.removeMotorcycleFromBasket(byMotorcycleName);
        basketRepository.save(byBasketName);

}

    public void addMotorcycleToBasket(String basketName, String motorcycleName){
        Basket basket = basketRepository.findByBasketName(basketName)
                .orElseThrow(()->new BasketNotFoundException("basket"  +basketName+ "was not found"));
        Motorcycle motorcycle = motorcycleRepository.findByMotorcycleName(motorcycleName)
                .orElseThrow(()-> new MotorcycleNotFoundException("Motorcycle" + motorcycleName + "was not found"));
        basket.getMotorcycles().add(motorcycle);
        basketRepository.save(basket);

}

    public List<Motorcycle> getAllMotorcycleFromBasket(String basketName){
        return basketRepository.findByBasketName(basketName)
                .orElseThrow(()-> new BasketNotFoundException("Basket" + basketName+"was not found"))
                .getMotorcycles();
    }
}
