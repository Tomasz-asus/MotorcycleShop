package com.example.motorcycleshop.service;

import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.DTO.MotorcycleDTO;

public class MotorcycleMapper {

    public static Motorcycle fromDTO(MotorcycleDTO motorcycleDTO) {
        return new Motorcycle(motorcycleDTO.getMotorcycleName(),
                motorcycleDTO.getMotorcycleDescription(),
                motorcycleDTO.getMotorcyclePrice(),
                motorcycleDTO.getImageURL(),
                motorcycleDTO.getCategory());
    }

    public static MotorcycleDTO fromEntity(Motorcycle motorcycle) {
        return new MotorcycleDTO(
                motorcycle.getMotorcycleName(),
                motorcycle.getMotorcycleDescription(),
                motorcycle.getMotorcyclePrice(),
                motorcycle.getImageURL(),
                motorcycle.getCategory());
    }
}
