package com.example.spb_api.service.impl;

import com.example.spb_api.entity.Weather;
import com.example.spb_api.service.WeatherService;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.util.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository weatherRepository;

    @Override
    public List<Weather> getAllCityWeather(){
        return weatherRepository.findAll();
    }

    @Override
    public Weather  getWeatherByCityName(String city){
        Optional<Weather> query = weatherRepository.findById(StringUtil.cleanString(city));
        return query.orElse(null);
    }

    @Override
    public Weather createWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public Weather updateWeatherByCityName(String city, Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public String deleteWeatherByCityName(String city) {
         weatherRepository.deleteById(city);
         return "Successfully deleted";
    }

}
