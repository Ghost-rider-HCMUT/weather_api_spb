package com.example.spb_api.service.impl;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.mapper.WeatherMapper;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.service.WeatherService;
import com.example.spb_api.util.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public Page<WeatherEntity> getAllCityWeather(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return weatherRepository.findAllLatest(pageable);
    }

    @Override
    public WeatherDTO getWeatherByCityName(String city) {
        System.out.println(weatherRepository.findLatestByCity(StringUtil.cleanString(city)));
        WeatherEntity weatherEntity = weatherRepository.findLatestByCity(StringUtil.cleanString(city));
        return weatherMapper.toDto(weatherEntity);
    }

    @Override
    public WeatherDTO createWeather(WeatherEntity weatherEntity) {
        weatherRepository.save(weatherEntity);
        return weatherMapper.toDto(weatherEntity);
    }

    @Override
    public WeatherDTO updateWeatherByCityName(String city, WeatherDTO weatherDTO) {
        WeatherEntity weatherEntity;
        WeatherEntity optionalWeatherEntity = weatherRepository.findLatestByCity(city);
        if (optionalWeatherEntity != null) {
            Long id = optionalWeatherEntity.getId();
            weatherEntity = weatherMapper.toEntity(Optional.ofNullable(id), weatherDTO);
            weatherRepository.save(weatherEntity);
            return weatherDTO;
        } else {
            return null;
        }
    }

    @Override
    public void deleteWeatherByCityName(String city) {
        weatherRepository.deleteByCity(city);
    }

    @Override
    public List<WeatherEntity> getWeathersByCityName(String city) {
        return weatherRepository.findByCity(StringUtil.cleanString(city));
    }
}
