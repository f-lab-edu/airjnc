package com.airjnc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;

@SpringBootTest
//@TestPropertySource("classpath:application-local.properties")
class AirjncApplicationTests {

    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private DataSource dataSource;
    
    @Test
    void contextLoads() {
    }
    
    @Test
    void checkDataSource() throws Exception{
        Connection conn = dataSource.getConnection();
    }
    
    @Test
    void doSomething() throws Exception{
        System.out.println(Arrays.toString(context.getResources("classpath:mybatis/**/*.xml")));
        
    }
    
    
    
    

}
