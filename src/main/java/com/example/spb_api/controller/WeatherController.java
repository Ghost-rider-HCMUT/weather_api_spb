package com.example.spb_api.controller;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.i18n.I18nUtil;
import com.example.spb_api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final I18nUtil i18nUtil;

    @GetMapping
    public ResponseEntity<Page<WeatherEntity>> getWeather(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<WeatherEntity> weatherEntityList = weatherService.getAllCityWeather(pageNo, pageSize);
        System.out.println(i18nUtil.getMessage("success.message"));
        return ResponseEntity.ok(weatherEntityList);
    }

    @PostMapping
    public ResponseEntity<WeatherDTO> createWeather(@RequestBody WeatherEntity weatherEntity) {
        WeatherDTO weatherDTO = weatherService.createWeather(weatherEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherDTO);
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDTO> getWeatherByCity(@PathVariable String city) {
        WeatherDTO weatherDTO = weatherService.getWeatherByCityName(city);
        return ResponseEntity.ok(weatherDTO);
    }

    @PutMapping("/{city}")
    public ResponseEntity<WeatherDTO> updateWeather(@PathVariable String city, @RequestBody WeatherDTO weatherDTO) {
        WeatherDTO updatedWeatherDTO = weatherService.updateWeatherByCityName(city, weatherDTO);
        return new ResponseEntity<>(updatedWeatherDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{city}")
    public ResponseEntity<String> deleteWeather(@PathVariable String city) {
        weatherService.deleteWeatherByCityName(city);
        return ResponseEntity.ok("Weather data for city " + city + " deleted successfully");
    }

    @GetMapping("/search/{city}")
    public ResponseEntity<List<WeatherEntity>> searchWeather(@PathVariable String city) {
        List<WeatherEntity> listWeathersByCity = weatherService.getWeathersByCityName(city);
        return ResponseEntity.ok(listWeathersByCity);
    }
}