package com.epam.hibernate.service;

import com.epam.hibernate.config.PasswordConfig;
import com.epam.hibernate.dto.JwtResponse;
import com.epam.hibernate.dto.OnOffRequest;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.dto.user.UserInfoDTO;
import com.epam.hibernate.entity.RefreshToken;
import com.epam.hibernate.entity.RoleEnum;
import com.epam.hibernate.entity.User;
import com.epam.hibernate.exception.ExceptionResponse;
import com.epam.hibernate.exception.NoAuthorityException;
import com.epam.hibernate.exception.SamePasswordException;
import com.epam.hibernate.exception.WrongPasswordException;
import com.epam.hibernate.repository.UserRepository;
import com.epam.hibernate.security.ApplicationUser;
import com.epam.hibernate.security.jwt.JwtUtils;
import com.epam.hibernate.security.jwt.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

import java.time.LocalDateTime;

import static com.epam.hibernate.Utils.checkAdmin;

@Service
public class UserService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public UserService(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public ResponseEntity<?> authenticate(LoginDTO loginDTO) throws AuthenticationException {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                        .orElseThrow(EntityNotFoundException::new);
        refreshTokenService.deleteByUserId(user.getUserId());
        if(!user.getActive()){
            return ResponseEntity.badRequest().body(new ExceptionResponse("User set to inactive, please contact support", LocalDateTime.now()));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));
        System.out.println("blah");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(applicationUser.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshToken.getToken(),
                applicationUser.getId(),
                applicationUser.getUsername(),
                applicationUser.getAuthorities().stream().findFirst()
                        .orElseThrow(NoAuthorityException::new).toString()
        ));
    }

    public ResponseEntity<?> changePassword(@NotNull UserInfoDTO userInfoDTO) throws AuthenticationException {
        if (userInfoDTO.getNewPassword() == null || userInfoDTO.getNewPassword().length() < 8) {
            throw new WrongPasswordException();
        }
        if (userInfoDTO.getNewPassword().equals(userInfoDTO.getOldPassword())) {
            throw new SamePasswordException();
        }
        User user = userRepository.findByUsername(userInfoDTO.getUsername()).get();
        authenticate(new LoginDTO(userInfoDTO.getUsername(), userInfoDTO.getOldPassword()));
        String encryptedNewPass = PasswordConfig.passwordEncoder().encode(userInfoDTO.getNewPassword());
        userRepository.changePassword(encryptedNewPass, user.getUserId());
        return ResponseEntity.status(200).body("Password changed successfully");
    }

    public ResponseEntity<?> activateDeactivate(@NotNull String username, @NotNull OnOffRequest request) throws AuthenticationException {
        checkAdmin(request.getUsername(), userRepository);
        authenticate(new LoginDTO(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(username).get();
        user.setActive(!user.getActive());
        userRepository.activateDeactivate(user.getActive(), user.getUserId());
        return ResponseEntity.ok("User activated/deactivated successfully");
    }

    public void saveAdmin() {
        User admin = new User();
        String password = PasswordConfig.passwordEncoder().encode("admin");
        admin.setActive(true);
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setUsername("admin");
        admin.setPassword(password);
        admin.setRole(RoleEnum.ADMIN);
        userRepository.save(admin);
    }
}
