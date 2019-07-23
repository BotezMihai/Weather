package com.example.demo.scheduledtasks;

import com.example.demo.entity.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherCrawler {
    @Scheduled(cron = "0 0 0/2 ? * * *")
    public void crawl() {
        String[] cities = {"Alba", "Arad", "Arges", "Bacau", "Bihor", "Bistrita Nasaud", "Botosani", "Braila", "Brasov", "Bucuresti", "Buzau", "Calarasi", "Caras Severin", "Cluj", "Constanta", "Covasna", "Dambovita", "Dolj", "Galati", "Giurgiu", "Gorj", "Harghita", "Hunedoara", "Ialomita", "Iasi", "Ilfov", "Maramures", "Mehedinti", "Mures", "Neamt", "Olt", "Prahova", "Salaj", "Satu Mare", "Sibiu", "Suceava", "Teleorman", "Timis", "Tulcea", "Valcea", "Vaslui", "Vrancea"};
        for (String city : cities) {
            // call to OWM API
            String key = "58f9b3be23fbf4bff065c9fa498cbe75";
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String body = response.getBody();

            // get JSON response
            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
            JsonElement weatherElement = jsonObject.get("weather").getAsJsonArray().get(0);
            System.out.println(weatherElement.getAsJsonObject().get("main"));

            // create weather object
            Weather weather = new Weather();
            weather.setMain(weatherElement.getAsJsonObject().get("main").getAsString());
            weather.setDescription(weatherElement.getAsJsonObject().get("description").getAsString());
            // converting from fahrenheit to celsius metric
            double fahrenheit = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
            double celsius = ((fahrenheit - 32) * 5) / 9;
            weather.setTemperature(celsius);
            weather.setUmidity(jsonObject.get("main").getAsJsonObject().get("humidity").getAsDouble());
            weather.setWindSpeed(jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble());
            weather.setClouds(jsonObject.get("clouds").getAsJsonObject().get("all").getAsDouble());
            weather.setCountry(jsonObject.get("country").getAsString());
            weather.setCity(jsonObject.get("city").getAsString());

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonWeather;
            try {
                jsonWeather = objectWriter.writeValueAsString(weather);
                System.out.println("Vremea" + jsonWeather);
            } catch (JsonProcessingException e) {
                System.out.println("An error occured while trying to convert from object to JSON.");
            }
        }
    }
}