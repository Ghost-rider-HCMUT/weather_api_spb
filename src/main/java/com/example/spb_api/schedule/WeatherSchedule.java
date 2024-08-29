package com.example.spb_api.schedule;

import com.example.spb_api.entity.Weather;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class WeatherSchedule {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WeatherRepository weatherRepository;

    @Value("${app.api-url}")
    private String apiUrl;

    @Value("${app.api-key}")
    private String apiKey;

    @Scheduled(fixedRate = 100_000_000)
    public void fetDataAndUpdateDB() {
        String[] vietnamProvinces = {
                "Hanoi",
                "Ho Chi Minh City",
                "Da Nang",
                "Hai Phong",
                "Can Tho",
                "An Giang",
                "Ba Ria-Vung Tau",
                "Bac Giang",
                "Bac Kan",
                "Bac Lieu",
                "Bac Ninh",
                "Ben Tre",
                "Binh Dinh",
                "Binh Duong",
                "Binh Phuoc",
                "Binh Thuan",
                "Ca Mau",
                "Cao Bang",
                "Dak Lak",
                "Dak Nong",
                "Dien Bien",
                "Dong Nai",
                "Dong Thap",
                "Gia Lai",
                "Ha Giang",
                "Ha Nam",
                "Ha Tinh",
                "Hai Duong",
                "Hau Giang",
                "Hoa Binh",
                "Hung Yen",
                "Khanh Hoa",
                "Kien Giang",
                "Kon Tum",
                "Lai Chau",
                "Lam Dong",
                "Lang Son",
                "Lao Cai",
                "Long An",
                "Nam Dinh",
                "Vinh",
                "Ninh Binh",
                "Ninh Thuan",
                "Phu Tho",
                "Phu Yen",
                "Quang Binh",
                "Quang Nam",
                "Quang Ngai",
                "Quang Ninh",
                "Quang Tri",
                "Soc Trang",
                "Son La",
                "Tay Ninh",
                "Thai Binh",
                "Thai Nguyen",
                "Thanh Hoa",
                "Thua Thien-Hue",
                "Tien Giang",
                "Tra Vinh",
                "Tuyen Quang",
                "Vinh Long",
                "Vinh Phuc",
                "Yen Bai"
        };

        try {
            for (String province : vietnamProvinces) {
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
                Weather weather = Weather.builder()
                        .city(StringUtil.cleanString(province))
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
            }
            System.out.println("FetchDataAndUpdateDB Successfully!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
