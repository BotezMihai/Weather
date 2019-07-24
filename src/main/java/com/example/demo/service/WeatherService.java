package com.example.demo.service;

import com.example.demo.entity.Weather;
import com.google.gson.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


@Service
public class WeatherService {

    public Weather getWeatherNow(String place) {
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

        double kelvin = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
        double celsius = kelvin - 273.15;
        System.out.println(celsius);
        weather.setTemperature(celsius);

        weather.setUmidity(jsonObject.get("main").getAsJsonObject().get("humidity").getAsDouble());

        weather.setWindSpeed(jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble());

        weather.setClouds(jsonObject.get("clouds").getAsJsonObject().get("all").getAsDouble());

        JsonObject element = jsonObject.get("sys").getAsJsonObject();
        System.out.println(element.get("country"));
        weather.setCountry(element.get("country").getAsString());

        weather.setCity(jsonObject.get("name").getAsString());

        try {
            writeJson(weather);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setLenient().create();
        return weather;
    }

    /**
     * @param weather
     * @throws IOException
     */
    private void writeJson(Weather weather) throws IOException {
        //creez un obiect gson
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setLenient().create();
        //citesc din weather.json
        File jsonFile = new File("weather.json");
        JsonElement obj = new JsonParser().parse(new FileReader(jsonFile.getAbsolutePath()));
        JsonArray arr = obj.getAsJsonArray();
        //adaug element in array
        arr.add(gson.toJson(weather));
        //scriu in fisier
        String jsonString = gson.toJson(arr);
        FileWriter fileWriter = new FileWriter(jsonFile.getAbsolutePath());
        fileWriter.write(jsonString);
        fileWriter.close();
    }
}
