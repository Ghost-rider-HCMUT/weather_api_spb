package com.example.spb_api.mapper;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeatherMapper {
    // Convert from Entity to DTO
    public WeatherDTO toDto(WeatherEntity entity) {
        return WeatherDTO.builder()
                .city(entity.getCity())
                .conditionWeather(entity.getConditionWeather())
                .temp(entity.getTemp())
                .windKph(entity.getWindKph())
                .feelsLike(entity.getFeelsLike())
                .pressureIn(entity.getPressureIn())
                .humidity(entity.getHumidity())
                .heatIndex(entity.getHeatIndex())
                .dewPoint(entity.getDewPoint())
                .precip(entity.getPrecip())
                .gustKph(entity.getGustKph())
                .isDay(entity.getIsDay())
                .time(entity.getTime())
                .build();
    }

    public WeatherEntity toEntity(Optional<Long> id, WeatherDTO weatherDTO) {
        return WeatherEntity.builder()
                .id(id.orElse(null))
                .city(weatherDTO.getCity())
                .conditionWeather(weatherDTO.getConditionWeather())
                .temp(weatherDTO.getTemp())
                .windKph(weatherDTO.getWindKph())
                .feelsLike(weatherDTO.getFeelsLike())
                .pressureIn(weatherDTO.getPressureIn())
                .humidity(weatherDTO.getHumidity())
                .heatIndex(weatherDTO.getHeatIndex())
                .dewPoint(weatherDTO.getDewPoint())
                .precip(weatherDTO.getPrecip())
                .gustKph(weatherDTO.getGustKph())
                .isDay(weatherDTO.getIsDay())
                .time(weatherDTO.getTime())
                .build();
    }

}

