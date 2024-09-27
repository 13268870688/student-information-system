package com.system.sims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.system.sims.dao")
public class SimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimsApplication.class, args);
    }

}
