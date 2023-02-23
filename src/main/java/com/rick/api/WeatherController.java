package com.rick.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick.util.WeatherAPI;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rick
 * @createdAt 2023-02-22 13:17:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("weather")
@CrossOrigin
public class WeatherController {


    private final WeatherAPI weatherAPI;

    private final Map<String, String> defaultWeather = new HashMap<>(4);

    {
        defaultWeather.put("temperature", "19");
        defaultWeather.put("info", "晴");
    }

    @GetMapping("suzhou")
    @ResponseBody
    public Map<String, String> getWeatherBySuZhou() {
        String json = weatherAPI.byCity("苏州");
        if (!StringUtils.hasText(json)) {
            return defaultWeather;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(json);
            JsonNode realtimeNode = node.get("msg").get("result").get("realtime");
            if (realtimeNode == null) {
                return defaultWeather;
            } else {
                HashMap<String, String> weatherInfo = new HashMap<>(4);
                weatherInfo.put("temperature", realtimeNode.get("temperature").asText());
                weatherInfo.put("info", realtimeNode.get("info").asText());
                return weatherInfo;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return defaultWeather;
    }
}
