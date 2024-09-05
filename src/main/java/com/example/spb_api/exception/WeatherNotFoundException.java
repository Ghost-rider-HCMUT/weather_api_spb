package com.example.spb_api.exception;

public class WeatherNotFoundException extends RuntimeException {
    public WeatherNotFoundException(String city) {
        super("Weather data not found for city: " + city);
    }
}
