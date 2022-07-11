package com.airjnc.user.controller;

import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.SessionService;
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
    private final SessionService sessionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Validated CreateDTO createDTO) {
        UserDTO userDTO = userService.create(createDTO);
        sessionService.create(userDTO.getId());
        return userDTO;
    }


    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@CurrentUserId Long currentUserId) {
        userService.remove(currentUserId);
        sessionService.remove();
    }
}
