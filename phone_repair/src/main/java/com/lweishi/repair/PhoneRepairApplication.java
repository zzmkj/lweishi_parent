package com.lweishi.repair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lweishi"})
@EnableJpaRepositories(basePackages = "com.lweishi")
public class PhoneRepairApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneRepairApplication.class, args);
    }
}
