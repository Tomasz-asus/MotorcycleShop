package com.example.motorcycleshop.exceptions;

public class MotorcycleAlreadyExistException extends RuntimeException{

    public MotorcycleAlreadyExistException(String message) {
        super(message);
    }
}
