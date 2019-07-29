package com.example.demo.interfaces;

import com.example.demo.entity.WeatherMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherRepositoryMongo extends MongoRepository<WeatherMongo, String> {
}
