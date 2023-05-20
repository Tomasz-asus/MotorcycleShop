package com.example.motorcycleshop.api;


import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.service.MotorcycleShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class MotorcycleShopController {

    private final MotorcycleShopService service;

    public MotorcycleShopController(MotorcycleShopService service) {
        this.service = service;
    }

    @PostMapping("/motorcycle")
    public ResponseEntity<MotorcycleDTO> addMotorcycle(@RequestBody MotorcycleDTO motorcycleDTO) {
        return new  ResponseEntity<>(service.addMotorcycle(motorcycleDTO), HttpStatus.CREATED);
            }

    @GetMapping("/motorcycles")
    @ResponseBody
    public ResponseEntity<List<MotorcycleDTO>> getAllMotorcycles() {
        return ResponseEntity.ok().body(service.getAllMotorcycles());
    }

    @GetMapping("/baskets")
    @ResponseBody
    public ResponseEntity<List<Basket>> getAllBaskets() {
        return ResponseEntity.ok().body(service.getAllBaskets());
    }

    @DeleteMapping("/motorcycle/{name}")
    @ResponseBody
    public ResponseEntity<Void> removeMotorcycle(@PathVariable String name) {
        service.deleteMotorcycle(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/motorcycle/fromBasket/{basketName}/{motorcycleName}")
    @ResponseBody
    public ResponseEntity<Void> removeMotorcycleFromBasket(@PathVariable String basketName, @PathVariable String motorcycleName) {
        service.deleteMotorcycleFromBasket(basketName, motorcycleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/basket/{name}")
    public ResponseEntity<Void> removeBasket(@PathVariable String name) {
        service.deleteBasket(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/motorcycle/toBasket/{basketName}/{motorcycleName}")
    public ResponseEntity<Void> addMotorcycleToBasket(@PathVariable String basketName, @PathVariable String motorcycleName) {
        service.addProductToBasket(basketName, motorcycleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/motorcycles/fromBasket/{basketName}")
    public ResponseEntity<List<Motorcycle>> getAllMotorcyclesFromBasket(@PathVariable String basketName) {
        return ResponseEntity.ok().body(service.getAllMotorcyclesFromBasket(basketName));
    }

    @PostMapping("/order")
    public ResponseEntity<OrderCartDTO> addOrder(@RequestBody OrderCartDTO orderDTO) {
        return new  ResponseEntity<>(service.addOrder(orderDTO), HttpStatus.CREATED);
    }

}
