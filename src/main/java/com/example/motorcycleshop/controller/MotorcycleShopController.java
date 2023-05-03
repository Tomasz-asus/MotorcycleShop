package com.example.motorcycleshop.controller;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
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
}
