package com.example.demo.controllers;

import com.example.demo.dto.WeatherDto;
import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/{place}", method = RequestMethod.GET)
    public ResponseEntity<Weather> getWeatherNow(@PathVariable("place") String place) {
        Weather weather = weatherService.getWeatherNow(place);
        if (weather.getMain() != null)
            return new ResponseEntity<>(weather, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }

    @RequestMapping(value = "/dto/{place}", method = RequestMethod.GET)
    public WeatherDto getDtoWeatherNow(@PathVariable("place") String place) {
        Weather weather = weatherService.getWeatherNow(place);
        if (weather.getMain() != null)
            return new WeatherDto(weather, HttpStatus.OK.toString());
        return new WeatherDto(HttpStatus.NOT_FOUND.toString());
    }

}