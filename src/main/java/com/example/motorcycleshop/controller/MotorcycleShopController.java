package com.example.motorcycleshop.controller;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.service.MotorcycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/motoShop")
public class MotorcycleShopController {


    private final MotorcycleService motorcycleService;

    public MotorcycleShopController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @PostMapping("/motorcycle")
    public ResponseEntity<MotorcycleDTO> addMotorcycle(@RequestBody MotorcycleDTO motorcycleDTO){
        return new ResponseEntity<>(motorcycleService.addMotorcycle(motorcycleDTO), HttpStatus.CREATED);
    }

    @GetMapping("/motorcycle")
    public ResponseEntity<List<MotorcycleDTO>> getAllMotorcycles(){
        return ResponseEntity.ok().body(motorcycleService.getAllMotorcycles());
    }

    @GetMapping("/baskets")
    @ResponseBody
    public ResponseEntity<List<Basket>> getAllBaskets(){
        return ResponseEntity.ok().body(motorcycleService.getAllBaskets());
    }

    @GetMapping("/motorcycle/fromBasket/{basketName}")
    public ResponseEntity<List<Motorcycle>> getAllMotorcyclesFromBasket(@PathVariable String basketName) {
        return ResponseEntity.ok().body(motorcycleService.getAllMotorcycleFromBasket(basketName));
    }

    @PostMapping("/motorcycle/toBasket/{basketName}/{motorcycleName}")
    public ResponseEntity<Void> addMotorcycleToBasket(@PathVariable String basketName,
                                                      @PathVariable String motorcycleName) {
        motorcycleService.addMotorcycleToBasket(basketName, motorcycleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @DeleteMapping("/motorcycle/{motorcycleName}")
    @ResponseBody
    public ResponseEntity<Void> removeMotorcycle(@PathVariable String motorcycleName){
        motorcycleService.deleteMotorcycle(motorcycleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();}


    @DeleteMapping("/basket/{basketName}")
    public ResponseEntity<Void> removeBasket(@PathVariable String basketName) {
        motorcycleService.deleteBasket(basketName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/motorcycle/fromBasket/{basketName}/{motorcycleName}")
    @ResponseBody
    public ResponseEntity<Void> removeMotorcycleFromBasket(@PathVariable String basketName,
                                                           @PathVariable String motorcycleName) {
        motorcycleService.deleteMotorcycleFromBasket(basketName, motorcycleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @PostMapping("/order")
    public ResponseEntity<OrderCartDTO> addOrder(@RequestBody OrderCartDTO orderCardDTO) {
        return new  ResponseEntity<>(motorcycleService.addOrderCart(orderCardDTO), HttpStatus.CREATED);
    }

}
