package com.example.demo.service;

import com.example.demo.commonfunctions.InitialiseNewWeatherObject;
import com.example.demo.commonfunctions.JsonOperations;
import com.example.demo.entity.Weather;
import com.example.demo.handlers.RestTemplateResponseErrorHandler;
import com.google.gson.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        String responseCode = jsonObject.get("cod").getAsString();
        if (responseCode.equals("200")) {
            initialiseWeather = new InitialiseNewWeatherObject();
            Weather weather = new Weather();
            weather = initialiseWeather.functionInitialiseNewWeatherObject(jsonObject);
            try {
                jsonOperations.writeJson(weather);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gson.toJson(weather);
        }
        return gson.toJson("{ \"message\" : \"This city is not in our database!\", \"code\": \"404\" }");

    }
}

