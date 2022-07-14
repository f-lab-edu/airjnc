package com.airjnc.user.controller;

import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("login/findpassword")
    public ResponseEntity<UserDTO> findPasswordByEmail(@RequestParam("email")
                                                       @NotBlank(message = "{Validation.NotNull}") @Email(message = "{Validation.Email}") String email) {
        UserDTO pwdUserDTO = userService.findPasswordByEmail(email);
        return ResponseEntity.ok().body(pwdUserDTO);
    }

    @PostMapping("signup")
    public ResponseEntity<UserDTO> userSignUp(@Validated @RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.create(signUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

}
