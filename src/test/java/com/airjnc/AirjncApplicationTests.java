package com.airjnc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;

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
