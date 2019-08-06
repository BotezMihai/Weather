package com.example.demo.interfaces;

import com.example.demo.entity.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {
    List<Weather> findAllByCity(String city);
}
