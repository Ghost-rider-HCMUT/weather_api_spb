package com.example.spb_api.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "weather")
public class Weather {
    @Id
    private String city;

    @Column(nullable = false)
    private String condition_weather;

    @Column(nullable = false)
    private float temp;

    @Column(nullable = false)
    private float wind_kph;

    @Column(nullable = false)
    private float feelslike_c;

    @Column(nullable = false)
    private float pressure_in;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private float heatindex_c;

    @Column(nullable = false)
    private float dewpoint_c;

    @Column(nullable = false)
    private float precip_mm;

    @Column(nullable = false)
    private float gust_kph;

    @Column(nullable = false)
    private float is_day;

    @Column(nullable = false)
    private String time;
}
