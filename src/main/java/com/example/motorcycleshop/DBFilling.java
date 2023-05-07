package com.example.motorcycleshop;

import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.controller.MotorcycleShopController;
import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.MotorcycleTyp;
import com.example.motorcycleshop.model.Role;
import com.example.motorcycleshop.repository.RoleRepository;
import com.example.motorcycleshop.service.ClientServiceImp;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Profile("prod")
public class DBFilling {


    private final MotorcycleShopController motorcycleShopController;
    private final ClientServiceImp clientServiceImp;
    private final RoleRepository roleRepository;

    public DBFilling(MotorcycleShopController motorcycleShopController, ClientServiceImp clientServiceImp, RoleRepository roleRepository) {
        this.motorcycleShopController = motorcycleShopController;
        this.clientServiceImp = clientServiceImp;
        this.roleRepository = roleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void someDataAfterStartUp(){

        motorcycleShopController.addMotorcycle(new MotorcycleDTO("yamaha",
                "the best of Yamaha's motorcycle",
                20000.00,
                "2008",
                "D:\\IT\\Programing\\IdeaProjects\\job\\MotorcycleShop\\src\\main\\resources\\templates\\yamaha.jpg",
                MotorcycleTyp.CRUISER));
        motorcycleShopController.addMotorcycle(new MotorcycleDTO("honda",
                "the best of Honda's motorcycle",
                10000.00,
                "2007",
                "D:\\IT\\Programing\\IdeaProjects\\job\\MotorcycleShop\\src\\main\\resources\\templates\\Honda.jpg",
                MotorcycleTyp.TOURING));

        Role admin = new Role("ADMIN");
        Role user = new Role("USER");

        roleRepository.save(admin);
        roleRepository.save(user);

        Client admin2 = new Client("Tom","admin123", new ArrayList<>());
        clientServiceImp.saveAdmin(admin2);
    }
}
