package com.example.spb_api.schedule;

import com.example.spb_api.entity.Weather;
import com.example.spb_api.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Component
public class WeatherSchedule {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private WeatherRepository weatherRepository;

    @Value("${app.api-url}")
    private String apiUrl;

    @Value("${app.api-key}")
    private String apiKey;

    @Scheduled(fixedRate = 100_000_000)
    public void fetDataAndUpdateDB() {
        try {
            // Fetch Data
            String url = String.format("%s?key=%s&q=Hanoi", apiUrl, apiKey);
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            JsonNode currentNode = jsonNode.get("current");

            // Handle Update DB
            Weather weather = Weather.builder()
                    .city("Hanoi")
                    .condition_weather(currentNode.get("condition").get("text").toString())
                    .temp(currentNode.get("temp_c").floatValue())
                    .wind_kph(currentNode.get("wind_kph").floatValue())
                    .feelslike_c(currentNode.get("feelslike_c").floatValue())
                    .pressure_in(currentNode.get("pressure_in").floatValue())
                    .humidity(currentNode.get("humidity").floatValue())
                    .heatindex_c(currentNode.get("heatindex_c").floatValue())
                    .dewpoint_c(currentNode.get("dewpoint_c").floatValue())
                    .precip_mm(currentNode.get("precip_mm").floatValue())
                    .gust_kph(currentNode.get("gust_kph").floatValue())
                    .is_day(currentNode.get("is_day").asInt())
                    .time(currentNode.get("last_updated").asText())
                    .build();
            weatherRepository.save(weather);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
