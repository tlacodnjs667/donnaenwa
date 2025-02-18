package com.wooruk.donnaenwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DonnaenwaApplication {

  public static void main(String[] args) {
    SpringApplication.run(DonnaenwaApplication.class, args);
  }

}
