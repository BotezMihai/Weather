package com.example.demo.service;

import com.example.demo.entity.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
        String body = response.getBody();
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        JsonElement weatherElement = jsonObject.get("weather").getAsJsonArray().get(0);
        System.out.println(weatherElement.getAsJsonObject().get("main"));
        Weather weather = new Weather();
        weather.setMain(weatherElement.getAsJsonObject().get("main").getAsString());
        weather.setDescription(weatherElement.getAsJsonObject().get("description").getAsString());

        double fahrenheit = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
        double celsius = ((fahrenheit - 32) * 5) / 9;
        System.out.println(celsius);
        weather.setTemperature(celsius);

        weather.setUmidity(jsonObject.get("main").getAsJsonObject().get("humidity").getAsDouble());

        weather.setWindSpeed(jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble());

        weather.setClouds(jsonObject.get("clouds").getAsJsonObject().get("all").getAsDouble());

        weather.setCountry(jsonObject.get("country").getAsString());

        weather.setCity(jsonObject.get("city").getAsString());



    }
}
