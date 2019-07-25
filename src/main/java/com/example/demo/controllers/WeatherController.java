package com.example.demo.controllers;

import com.example.demo.service.WeatherService;
import com.google.gson.*;
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
        String weather = weatherService.getWeatherNow(place);
        JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(weather).getAsJsonObject();
        if (jo.has("code"))
            return new ResponseEntity<String>(weather, HttpStatus.NOT_FOUND);
        return new ResponseEntity<String>(weather, HttpStatus.OK);
    }
}