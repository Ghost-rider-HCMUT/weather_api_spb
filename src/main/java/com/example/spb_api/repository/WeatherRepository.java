package com.example.spb_api.repository;

import com.example.spb_api.entity.WeatherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    // Get All Latest
    @Query("SELECT w FROM WeatherEntity w ORDER BY w.time DESC")
    Page<WeatherEntity> findAllLatest(Pageable pageable);

    // Get Latest By City
    @Query("SELECT w FROM WeatherEntity w WHERE w.city = :city ORDER BY w.time DESC LIMIT 1")
    WeatherEntity findLatestByCity(@Param("city") String city);

    // Get All By City
    @Query("SELECT w FROM WeatherEntity w WHERE w.city = :city ORDER BY w.time DESC")
    List<WeatherEntity> findByCity(@Param("city") String city);

    // Delete By City
    @Modifying
    @Transactional
    @Query("DELETE FROM WeatherEntity w WHERE w.city = :city")
    void deleteByCity(@Param("city") String city);

    // Delete By Time
    @Modifying
    @Transactional
    @Query("DELETE FROM WeatherEntity w WHERE w.time < :time")
    void deleteByTimeBefore(LocalDateTime time);
}