package com.example.spb_api.dto.weather;

import com.example.spb_api.i18n.I18nUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDTO {
    private String city;

    private String conditionWeather;

    private float temp;

    private float windKph;

    private float feelsLike;

    private float pressureIn;

    private float humidity;

    private float heatIndex;

    private float dewPoint;

    private float precip;

    private float gustKph;

    private float isDay;

    private String time;
}

