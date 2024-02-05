package com.cydeo.fintracker;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class FinTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinTrackerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void logStartUp() {
        log.info("**** Fin Tracker Application is being started **** ");
        System.out.println("System.getenv() = " + System.getenv());
    }


    @PreDestroy
    public void logShutDown() {
        log.info("**** Fin Tracker Application was shut down cleanly **** ");
    }


    @Bean
    public MigrateResult migrateResult(DataSource dataSource){
        return Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
    }
}
