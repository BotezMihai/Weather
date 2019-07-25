package com.example.demo.commonfunctions;

import com.example.demo.constants.ApiWeatherConstants;
import com.example.demo.entity.Weather;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class InitialiseNewWeatherObject {

    public InitialiseNewWeatherObject() {

    }

    public Weather functionInitialiseNewWeatherObject(JsonObject jsonObject) {


        JsonElement weatherElement = jsonObject.get("weather").getAsJsonArray().get(0);

        Weather weather = new Weather();

        weather.setMain(weatherElement.getAsJsonObject().get("main").getAsString());

        weather.setDescription(weatherElement.getAsJsonObject().get("description").getAsString());

        double kelvin = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
        double celsius = kelvin - ApiWeatherConstants.CELSIUS_DEGREES;

        weather.setTemperature(celsius);

        weather.setUmidity(jsonObject.get("main").getAsJsonObject().get("humidity").getAsDouble());

        weather.setWindSpeed(jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble());

        weather.setClouds(jsonObject.get("clouds").getAsJsonObject().get("all").getAsDouble());

        JsonObject element = jsonObject.get("sys").getAsJsonObject();

        weather.setCountry(element.get("country").getAsString());

        weather.setCity(jsonObject.get("name").getAsString());

        return weather;


    }
}
