package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.OnOffRequest;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.dto.user.UserInfoDTO;
import com.epam.hibernate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(value = "/v1/user", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
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

}
