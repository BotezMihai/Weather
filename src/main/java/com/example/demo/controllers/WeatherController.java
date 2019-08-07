package com.example.demo.controllers;

import com.example.demo.commonfunctions.SharedVariables;
import com.example.demo.dto.WeatherDto;
import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@Component
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/{city}", method = RequestMethod.GET)
    public ResponseEntity<Weather> getWeatherNow(@PathVariable("city") String city) {
        Weather weather = weatherService.getWeatherNow(city);
        if (weather.getMain() == null)
            return new ResponseEntity<>(weather, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }

    @RequestMapping(value = "/dto/{city}", method = RequestMethod.GET)
    public WeatherDto getDtoWeatherNow(@PathVariable("city") String city) {
        Weather weather = weatherService.getWeatherNow(city);
        if (weather.getMain() == null)
            return new WeatherDto(weather, HttpStatus.NOT_FOUND.toString());
        return new WeatherDto(HttpStatus.OK.toString());
    }
}