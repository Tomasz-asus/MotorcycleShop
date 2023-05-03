package com.example.motorcycleshop.service;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.model.Motorcycle;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleMapper {

    public static Motorcycle fromDTO(MotorcycleDTO motorcycleDTO){
        return new Motorcycle(
                motorcycleDTO.getMotorcycleName(),
                motorcycleDTO.getDescription(),
                motorcycleDTO.getPrice(),
                motorcycleDTO.getYearOfManufacture(),
                motorcycleDTO.getImageUrl(),
                motorcycleDTO.getMotorcycleTyp());
    }

    public static MotorcycleDTO fromEntity(Motorcycle motorcycle) {
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
