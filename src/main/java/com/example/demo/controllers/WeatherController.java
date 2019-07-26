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
    public ResponseEntity<String> getWeatherNow(@PathVariable("place") String place) {
        String weather = weatherService.getWeatherNow(place);
        JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(weather).getAsJsonObject();
        if (jo.has("code"))
            return new ResponseEntity<>(weather, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }

    @RequestMapping(value = "/dto/{place}", method = RequestMethod.GET)
    public WeatherDto getDtoWeatherNow(@PathVariable("place") String place) {
        String weather = weatherService.getWeatherNow(place);
        JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(weather).getAsJsonObject();
        if (!jo.has("code"))
            return new WeatherDto(jo.get("main").getAsString(), jo.get("description").getAsString(), jo.get("temperature").getAsDouble(), jo.get("umidity").getAsDouble(), jo.get("windSpeed").getAsDouble(), jo.get("clouds").getAsDouble(), jo.get("country").getAsString(), jo.get("city").getAsString(), "ok");
        return new WeatherDto("not found");
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertStudent(@RequestBody Weather weather) {
        weatherService.createWeather(weather);
        return new ResponseEntity<String>("Added the student " + weather, HttpStatus.OK);
    }
}