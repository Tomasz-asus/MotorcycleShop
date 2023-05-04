package com.example.motorcycleshop.exceptions;

public class ClientAlreadyExistException extends RuntimeException{

    public ClientAlreadyExistException(String message) {
        super(message);
    }
}
