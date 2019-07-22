package com.example.demo.service;

import com.example.demo.entity.Weather;
import com.google.gson.*;
import net.minidev.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



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

        JsonObject element = jsonObject.get("sys").getAsJsonObject();
        System.out.println(element.get("country"));
        weather.setCountry(element.get("country").getAsString());

        weather.setCity(jsonObject.get("name").getAsString());


        try {
            writeJson(weather);
        } catch (IOException e) {
            e.printStackTrace();
        }

   }

    private void writeJson(Weather weather) throws IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
       // FileWriter writer = new FileWriter("weather.json");
       // System.out.println("json ul e"+gson.toJson(weather));
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(new FileReader("C:\\Users\\mihai.botez\\Desktop\\git\\Proiect\\Weather\\WeatherApp\\src\\main\\java\\com\\example\\demo\\service\\weather.json"));
        JSONArray jsonArray = (JSONArray)obj;
        jsonArray.add(gson.toJson(weather));
        FileWriter file = new FileWriter("./weather.json");
        file.write(jsonArray.toJSONString());
        file.flush();
        file.close();



    }
}
