package com.example.motorcycleshop.controller;

import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import com.example.motorcycleshop.repository.MotorcycleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.motorcycleshop.model.MotorcycleTyp.CRUISER;
import static com.example.motorcycleshop.model.MotorcycleTyp.SPORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class MotorcycleShopControllerTest {

@Autowired
private MockMvc mockMvc;
@Autowired
private ObjectMapper objectMapper;
@Autowired
private MotorcycleRepository motorcycleRepository;
@Autowired
private BasketRepository basketRepository;
@Autowired
private ClientRepository clientRepository;


@BeforeEach
public void setup(){
    motorcycleRepository.deleteAll();
}

@Test
public void shouldAllMotorcyclesList() throws Exception{
    //given
    Motorcycle Honda = new Motorcycle("PanEurope","The best for europe-touring",19000.00,"2007","image",SPORT);
    motorcycleRepository.save(Honda);
    Motorcycle Yamaha = new Motorcycle("Warrior","The best cruiser all the world",29000.00,"2007","image",CRUISER);
    motorcycleRepository.save(Yamaha);
    //when
    motorcycleRepository.save(Honda);
    motorcycleRepository.save(Yamaha);

    //then
    List<Motorcycle> motorcycleList = motorcycleRepository.findAll();
    assertThat(motorcycleList.size()).isEqualTo(2);


}
    @Test
    public void shouldAllMotorcycleList() throws Exception{
        //given
       Motorcycle yamaha =  addData();
        //when
        motorcycleRepository.save(yamaha);


        //then
        List<Motorcycle> motorcycleList = motorcycleRepository.findAll();
        assertThat(motorcycleList.size()).isEqualTo(1);


    }




    private Motorcycle addData(){
        return new  Motorcycle("PanEurope","The best for europe-touring",19000.00,"2007","image",SPORT);
    }
}
