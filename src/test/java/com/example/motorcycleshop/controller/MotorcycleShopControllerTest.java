package com.example.motorcycleshop.controller;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.model.MotorcycleTyp;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import com.example.motorcycleshop.repository.MotorcycleRepository;
import com.example.motorcycleshop.service.MotorcycleMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.motorcycleshop.model.MotorcycleTyp.CRUISER;
import static com.example.motorcycleshop.model.MotorcycleTyp.SPORT;
import static com.mysql.cj.exceptions.MysqlErrorNumbers.get;
import static org.apache.logging.log4j.ThreadContext.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
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


@Test
public void shouldGetAllMotorcycle () throws Exception{
    //GIVEN
        motorcycleRepository.save(new Motorcycle("yamaha",
            "the best of Yamaha's motorcycle",
            20000.00,
            "2008",
            "image",
            MotorcycleTyp.CRUISER));
    //WHEN
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/motoShop/motorcycle")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<MotorcycleDTO> products = Arrays.asList(objectMapper.readValue(contentAsString, MotorcycleDTO[].class));
    //THEN
        assertThat(products.size()).isEqualTo(1);
}


    @Test
    public void shouldGetTheSameMotorcyclesAsGiven() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle("yamaha",
                "the best of Yamaha's motorcycle",
                20000.00,
                "2008",
                "image",
                MotorcycleTyp.CRUISER));

        motorcycleRepository.save(new Motorcycle("yamaha2",
                "the best of Yamaha's motorcycle",
                20000.00,
                "2008",
                "image",
                MotorcycleTyp.CRUISER));
        //WHEN
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/motoShop/motorcycle")).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            String contentAsString = response.getContentAsString();
            List<MotorcycleDTO> motorcycle = Arrays.asList(objectMapper.readValue(contentAsString, MotorcycleDTO[].class));
        //THEN
        assertThat(motorcycle).containsExactlyInAnyOrder(
                new MotorcycleDTO("yamaha",
                        "the best of Yamaha's motorcycle",
                        20000.00,
                        "2008",
                        "image",
                        MotorcycleTyp.CRUISER),
                new MotorcycleDTO("yamaha2",
                        "the best of Yamaha's motorcycle",
                        20000.00,
                        "2008",
                        "image",
                        MotorcycleTyp.CRUISER));
    }
    @Test
    public void shouldAddMotorcycleToDatabase() throws Exception {
        //GIVEN
        MotorcycleDTO motorcycleDTO =
                new MotorcycleDTO("yamaha",
                        "the best of Yamaha's motorcycle",
                        20000.00,
                        "2008",
                        "image",
                        MotorcycleTyp.CRUISER);
        String json = objectMapper.writeValueAsString(motorcycleDTO);
        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(post("/motoShop/motorcycle")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        //THEN
        assertThat(status).isEqualTo(201);
        assertThat(MotorcycleMapper.fromEntityToMotorcycleDTO(motorcycleRepository.findByMotorcycleName("yamaha").get())).isEqualTo(motorcycleDTO);
    }
    @Test
    public void shouldRemoveMotorcycleFromDatabase() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle(
                "yamaha",
                "the best of Yamaha's motorcycle",
                20000.00,
                "2008",
                "image",
                MotorcycleTyp.CRUISER));
        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(delete("/motoShop/motorcycle/yamaha")).andReturn();
        int status = mvcResult.getResponse().getStatus();

        //THEN
        assertThat(status).isEqualTo(202);
        assertThat(motorcycleRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void shouldAddMotorcycleToBasket() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle(
                "yamaha",
                "the best of Yamaha's motorcycle",
                20000.00,
                "2008",
                "image",
                MotorcycleTyp.CRUISER));
        basketRepository.save(new Basket("testBasket"));
        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(post("/motoShop/motorcycle/toBasket/testBasket/yamaha")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        int productListSize = basketRepository.findByBasketName("testBasket").get().getMotorcycles().size();
        //THEN
        assertThat(status).isEqualTo(202);
        assertThat(productListSize).isEqualTo(1);
    }
    @Test
    public void shouldGetAllBaskets() throws Exception {
        //GIVEN
        basketRepository.save(new Basket("testBasket1"));
        basketRepository.save(new Basket("testBasket2"));
        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/motoShop/baskets")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<Basket> baskets = Arrays.asList(objectMapper.readValue(contentAsString, Basket[].class));
        //THEN
        assertThat(baskets.size()).isEqualTo(2);
    }
    @Test
    public void shouldRemoveBasketFromDatabase() throws Exception {
        //GIVEN
        basketRepository.save(new Basket("testBasket"));
        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(delete("/motoShop/basket/testBasket")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        int basketNumber = basketRepository.findAll().size();
        //THEN
        assertThat(status).isEqualTo(202);
        assertThat(basketNumber).isEqualTo(0);
    }
}
