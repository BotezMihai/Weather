package com.example.demo.scheduledtasks;

import com.example.demo.entity.Weather;
import com.example.demo.handlers.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.*;
import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;

@Component
public class WeatherCrawler {

    public WeatherCrawler() {

    }

    @Autowired
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Scheduled(cron = "0 0 2 ? * * *")
    public void crawl() {
        String[] cities = {"Alba", "Arad", "Bacau", "Botosani", "Braila", "Brasov", "Bucuresti", "Buzau", "Calarasi", "Caras Severin", "Cluj", "Constanta", "Covasna", "Dambovita", "Dolj", "Galati", "Giurgiu", "Gorj", "Harghita", "Hunedoara", "Ialomita", "Iasi", "Ilfov", "Maramures", "Mehedinti", "Mures", "Neamt", "Olt", "Prahova", "Salaj", "Satu Mare", "Sibiu", "Suceava", "Teleorman", "Timis", "Tulcea", "Valcea", "Vaslui", "Vrancea"};
        for (String city : cities) {
            // call to OWM API
            String key = "58f9b3be23fbf4bff065c9fa498cbe75";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + ",RO&APPID=" + key;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String body = response.getBody();

            // get JSON response
            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
            String responseCode = jsonObject.get("cod").getAsString();

            if (!responseCode.equals("404")) {
                JsonElement weatherElement = jsonObject.get("weather").getAsJsonArray().get(0);

                // create weather object
                Weather weather = new Weather();
                weather.setMain(weatherElement.getAsJsonObject().get("main").getAsString());
                weather.setDescription(weatherElement.getAsJsonObject().get("description").getAsString());
                weather.setUmidity(jsonObject.get("main").getAsJsonObject().get("humidity").getAsDouble());
                weather.setWindSpeed(jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble());
                weather.setClouds(jsonObject.get("clouds").getAsJsonObject().get("all").getAsDouble());
                JsonObject sysElement = jsonObject.get("sys").getAsJsonObject();
                weather.setCountry(sysElement.get("country").getAsString());
                weather.setCity(jsonObject.get("name").getAsString());

                // converting from fahrenheit to celsius metric
                double kelvin = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
                double celsius = kelvin -273.15;
                weather.setTemperature(celsius);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.setLenient().create();

                // read from weather.json
                File jsonFile = new File("weather.json");
                JsonElement fileContent = null;
                try {
                    fileContent = new JsonParser().parse(new FileReader(jsonFile.getAbsolutePath()));
                } catch (FileNotFoundException e) {
                    System.out.println("There was a problem trying to read from the file!");
                }

                JsonArray fileArray = null;
                if (fileContent != null) {
                    fileArray = fileContent.getAsJsonArray();
                }

                // add element in json array
                if (fileArray != null) {
                    fileArray.add(gson.toJson(weather));
                }

                // write to file
                String jsonString = gson.toJson(fileArray);
                FileWriter fileWriter;
                try {
                    fileWriter = new FileWriter(jsonFile.getAbsolutePath());
                    fileWriter.write(jsonString);
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("There was a problem writing in/closing the file!");
                }
            }
        }
    }
}