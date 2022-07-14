package com.airjnc.common.config;

import com.airjnc.user.dto.request.SignUpDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import util.UserFixture;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

@SpringBootTest
public class MessageTest {
    
    @Test
    public void chk_KR(){
        //given
        Locale.setDefault(Locale.KOREA);
        SignUpDTO invalidSignUpDTO = UserFixture.getSignUpDTOBuilder()
            .email("just_id")
            .password("asdferqewrasdfasdf")
            .name(null)
            .gender(null)
            .phoneNumber(null)
            .address(null)
            .birthDate(null)
            .build();

        //when
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<SignUpDTO>> validate = validator.validate(invalidSignUpDTO);
        validate.forEach(System.out::println);
    }
    
}
