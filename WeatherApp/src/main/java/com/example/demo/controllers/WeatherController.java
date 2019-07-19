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
        String key = "58f9b3be23fbf4bff065c9fa498cbe75";
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + place + "&APPID=" + key;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        //System.out.println(response);
        String test = response.getBody();
        JsonObject jsonObject = new JsonParser().parse(test).getAsJsonObject();
        System.out.print(jsonObject.get("coord"));


    }


}
