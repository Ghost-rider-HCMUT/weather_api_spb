package com.example.spb_api.service;

import com.example.spb_api.entity.Weather;
import java.util.List;

public interface WeatherService {
    List<Weather> getAllCityWeather();
    Weather getWeatherByCityName(String cityName);
    Weather createWeather(Weather weather);
    Weather updateWeatherByCityName(String cityName, Weather weather);
    String deleteWeatherByCityName(String cityName);
}
