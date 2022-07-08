package com.airjnc.user.controller;

import com.airjnc.common.dto.SuccessResponseBodyHandler;
import com.airjnc.common.exception.InvalidFormException;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login/findpassword")
    public ResponseEntity<SuccessResponseBodyHandler<UserDTO>> findPasswordByEmail(@RequestParam @Email(message = "EmailForm") String email) {
//        if (bindingResult.hasErrors()){
//            throw new InvalidFormException(bindingResult);
//        }
        UserDTO pwdUserDTO = userService.findPasswordByEmail(email);
        
        return ResponseEntity.ok()
            .body(SuccessResponseBodyHandler.<UserDTO>builder()
                .message("success find password")
                .data(pwdUserDTO)
                .build());
    }

    @PostMapping("signup")
    public ResponseEntity<SuccessResponseBodyHandler<UserDTO>> userSignUp(@Validated @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }
        UserDTO userDTO = userService.create(signUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(SuccessResponseBodyHandler.<UserDTO>builder()
                .message("success user signup")
                .data(userDTO)
                .build());
    }


}
