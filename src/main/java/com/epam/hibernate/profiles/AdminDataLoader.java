package com.epam.hibernate.profiles;

import com.epam.hibernate.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("admin")
public class AdminDataLoader {
    private final UserService userService;

    public AdminDataLoader(UserService userService) {
        this.userService = userService;
    }
    @PostConstruct
    @Transactional
    public void loadAdminData(){
        userService.saveAdmin();
    }
}
