package com.example.demo.service;

import com.example.demo.entity.Weather;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    public void getWeatherNow(String place) {
        String key = "58f9b3be23fbf4bff065c9fa498cbe75";
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + place + "&APPID=" + key;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        //System.out.println(response);
        String test = response.getBody();
        JsonObject jsonObject = new JsonParser().parse(test).getAsJsonObject();
        System.out.print(jsonObject.get("coord"));
        System.out.println(jsonObject.get("weather"));
//        Weather weather = new Weather();
//        weather.setMain(jsonObject.get("weather.main").getAsString());
//        weather.setDescription(jsonObject.get("weather.description").getAsString());
    }
}
