package com.example.motorcycleshop.exceptions;

import com.example.motorcycleshop.repository.BasketRepository;

public class BasketNotFoundException extends RuntimeException{


    public BasketNotFoundException(String message){
    super(message);}

}
