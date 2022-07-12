package com.airjnc.user.controller;

import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.common.util.constant.SessionKey;
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
        UserDTO userDTO = userService.create(signUpDTO);
        // 회원가입과 동시에 로그인 진행 [세션 저장(or)JWT 토큰 발급]
        authService.logIn(SessionKey.USER, userDTO.getId());
        return userDTO;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@CurrentUserId Long currentUserId) {
        userService.remove(currentUserId);
        // 로그아웃 이후, 세션 제거
        authService.logOut(SessionKey.USER);
    }
}
