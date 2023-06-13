package ru.knastnt.weather.weatherparser.implementations.openweathermap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.weatherparser.RetryableWeatherParserService;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapParserTest extends WeatherApplicationTests {
    @Autowired
    private OpenWeatherMapParser openWeatherMapParser;
    @Test
    void retryTest() {
        try {
            WeatherDto weatherDto = openWeatherMapParser.parseForCity("комсомольск-на-амуре");
        } catch (Exception e) {
            System.out.println();
        }
        System.out.println();
    }
}