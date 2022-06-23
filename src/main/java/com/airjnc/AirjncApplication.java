package com.airjnc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class AirjncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirjncApplication.class, args);
    }

}
