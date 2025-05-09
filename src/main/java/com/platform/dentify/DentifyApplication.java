package com.platform.dentify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class DentifyApplication {

    public static void main(String[] args) {

        SpringApplication.run(DentifyApplication.class, args);
    }

}
