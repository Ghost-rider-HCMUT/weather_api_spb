package com.example.spb_api.service;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;

import java.util.List;

public interface WeatherService {
    List<WeatherEntity> getAllCityWeather();

    WeatherDTO getWeatherByCityName(String cityName);

    WeatherDTO createWeather(WeatherEntity weatherEntity);

    WeatherDTO updateWeatherByCityName(String city, WeatherDTO weatherDTO);

    void deleteWeatherByCityName(String cityName);

    List<WeatherEntity> getWeathersByCityName(String cityName);
}
