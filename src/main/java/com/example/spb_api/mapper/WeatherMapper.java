package com.example.spb_api.mapper;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

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

    public WeatherEntity toEntity(WeatherDTO dto, Long id) {
        WeatherEntity.WeatherEntityBuilder builder = WeatherEntity.builder()
                .city(dto.getCity())
                .conditionWeather(dto.getConditionWeather())
                .temp(dto.getTemp())
                .windKph(dto.getWindKph())
                .feelsLike(dto.getFeelsLike())
                .pressureIn(dto.getPressureIn())
                .humidity(dto.getHumidity())
                .heatIndex(dto.getHeatIndex())
                .dewPoint(dto.getDewPoint())
                .precip(dto.getPrecip())
                .gustKph(dto.getGustKph())
                .isDay(dto.getIsDay())
                .time(dto.getTime());

        if (id != null) {
            builder.id(id);
        }
        return builder.build();
    }
}