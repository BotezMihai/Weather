package com.example.demo.service;

import com.example.demo.commonfunctions.InitialiseNewWeatherObject;
import com.example.demo.commonfunctions.JsonOperations;
import com.example.demo.commonfunctions.SharedVariables;
import com.example.demo.entity.Weather;
import com.example.demo.handlers.RestTemplateResponseErrorHandler;
import com.google.gson.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Configuration
public class WeatherService {
    @Value("${url.weatherUrl}")
    String apiUrl;
    @Value("${key.keyForApi}")
    String apiKey;
    public InitialiseNewWeatherObject initialiseWeather;

    @Autowired
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    public String getWeatherNow(String place) {
        RestTemplate restTemplate = new RestTemplate();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setLenient().create();
        JsonOperations jsonOperations = new JsonOperations();
        String url = apiUrl + place + "&APPID=" + apiKey;
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        SharedVariables sharedVariables = new SharedVariables(restTemplate, url);
        if (sharedVariables.getResponseCode().equals("200")) {
            initialiseWeather = new InitialiseNewWeatherObject();
            Weather weather = new Weather();
            weather = initialiseWeather.functionInitialiseNewWeatherObject(sharedVariables.getJsonObject());
            try {
                jsonOperations.writeJson(weather);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gson.toJson(weather);
        }
        return gson.toJson(jsonOperations.getJsonObject("{ \"message\" : \"This city is not in our database!\", \"code\": \"404\"}"));
    }
}

