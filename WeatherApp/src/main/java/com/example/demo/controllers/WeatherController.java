package com.example.demo.controllers;

import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/{place}", method = RequestMethod.GET)
    public void getWeatherNow(@PathVariable("place") String place) {
        weatherService.getWeatherNow(place);
    }
}
