package com.airjnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AirjncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirjncApplication.class, args);
    }

}
