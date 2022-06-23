package com.airjnc.user.controller;

import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO signUp(@RequestBody @Validated SignUpDTO signUpDTO) {
        UserDTO userDTO = this.userService.create(signUpDTO);
        // 회원가입과 동시에 로그인 진행 [세션 저장(or)JWT 토큰 발급]
        authService.logIn(AuthService.Key.USER, userDTO.getId());
        return userDTO;
    }
}
