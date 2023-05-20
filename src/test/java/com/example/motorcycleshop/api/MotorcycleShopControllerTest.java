package com.example.motorcycleshop.api;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.DTO.OrderCartDTO;
import com.example.motorcycleshop.model.AppUser;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Motorcycle;
import com.example.motorcycleshop.model.MotorcycleCategory;
import com.example.motorcycleshop.repository.AppUserRepository;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.MotorcycleRepository;
import com.example.motorcycleshop.service.MotorcycleMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class MotorcycleShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private AppUserRepository appUserRepository;


    @Test
    public void shouldGetAllMotorcycles() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));
        motorcycleRepository.save(new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(get("/shop/motorcycles")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<MotorcycleDTO> motorcycles = Arrays.asList(objectMapper.readValue(contentAsString, MotorcycleDTO[].class));

        //THEN

        assertThat(motorcycles.size()).isEqualTo(2);
    }

    @Test
    public void shouldGetTheSameMotorcyclesAsGiven() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));
        motorcycleRepository.save(new Motorcycle("Honda", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(get("/shop/motorcycles")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<MotorcycleDTO> motorcycles = Arrays.asList(objectMapper.readValue(contentAsString, MotorcycleDTO[].class));

        //THEN
        assertThat(motorcycles).containsExactlyInAnyOrder(
                new MotorcycleDTO("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER),
                new MotorcycleDTO("Honda", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));
    }

    @Test
    public void shouldAddMotorcycleToDatabase() throws Exception {
        //GIVEN

        MotorcycleDTO motorcycleDTO = new MotorcycleDTO
                ("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER);

        String json = objectMapper.writeValueAsString(motorcycleDTO);

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(post("/shop/motorcycle")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();

        //THEN
        assertThat(status).isEqualTo(201);
        assertThat(MotorcycleMapper.fromEntity(motorcycleRepository.findByMotorcycleName("Yamaha").get())).isEqualTo(motorcycleDTO);
    }

    @Test
    public void shouldRemoveMotorcycleFromDatabase() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(delete("/shop/motorcycle/Yamaha")).andReturn();
        int status = mvcResult.getResponse().getStatus();

        //THEN
        assertThat(status).isEqualTo(202);
        assertThat(motorcycleRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void shouldAddMotorcycleToBasket() throws Exception {
        //GIVEN
        motorcycleRepository.save(new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));
        basketRepository.save(new Basket("testBasket"));

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(post("/shop/motorcycle/toBasket/testBasket/Yamaha")).andReturn();
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

        MvcResult mvcResult = this.mockMvc.perform(get("/shop/baskets")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<Basket> baskets = Arrays.asList(objectMapper.readValue(contentAsString, Basket[].class));

        //THEN
        assertThat(baskets.size()).isEqualTo(2);
    }

    @Test
    public void shouldRemoveMotorcycleFromBasket() throws Exception {
        //GIVEN
        Motorcycle motorcycle = new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER);
        Basket basket = new Basket("testBasket");
        basket.addMotorcycleToBasket(motorcycle);
        basketRepository.save(basket);

        //
        MvcResult mvcResult = this.mockMvc.perform(delete("/shop/motorcycle/fromBasket/testBasket/Yamaha")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        int size = basketRepository.findByBasketName("testBasket").get().getMotorcycles().size();

        //THEN

        assertThat(status).isEqualTo(202);
        assertThat(size).isEqualTo(0);
    }

    @Test
    public void shouldRemoveBasketFromDatabase() throws Exception {
        //GIVEN
        basketRepository.save(new Basket("testBasket"));

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(delete("/shop/basket/testBasket")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        int basketNumber = basketRepository.findAll().size();

        //THEN

        assertThat(status).isEqualTo(202);
        assertThat(basketNumber).isEqualTo(0);
    }

    @Test
    public void shouldGetAllMotorcyclesFromBasket() throws Exception {
        //GIVEN
        Basket basket = new Basket("testBasket");
        Motorcycle motorcycleFirst = new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER);
        Motorcycle motorcycleSecond = new Motorcycle("Honda", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER);
        basket.addMotorcycleToBasket(motorcycleFirst);
        basket.addMotorcycleToBasket(motorcycleSecond);
        basketRepository.save(basket);

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(get("/shop/motorcycles/fromBasket/testBasket")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<MotorcycleDTO> motorcycles = Arrays.asList(objectMapper.readValue(contentAsString, MotorcycleDTO[].class));

        //THEN
        assertThat(motorcycles.size()).isEqualTo(2);
    }

    @Test()
    public void shouldMakeOrder() throws Exception {
        //GIVEN
        Motorcycle motorcycle = motorcycleRepository.save(new Motorcycle("Yamaha", "YamahaBest", 1., "link", MotorcycleCategory.CHOPPER));
        Basket basket = new Basket("testBasket");
        basket.addMotorcycleToBasket(motorcycle);
        basketRepository.save(basket);
        AppUser appUser = new AppUser("Jon", "test", "test", new ArrayList<>());
        appUser.setBasket(basketRepository.findByBasketName("testBasket").get());
        appUserRepository.save(appUser);
        OrderCartDTO orderCartDTO = new OrderCartDTO("test", "test test", "testBasket",
                "street", "31-311", "testCity", 999888777);
        String json = objectMapper.writeValueAsString(orderCartDTO);

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(post("/shop/order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        OrderCartDTO responseOrderCartDTO = objectMapper.readValue(contentAsString, OrderCartDTO.class);

        //THEN
        assertThat(status).isEqualTo(201);
        assertThat(responseOrderCartDTO).isEqualTo(orderCartDTO);
        assertThat(appUserRepository.findByUsername("test").get().getBasket().getMotorcycles()).isEmpty();
    }
}
