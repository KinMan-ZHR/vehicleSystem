package com.scuse.volunteerhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.scuse.volunteerhub.mapper")
@SpringBootApplication
public class VolunteerhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerhubApplication.class, args);
    }

}
