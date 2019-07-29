package com.example.demo;

import com.example.demo.entity.WeatherMongo;
import com.example.demo.interfaces.WeatherRepository;
import com.example.demo.interfaces.WeatherRepositoryMongo;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class DemoApplication {
    @Autowired
    private WeatherRepositoryMongo weatherMongoRepository;
    public static void main(String[] args) {


        SpringApplication.run(DemoApplication.class, args);
    }

}
