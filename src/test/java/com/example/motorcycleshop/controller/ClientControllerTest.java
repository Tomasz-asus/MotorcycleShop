package com.example.motorcycleshop.controller;

import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Role;
import com.example.motorcycleshop.model.RoleToUser;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import com.example.motorcycleshop.repository.MotorcycleRepository;
import com.example.motorcycleshop.repository.RoleRepository;
import com.example.motorcycleshop.security.filter.CustomAuthenticationFilter;
import com.example.motorcycleshop.service.ClientService;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Ignore;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import com.example.motorcycleshop.service.ClientServiceImp;
import com.example.motorcycleshop.service.MotorcycleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientControllerTest {


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
    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private ClientServiceImp clientServiceImp;
    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void shouldAddClient() throws  Exception{

        //GIVEN

        Client client1 = new Client("tom","tom", "password",new  ArrayList());
        Client client2 = new Client("tom2", "tom2", "password",new  ArrayList());

        basketRepository.save(new Basket("first"));
        basketRepository.save(new Basket("second"));

        clientServiceImp.saveRole(new Role("USER"));

        client1.setBasket(basketRepository.findByBasketName("first").get());
        client2.setBasket(basketRepository.findByBasketName("second").get());

        clientRepository.save(client1);
        clientRepository.save(client2);

        clientServiceImp.addRoleToClient("tom","USER");
        clientServiceImp.addRoleToClient("tom2","USER");

        //WHEN

        String token = CustomAuthenticationFilter.get_admin_access_token("tom");
        MvcResult mvcResult= this.mockMvc.perform(get("/api/clients")
                        .header("Authorization", "Bearer "+token))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        List<Client> clients = Arrays.asList(objectMapper.readValue(contentAsString, Client[].class));

        //THEN
        assertNotNull(token);
        assertThat(clients.size()).isEqualTo(2);

    }
    //TODO
//    @Test
//    public void shouldSaveClient() throws Exception {
//        //GIVEN
//        roleRepository.save(new Role("USER"));
//        Client client = new Client("tom","tom", "password", new ArrayList<>());
//        String json = objectMapper.writeValueAsString(client);
//
//        //WHEN
//        MvcResult mvcResult = this.mockMvc.perform(post("/api/clients")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(json)
//                        .servletPath("/api/clients"))
//                .andReturn();
//        int status = mvcResult.getResponse().getStatus();
//
//        //THEN
//        assertThat(status).isEqualTo(201);
//        AssertionsForClassTypes.assertThat(clientRepository.findAll().size()).isEqualTo(1);
//    }

    @Test
    public void shouldVerifyClient() throws Exception {
        //GIVEN
        clientServiceImp.saveRole(new Role("USER"));
        basketRepository.save(new Basket("test1"));
        Client client = new Client("tomi", "tomi", "password", new ArrayList<>());
        client.setBasket(basketRepository.findByBasketName("test1").get());
        String randomCode = RandomString.make(64);
        client.setVerificationCode(randomCode);
        clientRepository.save(client);
        clientServiceImp.addRoleToClient("tomi", "USER");

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(get("/api/verify?code=" + randomCode)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String body = mvcResult.getResponse().getContentAsString();

        //THEN
        assertThat(status).isEqualTo(200);
        assertThat(body).isEqualTo("verify_ok");
    }

    @Test
    public void shouldAddRole() throws Exception {
        //GIVEN
        Role role = new Role("USER");
        String json = objectMapper.writeValueAsString(role);

        //WHEN
        String token = CustomAuthenticationFilter.get_admin_access_token("tom");
        MvcResult mvcResult = this.mockMvc.perform(post("/api/role")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();

        //THEN
        assertThat(status).isEqualTo(201);
        AssertionsForClassTypes.assertThat(roleRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void shouldAddRoleToClient() throws Exception {
        //GIVEN
        Role role = new Role("ADMIN");
        roleRepository.save(role);
        basketRepository.save(new Basket("test1"));

        Client client = new Client("tomi", "tomi", "password", new ArrayList<>());
        client.setBasket(basketRepository.findByBasketName("test1").get());
        clientRepository.save(client);

        String json = objectMapper.writeValueAsString(new RoleToUser("tomi", "ADMIN"));

        //WHEN
        String token = CustomAuthenticationFilter.get_admin_access_token("tomi");
        MvcResult mvcResult = this.mockMvc.perform(post("/api/role/toClient")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();

        //THEN
        assertThat(status).isEqualTo(200);
        AssertionsForClassTypes.assertThat(roleRepository.findByName("ADMIN")).isSameAs(role);
    }
}
