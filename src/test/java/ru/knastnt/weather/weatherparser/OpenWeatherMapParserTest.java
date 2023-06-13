package ru.knastnt.weather.weatherparser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

class OpenWeatherMapParserTest extends WeatherApplicationTests {
    @Autowired
    private RetryableWeatherParserService openWeatherMapParser;
    @Test
    void retryTest() {
        try {
            WeatherDto weatherDto = openWeatherMapParser.parseForCity("");
        } catch (Exception e) {
            System.out.println();
        }
        System.out.println();
    }
}