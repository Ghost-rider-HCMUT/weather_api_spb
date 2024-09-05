package com.example.spb_api.controller;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.mapper.WeatherMapper;
import com.example.spb_api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherMapper weatherMapper;

    @GetMapping
    public ResponseEntity<List<WeatherEntity>> getWeather() {
        List<WeatherEntity> weatherEntityList = weatherService.getAllCityWeather();
        return new ResponseEntity<>(weatherEntityList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WeatherDTO> createWeather(@RequestBody WeatherEntity WeatherEntity) {
        WeatherDTO weatherDTO = weatherService.createWeather(WeatherEntity);
        return new ResponseEntity<>(weatherDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDTO> getWeatherByCity(@PathVariable String city) {
        WeatherDTO weatherDTO = weatherService.getWeatherByCityName(city);
        return new ResponseEntity<>(weatherDTO, HttpStatus.OK);
    }

    @PatchMapping("/{city}")
    public ResponseEntity<WeatherDTO> updateWeather(@PathVariable String city, @RequestBody WeatherDTO weatherDTO) {
        WeatherDTO updatedWeatherDTO = weatherService.updateWeatherByCityName(city, weatherDTO);
        return new ResponseEntity<>(updatedWeatherDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{city}")
    public ResponseEntity<String> deleteWeather(@PathVariable String city) {
        weatherService.deleteWeatherByCityName(city);
        return new ResponseEntity<>("Weather data for city " + city + " deleted successfully", HttpStatus.OK);
    }

    // Feature Search
    @GetMapping("/search/{city}")
    public ResponseEntity<List<WeatherEntity>> searchWeather(@PathVariable String city) {
        List<WeatherEntity> listWeathersByCity = weatherService.getWeathersByCityName(city);
        return new ResponseEntity<>(listWeathersByCity, HttpStatus.OK);
    }
}
