package com.elbanking.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.elbanking.core")
@EnableJpaRepositories("com.elbanking.core")
public class ElbankingCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElbankingCoreApplication.class, args);
    }

}
