package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final StateService stateService;

    @PostMapping("/logIn")
    public UserDTO logIn(@RequestBody @Validated LogInDTO logInDTO) {
        UserDTO userDTO = authService.logIn(logInDTO);
        stateService.create(userDTO.getId());
        return userDTO;
    }

    @GetMapping("/logOut")
    @CheckAuth
    public void logOut() {
        stateService.remove();
    }
}
