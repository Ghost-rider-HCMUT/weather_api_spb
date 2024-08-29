package com.example.spb_api.controller;

import com.example.spb_api.entity.Weather;
import com.example.spb_api.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/weather")
public class WeatherController {

    @Autowired
    private  WeatherService weatherService;

    @GetMapping
    public List<Weather> getWeather() {
        return weatherService.getAllCityWeather();
    }

    @GetMapping("/{city}")
    public Weather getWeatherByCity(@PathVariable("city") String city) {
        return weatherService.getWeatherByCityName(city);
    }

    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {
        Weather savedWeather = weatherService.createWeather(weather);
        return new ResponseEntity<>(savedWeather, HttpStatus.CREATED);
    }

    @PatchMapping("/{city}")
    public Weather updateWeather(@PathVariable String city, @RequestBody Weather weather) {
        return weatherService.updateWeatherByCityName(city, weather);
    }

    @DeleteMapping("/{city}")
    public String deleteWeather(@PathVariable String city) {
        return weatherService.deleteWeatherByCityName(city);
    }
}
