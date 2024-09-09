package com.example.spb_api.schedule;

import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherScheduler {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WeatherRepository weatherRepository;
    private final String[] fileContent;

    @Value("${app.api-url}")
    private String apiUrl;

    @Value("${app.api-key}")
    private String apiKey;

    @Scheduled(fixedRateString = "${app.time-interval}")
    public void fetchDataAndUpdateDB() {
        String lastUpdatedTime = null;
        long startTime = System.currentTimeMillis();
        for (String province : fileContent) {
            try {
                String url = String.format("%s?key=%s&q=%s", apiUrl, apiKey, province);
                String jsonResponse = Optional.ofNullable(restTemplate.getForObject(url, String.class))
                        .orElseThrow(() -> new RuntimeException("Failed to fetch data for " + province));

                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode currentNode = jsonNode.get("current");

                WeatherEntity weatherEntity = buildWeatherEntity(currentNode, StringUtil.cleanString(province));
                weatherRepository.save(weatherEntity);

                lastUpdatedTime = currentNode.get("last_updated").asText();
                log.info("Updated weather data for {}", province);
            } catch (Exception e) {
                log.error("Error processing weather data for {}: {}", province, e.getMessage());
            }
        }

        if (lastUpdatedTime != null) {
            deleteOldData(lastUpdatedTime);
        }

        log.info("FetchDataAndUpdateDB completed successfully!");
        long endTime = System.currentTimeMillis();
        log.info("FetchDataAndUpdateDB completed in {} ms", (endTime - startTime));
    }

    private WeatherEntity buildWeatherEntity(JsonNode currentNode, String province) {
        return WeatherEntity.builder()
                .city(province)
                .conditionWeather(currentNode.get("condition").get("text").asText())
                .temp(currentNode.get("temp_c").floatValue())
                .windKph(currentNode.get("wind_kph").floatValue())
                .feelsLike(currentNode.get("feelslike_c").floatValue())
                .pressureIn(currentNode.get("pressure_in").floatValue())
                .humidity(currentNode.get("humidity").floatValue())
                .heatIndex(currentNode.get("heatindex_c").floatValue())
                .dewPoint(currentNode.get("dewpoint_c").floatValue())
                .precip(currentNode.get("precip_mm").floatValue())
                .gustKph(currentNode.get("gust_kph").floatValue())
                .isDay(currentNode.get("is_day").asInt())
                .time(LocalDateTime.parse(currentNode.get("last_updated").asText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    private void deleteOldData(String lastUpdatedTime) {
        try {
            LocalDateTime cutoffTime = StringUtil.handleTime(lastUpdatedTime);
            weatherRepository.deleteByTimeBefore(cutoffTime);
            log.info("Deleted weather data older than {}", cutoffTime);
        } catch (Exception e) {
            log.error("Error deleting old weather data: {}", e.getMessage());
        }
    }
}