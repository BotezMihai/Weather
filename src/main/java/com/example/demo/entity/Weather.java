package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String main;
    private String description;
    private double temperature;
    private double umidity;
    private double windSpeed;
    private double clouds;
    private String country;
    private String city;

    public Weather(String main, String description, double temperature, double umidity, double windSpeed, double clouds, String country, String city) {
        this.main = main;
        this.description = description;
        this.temperature = temperature;
        this.umidity = umidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.country = country;
        this.city = city;
    }

    public Weather() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}