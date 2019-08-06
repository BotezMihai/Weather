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
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Service
@Configuration
public class WeatherService {
    @Value("${url.weatherUrl}")
    private String apiUrl;
    @Value("${key.keyForApi}")
    private String apiKey;
    @Value("${api.numberOfApiCallsPerMinute}")
    private int numberOfApiCallsPerMinute;

    private InitialiseNewWeatherObject initialiseWeather = new InitialiseNewWeatherObject();

    @Autowired
    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Autowired
    private WeatherRepository weatherRepository;

    /**
     * Makes a call to an API to return the weather forecast for a specific city
     *
     * @param city the city
     * @return the forecast for the city
     */
    @CountIncrement
    public Weather getWeatherNow(String city) {
        if (SharedVariables.getCount() <= this.numberOfApiCallsPerMinute) {
            RestTemplate restTemplate = new RestTemplate();
            JsonOperations jsonOperations = new JsonOperations();
            String url = apiUrl + city + "&APPID=" + apiKey;
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
        } else {
            return findLastForecastForCityFromDb(city);
        }

        return new Weather();
    }

    /**
     * Save the forecast in the DB
     *
     * @param weather the forecast
     */
    public void createWeather(Weather weather) {
        weatherRepository.save(weather);
    }

    /**
     * Retrieves all weather forecasts for a specific city
     *
     * @param city the city
     * @return all weather forecasts for the city
     */
    public List<Weather> findAllByCity(String city) {
        return weatherRepository.findAllByCity(city);
    }

    /**
     * Returns the most recent forecast for a city
     *
     * @param city the city
     * @return the most recent forecast
     */
    public Weather findLastForecastForCityFromDb(String city) {
        List<Weather> weatherList = this.findAllByCity(city);
        weatherList.sort(Comparator.comparing(Weather::getTimestamp).reversed());
        Weather lastForecast;
        try {
            lastForecast = weatherList.get(0);
        } catch (Exception e) {
            return new Weather();
        }
        return lastForecast;
    }

}