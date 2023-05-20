package com.example.motorcycleshop;


import com.example.motorcycleshop.DTO.MotorcycleDTO;
import com.example.motorcycleshop.api.MotorcycleShopController;
import com.example.motorcycleshop.model.AppUser;
import com.example.motorcycleshop.model.MotorcycleCategory;
import com.example.motorcycleshop.model.Role;
import com.example.motorcycleshop.repository.RoleRepository;
import com.example.motorcycleshop.service.AppUserServiceImpl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DbFilling {

    private final MotorcycleShopController controller;
    private final AppUserServiceImpl appUserService;
    private final RoleRepository roleRepository;

    public DbFilling(MotorcycleShopController controller, AppUserServiceImpl appUserService, RoleRepository roleRepository) {
        this.controller = controller;
        this.appUserService = appUserService;
        this.roleRepository = roleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.TOURING));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.TOURING));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.TOURING));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.TOURING));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.CHOPPER));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.CHOPPER));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.CHOPPER));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.CHOPPER));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.SPORT));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.SPORT));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.SPORT));
        controller.addMotorcycle(new MotorcycleDTO("Yamaha", "YamahaBike", 2.00,
                "https://motomoda24.pl/wp-content/uploads/2022/06/ktm-690.jpg",
                MotorcycleCategory.SPORT));

        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        roleRepository.save(admin);
        roleRepository.save(user);

        AppUser adminUser = new AppUser("Tomasz", "admin", "admin", new ArrayList<>());
        appUserService.saveAdmin(adminUser);
    }
}
