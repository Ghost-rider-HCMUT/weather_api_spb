package com.example.spb_api.repository;

import com.example.spb_api.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, String> {
    // Query Method
}
