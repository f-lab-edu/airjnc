package com.airjnc.user.controller;

import com.airjnc.common.annotation.CurrentUser;
import com.airjnc.common.annotation.UserLoginCheck;
import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.common.auth.service.AuthService;
import com.airjnc.user.dto.request.FindEmailRequestDTO;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindEmailResponseDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import com.airjnc.user.valid.group.SignUpValid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("login/findemail")
    public ResponseEntity<FindEmailResponseDTO> findEmailByNameAndPhoneNumber(@RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber) {
        FindEmailResponseDTO findEmailResponseDTO = userService.findEmailByNameAndPhoneNumber(
            FindEmailRequestDTO.builder()
                .name(name).phoneNumber(phoneNumber)
                .build());
        return ResponseEntity.ok().body(findEmailResponseDTO);
    }

    @GetMapping("login/findpassword")
    public ResponseEntity<FindPwdResponseDTO> findPasswordByEmail(@RequestParam("email")
                                                                  @NotBlank(message = "{Validation.NotNull}") @Email(message = "{Validation.Email}") String email) {
        FindPwdResponseDTO findPwdResponseDTO = userService.findPasswordByEmail(email);
        return ResponseEntity.ok().body(findPwdResponseDTO);
    }

    @PostMapping("signup")
    public ResponseEntity<UserDTO> userSignUp(@Validated(SignUpValid.class) @RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.create(signUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("login")
    public ResponseEntity<Object> userLogIn(@RequestBody LogInRequestDTO logInRequestDTO) {
        AuthInfoDTO authInfoDTO = userService.logIn(logInRequestDTO);
        authService.setAuthInfo(authInfoDTO);
        return ResponseEntity.ok().body("LogIn Success");
    }

    @GetMapping("logout")
    public ResponseEntity<Object> userLogOut() {
        authService.clearAuthInfo();
        return ResponseEntity.ok().body("LogOut Success");
    }


    // @UserLoginCheck, @CurrentUser 통합테스트를 위한 임시 url  -> 추후 정상 URL추가 후 삭제 예정
    @GetMapping("")
    @UserLoginCheck

    public ResponseEntity<AuthInfoDTO> tempMain(@CurrentUser AuthInfoDTO authInfoDTO) {
        return ResponseEntity.ok().body(authInfoDTO);
    }


}
