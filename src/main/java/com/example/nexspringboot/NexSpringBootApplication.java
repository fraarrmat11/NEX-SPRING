package com.example.nexspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.nexspringboot.model.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NexSpringBootApplication {
    public static void main(String[] args) {SpringApplication.run(NexSpringBootApplication.class, args);}
}
