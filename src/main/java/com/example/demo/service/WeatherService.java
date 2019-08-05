package com.example.demo.service;

import com.example.demo.annotations.CountIncrement;
import com.example.demo.commonfunctions.InitialiseNewWeatherObject;
import com.example.demo.commonfunctions.JsonOperations;
import com.example.demo.commonfunctions.SharedVariables;
import com.example.demo.entity.Weather;
import com.example.demo.handlers.RestTemplateResponseErrorHandler;
import com.example.demo.interfaces.WeatherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

@Service
@Configuration
public class WeatherService {
    @Value("${url.weatherUrl}")
    private String apiUrl;
    @Value("${key.keyForApi}")
    private String apiKey;

    private InitialiseNewWeatherObject initialiseWeather = new InitialiseNewWeatherObject();

    @Autowired
    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Autowired
    private WeatherRepository weatherRepository;

    @CountIncrement
    public Weather getWeatherNow(String place) {
        RestTemplate restTemplate = new RestTemplate();
        JsonOperations jsonOperations = new JsonOperations();
        String url = apiUrl + place + "&APPID=" + apiKey;
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        SharedVariables sharedVariables = new SharedVariables(restTemplate, url);
        if (sharedVariables.getResponseCode().equals(String.valueOf(HttpStatus.OK.value()))) {
            Weather weather = initialiseWeather.functionInitialiseNewWeatherObject(sharedVariables.getJsonObject());
            try {
                jsonOperations.writeJson(weather);
            } catch (IOException e) {
                e.printStackTrace();
            }
            createWeather(weather);
            return weather;
        }
        return new Weather();
    }

    public void createWeather(Weather weather) {
        weatherRepository.save(weather);
    }

    /**
     * Returns the most recent forecast for a city
     * @param city the city
     * @return the most recent forecast
     */
    public Weather mostRecentWeatherForCity(String city) {
        Iterable<Weather> allForecasts = weatherRepository.findAll();
        ArrayList<Weather> citysForecasts = new ArrayList<>();
        for (Weather weather : allForecasts) {
            if (weather.getCity().equals(city))
                citysForecasts.add(weather);
        }
        citysForecasts.sort(Comparator.comparing(Weather::getTimestamp).reversed());
        return citysForecasts.get(0);
    }
}