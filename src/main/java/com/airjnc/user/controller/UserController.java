package com.airjnc.user.controller;

import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login/findpassword")
    public ResponseEntity<UserDTO> findPasswordByEmail(@RequestParam String email) {
        UserDTO userDTO = userService.findPasswordByEmail(email);

        return ResponseEntity.ok().body(UserDTO.builder().password(userDTO.getPassword()).build());
    }

    @PostMapping("signup")
    public ResponseEntity<UserDTO> userSignUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.create(signUpDTO);
        System.out.println(userDTO.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }


}
