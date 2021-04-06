package org.acowzon.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.acowzon.backend.mapper")
public class AcowzonApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcowzonApplication.class, args);
    }
}
