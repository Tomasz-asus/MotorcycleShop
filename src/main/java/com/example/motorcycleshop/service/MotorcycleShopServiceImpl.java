package com.example.motorcycleshop.service;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.exceptions.BasketNotFoundException;
import com.example.motorcycleshop.exceptions.MotorcycleNotFoundException;
import com.example.motorcycleshop.exceptions.UserNotFoundException;
import com.example.motorcycleshop.model.AppUser;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.model.OrderCart;
import com.example.motorcycleshop.repository.AppUserRepository;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.MotorcycleRepository;
import com.example.motorcycleshop.repository.OrderCartRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MotorcycleShopServiceImpl implements MotorcycleShopService {

    private final MotorcycleRepository motorcycleRepository;
    private final BasketRepository basketRepository;
    private final OrderCartRepository orderCartRepository;
    private final AppUserRepository appUserRepository;

    public MotorcycleShopServiceImpl(MotorcycleRepository motorcycleRepository, BasketRepository basketRepository, OrderCartRepository orderCartRepository, AppUserRepository appUserRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.basketRepository = basketRepository;
        this.orderCartRepository = orderCartRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<MotorcycleDTO> getAllMotorcycles() {
        return motorcycleRepository.findAll().stream()
                .map(MotorcycleMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public MotorcycleDTO addMotorcycle(MotorcycleDTO motorcycleDTO) {
        if (motorcycleRepository.findByMotorcycleName(motorcycleDTO.getMotorcycleName()).isPresent()) {
            throw new RuntimeException("");
        } else {
            Motorcycle save = motorcycleRepository.save(MotorcycleMapper.fromDTO(motorcycleDTO));
            return MotorcycleMapper.fromEntity(save);
        }
    }

    public void deleteMotorcycle(String name) {
        motorcycleRepository.findByMotorcycleName(name).orElseThrow(() ->
                new MotorcycleNotFoundException("Not found"));
        motorcycleRepository.deleteByMotorcycleName(name);
    }

    public void clearProductsList() {
        motorcycleRepository.deleteAll();
    }

    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    public void addBasket(Basket basket) {
        basketRepository.save(basket);
    }

    public void deleteBasket(String name) {
        basketRepository.deleteByBasketName(name);
    }

    public void deleteMotorcycleFromBasket(String basket, String motorcycleName) {
        Basket basketEntity = basketRepository.findByBasketName(basket).orElseThrow(()
                -> new BasketNotFoundException("Basket: " + basket + ", was not found"));
        Motorcycle byMotorcycleName = motorcycleRepository.findByMotorcycleName(motorcycleName).orElseThrow(()
                -> new MotorcycleNotFoundException("Motorcycle: " + motorcycleName + " is not present in database."));
        if (!basketEntity.getMotorcycles().contains(byMotorcycleName)) {
            throw new MotorcycleNotFoundException("Motorcycle: " + motorcycleName + " is not present.");
        }
        basketEntity.removeMotorcycleFromBasket(byMotorcycleName);
        basketRepository.save(basketEntity);
    }

    @Override
    public void clearMotorcyclesList() {motorcycleRepository.deleteAll();

    }

    public void addProductToBasket(String basketName, String motorcycleName) {
        Basket basket = basketRepository.findByBasketName(basketName).orElseThrow(()
                -> new BasketNotFoundException("Basket" + basketName + "was not found"));
        Motorcycle motorcycle = motorcycleRepository.findByMotorcycleName(motorcycleName).orElseThrow(
                () -> new MotorcycleNotFoundException("Motorcycle: " + motorcycleName + ", was not found"));
        basket.getMotorcycles().add(motorcycle);
        basketRepository.save(basket);
    }

    public List<Motorcycle> getAllMotorcyclesFromBasket(String basketName) {
        return basketRepository.findByBasketName(basketName)
                .orElseThrow(() -> new BasketNotFoundException("Basket: " + basketName + ", was not found."))
                .getMotorcycles();
    }

    public OrderCartDTO addOrder(OrderCartDTO orderDTO) {
        OrderCart save = orderCartRepository.save(OrderCartMapper.fromDTO(orderDTO));
        AppUser appUser = appUserRepository.findByUsername(orderDTO.getUsername()).orElseThrow(()
                -> new UserNotFoundException("User " + orderDTO.getUsername() + " was not found."));
        appUser.getOrderCarts().add(save);
        String basketCustomName = RandomString.make(20);
        Basket basket = new Basket(basketCustomName);
        basketRepository.save(basket);
        appUser.setBasket(basketRepository.findByBasketName(basketCustomName).orElseThrow(() ->
                new BasketNotFoundException("Basket " +  basketCustomName + " was not found.")));
        appUserRepository.save(appUser);
        return OrderCartMapper.fromEntity(save);
    }

}
