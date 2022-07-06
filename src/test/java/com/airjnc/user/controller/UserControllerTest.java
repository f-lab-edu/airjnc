package com.airjnc.user.controller;

import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//
//@SpringBootTest
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest
class UserControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private UserService userService;
    
    private UserDTO userDTO;
    private SignUpDTO signUpDTO;

    @BeforeEach
    public void setUp() {
        this.userDTO = UserDTO.builder()
            .email("test@naver.com")
            .password("ch1234")
            .name("창훈")
            .gender(UserDTO.Gender.MALE)
            .phoneNumber("010-2222-3333")
            .address("서울시 강동구")
            .active(true)
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();

        this.signUpDTO = SignUpDTO.builder()
            .email("test@naver.com")
            .password("ch1234")
            .name("창훈")
            .gender(SignUpDTO.Gender.MALE)
            .phoneNumber("010-2222-3333")
            .address("서울시 강동구")
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();
    }
        
    @Test
    @DisplayName("존재하는 이메일 입력 패스워드 찾음")
    public void whenExistsEmailThenGetPassword() throws Exception{
        //given
        Mockito.doReturn(userDTO).when(userService).findPasswordByEmail("test@naver.com");
        
        //when , then
        mvc.perform(get("/user/login/findpassword").param("email", "test@naver.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("password").value(userDTO.getPassword()));
    }
    
    @Test
    @DisplayName("패스워드 찾을경우 패스워드만 반환 -> Service로 로직 이전 여부 결정필요")
    public void whenExistsEmailThenNotPasswordEmpty() throws Exception{
        //given
        Mockito.doReturn(userDTO).when(userService).findPasswordByEmail("test@naver.com");

        //when
        MvcResult mvcResult = mvc.perform(get("/user/login/findpassword").param("email", "test@naver.com"))
            .andReturn();
        //then
        ObjectMapper mapper = new ObjectMapper();
        UserDTO responseUserDTO = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        HashMap <String,String> responseUserDTOMap = mapper.convertValue(responseUserDTO, HashMap.class);
        responseUserDTOMap.entrySet().stream().filter(k -> !k.getKey().equals("password") && !k.getKey().equals("active"))
            .forEach(v -> Assertions.assertThat(v.getValue()).isNull());
    }
    
    
    @Test
    @DisplayName("회원가입 성공")
    public void whenJoinUserThenSuccessCreateUser() throws Exception{
        //given
        String createUserJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(signUpDTO);
        Mockito.doReturn(userDTO).when(userService).create(signUpDTO);
        //when, then
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("email").value(userDTO.getEmail()));
    }
    
    
}