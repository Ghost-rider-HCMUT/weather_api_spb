package com.example.spb_api.repository;

import com.example.spb_api.entity.WeatherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    // Get All Latest
    @Query("SELECT w FROM WeatherEntity w ORDER BY w.time DESC")
    Page<WeatherEntity> findAllLatest(Pageable pageable);

    // Get Latest By City
    @Query("SELECT w FROM WeatherEntity w WHERE w.city = :city ORDER BY w.time DESC LIMIT 1")
    WeatherEntity findLatestByCity(String city);

    // Get All By City
    @Query("SELECT w FROM WeatherEntity w WHERE w.city = :city")
    List<WeatherEntity> findByCity(String city);

    // Delete By City
    @Modifying
    @Query("DELETE FROM WeatherEntity w WHERE w.city = :city")
    void deleteByCity(String city);

    // Delete By Time
    @Modifying
    @Query("DELETE FROM WeatherEntity w WHERE w.time = :time")
    void deleteByTime(String time);
}
