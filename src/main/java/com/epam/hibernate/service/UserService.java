package com.epam.hibernate.service;

import com.epam.hibernate.dto.OnOffRequest;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.dto.user.UserInfoDTO;
import com.epam.hibernate.entity.RoleEnum;
import com.epam.hibernate.entity.User;
import com.epam.hibernate.exception.WrongPasswordException;
import com.epam.hibernate.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

import static com.epam.hibernate.Utils.checkAdmin;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> authenticate(LoginDTO loginDTO) throws AuthenticationException {
        userRepository.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.status(200).body("User authenticated successfully");
    }

    public ResponseEntity<?> changePassword(@NotNull UserInfoDTO userInfoDTO) throws AuthenticationException {
        if (userInfoDTO.getNewPassword() == null || userInfoDTO.getNewPassword().length() < 8) {
            throw new WrongPasswordException();
        }
        User user = userRepository.findByUsername(userInfoDTO.getUsername());
        authenticate(new LoginDTO(userInfoDTO.getUsername(), userInfoDTO.getOldPassword()));
        userRepository.changePassword(userInfoDTO.getNewPassword(), user.getUserId());
        return ResponseEntity.status(200).body("Password changed successfully");
    }

    public ResponseEntity<?> activateDeactivate(@NotNull String username, @NotNull OnOffRequest request) throws AuthenticationException {
        checkAdmin(request.getUsername(), userRepository);
        authenticate(new LoginDTO(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(username);
        user.setActive(!user.getActive());
        userRepository.activateDeactivate(user.getActive(), user.getUserId());
        return ResponseEntity.ok("User activated/deactivated successfully");
    }

    public void saveAdmin() {
        User admin = new User();
        admin.setActive(true);
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(RoleEnum.ADMIN);
        userRepository.save(admin);
    }
}
