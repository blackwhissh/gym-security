package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.OnOffRequest;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.dto.user.RefreshTokenRequest;
import com.epam.hibernate.dto.user.RefreshTokenResponse;
import com.epam.hibernate.dto.user.UserInfoDTO;
import com.epam.hibernate.entity.RefreshToken;
import com.epam.hibernate.entity.User;
import com.epam.hibernate.exception.RefreshTokenNotFoundException;
import com.epam.hibernate.repository.UserRepository;
import com.epam.hibernate.security.jwt.AuthTokenFilter;
import com.epam.hibernate.security.jwt.JwtUtils;
import com.epam.hibernate.security.jwt.RefreshTokenService;
import com.epam.hibernate.security.jwt.TokenManager;
import com.epam.hibernate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(value = "/v1/user", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final TokenManager tokenManager;
    private final AuthTokenFilter authTokenFilter;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(TokenManager tokenManager, AuthTokenFilter authTokenFilter,
                          JwtUtils jwtUtils, RefreshTokenService refreshTokenService,
                          UserService userService, UserRepository userRepository) {
        this.tokenManager = tokenManager;
        this.authTokenFilter = authTokenFilter;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/login")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "User Login", description = "This method is used to Log In")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws AuthenticationException {
        return userService.authenticate(loginDTO);
    }

    @PutMapping(value = "/change-password")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Change User Password", description = "This method changes User's password and returns new password")
    public ResponseEntity<?> changePassword(@RequestBody UserInfoDTO userInfoDTO) throws AuthenticationException {
        return userService.changePassword(userInfoDTO);
    }

    @PatchMapping(value = "/on-off/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Activate/Deactivate", description = "This method Activates/Deactivates User")
    public ResponseEntity<?> onOffTrainee(@PathVariable String username,
                                          @RequestBody OnOffRequest request) throws AuthenticationException {
        return userService.activateDeactivate(username, request);
    }

    @PostMapping(value = "/refresh")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Refresh Token", description = "This method is used to refresh JWT token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request,
                                          HttpServletRequest httpRequest) {
        String refreshToken = request.getRefreshToken();
        String oldActiveToken = authTokenFilter.parseJwt(httpRequest);
        tokenManager.invalidateToken(oldActiveToken);
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtToken(user.getUsername());
                    return ResponseEntity.ok(new RefreshTokenResponse(token, refreshToken));
                }).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest httpRequest) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(EntityNotFoundException::new);
        String oldActiveToken = authTokenFilter.parseJwt(httpRequest);
        tokenManager.invalidateToken(oldActiveToken);
        Long userId = user.getUserId();
        refreshTokenService.deleteByUserId(userId);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Log out successful");
    }

}
