package com.airjnc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("local")
class AirjncApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws Exception {
        Connection conn = dataSource.getConnection();
    }

    @Test
    void doSomething() throws Exception {
    }
    

}
