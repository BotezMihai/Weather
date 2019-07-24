package com.example.demo.controllers;

import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/{place}", method = RequestMethod.GET)
    public ResponseEntity<String> getWeatherNow(@PathVariable("place") String place) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setLenient().create();
        Weather weather = weatherService.getWeatherNow(place);
        return new ResponseEntity<>(gson.toJson(weather), HttpStatus.OK);
    }
}
