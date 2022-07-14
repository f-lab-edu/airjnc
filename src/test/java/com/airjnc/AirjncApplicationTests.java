package com.airjnc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import javax.validation.Validator;
import java.sql.Connection;
import java.util.Arrays;

@SpringBootTest
class AirjncApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws Exception {
        Connection conn = dataSource.getConnection();
    }

    @Test
    void doSomething() throws Exception {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println(Arrays.asList(beanDefinitionNames));
    }


}
