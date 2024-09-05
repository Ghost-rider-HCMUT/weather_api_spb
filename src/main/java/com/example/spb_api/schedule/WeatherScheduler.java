package com.example.spb_api.schedule;

import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    public void fetDataAndUpdateDB() {
        String time = "";
        try {
            for (String province : fileContent) {
                // Fetch Data
                String url = String.format("%s?key=%s&q=%s", apiUrl, apiKey, province);
                String jsonResponse = restTemplate.getForObject(url, String.class);
                if (jsonResponse == null) {
                    System.out.println(province + "is null");
                    break;
                }
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                JsonNode currentNode = jsonNode.get("current");
                System.out.println(StringUtil.cleanString(province));
                // Handle Update DB
                WeatherEntity weatherEntity = WeatherEntity.builder()
                        .city(StringUtil.cleanString(province))
                        .conditionWeather(currentNode.get("condition").get("text").toString())
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
                        .time(currentNode.get("last_updated").asText())
                        .build();
                weatherRepository.save(weatherEntity);
                time = currentNode.get("last_updated").asText();
            }
            System.out.println("FetchDataAndUpdateDB Successfully!");

            // Delete Data Before 1 Days
            System.out.println(StringUtil.handleTime(time));
            weatherRepository.deleteByTime(StringUtil.handleTime(time));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
