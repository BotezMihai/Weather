package com.example.demo.scheduledtasks;

import com.example.demo.commonfunctions.InitialiseNewWeatherObject;
import com.example.demo.commonfunctions.JsonOperations;
import com.example.demo.commonfunctions.SharedVariables;
import com.example.demo.entity.Weather;
import com.example.demo.enums.Cities;
import com.example.demo.handlers.RestTemplateResponseErrorHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;


@Component
public class WeatherCrawler {

    public WeatherCrawler() {

    }

    @Autowired
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Value("${url.weatherUrl}")
    String apiUrl;
    @Value("${key.keyForApi}")
    String apiKey;
    public InitialiseNewWeatherObject initialiseWeather;

    @Scheduled(cron = "0 0 2 ? * *")
    public void crawl() {
        JsonOperations jsonOperations = new JsonOperations();
        for (Cities city : Cities.values()) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
            String url = apiUrl + city + ",RO&APPID=" + apiKey;
            SharedVariables sharedVariables = new SharedVariables(restTemplate, url);
            if (!sharedVariables.getResponseCode().equals(HttpStatus.NOT_FOUND.value())) {
                Weather weather = new Weather();
                initialiseWeather = new InitialiseNewWeatherObject();
                weather = initialiseWeather.functionInitialiseNewWeatherObject(sharedVariables.getJsonObject());
                try {
                    jsonOperations.writeJson(weather);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}