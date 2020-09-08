package com.zouhuibo.job.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = { "com.zouhuibo.*" })
@PropertySource(value = { "application.yml" })
public class MytechApplication {
    public static void main(String[] args) {
        SpringApplication.run(MytechApplication.class, args);
    }
}
