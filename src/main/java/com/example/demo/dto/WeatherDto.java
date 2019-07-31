package com.example.demo.dto;

import com.example.demo.entity.Weather;

public class WeatherDto {
    private String main;
    private String description;
    private double temperature;
    private double umidity;
    private double windSpeed;
    private double clouds;
    private String country;
    private String city;
    private String status;

    public WeatherDto(String main, String description, double temperature, double umidity, double windSpeed, double clouds, String country, String city, String status) {
        this.main = main;
        this.description = description;
        this.temperature = temperature;
        this.umidity = umidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.country = country;
        this.city = city;
        this.status = status;
    }

    public WeatherDto(Weather weather, String status) {
        this.main = weather.getMain();
        this.description = weather.getDescription();
        this.city = weather.getCity();
        this.country = weather.getCountry();
        this.clouds = weather.getClouds();
        this.temperature = weather.getTemperature();
        this.umidity = weather.getUmidity();
        this.windSpeed = weather.getWindSpeed();
        this.status = status;
    }

    public WeatherDto(String status) {
        this.main = null;
        this.description = null;
        this.temperature = 0;
        this.umidity = 0;
        this.windSpeed = 0;
        this.clouds = 0;
        this.country = null;
        this.city = null;
        this.status = status;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public double getUmidity() {
        return umidity;
    }

    public void setUmidity(double umidity) {
        this.umidity = umidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
